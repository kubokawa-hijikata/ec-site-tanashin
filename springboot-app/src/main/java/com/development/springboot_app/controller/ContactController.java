package com.development.springboot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.development.springboot_app.services.ContactService;
import com.development.springboot_app.services.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ContactController {

    // お問い合わせ内容を送信するための処理を行うクラス
    private final ContactService contactService;
    private final SessionService sessionService;

    @Autowired
    public ContactController(ContactService contactService, SessionService sessionService) {
        this.contactService = contactService;
        this.sessionService = sessionService;
    }

    // 「contact」画面へ遷移
    @GetMapping("/contact")
    public String contact(Model model,
        HttpServletRequest request, HttpServletResponse response) {

        int cartSize = sessionService.getCartSize(request, response);
        model.addAttribute("cartSize", cartSize);

        return "contact";
    }

    // お客様からの問い合わせメールを受信するための処理
    @PostMapping("/contact")
    public String getMessage(
        Model model,
        @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
        @RequestParam(value = "email", required = true) String email,
        @RequestParam(value = "subject", required = true) String subject,
        @RequestParam(value = "content", required = true) String content) {
            
        contactService.getMessege(subject, name, phoneNumber, email, content);

        return "contact";
    }
 
}
