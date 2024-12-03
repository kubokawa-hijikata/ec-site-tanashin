package com.development.springboot_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class ContactService {

    private final JavaMailSender eMailSender;
    
    @Autowired
    public ContactService(JavaMailSender eMailSender) {
        this.eMailSender = eMailSender;
    }

    // お客様からの問い合わせメールを受信するための処理
    public void getMessege(String subject, String text) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(@SuppressWarnings("null") MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, 
                new InternetAddress("tanaka.works2024@gmail.com"));
                mimeMessage.setFrom(new InternetAddress("tanaka.works2024@gmail.com", "お客様からの問い合わせ", "UTF-8"));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(text);
            }
        };

        try {
            // eMailSender.send(message);
            eMailSender.send(preparator);
        } catch (MailException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // 購入したお客様からの問い合わせメールを受信するための処理
    public void sendMessege(String toEmail, String text) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(@SuppressWarnings("null") MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, 
                new InternetAddress(toEmail));
                mimeMessage.setFrom(new InternetAddress("tanaka.works2024@gmail.com", "田中商店", "UTF-8"));
                mimeMessage.setSubject("ご購入ありがとうございます");
                mimeMessage.setText(text);
            }
        };

        try {
            // eMailSender.send(message);
            eMailSender.send(preparator);
        } catch (MailException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
