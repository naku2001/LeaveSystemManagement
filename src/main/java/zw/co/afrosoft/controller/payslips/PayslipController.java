package zw.co.afrosoft.controller.payslips;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.employee.Employee;
import zw.co.afrosoft.model.payslips.Payslip;
import zw.co.afrosoft.model.payslips.PayslipRequest;
import zw.co.afrosoft.security.dto.EmployeeRequest;
import zw.co.afrosoft.service.payslips.PayslipService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/payslip")
public class PayslipController {


    private final PayslipService payslipService;

    @PostMapping("create")
    public ResponseEntity create(@RequestBody PayslipRequest payslipRequest){
        return payslipService.create(payslipRequest);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        return payslipService.delete(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id,PayslipRequest payslipRequest)
    {
        return payslipService.update(id,payslipRequest);
    }
    @GetMapping("/getById/{id}")
    public ResponseEntity getById(@PathVariable Long id)
    {
        return payslipService.getById(id);
    }
    @GetMapping("getAll")
    public List<Payslip> getAll()
    {
        return payslipService.getAll();
    }







}
