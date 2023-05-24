package zw.co.afrosoft.service;


import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.PassWordResetCode;
import zw.co.afrosoft.model.User;
import zw.co.afrosoft.repository.PasswordTokenRepository;
import zw.co.afrosoft.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
    public class PasswordResetService {
        private PasswordTokenRepository passwordResetTokenRepository;
        private EmailService emailService;
        private JavaMailSender javaMailSender;

        private UserRepository userRepository;

    public PasswordResetService(PasswordTokenRepository passwordResetTokenRepository, EmailService emailService, JavaMailSender javaMailSender, UserRepository userRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
    }


    public ResponseEntity<String> initiatePasswordReset(String email) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                String code = generateCode();
                PassWordResetCode passwordResetToken = new PassWordResetCode();
                passwordResetToken.setCode(code);
                passwordResetToken.setUser(user.get());
                passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24));
                passwordResetTokenRepository.save(passwordResetToken);
                try {
                    SimpleMailMessage mailMessage
                            = new SimpleMailMessage();
                    String sender = "perfect.makuwerere@students.uz.zw";

                    mailMessage.setFrom(sender);
                    mailMessage.setTo(String.valueOf(user));
                    mailMessage.setText(code);
                    mailMessage.setSubject("Reset Password");
                    javaMailSender.send(mailMessage);
                }
                catch (Exception e) {
                    return ResponseEntity.ok().body("Error while Sending Mail Please Check If" +
                            " Your Email Address Is Correct");
                }

            }
        return null;
    }

    private String generateCode() {
        Random random = new Random();
        int min = 100000; // Minimum 6-digit number
        int max = 999999; // Maximum 6-digit number
        int code = random.nextInt(max - min + 1) + min;
        return String.valueOf(code);

    }

    public void resetPassword(String code, String newPassword) {
            Optional<PassWordResetCode> resetToken = passwordResetTokenRepository.findByCode(code);
            if (resetToken.isPresent()) {
                PassWordResetCode passwordResetToken = resetToken.get();
                if (passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                    User user = passwordResetToken.getUser();
                    user.setPassword(newPassword);
                    userRepository.save(user);
                    passwordResetTokenRepository.delete(passwordResetToken);
                } else {
                    throw new RuntimeException("Password reset code has expired");
                }
            } else {
                throw new RuntimeException("Invalid password reset code");
            }
        }

//        private String generateCode() {
//            // Generate a 6-digit code
//            // You can use a random number generator or any other method to generate the code
//        }
    }

