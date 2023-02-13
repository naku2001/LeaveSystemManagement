package zw.co.afrosoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.Employee;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee,Long> {


    Page<Employee> findAll(Pageable pageable);

//    Optional<Employee>


//    Optional<Employee>findEmployeesByIdAnd(Long EmployeeId);
}
