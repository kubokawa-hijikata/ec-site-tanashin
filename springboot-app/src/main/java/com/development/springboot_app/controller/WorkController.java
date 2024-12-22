package com.development.springboot_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.SessionService;
import com.development.springboot_app.services.WorkService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WorkController {

    private final WorkService workService;
    private final SessionService sessionService;

    public WorkController(WorkService workService, SessionService sessionService) {
        this.workService = workService;
        this.sessionService = sessionService;
    }

    @GetMapping("/works/{workId}")
    public String showWork(Model model,
    @PathVariable("workId") Integer workId,
    HttpServletRequest request, HttpServletResponse response) {

        Work work = workService.getOne(workId);
        boolean cartFlag = sessionService.getCartFlag(workId, request, response);
        int cartSize = sessionService.getCartSize(request, response);

        model.addAttribute("cartSize", cartSize);
        model.addAttribute("cartFlag", cartFlag);
        model.addAttribute("work", work);

        return "work";
    }

}
