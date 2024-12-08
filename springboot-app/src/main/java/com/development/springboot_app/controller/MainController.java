package com.development.springboot_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.WorkService;

@Controller
public class MainController {

    private final WorkService workService;

    @Autowired
    public MainController(WorkService workService) {
        this.workService = workService;
    }

    // 「ホーム」画面へ遷移する
    @GetMapping("/")
    public String home(Model model) {

        // 全作品を取得する
        List<Work> works = workService.getAll();
        model.addAttribute("works", works);

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
