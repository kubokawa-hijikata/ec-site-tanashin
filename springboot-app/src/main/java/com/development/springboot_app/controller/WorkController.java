package com.development.springboot_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.WorkService;

@Controller
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @GetMapping("/works/{workId}")
    public String showWork(Model model,
    @PathVariable("workId") Integer workId) {

        Work work = workService.getOne(workId);
        model.addAttribute("work", work);

        return "work";
    }

}
