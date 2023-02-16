package zw.co.afrosoft.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import zw.co.afrosoft.model.EmailDetails;

@Service
public class EmailServiceImplementation implements EmailService{
    private final JavaMailSender javaMailSender;

    public EmailServiceImplementation(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendSimpleMail(EmailDetails details) {


        {

            // Try block to check for exceptions
            try {

                // Creating a simple mail message
                SimpleMailMessage mailMessage
                        = new SimpleMailMessage();
                String sender = "perfect.makuwerere@students.uz.zw";

                // Setting up necessary details
                mailMessage.setFrom(sender);
                mailMessage.setTo(details.getRecipient());
                mailMessage.setText(details.getMsgBody());
                mailMessage.setSubject(details.getSubject());

                // Sending the mail
                javaMailSender.send(mailMessage);
                return "Mail Sent Successfully...";
            }

            // Catch block to handle the exceptions
            catch (Exception e) {
                return "Error while Sending Mail";
            }
        }


//        @Value("${spring.mail.username}") private String sender;\



    }
}
