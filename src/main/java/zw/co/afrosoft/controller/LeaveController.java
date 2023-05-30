package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.LeaveUpdate;
import zw.co.afrosoft.model.Status;
import zw.co.afrosoft.service.LeaveRequest;
import zw.co.afrosoft.service.LeaveService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;


    @PostMapping("applyLeave")
    public ResponseEntity applyLeave(@RequestBody LeaveRequest request){

        return leaveService.applyLeave(request);
    }
    @GetMapping("getPendingLeaves")
    public ResponseEntity getAll(){

        return leaveService.getAll();
    }
    @GetMapping("getApprovedLeaves")
    public ResponseEntity getAllApproved(){

        return leaveService.getAllApproved();
    }
    @GetMapping("getRejectedLeaves")
    public ResponseEntity getAllRejected(){

        return leaveService.getAllRejected();
    }
    @GetMapping("getAllLeaves")
    public ResponseEntity getAllLeaves(){

        return leaveService.getAllLeaves();
    }
    @GetMapping("getAllRemainingLeaveDays/{id}")
    public ResponseEntity getRemainingLeaveDays(@PathVariable Long id){

        return leaveService.getRemainingLeaveDays(id);
    }
    @PutMapping("/approve/{id}")
    public  ResponseEntity approveLeave(@PathVariable Long id){
        return leaveService.approveLeave(id);
    }

    @DeleteMapping ("/cancelLeave/{leaveId}/{employeeId}")
    public  ResponseEntity cancelLeave(@PathVariable Long leaveId,@PathVariable Long employeeId){
        return leaveService.cancelLeave(leaveId,employeeId);
    }
    @PutMapping ("/updateLeave/{leaveId}/{employeeId}")
    public  ResponseEntity updateLeave(@PathVariable Long leaveId, @PathVariable Long employeeId, @RequestBody LeaveUpdate update){
        return leaveService.updateLeave(leaveId,employeeId,update);
    }

    @PutMapping("/reject/{id}")
    public  ResponseEntity rejectLeave(@PathVariable Long id){
        return leaveService.rejectLeave(id);
    }

    @GetMapping("/myleaves/{id}")
    public  ResponseEntity myleaves(@PathVariable  Long id){
        return leaveService.myleaves(id);
    }
    @GetMapping("/numberOfRejectedLeaves")
    public  ResponseEntity totalRejected(){
        return leaveService.totalRejected();
    }
    @GetMapping("/numberOfPendingLeaves")
    public  ResponseEntity totalPending(){
        return leaveService.totalPending();
    }
    @GetMapping("/numberOfApproved")
    public  ResponseEntity totalApproved(){
        return leaveService.totalApproved();
    }
    @GetMapping("/numberOfLeaves")
    public  ResponseEntity totalLeaves(){
        return leaveService.totalLeaves();
    }
    @GetMapping("/findLeaveByStatus/{status}")
    public  ResponseEntity leaveByStatus(@PathVariable  Status status){
        return leaveService.leaveByStatus(status);
    }
    @GetMapping("/calendar")
    public  ResponseEntity calenda(){
        return leaveService.calenda();
    }




}
