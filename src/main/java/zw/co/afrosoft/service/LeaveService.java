package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;

public interface LeaveService {

    public ResponseEntity applyLeave(LeaveRequest request);


    ResponseEntity getAll();
}
