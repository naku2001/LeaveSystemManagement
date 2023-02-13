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
        leave.setLeaveType(request.getLeaveType());
        leave.setToDate(request.getToDate());
        leave.setFromDate(request.getFromDate());
        Period period =Period.between(request.getFromDate(),request.getToDate());
        leave.setDuration(period.getDays() + 1);
        leave.setEmployee(employee.get());

        leave.setStatus(status);

        return ResponseEntity.ok().body(leaveRepository.save(leave));
    }

    @Override
    public ResponseEntity getAll() {
        List<Leave> leaveList = leaveRepository.findAll();
        List<Leave> filteredLeave = leaveList.stream()
                .filter(leave ->leave.getStatus().
                        equals(Status.PENDING)).collect(Collectors.toList());
        return ResponseEntity.ok().body(filteredLeave) ;
    }


}
