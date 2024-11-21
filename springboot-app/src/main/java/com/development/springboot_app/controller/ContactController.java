package com.development.springboot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.development.springboot_app.services.ContactService;

@Controller
public class ContactController {

    // お問い合わせ内容を送信するための処理を行うクラス
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    // お客様からの問い合わせメールを受信するための処理
    @PostMapping("/contact")
    public String getMessage(
        Model model,
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "subject", required = true) String subject,
        @RequestParam(value = "content", required = true) String content
    ) {
        String text = "お名前：" + name + "\n" 
                    + "電話番号：" + phoneNumber + "\n"
                    + "メールアドレス：" + email + "\n\n"
                    + "問い合わせ内容：\n" + content;

        contactService.getMessege(subject, text);

        return "contact";
    }

    // 「contact」画面へ遷移
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
 
}
