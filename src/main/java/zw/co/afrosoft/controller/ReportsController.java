//package zw.co.afrosoft.controller;
//
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.bind.annotation.*;
//import zw.co.afrosoft.service.EmployeeReportService;
//
//
//@RequestMapping("/Reports")
//@RestController
//
//@CrossOrigin
//public class ReportsController {
//    private final EmployeeReportService employeeReportService;
//
//
//    String report;
//
//    public ReportsController(EmployeeReportService employeeReportService) {
//        this.employeeReportService = employeeReportService;
//    }
//
//    @RequestMapping(value = "employees", method = RequestMethod.GET)
//    public ResponseEntity<byte[]> employees(HttpServletResponse response) throws Exception {
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource
//                (employeeReportService.employees());
//        report= "employee";
//        return employeeReportService.generateReports(dataSource,report);
//    }
//}
