package zw.co.afrosoft.service;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface LeaveService {


    public ResponseEntity  applyLeave(LeaveRequest request,Long id);


    Page getAllAppliedLeaves(int offset, int size);

    ResponseEntity approveLeave(Long id);
}
