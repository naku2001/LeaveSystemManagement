package zw.co.afrosoft.service.email;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import javax.mail.internet.MimeMessage;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);
    private final JavaMailSender javaMailSender;

    private final Configuration freemarkerConfig;
    @Value("${spring.mail.username}")
    private String emailSender;

    @Autowired
    public EmailNotificationServiceImpl(JavaMailSender javaMailSender,

                                        Configuration freemarkerConfig) {
        this.javaMailSender = javaMailSender;

        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public void sendEmail() {
        SimpleMailMessage mail = new SimpleMailMessage();

        try {
            String surname;
            String setToEmail;


            Map model = new HashMap();
            model.put("user", "wattie");
            model.put("link", "wattie");
            model.put("message", "wattie");
            model.put("year", "wattie");
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            mail.setFrom("perfect.makuwerere@students.uz.ac.zw");
            mail.setTo("perfect.makuwerere@students.uz.ac.zw");
            mail.setSentDate(new Date());
            mail.setSubject("test");
            Template t = freemarkerConfig.getTemplate("email-template.ftl");
//            if (notification.getUserAccount().getUserType().equals(UserType.MEMBER.name())) {
//                t = freemarkerConfig.getTemplate("response-email-template.ftl");
//                model.remove("message");
//                model.put("message", notification.getContent().concat(" ").concat(notification.getMessageLink()));
//            }
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            helper.setTo("perfect.makuwerere@students.uz.ac.zw");
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            javaMailSender.send(message);
//            notification.setIsEmail(true);
//            notification.setStatus(Status.COMPLETED);
//            notificationRepository.save(notification);
        } catch (MailException e) {
            //catch error
            log.error("Error while sending out email..{}", e.getMessage());

        } catch (Exception e) {
            //catch error

            log.error("Error while sending out email..{}", e.getMessage());
            log.error("stack trace..{}", e.getStackTrace());
            log.error("throwable..{}", e.getCause());
            log.error("Erro localised..{}", e.getLocalizedMessage());
            log.error("exceptionl..{}", e);

        }

    }





}
