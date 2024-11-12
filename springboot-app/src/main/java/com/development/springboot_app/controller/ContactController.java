package com.development.springboot_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {

    // お問い合わせ内容を送信するための処理を行うクラス

    // 「contact」画面へ遷移
    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
 
}
