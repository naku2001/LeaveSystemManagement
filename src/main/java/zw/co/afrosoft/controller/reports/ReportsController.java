package zw.co.afrosoft.controller.reports;

import io.micrometer.core.lang.NonNullApi;
import io.swagger.v3.oas.annotations.Parameter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.service.employee.EmployeeService;
import zw.co.afrosoft.service.report.EmployeeReportService;


@RequestMapping("/reports")
@RestController
@CrossOrigin
public class ReportsController {
    private final EmployeeService employeeService;
    String report;

    public ReportsController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    @RequestMapping(value = "employees/report/", method = RequestMethod.GET)
    public ResponseEntity<byte[]> employees(@RequestParam(required = false) Long id ) throws Exception {
        if(id != null){
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.getEmployeeLeave(id));
            String report;
            report= "Employee Report";
            return employeeService.generateReport(dataSource,report);
        }
        else{
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.employees());
            String report;
            report= "Employee Report";
            return employeeService.generateReport(dataSource,report);
        }
    }


    @GetMapping("leave/report/")
    public ResponseEntity<byte[]> leave(@RequestParam(required = false) Long id ) throws Exception {
        if(id != null){
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.getEmployeeLeave(id));
            String report;
            report= "Leave";
            return employeeService.generateReport(dataSource,report);
        }
        else{
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.leave());
            String report;
            report= "Leave";
            return employeeService.generateReport(dataSource,report);
        }


    }

}
