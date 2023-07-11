package zw.co.afrosoft.service;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.exceptions.leave.LeaveException;
import zw.co.afrosoft.model.*;
import zw.co.afrosoft.model.calendar.CalendarInfo;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.leave.Leave;
import zw.co.afrosoft.model.leave.LeaveType;
import zw.co.afrosoft.model.leave.LeaveUpdate;
import zw.co.afrosoft.model.leave.Status;
import zw.co.afrosoft.repository.employee.EmployeeRepository;
import zw.co.afrosoft.repository.leave.LeaveRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveServiceImplementation implements LeaveService{
    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;
    private  final EmailService emailService;


    @Override
    public ResponseEntity applyLeave(LeaveRequest request) {
        Leave leave = new Leave();
        Status status = Status.PENDING;
        Optional<Employee> employee = employeeRepository.findById(request.getEmployeeId());
        leave.setReason(request.getReason());
        Map<LeaveType, Integer> leaveTypes = new HashMap<>();
        leaveTypes.put(LeaveType.Annual, employee.get().getAvailableAnnualLeave());
        leaveTypes.put(LeaveType.Maternity, employee.get().getAvailableMaternityLeave());
        Integer availableLeaveDays = leaveTypes.get(request.getLeaveType());
        if (availableLeaveDays == null || availableLeaveDays == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("You can't apply, you have no available " +
                            request.getLeaveType().toString() + " leave days left");
        }

        leave.setLeaveType(request.getLeaveType());
        if (request.getToDate().isBefore(request.getFromDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("To Date should be after From Date");
        }
        leave.setToDate(request.getToDate());
        if (request.getFromDate().isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                    body("Cannot apply for past date");}
        leave.setFromDate(request.getFromDate());
        int duration = 0;
        LocalDate currentDate = leave.getFromDate();
        while (!currentDate.isAfter(leave.getToDate())) {
            if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                duration++;
            }
            currentDate = currentDate.plusDays(1);
        }
        leave.setDuration(duration);
        leave.setEmployee(employee.get());
        leave.setStatus(status);
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

        emailService.sendEmail(emailContent,"perfect.makuwerere@studemnts",
                "LEAVE APPLICATION");

        return ResponseEntity.ok().body(leaveRepository.save(leave));

    }

    @Override
    public ResponseEntity approveLeave(Long id) {
        Optional<Leave> leave = leaveRepository.findById(id);
        Leave leaveApprove = leave.get();
        leaveApprove.setReason(leaveApprove.getReason());
        leaveApprove.setLeaveType(leaveApprove.getLeaveType());
        leaveApprove.setToDate(leaveApprove.getToDate());
        leaveApprove.setFromDate(leaveApprove.getFromDate());
        leaveApprove.setDuration(leaveApprove.getDuration());
        leaveApprove.setEmployee(leaveApprove.getEmployee());
        leaveApprove.setStatus(Status.APPROVED);
        String emailContent = "Congratulations! Your leave request for "
                    + leaveApprove.getLeaveType() + " from " + leaveApprove.getFromDate() + " to "
                    + leaveApprove.getToDate() + " has been approved.<br><br>"
                    + "Please make sure to plan your work accordingly and inform your " +
                    "team members about your absence.<br><br>"
                    + "If you have any questions or need further assistance, feel free to " +
                    "reach out to your manager or the HR department.<br><br>"
                    + "Best regards,<br>"
                    + "Afrosoft Holdings";
          emailService.sendEmail(emailContent,"j","ju");
          return ResponseEntity.ok().body(leaveRepository.save(leaveApprove));
    }

    @Override
    public ResponseEntity getAll() {
        List<Leave> leaveList = leaveRepository.findAll().stream().
                filter(leave ->leave.getStatus().
                equals(Status.PENDING)).collect(Collectors.toList());
        return ResponseEntity.ok().body(leaveList) ;
    }
    @Override
    public ResponseEntity getAllApproved() {
        List<Leave> leaveList = leaveRepository.findAll().stream().
                filter(leave ->leave.getStatus().
                        equals(Status.APPROVED)).
                collect(Collectors.toList());
        return ResponseEntity.ok().body(leaveList) ;

    }
    @Override
    public ResponseEntity getAllRejected() {
        List<Leave> leaveList = leaveRepository.findAll().stream()
                .filter(leave ->leave.getStatus().
                        equals(Status.REJECTED)).collect(Collectors.toList());;
        return ResponseEntity.ok().body(leaveList);

    }


    @Override
    public ResponseEntity rejectLeave(Long id) {
        Optional<Leave> leave = leaveRepository.findById(id);
        Leave leaveReject = leave.get();
        leaveReject.setReason(leaveReject.getReason());
        leaveReject.setLeaveType(leaveReject.getLeaveType());
        leaveReject.setToDate(leaveReject.getToDate());
        leaveReject.setFromDate(leaveReject.getFromDate());
        leaveReject.setDuration(leaveReject.getDuration());
        leaveReject.setEmployee(leaveReject.getEmployee());
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
            emailService.sendEmail(emailContent,"pee","leave");
        return ResponseEntity.ok().body(leaveRepository.save(leaveReject));
    }

    @Override
    public ResponseEntity getAllLeaves() {
        List<Leave> leaveList = leaveRepository.findAll();
        return ResponseEntity.ok().body(leaveList);
    }

    @Override
    public ResponseEntity totalRejected() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.REJECTED);
        Long count = leaveList.stream().count();
        return  ResponseEntity.ok().body(count);
    }

    @Override
    public ResponseEntity totalPending() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.PENDING);
        Long count = leaveList.stream().count();
        return  ResponseEntity.ok().body(count);
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
    public ResponseEntity calenda() {
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
        return ResponseEntity.ok().body(calendarInfos);
    }
    @Override
    public ResponseEntity myleaves(Long id) {
        List<Leave> leaveList = leaveRepository.findAllByEmployeeId(id);
        return ResponseEntity.ok().body(leaveList);
    }

    @Override
    public ResponseEntity getRemainingLeaveDays(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
        TotalLeaveDaysLeft totalLeaveDaysLeft = new TotalLeaveDaysLeft();
//        totalLeaveDaysLeft.setAvailableSickLeave(employee.get().getAvailableSickLeave());
//        totalLeaveDaysLeft.setAvailableUnpaidLeave(employee.get().getAvailableUnpaidLeave());
//           totalLeaveDaysLeft.setAvailableVacationLeave(employee.get().getAvailableVacationLeave());
            return ResponseEntity.ok().body(totalLeaveDaysLeft);

        }
        return ResponseEntity.ok().body("employee not found");
    }

    @Override
    public ResponseEntity cancelLeave(Long id, Long employeeId) {
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
        return ResponseEntity.ok().body("Employee or Leave not found");
    }

    @Override
    public ResponseEntity updateLeave(Long leaveId, Long employeeId, LeaveUpdate leaveRequestUpdate){
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
                    Period period =Period.between(leaveRequestUpdate.
                            getFromDate(),leaveRequestUpdate.getToDate());
                    leaveUpdate.setDuration(period.getDays()+ 1);
                    leaveRepository.save(leaveUpdate);
                    return ResponseEntity.ok().body(leaveUpdate);
                }
            }
        }
        throw new LeaveException("Employee not found");
    }


}
