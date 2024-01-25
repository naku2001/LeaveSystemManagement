package zw.co.afrosoft.repository.headOfDepartment;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartment;

import java.util.Optional;

public interface HeadOfDepartmentRepository extends JpaRepository<HeadOfDepartment, Long> {

    Optional<HeadOfDepartment> findHeadOfDepartmentByDepartment_Id(Long id);
}