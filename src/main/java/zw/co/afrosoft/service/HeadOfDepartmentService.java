package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;

import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartment;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartmentRequest;

public interface HeadOfDepartmentService {

    ResponseEntity<HeadOfDepartment> create(Long departmentId ,Long employeeId);

    ResponseEntity update(HeadOfDepartmentRequest request, Long id);

    ResponseEntity getById(Long id);

    ResponseEntity getAll();

    ResponseEntity delete(Long id);
}
