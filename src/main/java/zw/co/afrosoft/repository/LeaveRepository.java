package zw.co.afrosoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.Leave;
import zw.co.afrosoft.model.Status;

import java.util.List;

public interface LeaveRepository  extends JpaRepository<Leave,Long> {
    List<Leave> findAllByStatus(Status status);
}
