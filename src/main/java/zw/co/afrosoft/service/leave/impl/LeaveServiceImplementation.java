package zw.co.afrosoft.service.leave.impl;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.exceptions.leave.LeaveException;
import zw.co.afrosoft.model.calendar.CalendarInfo;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartment;
import zw.co.afrosoft.model.leave.Leave;
import zw.co.afrosoft.model.leave.LeaveType;
import zw.co.afrosoft.model.leave.LeaveUpdate;
import zw.co.afrosoft.model.leave.Status;
import zw.co.afrosoft.repository.employee.EmployeeRepository;
import zw.co.afrosoft.repository.headOfDepartment.HeadOfDepartmentRepository;
import zw.co.afrosoft.repository.leave.LeaveRepository;
import zw.co.afrosoft.service.email.EmailService;
import zw.co.afrosoft.service.leave.LeaveRequest;
import zw.co.afrosoft.service.leave.LeaveService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveServiceImplementation implements LeaveService {
    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;
    private  final EmailService emailService;
    private final HeadOfDepartmentRepository headOfDepartmentRepository;

    @Override
    public ResponseEntity<Leave> applyLeave(LeaveRequest request) {
        Leave leave = new Leave();
        Optional<Employee> employee = employeeRepository.findById(request.getEmployeeId());
        if(!employee.isPresent())
            throw new LeaveException("Employee not found");
        Map<LeaveType, Integer> leaveTypes = new HashMap<>();
        leaveTypes.put(LeaveType.Annual, employee.get().getAvailableAnnualLeave());
        leaveTypes.put(LeaveType.Special, employee.get().getAvailableSpecialLeave());
        leaveTypes.put(LeaveType.Unpaid,employee.get().getAvailableUnpaidLeave());
        Integer availableLeaveDays = leaveTypes.get(request.getLeaveType());

        int duration = 0;
        LocalDate currentDate = request.getFromDate();
        while (!currentDate.isAfter(request.getToDate())) {
            if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                duration++;
            }
            currentDate = currentDate.plusDays(1);
        }
        if(request.getLeaveType().equals(LeaveType.Annual) ||request.getLeaveType().equals(LeaveType.Maternity)
        ){
            if (availableLeaveDays < duration || availableLeaveDays == 0) {
                throw new LeaveException("You have no enough available leave days");
            }
        }


        if (request.getToDate().isBefore(request.getFromDate())) {
            throw new LeaveException("To Date should be after From Date");
        }

        if (request.getFromDate().isBefore(LocalDate.now())) {
            throw new LeaveException("Cannot apply for a past date");
        }


        leave.setToDate(request.getToDate());
        leave.setLeaveType(request.getLeaveType());
        leave.setFromDate(request.getFromDate());
        leave.setDuration(duration);
        leave.setEmployee(employee.get());
        leave.setStatus(Status.PENDING);
        leave.setReason(request.getReason());
        Long departmentId= employee.get().getDepartment().getId();
        Optional<HeadOfDepartment> head = headOfDepartmentRepository.
                findHeadOfDepartmentByDepartment_Id(departmentId);
        String receiver = head.get().getEmployee().getEmail();
        String emailContent =
                "This is to inform you that  " + employee.get().getFirstName() +
                        "  " + employee.get().getLastName() + " has applied for " +
                        leave.getLeaveType() + " from "
                        + leave.getFromDate() + " to " + leave.getToDate()+
                        ". Please take the necessary actions and approve/reject" +
                        " the leave request.<br><br>"
                        + "Thank You.<br>"
                        + "Best Regards, <br><br>"
                        + employee.get().getLastName().toUpperCase();
        emailService.sendEmail(emailContent,receiver, "LEAVE APPLICATION");
        return ResponseEntity.ok().body(leaveRepository.save(leave));
    }

    @Override
    public ResponseEntity<Leave> approveLeave(Long id) {
        Optional<Leave> leave = leaveRepository.findById(id);
        if (!leave.isPresent()) {
            // Leave not found with the given id
            return ResponseEntity.notFound().build();
        }

        Leave leaveApprove = leave.get();
        Employee employee = leaveApprove.getEmployee();
        LeaveType leaveType = leaveApprove.getLeaveType();
        int requestedDuration = leaveApprove.getDuration();

        // Get the employee's available leave days for the requested leave type
        int availableLeaveDays = 0;
        switch (leaveType) {
            case Annual:
                availableLeaveDays = employee.getAvailableAnnualLeave();
                break;
            case Maternity:
//                availableLeaveDays = employee.getAvailableMaternityLeave();
                break;
            case Unpaid:
                availableLeaveDays = employee.getAvailableUnpaidLeave();
                break;
            case Special:
                availableLeaveDays = employee.getAvailableSpecialLeave();
                break;
            // Add more cases for other leave types if needed
        }

        // Check if the employee has enough available leave days
        if (requestedDuration <= availableLeaveDays) {
            // Sufficient available leave days, approve the leave
            int remaining = availableLeaveDays - requestedDuration;
            leaveApprove.setStatus(Status.APPROVED);

            // Update the employee's available leave days based on the leave type
            switch (leaveType) {
                case Annual:
                    employee.setAvailableAnnualLeave(remaining);
                    break;
                case Maternity:
//                    employee.setAvailableMaternityLeave(remaining);
                    break;
                case Unpaid:
                    employee.setAvailableUnpaidLeave(remaining);
                    break;
                case Special:
                    employee.setAvailableSpecialLeave(remaining);
                    break;
                // Add more cases for other leave types if needed
            }

            // Save the updated employee and leave details
            employeeRepository.save(employee);
            leaveRepository.save(leaveApprove);

            // Send approval email
            String receiver = employee.getEmail();
            String emailContent = "Congratulations! Your leave request for "
                    + leaveApprove.getLeaveType() + " from " + leaveApprove.getFromDate() + " to "
                    + leaveApprove.getToDate() + " has been approved.<br><br>"
                    + "Please make sure to plan your work accordingly and inform your " +
                    "team members about your absence.<br><br>"
                    + "If you have any questions or need further assistance, feel free to " +
                    "reach out to your manager or the HR department.<br><br>"
                    + "Best regards,<br>"
                    + "Afrosoft Holdings";
            emailService.sendEmail(emailContent, receiver, "Leave Approval");

            // Return the approved leave
            return ResponseEntity.ok().body(leaveApprove);
        } else {
            // Insufficient available leave days, reject the leave
            leaveApprove.setStatus(Status.REJECTED);
            leaveRepository.save(leaveApprove);
            // Return the rejected leave
            return ResponseEntity.badRequest().body(leaveApprove);
        }
    }

    @Override
    public List<Leave> getAll() {
        List<Leave> leaveList = leaveRepository.findAll().stream().
                filter(leave ->leave.getStatus().
                equals(Status.PENDING)).collect(Collectors.toList());
        return leaveList ;
    }
    @Override
    public List<Leave> getAllApproved() {
        List<Leave> leaveList = leaveRepository.findAll().stream().
                filter(leave ->leave.getStatus().
                        equals(Status.APPROVED)).
                collect(Collectors.toList());
        return  leaveList ;

    }
    @Override
    public List<Leave> getAllRejected() {
        List<Leave> leaveList = leaveRepository.findAll().stream()
                .filter(leave ->leave.getStatus().
                        equals(Status.REJECTED)).collect(Collectors.toList());;
        return leaveList;

    }


    @Override
    public ResponseEntity<Leave> rejectLeave(Long id) {
        Optional<Leave> leave = leaveRepository.findById(id);
        if (!leave.isPresent())
            throw new LeaveException("Leave not found");
        Leave leaveReject = leave.get();
        leaveReject.setStatus(Status.REJECTED);
        String emailContent =  " Your leave request for " + leaveReject.getLeaveType()
                + " from " + leaveReject.getFromDate() + " to " + leaveReject.getToDate() +
                " has been rejected.<br><br>" + "If you have any questions or need" +
                " further assistance, " +
                "feel free to reach out to " +
                "your manager or the HR department.<br><br>"
                    + "Best regards,<br>"
                    + "Afrosoft Holdings";
            Map model = new HashMap();
            model.put("user", leaveReject.getEmployee().getFirstName() + " " +
                    leaveReject.getEmployee().getFirstName());
            model.put("link", "http://localhost:4200/");
            model.put("message",emailContent);
            model.put("year", "2023");
            emailService.sendEmail(emailContent,leaveReject.getEmployee().getEmail() ,"Leave Rejection");
        return ResponseEntity.ok().body(leaveRepository.save(leaveReject));
    }

    @Override
    public List<Leave> getAllLeaves() {
        List<Leave> leaveList = leaveRepository.findAll();
        return leaveList;
    }

    @Override
    public Long totalRejected() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.REJECTED);
        Long count = leaveList.stream().count();
        return  count;
    }

    @Override
    public Long totalPending() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.PENDING);
        Long count = leaveList.stream().count();
        return count;
    }

    @Override
    public ResponseEntity totalApproved() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.APPROVED);
        Long count =  leaveList.stream().count();
        return  ResponseEntity.ok().body(count);
    }
    @Override
    public ResponseEntity leaveByStatus(Status status) {
        List<Leave> leaveList = leaveRepository.findAllByStatus(status);
        return  ResponseEntity.ok().body(leaveList);
    }
    @Override
    public ResponseEntity totalLeaves() {
        List<Leave> leaveList = leaveRepository.findAll();
        Long  count = leaveList.stream().count();
       return ResponseEntity.ok().body(count);
    }
    @Override
    public List<CalendarInfo> calenda() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.APPROVED);
        List<CalendarInfo> calendarInfos = leaveList.stream()
                .map(leave -> {
                    CalendarInfo calendarInfo = new CalendarInfo();
                    calendarInfo.setStart(leave.getFromDate().toString());
                    calendarInfo.setEnd(leave.getToDate().toString());
                    calendarInfo.setLeaveType(leave.getLeaveType().toString());
                    calendarInfo.setTitle(leave.getEmployee().getFirstName() + " " +
                            leave.getEmployee().getLastName());
                    return calendarInfo;
                })
                .collect(Collectors.toList());
        return calendarInfos;
    }
    @Override
    public ResponseEntity<List<Leave>> myleaves(Long id) {
        List<Leave> leaveList = leaveRepository.findAllByEmployeeId(id);
        return ResponseEntity.ok().body(leaveList);
    }

    @Override
    public Map getRemainingLeaveDays(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
            throw new LeaveException("Employee not found");
        Map<LeaveType, Integer> leaveTypes = new HashMap<>();
        leaveTypes.put(LeaveType.Annual, employee.get().getAvailableAnnualLeave());
        leaveTypes.put(LeaveType.Unpaid, employee.get().getAvailableUnpaidLeave());
        leaveTypes.put(LeaveType.Special,employee.get().getAvailableSpecialLeave());
        return leaveTypes;

    }

    @Override
    public ResponseEntity<Leave> cancelLeave(Long id, Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        Optional<Leave> leave = leaveRepository.findById(id);
        if(employee.isPresent()){
            if(leave.isPresent() & leave.get().getStatus().equals(Status.PENDING)){
                if(leave.get().getEmployee().getId() == employeeId){
                    leaveRepository.delete(leave.get());
                    return ResponseEntity.ok().body(leave.get());
                }
            }
        }
        throw new LeaveException("Employee not found");
    }

    @Override
    public ResponseEntity<Leave> updateLeave(Long leaveId, Long employeeId, LeaveUpdate leaveRequestUpdate){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        Optional<Leave> leave = leaveRepository.findById(leaveId);
        if(employee.isPresent()){
            if(leave.isPresent() & leave.get().getStatus().equals(Status.PENDING)){
                if(leave.get().getEmployee().getId() == employeeId){
                    Leave leaveUpdate = leave.get();
                    leaveUpdate.setLeaveType(leaveRequestUpdate.getLeaveType());
                    leaveUpdate.setReason(leaveRequestUpdate.getReason());
                    leaveUpdate.setFromDate(leaveRequestUpdate.getFromDate());
                    leaveUpdate.setToDate(leaveRequestUpdate.getToDate());
                    leaveUpdate.setLeaveType(leaveRequestUpdate.getLeaveType());
                    int duration = 0;
                    LocalDate currentDate = leave.get().getFromDate();
                    while (!currentDate.isAfter(leave.get().getToDate())) {
                        if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
                                currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                            duration++;
                        }
                        currentDate = currentDate.plusDays(1);
                    }
                    leaveUpdate.setDuration(duration);
                    leaveRepository.save(leaveUpdate);
                    return ResponseEntity.ok().body(leaveUpdate);
                }
            }
        }
        throw new LeaveException("Employee not found");
    }




}
