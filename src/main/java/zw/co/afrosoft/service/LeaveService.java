package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface LeaveService {

    public ResponseEntity applyLeave(LeaveRequest request);

    public ResponseEntity approveLeave(Long id);


    ResponseEntity getAll();

    ResponseEntity getAllApproved();

    ResponseEntity getAllRejected();

    ResponseEntity rejectLeave(Long id);

    ResponseEntity getAllLeaves();
}
