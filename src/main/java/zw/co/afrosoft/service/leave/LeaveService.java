package zw.co.afrosoft.service.leave;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.calendar.CalendarInfo;
import zw.co.afrosoft.model.leave.Leave;
import zw.co.afrosoft.model.leave.LeaveUpdate;
import zw.co.afrosoft.model.leave.Status;

import java.util.List;
import java.util.Map;

public interface LeaveService {
    public ResponseEntity<Leave> applyLeave(LeaveRequest request);
    public ResponseEntity<Leave> approveLeave(Long id);
    List<Leave> getAll();
    List<Leave> getAllApproved();
    List<Leave> getAllRejected();
    ResponseEntity<Leave> rejectLeave(Long id);
    List<Leave> getAllLeaves();
    Long totalRejected();
    Long totalPending();
    ResponseEntity<Leave> totalApproved();
    ResponseEntity<Leave> leaveByStatus(Status status);
    ResponseEntity<Leave> totalLeaves();
    List<CalendarInfo> calenda();
    ResponseEntity<List<Leave>> myleaves(Long id);
    Map getRemainingLeaveDays(Long id);
    ResponseEntity<Leave> cancelLeave(Long id, Long employeeId);
    ResponseEntity<Leave> updateLeave(Long leaveId, Long employeeId, LeaveUpdate update);
}
