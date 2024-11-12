package com.development.springboot_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.springboot_app.entity.Images;
import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.ImagesService;
import com.development.springboot_app.services.WorkService;

@RestController
@RequestMapping("/works")
public class WorkController {

    private final WorkService workService;
    private final ImagesService imagesService;

    @Autowired
    public WorkController(ImagesService imagesService, WorkService workService) {
        this.imagesService = imagesService;
        this.workService = workService;
    }

    // 全ての作品情報を取得する
    @GetMapping("")
    public List<Work> getAllWorks() {
        
        List<Work> works = new ArrayList<>();
        works = workService.getAll();
        return works;
    }

    // 1つの作品情報を取得する
    @GetMapping("/{id}")
    public Work getWork(
        @PathVariable("id") int id
    ) {

        Work work = workService.getOne(id);
        return work;
    }

    // 全ての作品のイメージ画像を取得する
    @GetMapping("/images")
    public List<Images> getAllImages() {

        List<Images> images = new ArrayList<>();
        images = imagesService.getAll();
        return images;
    }

    // 1つの作品のイメージ画像を取得する
    @GetMapping("/images/{id}")
    public Images getImage(
        @PathVariable("id") int id
    ) {

        Images image = imagesService.getOne(id);
        return image;
    }
    
}
