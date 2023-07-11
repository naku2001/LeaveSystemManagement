package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.Departments;
import zw.co.afrosoft.model.Employee;
//import zw.co.afrosoft.service.EmailService;
import zw.co.afrosoft.model.HodRequest;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.service.EmployeeService;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeRestController {

    private final EmployeeService employeeService;
//    private final EmailService emailService;

   @PostMapping("create")
  public ResponseEntity createEmployee(@RequestBody EmployeeRequest   request){
       return employeeService.createEmployee(request);
    }
    @PostMapping("/createHod/{id}")
    public ResponseEntity createHod(@PathVariable Long id,@RequestBody HodRequest request){
       return employeeService.assignEmployeeAsHod(id,request);
    }
    @GetMapping("get_departments")
    public Departments[] getDepartments(){

        return Departments.values();
    }
  @PutMapping("/update/{id}")
  Employee updateUser(@PathVariable Long id,@RequestBody EmployeeRequest request){
      return employeeService.updateEmployee(id, request);
   }
  @GetMapping("/getById/{id}")
   public ResponseEntity getUser(@PathVariable Long id){
        return employeeService.getEmployee(id);
   }
  @DeleteMapping("/delete/{id}")
   public ResponseEntity deleteUser(@PathVariable Long id){
       return employeeService.deleteEmployee(id);
   }

   @GetMapping("getAllEmployees")
    public Page getAll(int offset,int size) {
        return employeeService.getAll(PageRequest.of(offset, size));
    }
    @GetMapping("/findEmployeeByName/{username}")
    public  ResponseEntity getEmployeeByName(@PathVariable String username){
        return employeeService.getEmployeeByName(username);
    }
    @GetMapping("/totalEmployees")
    public ResponseEntity totalEmployee(){
       return  employeeService.totalEmployee();
    }

    @RequestMapping(value = "employees/report", method = RequestMethod.GET)
    public ResponseEntity<byte[]> employees(HttpServletResponse response) throws Exception {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource
                (employeeService.employees());
        String report;
        report= "employee";
        return employeeService.generateReports(dataSource,report);
    }
    @RequestMapping(value = "leave/report", method = RequestMethod.GET)
    public ResponseEntity<byte[]> leave(HttpServletResponse response) throws Exception {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource
                (employeeService.leave());
        String report;
        report= "Leave";
        return employeeService.generateReport(dataSource,report);
    }



}
