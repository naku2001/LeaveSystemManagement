package zw.co.afrosoft.service.headOfDepartment;

import org.springframework.http.ResponseEntity;

import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartment;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartmentRequest;

public interface HeadOfDepartmentService {

    ResponseEntity<HeadOfDepartment> create(Long departmentId ,Long employeeId);

    ResponseEntity<HeadOfDepartment> update(HeadOfDepartmentRequest request, Long id);

    ResponseEntity<HeadOfDepartment> getById(Long id);

    ResponseEntity<HeadOfDepartment> getAll();

    ResponseEntity<HeadOfDepartment> delete(Long id);
}
