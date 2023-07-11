package zw.co.afrosoft.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import zw.co.afrosoft.repository.EmployeeRepository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Timer;

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
    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonBackReference
    private Department department;
    private int availableMaternityLeave;
    public int getAvailableMaternityLeave() {
        if(gender == Gender.Female){
            return availableMaternityLeave;
        }
        return 0;

    }
    private int availableAnnualLeave;


    public Employee() {
        this.availableMaternityLeave = 90;
        this.availableAnnualLeave = 30;

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





