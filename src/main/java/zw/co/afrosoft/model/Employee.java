package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    private Gender gender;
    private String dateOfBirth;
    private String email;
    private int availableSickLeave;
    private int availableVacationLeave;

    public Employee() {
        this.availableSickLeave = 10;
        this.availableVacationLeave = 22;
        this.availableUnpaidLeave = 365;
    }

    private int availableUnpaidLeave;


}
