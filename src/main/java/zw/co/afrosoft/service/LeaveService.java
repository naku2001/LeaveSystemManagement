package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.LeaveUpdate;
import zw.co.afrosoft.model.Status;

public interface LeaveService {

    public ResponseEntity applyLeave(LeaveRequest request);

    public ResponseEntity approveLeave(Long id);


    ResponseEntity getAll();

    ResponseEntity getAllApproved();

    ResponseEntity getAllRejected();

    ResponseEntity rejectLeave(Long id);

    ResponseEntity getAllLeaves();

    ResponseEntity totalRejected();

    ResponseEntity totalPending();

    ResponseEntity totalApproved();

    ResponseEntity leaveByStatus(Status status);


    ResponseEntity totalLeaves();

    ResponseEntity calenda();

    ResponseEntity myleaves(Long id);

    ResponseEntity getRemainingLeaveDays(Long id);

    ResponseEntity cancelLeave(Long id, Long employeeId);

    ResponseEntity updateLeave(Long leaveId, Long employeeId, LeaveUpdate update);
}
