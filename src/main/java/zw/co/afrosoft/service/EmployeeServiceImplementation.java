package zw.co.afrosoft.service;

import freemarker.template.Template;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import zw.co.afrosoft.model.*;
import zw.co.afrosoft.repository.EmployeeRepository;
import zw.co.afrosoft.repository.HeadOfDepartmentRepository;
import zw.co.afrosoft.repository.LeaveRepository;
import zw.co.afrosoft.repository.UserRepository;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.security.mapper.UserMapper;

import freemarker.template.Configuration;


import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;



@Service
public class EmployeeServiceImplementation implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final UserValidationService userValidationService;
    private final LeaveRepository leaveRepository;

    private final Configuration freemarkerConfig;
    private final HeadOfDepartmentRepository headOfDepartmentRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, JavaMailSender javaMailSender, UserValidationService userValidationService,
                                         LeaveRepository leaveRepository, Configuration freemarkerConfig,
                                         HeadOfDepartmentRepository headOfDepartmentRepository) {
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.userValidationService = userValidationService;
        this.leaveRepository = leaveRepository;
        this.freemarkerConfig = freemarkerConfig;
        this.headOfDepartmentRepository = headOfDepartmentRepository;
    }


    @Override
    public ResponseEntity createEmployee( EmployeeRequest request) {

        userValidationService.validateUser(request);

        Employee employees = new Employee();
        employees.setGender(request.getGender());
        employees.setEmail(request.getEmail());
        employees.setDateOfBirth(request.getDateOfBirth());
        employees.setDepartments(request.getDepartment());
        employees.setLastName(request.getLastName());
        employees.setFirstName(request.getFirstName());
        employees.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        employees.setUsername(request.getUsername());
        Employee employeeSaved = employeeRepository.save(employees);
        final User user = UserMapper.INSTANCE.convertToUser(request);
        user.setUserRole(UserRole.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmployee(employeeSaved);
        userRepository.save(user);
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
                String sender = "perfect.makuwerere@students.uz.ac.zw";
            SimpleMailMessage mail = new SimpleMailMessage();
            String user1 = employees.getFirstName().toUpperCase() + " " + employees
                   .getLastName().toUpperCase();
            String emailContent = new StringBuilder()
                    .append("<br>")
                    .append("Your AfroTech Leave Board System account has been created.<br>")
                    .append("Use the details below to log in to the system:<br><br>")
                    .append("Password: ")
                    .append(request.getPassword())
                    .append("<br>")
                    .append("Username: ")
                    .append(request.getUsername())
                    .append("<br><br>")
                    .append("Click the link below to access the login page:<br>")
                    .toString();
            Map model = new HashMap();
            model.put("user", user1);
            model.put("link", "http://localhost:4200/");
            model.put("message",emailContent);
            model.put("year", "2023");
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mail.setFrom(sender);
            mail.setTo(employees.getEmail());
            mail.setSentDate(new Date());
            mail.setSubject("AFROTECH LEAVE BOARD SYSTEM LOGIN DETAILS");
            Template t = freemarkerConfig.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(employees.getEmail());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            javaMailSender.send(message);

        } catch (MailException e) {

        } catch (Exception e) {

        }

        return null;
    }


    @Override
    public Page<Employee> getAll(Pageable pageable) {
        return  employeeRepository.findAll(pageable);
    }
    @Override
    public Page getAll(int offset, int size) {
        return employeeRepository.findAll(PageRequest.of(offset, size));
    }

    @Override
    public ResponseEntity getEmployeeByName(String username) {
        List<Employee> employee = employeeRepository.findByUsername(username);
//        List<Employee> employees = new ArrayList<>();
//        employees.add(employee);
        if(!employee.isEmpty())
            return ResponseEntity.ok().body(employee);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("employee not found");
    }

    @Override
    public ResponseEntity totalEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        DashboardTotal dashboardTotal = DashboardTotal.builder()
                .total(employees.size()).build();
        return ResponseEntity.ok().body(dashboardTotal);
    }
        @Override
    public ResponseEntity generateReports(JRBeanCollectionDataSource dataSource, String report) throws IOException, JRException {
        Map<String, Object> params = new HashMap<>();
        byte[] bytes;
//        BufferedImage logo = ImageIO.read(this.getClass().getResource("/templates/logo.png"));
//        params.put("logo", logo);
        report = "employee";
        InputStream inputStream = this.getClass().getResourceAsStream("/templates/" + report + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, dataSource );
        bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + report + ".pdf\"")
                .body(bytes);
    }
        @Override
    public List<Map<String, Object>> employees() {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<Employee> employeeList = employeeRepository.findAll();

        for (Employee employee : employeeList) {

            Map<String, Object> item = new HashMap<String, Object>();

            item.put("firstname", employee.getFirstName());
            item.put("lastname",employee.getLastName() );
            item.put("dob",employee.getDateOfBirth() );
            item.put("gender",employee.getGender());
            item.put("email", employee.getEmail());

            results.add(item);


        }
        return results;
    }

    @Override
    public List<Map<String, Object>> leave() {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        List<Leave> leaveList = leaveRepository.findAll();

        for (Leave leave : leaveList) {

            Map<String, Object> item = new HashMap<String, Object>();
//            item.put("name", leave.getEmployee().getFirstName() + " " + leave.getEmployee().getLastName());
            item.put("stat",leave.getStatus());
            item.put("duration",leave.getDuration() );
            item.put("to",leave.getToDate());
            item.put("from", leave.getFromDate());
            item.put("type", leave.getLeaveType());
            results.add(item);
        }
        return results;
    }

    @Override
    public Employee updateEmployee(Long id, @RequestBody EmployeeRequest employeeRequest) {
        Optional<Employee> user = employeeRepository.findById(id);
        String response = "Employee not found";
        if(user.isPresent()){
            Employee updatedEmployee = user.get();

            updatedEmployee.setGender(employeeRequest.getGender());
            updatedEmployee.setEmail(employeeRequest.getEmail());
            updatedEmployee.setDateOfBirth(employeeRequest.getDateOfBirth());
            updatedEmployee.setLastName(employeeRequest.getLastName());
            updatedEmployee.setFirstName(employeeRequest.getFirstName());
//          updatedEmployee.setNumberOfLeaveDays(employeeRequest.getNumberOfLeaveDays());
            employeeRepository.save(updatedEmployee);

            return  employeeRepository.save(updatedEmployee);
        }

        return null;
    }

    @Override
    public ResponseEntity assignEmployeeAsHod(Long id,HodRequest request) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            HeadOfDepartment headOfDepartment = new HeadOfDepartment();
            headOfDepartment.setEmployee(employee.get());
            headOfDepartment.setDepartments(request.getDepartments());
            headOfDepartmentRepository.save(headOfDepartment);
            return ResponseEntity.ok().body(headOfDepartment);

        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee Not Found");
    }

    @Override
    public ResponseEntity getEmployee(Long id) {

        Optional<Employee> user = employeeRepository.findById(id);
        if (user.isPresent())
        {
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee Not Found");
    }
    @Override
    public ResponseEntity deleteEmployee(Long id) {
        Optional<Employee> user = employeeRepository.findById(id);
        List<User> users = userRepository.findAll();
        Optional<User> user1 = userRepository.findUserByEmployeeId(id);
        if (user.isPresent() ){

//            employeeRepository.delete(user.get());
            userRepository.delete(user1.get());
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee Not Found");
    }
    @Override
    public ResponseEntity generateReport(JRBeanCollectionDataSource dataSource, String report) throws IOException, JRException {
        Map<String, Object> params = new HashMap<>();
        byte[] bytes;
//        BufferedImage logo = ImageIO.read(this.getClass().getResource("/templates/logo.png"));
//        params.put("logo", logo);
        report = "leave";
        InputStream inputStream = this.getClass().getResourceAsStream("/templates/" + report + ".jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, dataSource );
        bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        return ResponseEntity.ok().header("Content-Type", "application/pdf; charset=UTF-8")
                .header("Content-Disposition", "inline; filename=\"" + report + ".pdf\"")
                .body(bytes);
    }


}
