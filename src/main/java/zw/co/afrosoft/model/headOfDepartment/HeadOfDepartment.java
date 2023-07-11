package zw.co.afrosoft.model.headOfDepartment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.department.Department;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "head_of_depaertment")
public class HeadOfDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    private Employee employee;
    @OneToOne
    private Department department;
}
