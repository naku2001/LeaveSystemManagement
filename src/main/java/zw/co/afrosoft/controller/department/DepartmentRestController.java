package zw.co.afrosoft.controller.department;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.department.DepartmentRequest;
import zw.co.afrosoft.service.DepartmentService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentRestController {

    private  final DepartmentService departmentService;
    @PostMapping("create")
    public ResponseEntity create(@RequestBody DepartmentRequest request){
        return departmentService.create(request);
    }
    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity getDepartment(@PathVariable Long id){
        return departmentService.getById(id);
    }
    @GetMapping("/getAll")
    public ResponseEntity getAll(){
        return departmentService.getAll();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody DepartmentRequest request){
        return departmentService.update(request,id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return departmentService.delete(id);
    }

}
