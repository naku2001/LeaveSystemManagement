package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employeeLeave")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull(message = "Please provide start date!")
    private LocalDate fromDate;
    @NotNull(message = "Please provide end date!")
    private LocalDate toDate;
    @ManyToOne
    private Employee employee;
    @NotEmpty(message = "Please provide a reason for the leave!")
    private String reason;
    private int duration;
    private Status status ;
    private LeaveType leaveType;
}
