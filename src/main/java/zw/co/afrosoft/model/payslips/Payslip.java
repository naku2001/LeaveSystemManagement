package zw.co.afrosoft.model.payslips;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.leave.LeaveType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "payslip")
public class Payslip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    private Employee employee;
//    private String department;
    private LocalDate payPeriod;
    private Long basic_salary;
    private Long allowances;
    private Double tax;
    private Long netPay;
    private Long other_deductions;
    private int leaveDays;
    private String LeaveType;
    private int leaveDaysCharge;




}
