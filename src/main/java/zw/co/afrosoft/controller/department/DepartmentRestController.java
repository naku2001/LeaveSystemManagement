package zw.co.afrosoft.controller.department;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.department.DepartmentRequest;
import zw.co.afrosoft.service.department.DepartmentService;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentRestController {
    private  final DepartmentService departmentService;

    @Operation(summary = "Create department")
    @PostMapping("create")
    public ResponseEntity<Department> create(@RequestBody DepartmentRequest request){
        return departmentService.create(request);
    }

    @Operation(summary = "Get department by id")
    @GetMapping("/getDepartmentById/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id){
        return departmentService.getById(id);
    }
    @Operation(summary = "Get all departments")
    @GetMapping("/getAll")
    public List<Department> getAll(){
        return departmentService.getAll();
    }
    @Operation(summary = "Update department")
    @PutMapping("/update/{id}")
    public ResponseEntity<Department> update(@PathVariable Long id,
                                 @RequestBody DepartmentRequest request){
        return departmentService.update(request,id);
    }
    @Operation(summary = "Delete department")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Department> delete(@PathVariable Long id){
        return departmentService.delete(id);
    }

}
