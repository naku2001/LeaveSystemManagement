package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    private Departments departments;

    private int availableSickLeave;
    private int availableVacationLeave;
    private int availableUnpaidLeave;
    public Employee() {
        this.availableSickLeave = 10;
        this.availableVacationLeave = 22;
        this.availableUnpaidLeave = 365;
    }




}
