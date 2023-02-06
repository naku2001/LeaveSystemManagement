package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.Leaves;
import zw.co.afrosoft.repository.LeavesRepository;
import zw.co.afrosoft.service.LeaveTypeRequest;
import zw.co.afrosoft.service.LeaveTypeService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class LeaveTypesRestController {

    private final LeavesRepository leavesRepository;
    private final LeaveTypeService leaveTypeService;

    @PostMapping("leaveTypes")
    ResponseEntity setDefaultLeave(@RequestBody LeaveTypeRequest request){

        return leaveTypeService.setDefaultLeave(request);
    }
    @GetMapping("getAlLeaveTypes")
    public Page getAlLeaves(int offset,int size) {
        return leaveTypeService.getAlLeaves(offset,size);
    }
    @DeleteMapping("{id}")
    ResponseEntity delete(@PathVariable Long id){
        return leaveTypeService.delete(id);
    }
}
