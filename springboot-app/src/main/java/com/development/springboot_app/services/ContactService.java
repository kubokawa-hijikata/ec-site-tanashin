package com.development.springboot_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Customer;

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
    public void getMessege(String subject, String name, String phoneNumber, String email, String content) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            String text = "お名前：" + name + "\n" 
                    + "電話番号：" + phoneNumber + "\n"
                    + "メールアドレス：" + email + "\n\n"
                    + "問い合わせ内容：\n" + content;

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
    public void sendMessege(String toEmail, Customer customer, StringBuilder sb, int totalPrice, String orderNumber) {

        // 購入完了メールを送信する
        String text = customer.getLastName() + " " + customer.getFirstName() + " 様" + "\n\n"
        + "この度は田中商店をご利用頂き誠にありがとうございます。" + "\n\n" 
        + "ご注文の変更、キャンセルをご希望の場合は、お早めにお問い合わせ下さいませ。" + "\n\n"
        + "お問い合わせのタイミングによりましては、対応できかねる場合がございます。" + "\n"
        + "あらかじめご了承下さい。" + "\n\n\n" 
        + "■お支払いについて" + "\n" 
        + "お支払い方法：" + "?????" + "\n"
        + "お支払い金額：" + totalPrice + "円" + "\n"
        + "(送料：" + "xxx" + "円込み)" + "\n\n\n"
        + "■注文内容の確認" + "\n" 
        + "注文番号：" + orderNumber + "\n\n"
        + "送付先：" + "\n"
        + customer.getLastName() + " " + customer.getFirstName() + " 様" + "\n"
        + "〒" + customer.getPostalCode() + "\n" 
        + customer.getPrefecture() + customer.getCity() + customer.getAddress() + customer.getBuilding() + "\n\n"
        + "注文内容：" + "\n"
        + sb.toString();

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
