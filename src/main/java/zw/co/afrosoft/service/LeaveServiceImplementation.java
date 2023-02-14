package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.*;
import zw.co.afrosoft.repository.EmployeeRepository;
import zw.co.afrosoft.repository.LeaveRepository;

import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class LeaveServiceImplementation implements LeaveService{
    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;

    public LeaveServiceImplementation(EmployeeRepository employeeRepository, LeaveRepository leaveRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
    }
    @Override
    public ResponseEntity applyLeave(LeaveRequest request) {

        Leave leave = new Leave();
        Status status = Status.PENDING;
        Optional<Employee> employee = employeeRepository.findById(request.getEmployeeId());

        leave.setReason(request.getReason());

        leave.setToDate(request.getToDate());
        leave.setFromDate(request.getFromDate());
        Period period =Period.between(request.getFromDate(),request.getToDate());
        leave.setDuration(period.getDays() + 1);
        leave.setEmployee(employee.get());

        leave.setStatus(status);

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

        return ResponseEntity.ok().body(leaveRepository.save(leave1));
    }

    @Override
    public ResponseEntity getAllLeaves() {
        List<Leave> leaveList = leaveRepository.findAll();
        return ResponseEntity.ok().body(leaveList);
    }


}
