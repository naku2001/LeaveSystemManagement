package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.service.LeaveRequest;
import zw.co.afrosoft.service.LeaveService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
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

}
