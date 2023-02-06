package zw.co.afrosoft.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.service.LeaveRequest;
import zw.co.afrosoft.service.LeaveService;


@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeLeaveRestController {

    private final LeaveService leaveService;

    public EmployeeLeaveRestController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/applyLeave/{id}")
    public ResponseEntity applyLeave(@RequestBody LeaveRequest leaveRequest,@PathVariable Long id){

        return leaveService.applyLeave(leaveRequest,id);
    }
    @GetMapping("getAllAppliedLeaves")
    public Page getAllAppliedLeaves(int offset,int size){

        return leaveService.getAllAppliedLeaves(offset,size);
    }
    @PutMapping("/approveLeave/{id}")
    public ResponseEntity approveLeave(@PathVariable Long id){
        return leaveService.approveLeave(id);
    }



}
