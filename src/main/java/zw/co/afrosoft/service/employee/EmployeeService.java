package zw.co.afrosoft.service.employee;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.headOfDepartment.HodRequest;
import zw.co.afrosoft.security.dto.EmployeeRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public interface EmployeeService {


    ResponseEntity generateReports(JRBeanCollectionDataSource dataSource,
                                   String report) throws IOException, JRException;

    List<Map<String, Object>> employees();

    List<Map<String, Object>> leave();
    List<Map<String, Object>> getEmployeeLeave(Long id);

    List<Map<String, Object>> getEmployeeById(Long id);


    Employee updateEmployee(Long id, EmployeeRequest request);

    ResponseEntity assignEmployeeAsHod(Long id, HodRequest request);

    ResponseEntity getEmployee(Long id);

    ResponseEntity deleteEmployee(Long id);

    ResponseEntity createEmployee(EmployeeRequest request);

   public Page<Employee> getAll(Pageable pageable);

   public Page getAll(int offset,int size);


    List<Employee> getInActiveEmployees();

    List<Employee> getActiveEmployees();

    ResponseEntity getEmployeeByName(String username);

    ResponseEntity totalEmployee();


    ResponseEntity generateReport(JRBeanCollectionDataSource dataSource, String report) throws IOException, JRException;
}
