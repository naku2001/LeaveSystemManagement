package zw.co.afrosoft.service.passwordReset;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.email.EmailRequest;
import zw.co.afrosoft.model.passwordReset.PassWordResetCode;
import zw.co.afrosoft.model.passwordReset.PasswordResetRequest;
import zw.co.afrosoft.model.user.User;
import zw.co.afrosoft.repository.passwordReset.PasswordResetRepository;
import zw.co.afrosoft.repository.user.UserRepository;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;

@Service
@Component
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;
//    private final EmailService emailService;
    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordResetService(PasswordResetRepository passwordResetRepository, JavaMailSender javaMailSender, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordResetRepository = passwordResetRepository;
//
//        this.emailService = emailService;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public ResponseEntity initiatePasswordReset(EmailRequest request) {
            Optional<User> user = userRepository.findUserByEmail(request.getEmail());
            if (user.isPresent()) {
                String code = generateCode();
                PassWordResetCode passwordResetToken = new PassWordResetCode();
                passwordResetToken.setCode(code);
                passwordResetToken.setUser(user.get());
                passwordResetToken.setExpirytime(LocalTime.of(2,0,0));
                passwordResetRepository.save(passwordResetToken);
                try {
                    SimpleMailMessage mailMessage
                            = new SimpleMailMessage();
                    String sender = "perfect.makuwerere@students.uz.zw";

                    mailMessage.setFrom(sender);
                    mailMessage.setTo(request.getEmail());
                    mailMessage.setText(code);
                    mailMessage.setSubject("Reset Password");
                    javaMailSender.send(mailMessage);
                }
                catch (Exception e) {
                    return ResponseEntity.ok().body("Error while Sending Mail Please Check If" +
                            " Your Email Address Is Correct");
                }

                return ResponseEntity.ok().body(user);

            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Not Found");
            }

    }

    private String generateCode() {
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number
        int code = random.nextInt(max - min + 1) + min;
        return String.valueOf(code);

    }


    public ResponseEntity resetPassword(PasswordResetRequest request) {

        Optional<PassWordResetCode> resetToken = passwordResetRepository.findByCode(request.getCode());

            if (resetToken.isPresent()) {
                PassWordResetCode passwordResetToken = resetToken.get();
                if (passwordResetToken.getExpirytime() != LocalTime.of(0,0,0)) {
                   User user =  passwordResetToken.getUser();
                   user = passwordResetToken.getUser();
                   user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
                   user.getEmployee().setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
                   User user1 = userRepository.save(user);

                     passwordResetToken.setCode(null);
                   return ResponseEntity.ok().body(user1);
                } else {
                    throw new RuntimeException("Password reset code has expired");
                }
            } else {
//                throw new RuntimeException("Invalid password reset code");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Verification Code");
            }
        }



    }

