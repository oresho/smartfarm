package com.klusterthon.Smartfarm.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@RequiredArgsConstructor
public class JavaMailServiceImpl{
    private final JavaMailSender javaMailSender;

    public void sendEmailJava(String email, String subject, String from, String htmlContent) throws MessagingException, MailSendException {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setFrom(from);
            messageHelper.setSentDate(new Date());
            messageHelper.setSubject(subject);
            messageHelper.setTo(email);
            messageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
    }

}
