package zw.co.afrosoft.service.department.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.exceptions.department.DepartmentNotFoundException;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.department.DepartmentRequest;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.repository.department.DepartmentRepository;
import zw.co.afrosoft.service.department.DepartmentService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<Department> create(DepartmentRequest request) {
        Department department = Department.builder().
                name(request.getName()).build();
       Department departmentCreated = departmentRepository.save(department);
       return ResponseEntity.ok().body(departmentCreated)  ;
    }
    @Override
    public ResponseEntity<Department> update(DepartmentRequest request, Long id) {
       Optional<Department>  department = departmentRepository.findById(id);
       final String message = "Department not found";
        if (department.isEmpty())
           throw new DepartmentNotFoundException(message);
        Department departmentUpdate = department.get();
        departmentUpdate.setName(request.getName());
        Department departmentUpdated = departmentRepository.save(departmentUpdate);
        return ResponseEntity.ok().body(departmentUpdated) ;
    }
    @Override
    public ResponseEntity<Department> getById(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        Department foundDepartment = department.orElseThrow(() ->
                new DepartmentNotFoundException("Department not found"));
        return ResponseEntity.ok().body(foundDepartment);
    }
    @Override
    public List<Department> getAll() {
       List<Department> departmentList = departmentRepository.findAll();
        return departmentList ;
    }
    //to do
    @Override
    public ResponseEntity<Department> delete(Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        Department department = departmentOptional.orElseThrow(() ->
                new DepartmentNotFoundException("Department not found"));
        List<Employee> employees = department.getEmployees();
        if (employees.isEmpty()) {
            departmentRepository.delete(department);
            return ResponseEntity.ok().body(department);
        }
        return ResponseEntity.ok().build();


    }

}
