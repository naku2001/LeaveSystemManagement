package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
}
