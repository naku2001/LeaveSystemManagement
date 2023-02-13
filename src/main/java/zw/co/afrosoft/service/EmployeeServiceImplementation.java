package zw.co.afrosoft.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import zw.co.afrosoft.model.Employee;
import zw.co.afrosoft.repository.EmployeeRepository;

import java.util.Optional;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public ResponseEntity createEmployee( EmployeeRequest request) {

        Employee employees =  new Employee();
        employees .setGender(request.getGender());
        employees.setEmail(request.getEmail());
        employees.setDateOfBirth(request.getDateOfBirth());
        employees.setLastName(request.getLastName());
        employees.setFirstName(request.getFirstName());

        return ResponseEntity.ok().body(employeeRepository.save(employees));

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
    public Employee updateEmployee(Long id, @RequestBody EmployeeRequest employeeRequest) {
        Optional<Employee> user = employeeRepository.findById(id);
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
        if (user.isPresent()){
            employeeRepository.delete(user.get());
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee Not Found");
    }


}
