package zw.co.afrosoft.controller.payslips;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.payslips.PayslipRequest;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.service.payslips.PayslipService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/payslips")
public class PayslipController {


    private final PayslipService payslipService;

    @PostMapping("create")
    public ResponseEntity create(@RequestBody PayslipRequest payslipRequest){
        return payslipService.create(payslipRequest);
    }



}
