package zw.co.afrosoft.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.exceptions.headOfDepartment.HeadOfDepartmentException;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartment;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartmentRequest;
import zw.co.afrosoft.model.headOfDepartment.HeadOfDepartmentResponse;
import zw.co.afrosoft.repository.department.DepartmentRepository;
import zw.co.afrosoft.repository.employee.EmployeeRepository;
import zw.co.afrosoft.repository.headOfDepartment.HeadOfDepartmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HeadOfDepartmentServiceImpl implements HeadOfDepartmentService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final HeadOfDepartmentRepository headOfDepartmentRepository;

    public HeadOfDepartmentServiceImpl(EmployeeRepository employeeRepository,
                                       DepartmentRepository departmentRepository,
                                       HeadOfDepartmentRepository headOfDepartmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.headOfDepartmentRepository = headOfDepartmentRepository;
    }

    @Override
    public ResponseEntity<HeadOfDepartment> create(Long departmentId, Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        Optional<Department> department = departmentRepository.findById(departmentId);
        if(employee.isPresent()){
            if(department.isPresent()){
                HeadOfDepartment headOfDepartment = HeadOfDepartment.builder().
                employee(employee.get()).department(department.get()).build();
                HeadOfDepartment headOfDepartment1 = headOfDepartmentRepository
                        .save(headOfDepartment);
                return  ResponseEntity.ok().body(headOfDepartment1);
            }
        }
        final String fail = " Employee or department not found";
        throw new HeadOfDepartmentException(fail);

    }

    @Override
    public ResponseEntity update(HeadOfDepartmentRequest request, Long id) {
        return null;
    }

    @Override
    public ResponseEntity getById(Long id) {
        Optional<HeadOfDepartment> headOfDepartment = headOfDepartmentRepository.findById(id);
        if(headOfDepartment.isPresent()){
            HeadOfDepartmentResponse headOfDepartmentResponse = new HeadOfDepartmentResponse();
            headOfDepartmentResponse.setDepartment(headOfDepartment.get().getDepartment().getName());
            headOfDepartmentResponse.setLastname(headOfDepartment.get().getEmployee().getLastName());
            headOfDepartmentResponse.setFirstname(headOfDepartment.get().getEmployee().getFirstName());
            return  ResponseEntity.ok().body(headOfDepartmentResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("head of department not found");
    }

    @Override
    public ResponseEntity getAll() {
        List<HeadOfDepartment> headOfDepartmentList = headOfDepartmentRepository.findAll();
        HeadOfDepartmentResponse headOfDepartmentResponse = null;
        List<HeadOfDepartmentResponse> headOfDepartmentResponses =new ArrayList<>(20);
        for (HeadOfDepartment headOfDepartment : headOfDepartmentList) {
            headOfDepartmentResponse = new HeadOfDepartmentResponse();
            headOfDepartmentResponse.setHeadOfDepartmentId(headOfDepartment.getId());
            headOfDepartmentResponse.setDepartment(headOfDepartment.getDepartment().getName());
            headOfDepartmentResponse.setLastname(headOfDepartment.getEmployee().getLastName());
            headOfDepartmentResponse.setFirstname(headOfDepartment.getEmployee().getFirstName());

            headOfDepartmentResponses.add(headOfDepartmentResponse);

        }
        return ResponseEntity.ok().body(headOfDepartmentResponses);
    }

    @Override
    public ResponseEntity delete(Long id) {
        Optional<HeadOfDepartment> headOfDepartment = headOfDepartmentRepository.findById(id);
        final String fail = "Head of department not found";
        if(!headOfDepartment.isPresent())
            throw new HeadOfDepartmentException(fail);
        headOfDepartmentRepository.delete(headOfDepartment.get());
        return ResponseEntity.ok().body(headOfDepartment);
    }
}
