package zw.co.afrosoft.service;


import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.EmailRequest;
import zw.co.afrosoft.model.PassWordResetCode;
import zw.co.afrosoft.model.PasswordResetRequest;
import zw.co.afrosoft.model.User;
import zw.co.afrosoft.repository.PasswordResetRepository;
import zw.co.afrosoft.repository.UserRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;
    private final JavaMailSender javaMailSender;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordResetService(PasswordResetRepository passwordResetRepository, EmailService emailService, JavaMailSender javaMailSender, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordResetRepository = passwordResetRepository;

        this.emailService = emailService;
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
                return ResponseEntity.ok().body("user not found");
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
//         Long id = 14; String code = request.getCode();
        Optional<PassWordResetCode> resetToken = passwordResetRepository.findByCode(request.getCode());

            if (resetToken.isPresent()) {
                PassWordResetCode passwordResetToken = resetToken.get();
                if (passwordResetToken.getExpirytime() != LocalTime.of(0,0,0)) {
                    User user = passwordResetToken.getUser();
                    user.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
                    User user1 = userRepository.save(user);
                     passwordResetToken.setCode(null);
                   return ResponseEntity.ok().body(user);
                } else {
                    throw new RuntimeException("Password reset code has expired");
                }
            } else {
//                throw new RuntimeException("Invalid password reset code");
                return ResponseEntity.ok().body("wrong activation code");
            }
        }

    public ResponseEntity getAll() {
        List<PassWordResetCode> passWordResetCodeList = passwordResetRepository.findAll();
        return ResponseEntity.ok().body(passWordResetCodeList);
    }

//        private String generateCode() {
//            // Generate a 6-digit code
//            // You can use a random number generator or any other method to generate the code
//        }
    }

