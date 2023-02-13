package zw.co.afrosoft.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.Employee;


public interface EmployeeService {
    Employee updateEmployee(Long id, EmployeeRequest request);

    ResponseEntity getEmployee(Long id);

    ResponseEntity deleteEmployee(Long id);

    ResponseEntity createEmployee(EmployeeRequest request);

   public Page<Employee> getAll(Pageable pageable);

   public Page getAll(int offset,int size);


}
