package zw.co.afrosoft.repository.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.employee.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    Page<Employee> findAll(Pageable pageable);

    List<Employee> findByUsername(String username);

    Optional<Employee> findEmployeeByUsername(String username);

//    Optional<Employee>


//    Optional<Employee>findEmployeesByIdAnd(Long EmployeeId);
}
