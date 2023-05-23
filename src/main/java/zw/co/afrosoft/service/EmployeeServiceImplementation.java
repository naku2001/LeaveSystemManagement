package zw.co.afrosoft.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import zw.co.afrosoft.model.DashboardTotal;
import zw.co.afrosoft.model.Employee;
import zw.co.afrosoft.model.User;
import zw.co.afrosoft.model.UserRole;
import zw.co.afrosoft.repository.EmployeeRepository;
import zw.co.afrosoft.repository.UserRepository;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.security.mapper.UserMapper;


import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final UserValidationService userValidationService;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, JavaMailSender javaMailSender, UserValidationService userValidationService) {
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.userValidationService = userValidationService;
    }


    @Override
    public ResponseEntity createEmployee( EmployeeRequest request) {

        userValidationService.validateUser(request);

        Employee employees = new Employee();
        employees.setGender(request.getGender());
        employees.setEmail(request.getEmail());
        employees.setDateOfBirth(request.getDateOfBirth());
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
                SimpleMailMessage mailMessage
                        = new SimpleMailMessage();
                String sender = "perfect.makuwerere@students.uz.zw";
                // Setting up necessary details
                mailMessage.setFrom(sender);
                mailMessage.setTo(employees.getEmail());


            String link = "44.196.52.76:4200";
            mailMessage.setText("Dear "+ " "+ employees.getFirstName().toUpperCase() + " " + employees
                        .getLastName().toUpperCase()+"\n\n Your Leave Management System account " +
                        "has been created"
                        + "\n Use the details below to login into the system"
                        +"\n\n Password:" + " " +request.getPassword()
                        + "\n\n Username:" + " " + request.getUsername()
                        +"\n\n Click the link below to the login page"
                        +"\n " );

                mailMessage.setSubject("LEAVE SYSTEM LOGIN DETAILS");
                javaMailSender.send(mailMessage);
                return ResponseEntity.ok().body(employeeSaved);
            }
            catch (Exception e) {
                return ResponseEntity.ok().body("Error while Sending Mail Please Check If" +
                        " Your Email Address Is Correct");
            }

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
            userRepository.delete(user1.get());
            employeeRepository.delete(user.get());
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee Not Found");
    }


}
