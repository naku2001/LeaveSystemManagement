package zw.co.afrosoft.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.Employee;
import zw.co.afrosoft.model.EmployeeLeave;
import zw.co.afrosoft.model.Leaves;
import zw.co.afrosoft.model.Status;
import zw.co.afrosoft.repository.EmployeeLeaveRepository;
import zw.co.afrosoft.repository.EmployeeRepository;
import zw.co.afrosoft.repository.LeavesRepository;

import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveServiceImplementation implements LeaveService{


    private final EmployeeLeaveRepository employeeLeaveRepository;
    private final EmployeeRepository employeeRepository;
    private final LeavesRepository leavesRepository;

    public LeaveServiceImplementation(EmployeeLeaveRepository employeeLeaveRepository,
                                      EmployeeRepository employeeRepository,
                                      LeavesRepository leavesRepository) {
        this.employeeLeaveRepository = employeeLeaveRepository;
        this.employeeRepository = employeeRepository;
        this.leavesRepository = leavesRepository;
    }

    @Override
    public ResponseEntity applyLeave(LeaveRequest leaveRequest,Long id) {
        EmployeeLeave employeeLeave = new EmployeeLeave();
        Status status = Status.PENDING;
        Optional<Leaves>leaves = leavesRepository.findById(leaveRequest.getLeaveTypeId());
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!leaves.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("leave Type not available");

        employeeLeave.setReason(leaveRequest.getReason());
        employeeLeave.setToDate(leaveRequest.getToDate());
        employeeLeave.setFromDate(leaveRequest.getFromDate());
        Period period =Period.between(leaveRequest.getFromDate(),leaveRequest.getToDate());
        employeeLeave.setDuration(period.getDays());
//        employeeLeave.setEmployee(employee.get().getId().);
        employeeLeave.setStatus(status);
        employeeLeave.setLeaves(leaves.get());
        employeeLeave.setEmployee(employee.get());
        return ResponseEntity.ok().body(employeeLeaveRepository.save(employeeLeave));
    }
    @Override
    public Page getAllAppliedLeaves(int offset, int size) {
        return employeeLeaveRepository.findAll(PageRequest.of(offset, size));
    }
    @Override
    public ResponseEntity approveLeave(Long id) {
        Status status = Status.APPROVED;
        Optional<EmployeeLeave> employeeLeave = employeeLeaveRepository.findById(id);

        if (!employeeLeave.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not available");
        EmployeeLeave approvedLeave =employeeLeave.get();
        approvedLeave.setReason(approvedLeave.getReason());
        int availableDays = approvedLeave.getLeaves().getDefaultDays() - approvedLeave.getDuration();
        approvedLeave.setToDate(approvedLeave.getToDate());
        approvedLeave.setFromDate(approvedLeave.getFromDate());
        approvedLeave.setDuration(approvedLeave.getDuration());
        approvedLeave.setStatus(status);

        employeeRepository.save(approvedLeave.getEmployee());

        return ResponseEntity.ok().body(employeeLeaveRepository.save(approvedLeave));

    }

}
