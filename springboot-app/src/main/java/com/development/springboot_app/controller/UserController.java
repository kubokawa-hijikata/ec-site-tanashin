package com.development.springboot_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // aboutページへ遷移
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // 管理者がログイン/ログアウトするための処理
    
}
