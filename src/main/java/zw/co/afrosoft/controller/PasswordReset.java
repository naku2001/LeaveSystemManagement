package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.service.PasswordResetService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/reset")
public class PasswordReset {
    private final PasswordResetService passwordResetService;

    @PostMapping()
    public ResponseEntity<String> initiatePasswordReset(@RequestBody String email) {
        return passwordResetService.initiatePasswordReset(email);
    }
    @PutMapping("/reset-password")
    public void resetPassword(String code, String newPassword) {
        passwordResetService.resetPassword(code, newPassword);
    }
}


