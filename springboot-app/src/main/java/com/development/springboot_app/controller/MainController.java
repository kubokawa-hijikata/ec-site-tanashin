package com.development.springboot_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 「ホーム」画面へ遷移する
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // 「about」画面へ遷移する
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // 「プライバシーポリシー」画面へ遷移する

    // 「特定商取引法に基づく表記」画面へ遷移する
    
}
