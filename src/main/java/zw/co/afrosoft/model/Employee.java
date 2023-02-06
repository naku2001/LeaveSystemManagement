package zw.co.afrosoft.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToOne
    Leaves leaves;



//    public LeaveType setLeaves(LeaveType leaveType) {
//
//        return leaveType;
//    }
}
