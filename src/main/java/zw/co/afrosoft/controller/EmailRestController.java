package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.Email;
import zw.co.afrosoft.model.EmailDetails;
import zw.co.afrosoft.service.EmailSend;
//import zw.co.afrosoft.service.EmailService;

@CrossOrigin
@RestController

@RequestMapping("/employee")
public class EmailRestController {

//    private final EmailSend emailService;
//
//    public EmailRestController(EmailSend emailService) {
//        this.emailService = emailService;
//    }
//
//    // Sending a simple Email
//    @PostMapping("/sendMail")
//    public void sendMail(@RequestBody Email email)
//    {
//    emailService.sendEmail(email);
//    }
}
