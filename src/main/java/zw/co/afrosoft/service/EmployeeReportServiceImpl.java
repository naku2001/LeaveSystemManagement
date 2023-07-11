package zw.co.afrosoft.service;

import zw.co.afrosoft.repository.employee.EmployeeRepository;

public class EmployeeReportServiceImpl implements  EmployeeReportService{

    private final EmployeeRepository employeeRepository;

    public EmployeeReportServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

//    @Override
//    public List<Map<String, Object>> employees() {
//        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
//        List<Employee> employeeList = employeeRepository.findAll();
//
//        for (Employee employee : employeeList) {
//
//            Map<String, Object> item = new HashMap<String, Object>();
//
//            item.put("Firstname", employee.getFirstName());
//            item.put("Lastname",employee.getLastName() );
//            item.put("DOB",employee.getDateOfBirth() );
//            item.put("Gender",employee.getGender());
//            item.put("Email", employee.getEmail());
//
//            results.add(item);
//
//
//        }
//        return results;
//    }
//
//    @Override
//    public ResponseEntity generateReports(JRBeanCollectionDataSource dataSource, String report) throws IOException, JRException {
//        Map<String, Object> params = new HashMap<>();
//        byte[] bytes;
//        BufferedImage logo = ImageIO.read(this.getClass().getResource("/templates/logo.png"));
//        params.put("logo", logo);
//        report = "employee";
//        InputStream inputStream = this.getClass().getResourceAsStream("/templates/" + report + ".jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource );
//        bytes = JasperExportManager.exportReportToPdf(jasperPrint);
//        return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8")
//                .header("Content-Disposition", "inline; filename=\"" + report + ".pdf\"")
//                .body(bytes);
//    }
}
