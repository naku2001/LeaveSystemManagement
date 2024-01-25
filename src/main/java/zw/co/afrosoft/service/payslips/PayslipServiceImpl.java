package zw.co.afrosoft.service.payslips;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.payslips.Payslip;
import zw.co.afrosoft.model.payslips.PayslipRequest;
import zw.co.afrosoft.repository.employee.EmployeeRepository;
import zw.co.afrosoft.repository.payslip.PaySlipRepo;

import java.util.List;
import java.util.Optional;
@Service
public class PayslipServiceImpl implements PayslipService{

    private  final PaySlipRepo paySlipRepo;

    private final EmployeeRepository employeeRepository;

    public PayslipServiceImpl(PaySlipRepo paySlipRepo, EmployeeRepository employeeRepository) {
        this.paySlipRepo = paySlipRepo;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseEntity<Payslip> create(PayslipRequest payslipRequest) {
        Optional<Employee> payslip_user = employeeRepository.findById(payslipRequest.getEmployeeId());
        Payslip payslip = new Payslip();
        payslip.setAllowances(payslipRequest.getAllowances());
        payslip.setBasic_salary(payslipRequest.getBasic_salary());
        payslip.setEmployee(payslip_user.get());
        payslip.setTax(payslipRequest.getTax());
        payslip.setPayPeriod(payslipRequest.getPeriod());
        payslip.setLeaveType(payslip.getLeaveType());
        payslip.setLeaveDays(payslip.getLeaveDays());
        payslip.setLeaveDaysCharge(payslip.getLeaveDays()/12 * payslip.getBasic_salary());
        payslip.setOther_deductions(payslipRequest.getOtherDeductions());
        long tax_calc = payslipRequest.getTax()/100 * payslipRequest.getBasic_salary();
        payslip.setNetPay(payslipRequest.getBasic_salary() - (payslipRequest.getOtherDeductions() + tax_calc));
        Payslip saved = paySlipRepo.save(payslip);
        return ResponseEntity.ok().body(saved)  ;
    }
}
