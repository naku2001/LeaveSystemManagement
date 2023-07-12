package zw.co.afrosoft.controller.reports;

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
    public ReportsController( EmployeeService employeeService) {
        this.employeeService = employeeService;

    }
    @RequestMapping(value = "employees", method = RequestMethod.GET)
    public ResponseEntity<byte[]> employees(HttpServletResponse response) throws Exception {
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource
                (employeeService.employees());
        report= "employee";
        return employeeService.generateReports(dataSource,report);
    }
}
