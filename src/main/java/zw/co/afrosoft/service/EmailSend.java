package zw.co.afrosoft.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import zw.co.afrosoft.model.Email;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailSend implements EmailService{
    private final JavaMailSender javaMailSender;
    private final Configuration freemarkerConfig;

    public EmailSend(JavaMailSender javaMailSender, Configuration freemarkerConfig) {
        this.javaMailSender = javaMailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void sendEmail(String emailContent,String receiver,String subject) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String sender = "perfect.makuwerere@students.uz.ac.zw";
            SimpleMailMessage mail = new SimpleMailMessage();
            Map model = new HashMap();
            model.put("user", "Manager");
            model.put("link", "http://localhost:4200//leave/getPendingLeaves");
            model.put("message",emailContent);
            model.put("year", "2023");
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mail.setFrom(sender);
            mail.setTo(receiver);
            mail.setSentDate(new Date());
            mail.setSubject(subject);
            Template t = freemarkerConfig.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo(receiver);
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            javaMailSender.send(message);

        } catch (MailException e) {

        } catch (Exception e) {

        }


    }

}
