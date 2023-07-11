package zw.co.afrosoft.service;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.Department;
import zw.co.afrosoft.model.DepartmentRequest;
import zw.co.afrosoft.model.Departments;
import zw.co.afrosoft.security.dto.EmployeeRequest;

public interface DepartmentService {



    ResponseEntity<Department> create(DepartmentRequest request);

    ResponseEntity update(DepartmentRequest request, Long id);

    ResponseEntity getById(Long id);

    ResponseEntity getAll();

    ResponseEntity delete(Long id);
}
