package zw.co.afrosoft.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import zw.co.afrosoft.model.*;
import zw.co.afrosoft.repository.EmployeeRepository;
import zw.co.afrosoft.repository.LeaveRepository;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class LeaveServiceImplementation implements LeaveService{
    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;

    public LeaveServiceImplementation(EmployeeRepository employeeRepository, LeaveRepository leaveRepository, JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
    }
    @Override
    public ResponseEntity applyLeave(LeaveRequest request) {

        Leave leave = new Leave();
        Status status = Status.PENDING;
        Optional<Employee> employee = employeeRepository.findById(request.getEmployeeId());



        leave.setReason(request.getReason());
        if(request.getLeaveType().equals(LeaveType.SICK_LEAVE)){
            if(employee.get().getAvailableSickLeave()==0 )
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You cant apply you have no available sick leave days left");
        } else if (request.getLeaveType().equals(LeaveType.VACATION)){
            if (employee.get().getAvailableVacationLeave() == 0 )
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You cant apply you have no available vacation leave days left");
        }
        else {
            if (employee.get().getAvailableUnpaidLeave()==0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("You cant apply you have no available un paid leave days left");
        }
        leave.setLeaveType(request.getLeaveType());
        if(request.getToDate().isBefore(request.getFromDate()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("To Date Should Be After From Date");
        leave.setToDate(request.getToDate());
        if(request.getFromDate().isBefore(LocalDate.now()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot Apply For Past Date");
        leave.setFromDate(request.getFromDate());
        Period period =Period.between(request.getFromDate(),request.getToDate());
        leave.setDuration(period.getDays() + 1);
        leave.setEmployee(employee.get());
        leave.setStatus(status);
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String sender = "perfect.makuwerere@students.uz.ac.zw";
            SimpleMailMessage mail = new SimpleMailMessage();
            String emailContent =
                     "This is to inform you that  " + employee.get().getFirstName() +
                    "  " + employee.get().getLastName() + " has applied for " + leave.getLeaveType() + " from "
                    + leave.getFromDate() + " to " + leave.getToDate()+
                    ". Please take the necessary actions and approve/reject the leave request.<br><br>"
                    + "Thank You.<br>"
                    + "Best Regards, <br><br>"
                    + employee.get().getLastName().toUpperCase();
            Map model = new HashMap();
            model.put("user", "Manager");
            model.put("link", "http://localhost:4200//leave/getPendingLeaves");
            model.put("message",emailContent);
            model.put("year", "2023");
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mail.setFrom(sender);
            mail.setTo("perfect.makuwerere@students.uz.ac.zw");
            mail.setSentDate(new Date());
            mail.setSubject("LEAVE APPLICATION");
            Template t = freemarkerConfig.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo("perfect.makuwerere@students.uz.ac.zw");
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            javaMailSender.send(message);

        } catch (MailException e) {

        } catch (Exception e) {

        }
        return ResponseEntity.ok().body(leaveRepository.save(leave));

    }

    @Override
    public ResponseEntity approveLeave(Long id) {

        Optional<Leave> leave = leaveRepository.findById(id);
        Leave leave1 = leave.get();
        if (leave1.getLeaveType().equals(LeaveType.SICK_LEAVE)){
           int days = leave1.getEmployee().getAvailableSickLeave()-leave1.getDuration();
            leave1.getEmployee().setAvailableSickLeave(days);
        } else if (leave1.getLeaveType().equals(LeaveType.UNPAID_LEAVE)) {
            int avaidays = leave1.getEmployee().getAvailableUnpaidLeave()-leave1.getDuration();
            leave1.getEmployee().setAvailableUnpaidLeave(avaidays);
        }
        else {
            int available = leave1.getEmployee().getAvailableVacationLeave()-leave1.getDuration();
            leave1.getEmployee().setAvailableVacationLeave(available);
        }
        leave1.setReason(leave1.getReason());
        leave1.setLeaveType(leave1.getLeaveType());
        leave1.setToDate(leave1.getToDate());
        leave1.setFromDate(leave1.getFromDate());
        leave1.setDuration(leave1.getDuration());
        leave1.setEmployee(leave1.getEmployee());
        leave1.setStatus(Status.APPROVED);
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String sender = "perfect.makuwerere@students.uz.ac.zw";
            SimpleMailMessage mail = new SimpleMailMessage();
            String emailContent = "Congratulations! Your leave request for " + leave1.getLeaveType() + " from " + leave1.getFromDate() + " to " + leave1.getToDate() + " has been approved.<br><br>"
                    + "Please make sure to plan your work accordingly and inform your team members about your absence.<br><br>"
                    + "If you have any questions or need further assistance, feel free to reach out to your manager or the HR department.<br><br>"
                    + "Best regards,<br>"
                    + "Afrosoft Holdings";
            Map model = new HashMap();
            model.put("user", leave1.getEmployee().getFirstName() + " " +  leave1.getEmployee().getFirstName());
            model.put("link", "http://localhost:4200/");
            model.put("message",emailContent);
            model.put("year", "2023");
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mail.setFrom(sender);
            mail.setTo(leave1.getEmployee().getEmail());
            mail.setSentDate(new Date());
            mail.setSubject("LEAVE APPLICATION");
            Template t = freemarkerConfig.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(leave1.getEmployee().getEmail());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            javaMailSender.send(message);

        } catch (MailException e) {

        } catch (Exception e) {

        }



        return ResponseEntity.ok().body(leaveRepository.save(leave1));
    }

    @Override
    public ResponseEntity getAll() {
        List<Leave> leaveList = leaveRepository.findAll();
        List<Leave> filteredLeave = leaveList.stream()
                .filter(leave ->leave.getStatus().
                        equals(Status.PENDING)).collect(Collectors.toList());
        return ResponseEntity.ok().body(filteredLeave) ;
    }

    @Override
    public ResponseEntity getAllApproved() {
        List<Leave> leaveList = leaveRepository.findAll();
        List<Leave> filteredLeave = leaveList.stream()
                .filter(leave ->leave.getStatus().
                        equals(Status.APPROVED)).collect(Collectors.toList());
        return ResponseEntity.ok().body(filteredLeave) ;

    }
    @Override
    public ResponseEntity getAllRejected() {
        List<Leave> leaveList = leaveRepository.findAll();
        List<Leave> filteredLeave = leaveList.stream()
                .filter(leave ->leave.getStatus().
                        equals(Status.REJECTED)).collect(Collectors.toList());
        return ResponseEntity.ok().body(filteredLeave) ;

    }


    @Override
    public ResponseEntity rejectLeave(Long id) {

        Optional<Leave> leave = leaveRepository.findById(id);
        Leave leave1 = leave.get();
        leave1.setReason(leave1.getReason());
        leave1.setLeaveType(leave1.getLeaveType());
        leave1.setToDate(leave1.getToDate());
        leave1.setFromDate(leave1.getFromDate());
        leave1.setDuration(leave1.getDuration());
        leave1.setEmployee(leave1.getEmployee());
        leave1.setStatus(Status.REJECTED);
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String sender = "perfect.makuwerere@students.uz.ac.zw";
            SimpleMailMessage mail = new SimpleMailMessage();
            String emailContent =  " Your leave request for " + leave1.getLeaveType() + " from " + leave1.getFromDate() + " to " + leave1.getToDate() + " has been rejected.<br><br>"

                    + "If you have any questions or need further assistance, feel free to reach out to your manager or the HR department.<br><br>"
                    + "Best regards,<br>"
                    + "Afrosoft Holdings";
            Map model = new HashMap();
            model.put("user", leave1.getEmployee().getFirstName() + " " +  leave1.getEmployee().getFirstName());
            model.put("link", "http://localhost:4200/");
            model.put("message",emailContent);
            model.put("year", "2023");
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mail.setFrom(sender);
            mail.setTo(leave1.getEmployee().getEmail());
            mail.setSentDate(new Date());
            mail.setSubject("LEAVE APPLICATION");
            Template t = freemarkerConfig.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(leave1.getEmployee().getEmail());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            javaMailSender.send(message);

        } catch (MailException e) {

        } catch (Exception e) {

        }

        return ResponseEntity.ok().body(leaveRepository.save(leave1));
    }

    @Override
    public ResponseEntity getAllLeaves() {
        List<Leave> leaveList = leaveRepository.findAll();
        return ResponseEntity.ok().body(leaveList);
    }

    @Override
    public ResponseEntity totalRejected() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.REJECTED);
        DashboardTotal dashboardTotal = DashboardTotal.builder()
                .total(leaveList.size()).build();
        return  ResponseEntity.ok().body(dashboardTotal);
    }

    @Override
    public ResponseEntity totalPending() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.PENDING);
        DashboardTotal dashboardTotal = DashboardTotal.builder()
                .total(leaveList.size()).build();
        return  ResponseEntity.ok().body(dashboardTotal);
    }

    @Override
    public ResponseEntity totalApproved() {
        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.APPROVED);
        DashboardTotal dashboardTotal = DashboardTotal.builder()
                .total(leaveList.size()).build();
        return  ResponseEntity.ok().body(dashboardTotal);
    }
    @Override
    public ResponseEntity leaveByStatus(Status status) {
        List<Leave> leaveList = leaveRepository.findAllByStatus(status);

        return  ResponseEntity.ok().body(leaveList);
    }

    @Override
    public ResponseEntity totalLeaves() {
        List<Leave> leaveList = leaveRepository.findAll();
        DashboardTotal dashboardTotal = DashboardTotal.builder()
                .total(leaveList.size()).build();

        return ResponseEntity.ok().body(dashboardTotal);
    }

    @Override
    public ResponseEntity calenda() {

        List<Leave> leaveList = leaveRepository.findAllByStatus(Status.APPROVED);
        List<CalendarInfo> calendarInfos =new ArrayList<>();
        for(Leave leave : leaveList){
            CalendarInfo calendarInfo = new CalendarInfo();

            calendarInfo.setStart(leave.getFromDate().toString());
            calendarInfo.setEnd(leave.getToDate().toString());
            calendarInfo.setLeaveType(leave.getLeaveType().toString());
            calendarInfo.setTitle(leave.getEmployee().getFirstName()+ " " +
                    leave.getEmployee().getLastName());
            calendarInfos.add(calendarInfo);
        }

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
            totalLeaveDaysLeft.setAvailableSickLeave(employee.get().getAvailableSickLeave());
            totalLeaveDaysLeft.setAvailableUnpaidLeave(employee.get().getAvailableUnpaidLeave());
            totalLeaveDaysLeft.setAvailableVacationLeave(employee.get().getAvailableVacationLeave());
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
    public ResponseEntity updateLeave(Long leaveId, Long employeeId,LeaveUpdate leaveRequestUpdate) {
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
                    Period period =Period.between(leaveRequestUpdate.getFromDate(),leaveRequestUpdate.getToDate());
                    leaveUpdate.setDuration(period.getDays()+ 1);
                    leaveRepository.save(leaveUpdate);
                    return ResponseEntity.ok().body(leaveUpdate);
                }
            }
        }

        return ResponseEntity.ok().body("Employee or Leave not found");
    }


}
