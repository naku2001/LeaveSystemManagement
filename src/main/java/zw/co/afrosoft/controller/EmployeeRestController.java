package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.Employee;
import zw.co.afrosoft.service.EmployeeRequest;
import zw.co.afrosoft.service.EmployeeService;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeRestController {

    private final EmployeeService employeeService;


    @PostMapping("create")
    public ResponseEntity createEmployee(@RequestBody EmployeeRequest   request){

        return employeeService.createEmployee(request);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, EmployeeRequest request){
        return employeeService.updateEmployee(id,request);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        return employeeService.getEmployee(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        return employeeService.deleteEmployee(id);
    }
//
//    @GetMapping("getAll")
//    public Page<Employee> getAll( @PageableDefault  Pageable pageable) {
//        return employeeService.getAll(pageable);
//    }
    @GetMapping("getAllEmployees")
    public Page getAll(int offset,int size) {
        return employeeService.getAll(PageRequest.of(offset, size));
    }

}
