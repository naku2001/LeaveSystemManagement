package zw.co.afrosoft.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import zw.co.afrosoft.model.Email;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Service
public class EmailSend implements EmailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    Configuration configuration;
    @Override
    public void sendEmail(Email mail) {
        SimpleMailMessage mails = new SimpleMailMessage();

        try {
            String surname;
            String setToEmail;
//            if(notification.getUserAccount().getUserType().equals(UserType.ADMIN.name())){
//                surname = notification.getUserAccount().getSurname();
//                setToEmail = notification.getUserAccount().getContactDetails().getEmail();
//            }else{
//                surname = notification.getUserAccount().getMember().getSurname();
            setToEmail = "perfect.makuwerere@students.uz.ac.zw";
//            }

            Map model = new HashMap();
            model.put("user", "customer");
            model.put("link", "@perfect");
            model.put("message", "hie");
            model.put("year", String.valueOf(LocalDate.now().getYear()));
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mail.setFrom("perfect.makuwerere@students.uz.ac.zw");

            mail.setSubject("test");
            Template t = configuration.getTemplate("templates/email-template.ftl");

            t = configuration.getTemplate("src/main/resources/email-template.flth");
            model.remove("message");
            model.put("message","testing emails");

            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo("perfect.makuwerere@students.uz.ac.zw");
            helper.setText(html, true);
            helper.setSubject("Dev Test");
            helper.setFrom("perfect.makuwerere@students.uz.ac.zw");

            javaMailSender.send(message);

        } catch (MailException e) {


        } catch (Exception e) {



        }


    }

}
