package zw.co.afrosoft.model.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import zw.co.afrosoft.model.department.Department;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String firstName;
    private String lastName;
    public Long getId() {
        return id;
    }
    private Gender gender;
    private String dateOfBirth;
    private String email;
    private String password;
    private String username;
    private int availableAnnualLeave;
    private int availableUnpaidLeave;
    private int availableSpecialLeave;
    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;
//    private int availableMaternityLeave;
//    public int getAvailableMaternityLeave() {
//        if(gender == Gender.Female){
//            return availableMaternityLeave;
//        }
//        return 0;
//
//    }



    public Employee() {
        this.availableUnpaidLeave = 90;
        this.availableSpecialLeave = 30;
        this.availableAnnualLeave= 20;

    }
//    private final EmployeeRepository employeeRepository;
//    @Scheduled(cron = "0 0 0 31 12 ?")
//    public void resetLeaveDaysAtEndOfYear() {
//        int currentYear = LocalDate.now().getYear();
//
//        List<Employee> employees = employeeRepository.getAllEmployees();
//
//        for (Employee employee : employees) {
//            employee.setAvailableAnnualLeave(availableAnnualLeave);
//            employee.setAvailableMaternityLeave(availableMaternityLeave);
//            employeeRepository.save(employee);
//        }
    }





