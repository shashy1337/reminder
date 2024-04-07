package ru.shashy.remindertest.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final MimeMessagePreparator mimeMessagePreparator;
    private final JavaMailSender javaMailSender;


    @Async
    public void send(String to, String subject, String text){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessagePreparator.prepare(mimeMessage);
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(text);
            javaMailSender.send(mimeMessage);
        } catch (Exception e){
            log.error("Письмо не отправлено", e);
        }
        //log.info("Письмо отправлено");
    }
}