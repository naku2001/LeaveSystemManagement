package zw.co.afrosoft.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.Department;
import zw.co.afrosoft.model.DepartmentRequest;
import zw.co.afrosoft.model.Employee;
import zw.co.afrosoft.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public ResponseEntity<Department> create(DepartmentRequest request) {
        Department department = Department.builder().
                name(request.getName()).build();
       Department department1 = departmentRepository.save(department);
       return ResponseEntity.ok().body(department1)  ;
    }
    @Override
    public ResponseEntity update(DepartmentRequest request, Long id) {
       Optional<Department>  department = departmentRepository.findById(id);
        if (!department.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DEPARTMENT NOT FOUND")  ;

        Department department1 = department.get();
        department1.setName(request.getName());
        Department department2 = departmentRepository.save(department1);
        return ResponseEntity.ok().body(department1) ;
    }
    @Override
    public ResponseEntity getById(Long id) {
        Optional<Department>  department = departmentRepository.findById(id);
        if (!department.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DEPARTMENT NOT FOUND")  ;
        return ResponseEntity.ok().body(department.get()) ;
    }
    @Override
    public ResponseEntity getAll() {
       List<Department> department = departmentRepository.findAll();
        if (department.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DEPARTMENT NOT FOUND")  ;
        return ResponseEntity.ok().body(department) ;
    }

    @Override
    public ResponseEntity delete(Long id) {
        Optional<Department>  department = departmentRepository.findById(id);
        List<Employee> employee = department.get().getEmployees();
        if (!department.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("DEPARTMENT NOT FOUND")  ;
        if (employee.isEmpty()){
            departmentRepository.delete(department.get());
            return ResponseEntity.ok().body(department) ;
        }

        return null;

    }

}
