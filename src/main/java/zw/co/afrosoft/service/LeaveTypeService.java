package zw.co.afrosoft.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.Leaves;

public interface LeaveTypeService {

    ResponseEntity setDefaultLeave(LeaveTypeRequest leaveTypeRequest);

    Page getAlLeaves(int offset,int size);


    ResponseEntity delete(Long id);
}
