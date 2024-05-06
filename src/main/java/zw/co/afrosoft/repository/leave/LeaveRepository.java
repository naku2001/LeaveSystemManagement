package zw.co.afrosoft.repository.leave;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.leave.Leave;
import zw.co.afrosoft.model.leave.LeaveType;
import zw.co.afrosoft.model.leave.Status;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository  extends JpaRepository<Leave,Long> {
    List<Leave> findAllByStatus(Status status);

    List<Leave> findAllByEmployeeId(Long id);

    List<Leave> findAllByLeaveType(LeaveType leaveType);
}
