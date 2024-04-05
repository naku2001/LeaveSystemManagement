package zw.co.afrosoft.repository.department;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.department.DepartmentStatus;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

List<Department> findAllByDepartmentStatusEquals(DepartmentStatus Active);

}
