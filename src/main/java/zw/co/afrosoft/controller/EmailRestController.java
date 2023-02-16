package zw.co.afrosoft.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import zw.co.afrosoft.model.EmailDetails;
import zw.co.afrosoft.service.EmailService;

@CrossOrigin
@RestController

@RequestMapping("/employee")
public class EmailRestController {

    private final EmailService emailService;

    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status = emailService.sendSimpleMail(details);

        return status;
    }
}
