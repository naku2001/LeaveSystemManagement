package zw.co.afrosoft.service.payslips;

import org.springframework.http.ResponseEntity;
import zw.co.afrosoft.model.department.Department;
import zw.co.afrosoft.model.department.DepartmentRequest;
import zw.co.afrosoft.model.payslips.Payslip;
import zw.co.afrosoft.model.payslips.PayslipRequest;

public interface PayslipService {

    ResponseEntity<Payslip> create(PayslipRequest payslipRequest);
}
