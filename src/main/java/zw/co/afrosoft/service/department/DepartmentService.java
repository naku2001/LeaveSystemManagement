package zw.co.afrosoft.service.department;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.department.DepartmentRequest;

import java.util.List;

public interface DepartmentService {
    ResponseEntity<Department> create(DepartmentRequest request);

    ResponseEntity<Department> update(DepartmentRequest request, Long id);

    ResponseEntity<Department> getById(Long id);

    List<Department> getAll();

    ResponseEntity<Department> delete(Long id);
}
