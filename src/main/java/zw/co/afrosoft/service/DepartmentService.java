package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.department.DepartmentRequest;

public interface DepartmentService {



    ResponseEntity<Department> create(DepartmentRequest request);

    ResponseEntity update(DepartmentRequest request, Long id);

    ResponseEntity getById(Long id);

    ResponseEntity getAll();

    ResponseEntity delete(Long id);
}
