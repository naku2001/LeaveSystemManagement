package zw.co.afrosoft.model.team;
import lombok.*;
import zw.co.afrosoft.model.employee.Employee;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;
    private String name;
    private String description;
    @ManyToMany
    private List<Employee> employee;
    private String teamLead;

}
