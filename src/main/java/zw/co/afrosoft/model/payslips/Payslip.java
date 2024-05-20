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
    private Long housing_plan;
    private Long attendance_bonus;
    private Long transport_allowance;
    private Long night_allowance;
    private Long overtime;
    private Long overtime_holiday;
    private Long nssa ;
    private LocalDate payPeriod;
    private Long basic_salary;
    private Long allowances;
    private Long tax;
    private Long netPay;
    private Long other_deductions;
    private int leaveDays;
    private String currency;
    private String LeaveType;
    private int leaveDaysCharge;
    private String account_number;




}
