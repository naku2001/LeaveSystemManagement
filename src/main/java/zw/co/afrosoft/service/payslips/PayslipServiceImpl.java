package zw.co.afrosoft.service.payslips;

import org.springframework.http.HttpStatus;
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
        payslip.setTax(10/100L);
        payslip.setPayPeriod(payslipRequest.getPeriod());
        payslip.setLeaveType(payslipRequest.getLeavetype());
        payslip.setLeaveDays(payslipRequest.getLeaveDays());
        payslip.setLeaveDaysCharge(payslipRequest.getLeaveDays()/12 * payslip.getBasic_salary());
        payslip.setOther_deductions(payslipRequest.getOtherDeductions());
        long tax_calc = 10/100 * payslipRequest.getBasic_salary();
        payslip.setNetPay(payslipRequest.getBasic_salary() - (payslipRequest.getOtherDeductions() + tax_calc));
        Payslip saved = paySlipRepo.save(payslip);
        return ResponseEntity.ok().body(saved)  ;
    }

    @Override
    public ResponseEntity<Payslip> delete(Long id) {
        Optional<Payslip> payslip = paySlipRepo.findById(id);
        return ResponseEntity.ok().body(payslip.get());
    }

    @Override
    public List<Payslip> getAll() {
        return paySlipRepo.findAll();
    }

    @Override
    public ResponseEntity<Payslip> update(Long id,PayslipRequest payslipRequest) {
        Optional<Payslip> payslip1 = paySlipRepo.findById(id);
        if(payslip1.isPresent()){
            Payslip payslip = payslip1.get();
            payslip.setAllowances(payslipRequest.getAllowances());
            payslip.setBasic_salary(payslipRequest.getBasic_salary());
            payslip.setTax(10/100L);
            payslip.setPayPeriod(payslipRequest.getPeriod());
            payslip.setLeaveType(payslipRequest.getLeavetype());
            payslip.setLeaveDays(payslipRequest.getLeaveDays());
            payslip.setLeaveDaysCharge(payslipRequest.getLeaveDays()/12 * payslip.getBasic_salary());
            payslip.setOther_deductions(payslipRequest.getOtherDeductions());
            long tax_calc = 10/100 * payslipRequest.getBasic_salary();
            payslip.setNetPay(payslipRequest.getBasic_salary() - (payslipRequest.getOtherDeductions() + tax_calc));
            Payslip updated = paySlipRepo.save(payslip);
            return ResponseEntity.ok().body(updated);

        }
        return null;
    }

    @Override
    public ResponseEntity<Payslip> getById(Long id) {
        Optional<Payslip> payslip = paySlipRepo.findById(id);
        return ResponseEntity.ok().body(payslip.get());
    }
}
