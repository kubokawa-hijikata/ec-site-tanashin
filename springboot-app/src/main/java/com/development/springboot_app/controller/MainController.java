package com.development.springboot_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.SessionService;
import com.development.springboot_app.services.WorkService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    private final WorkService workService;
    private final SessionService sessionService;

    @Autowired
    public MainController(WorkService workService, SessionService sessionService) {
        this.workService = workService;
        this.sessionService = sessionService;
    }

    // 「ホーム」画面へ遷移する
    @GetMapping("/")
    public String home(Model model,
    HttpServletRequest request, HttpServletResponse response) {

        // 全作品を取得する
        List<Work> works = workService.getAll();

        int cartSize = sessionService.getCartSize(request, response);

        model.addAttribute("cartSize", cartSize);
        model.addAttribute("works", works);

        return "index";
    }

    // 「about」画面へ遷移する
    @GetMapping("/about")
    public String about(Model model,
        HttpServletRequest request, HttpServletResponse response) {

        int cartSize = sessionService.getCartSize(request, response);
        model.addAttribute("cartSize", cartSize);

        return "about";
    }

    // 「プライバシーポリシー」画面へ遷移する
    @GetMapping("/privacy")
    public String privacy(Model model,
    HttpServletRequest request, HttpServletResponse response){

        int cartSize = sessionService.getCartSize(request, response);
        model.addAttribute("cartSize", cartSize);

        return "privacy";
    }

    // 「特定商取引法に基づく表記」画面へ遷移する
    @GetMapping("/law")
    public String law(Model model,
    HttpServletRequest request, HttpServletResponse response) {

        int cartSize = sessionService.getCartSize(request, response);
        model.addAttribute("cartSize", cartSize);
        
        return "law";
    }
    
}
