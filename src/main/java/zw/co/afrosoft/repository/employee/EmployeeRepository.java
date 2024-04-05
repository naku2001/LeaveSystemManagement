package zw.co.afrosoft.repository.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.employee.EmployeeStatus;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    Page<Employee> findAll(Pageable pageable);

    List<Employee> findByUsername(String username);

    Optional<Employee> findEmployeeByUsername(String username);

    List<Employee> findAllByEmployeeStatusEquals(EmployeeStatus Active);


    boolean existsByUsername(String username);

//    List<Employee> findAll(EmployeeStatus active);

//    Optional<Employee>


//    Optional<Employee>findEmployeesByIdAnd(Long EmployeeId);
}
