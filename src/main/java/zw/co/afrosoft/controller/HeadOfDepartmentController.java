package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.service.HeadOfDepartmentService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/headOfDepartment")
public class HeadOfDepartmentController {
    private final HeadOfDepartmentService headOfDepartmentService;
    @PostMapping("create/{departmentId}/{employeeId}")
    public ResponseEntity create(@PathVariable Long departmentId,@PathVariable Long employeeId){
        return  headOfDepartmentService.create(departmentId,employeeId);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return  headOfDepartmentService.delete(id);
    }
    @GetMapping("getById/{id}")
    public ResponseEntity getHod(@PathVariable Long id){
        return  headOfDepartmentService.getById(id);
    }
    @GetMapping("getAll")
    public ResponseEntity getAll(){
        return  headOfDepartmentService.getAll();
    }


}
