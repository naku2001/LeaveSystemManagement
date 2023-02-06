package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "employee_leave")
public class EmployeeLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Please provide start date!")

    private LocalDate fromDate;

    @NotNull(message = "Please provide end date!")

    private LocalDate toDate;

//    private int availableLeaveDays;


    @ManyToOne
    Leaves leaves;

    @ManyToOne
    Employee employee;

    @NotEmpty(message = "Please provide a reason for the leave!")

    private String reason;


    private int duration;


    private Status status ;



    public Status getStatus(Status notApproved) {

        return status;
    }
}
