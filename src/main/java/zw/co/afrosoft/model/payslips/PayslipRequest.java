package zw.co.afrosoft.model.payslips;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayslipRequest {

    private Long employeeId;
    private Long basic_salary;
    private Long allowances;
    private Long otherDeductions;
//    private Long tax;
    private LocalDate period;
    private  String Leavetype;
    private int leaveDays;


}
