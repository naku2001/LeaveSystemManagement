package zw.co.afrosoft.controller.leave;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.calendar.CalendarInfo;
import zw.co.afrosoft.model.leave.Leave;
import zw.co.afrosoft.model.leave.LeaveType;
import zw.co.afrosoft.model.leave.LeaveUpdate;
import zw.co.afrosoft.model.leave.Status;
import zw.co.afrosoft.service.leave.LeaveRequest;
import zw.co.afrosoft.service.leave.LeaveService;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveRestController {
    private final LeaveService leaveService;

    @PostMapping("applyLeave")
    @Operation(summary = "Apply leave")
    public ResponseEntity<Leave> applyLeave(@RequestBody LeaveRequest request){
        return leaveService.applyLeave(request);
    }
    @Operation(summary = "Get pending leaves")
    @GetMapping("getPendingLeaves")
    public List<Leave> getAll(){
        return leaveService.getAll();
    }

    @Operation(summary = "Get approved leaves ")
    @GetMapping("getApprovedLeaves")
    public List<Leave> getAllApproved(){
        return leaveService.getAllApproved();
    }

    @Operation(summary = "Get rejected leaves")
    @GetMapping("getRejectedLeaves")
    public List<Leave> getAllRejected(){
        return leaveService.getAllRejected();
    }
    @Operation(summary = "Get all leaves")
    @GetMapping("getAllLeaves")
    public List<Leave> getAllLeaves(){
        return leaveService.getAllLeaves();
    }

    @Operation(summary = "Get remaining leave days")
    @GetMapping("getAllRemainingLeaveDays/{id}")
    public Map getRemainingLeaveDays(@PathVariable Long id){
        return leaveService.getRemainingLeaveDays(id);
    }
    @Operation(summary = "Approve leave")
    @PutMapping("/approve/{id}")
    public  ResponseEntity<Leave> approveLeave(@PathVariable Long id){
        return leaveService.approveLeave(id);
    }

    @Operation(summary = "Reject Leave")
    @DeleteMapping ("/cancelLeave/{leaveId}/{employeeId}")
    public  ResponseEntity<Leave>cancelLeave(@PathVariable Long leaveId,
                                       @PathVariable Long employeeId){
        return leaveService.cancelLeave(leaveId,employeeId);
    }

    @Operation(summary = "Update leave")
    @PutMapping ("/updateLeave/{leaveId}/{employeeId}")
    public  ResponseEntity<Leave> updateLeave(@PathVariable Long leaveId,
                                       @PathVariable Long employeeId,
                                       @RequestBody LeaveUpdate update){
        return leaveService.updateLeave(leaveId,employeeId,update);
    }
    @Operation(summary = "Rejected leave")
    @PutMapping("/reject/{id}")
    public  ResponseEntity<Leave> rejectLeave(@PathVariable Long id){
        return leaveService.rejectLeave(id);
    }

    @Operation(summary = "Get all my leaves")
    @GetMapping("/myleaves/{id}")
    public  ResponseEntity<List<Leave>> myleaves(@PathVariable  Long id){
        return leaveService.myleaves(id);
    }

    @Operation(summary = "Get number of rejected leaves")
    @GetMapping("/numberOfRejectedLeaves")
    public  Long totalRejected(){
        return leaveService.totalRejected();
    }

    @Operation(summary = "Get number of pending leaves")
    @GetMapping("/numberOfPendingLeaves")
    public  Long totalPending(){
        return leaveService.totalPending();
    }

    @Operation(summary = "Get number of approved leaves")
    @GetMapping("/numberOfApproved")
    public  ResponseEntity<Leave> totalApproved(){
        return leaveService.totalApproved();
    }

    @Operation(summary = "Get number of leaves")
    @GetMapping("/numberOfLeaves")
    public  ResponseEntity totalLeaves(){
        return leaveService.totalLeaves();
    }

    @Operation(summary = "get rejected leaves")
    @GetMapping("/findLeaveByStatus/{status}")
    public  ResponseEntity leaveByStatus(@PathVariable  Status status){
        return leaveService.leaveByStatus(status);
    }
    @Operation(summary = "Generate calendar of employees on leave")
    @GetMapping("/calendar")
    public  List<CalendarInfo> calendar(){
        return leaveService.calenda();
    }

    @Operation(summary = "Get leave types")
    @GetMapping("get_leave_types")
    public LeaveType[] getLeaveTypes(){
        return LeaveType.values();
    }




}
