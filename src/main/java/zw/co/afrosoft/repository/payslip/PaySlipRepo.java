package zw.co.afrosoft.repository.payslip;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.afrosoft.model.payslips.Payslip;

public interface PaySlipRepo extends JpaRepository<Payslip,Long> {
}
