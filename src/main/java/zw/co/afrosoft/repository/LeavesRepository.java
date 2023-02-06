package zw.co.afrosoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.Employee;
import zw.co.afrosoft.model.Leaves;
import zw.co.afrosoft.model.Status;

public interface LeavesRepository extends JpaRepository<Leaves,Long> {

    Page<Leaves> findAll(Pageable pageable);



}
