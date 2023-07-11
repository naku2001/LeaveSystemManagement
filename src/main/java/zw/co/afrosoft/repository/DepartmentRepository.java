package zw.co.afrosoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.Department;
import zw.co.afrosoft.model.Employee;

public interface DepartmentRepository extends JpaRepository<Department,Long> {



}
