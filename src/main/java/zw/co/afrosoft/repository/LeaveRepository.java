package zw.co.afrosoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.Leave;

public interface LeaveRepository  extends JpaRepository<Leave,Long> {
}
