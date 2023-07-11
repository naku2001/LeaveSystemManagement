package zw.co.afrosoft.controller.passwordReset;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.EmailRequest;
import zw.co.afrosoft.model.passwordReset.PasswordResetRequest;
import zw.co.afrosoft.service.PasswordResetService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/reset")
public class PasswordResetRestController {
    private final PasswordResetService passwordResetService;

    @PostMapping("/request-password-reset")
    public ResponseEntity initiatePasswordReset(@RequestBody EmailRequest request) {
        return passwordResetService.initiatePasswordReset(request);
    }
    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest request) {
        return  passwordResetService.resetPassword(request);
    }


}


