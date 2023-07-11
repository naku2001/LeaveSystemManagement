package zw.co.afrosoft.service;

import zw.co.afrosoft.model.Email;
import zw.co.afrosoft.model.EmailDetails;

public interface EmailService {
    void sendEmail(String emailContent,String reciever,String subject);
}
