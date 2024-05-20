package zw.co.afrosoft.controller.reports;

import io.micrometer.core.lang.NonNullApi;
import io.swagger.v3.oas.annotations.Parameter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.leave.LeaveType;
import zw.co.afrosoft.service.employee.EmployeeService;
import zw.co.afrosoft.service.report.EmployeeReportService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


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
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.getEmployeeById(id));
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
    public ResponseEntity<byte[]> leave(@RequestParam(required = false) LeaveType leavetype ) throws Exception {
        if(leavetype != null){
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.getEmployeeLeave(leavetype));
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
    @GetMapping("payslip/report/")
    public ResponseEntity<byte[]> payslip(@RequestParam(required = false) Long id  ) throws Exception {
        if(id != null){
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.getPayslipEmployeeById(id));
            String report;
            report= "payslip";
            return generatePayslip(dataSource,report);
        }
        else{
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employeeService.getPayslipEmployeeById(id));
            String report;
            report= "payslip";
            return generatePayslip(dataSource,report);
        }


    }

    public ResponseEntity generatePayslip(JRBeanCollectionDataSource dataSource, String report) throws IOException, JRException {
        Map<String, Object> params = new HashMap<>();
        byte[] bytes;
        report = "payslip";
        InputStream inputStream = this.getClass().getResourceAsStream("/templates/" + report + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, dataSource );
        bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + report + ".pdf\"")
                .body(bytes);
    }

}
