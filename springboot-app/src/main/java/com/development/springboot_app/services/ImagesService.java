package com.development.springboot_app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Images;
import com.development.springboot_app.repository.ImagesRepository;

@Service
public class ImagesService {

    private final ImagesRepository imagesRepository;

    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }
    
    public List<Images> getAll() {
        return imagesRepository.findAll();
    }

    public Images getOne(int id) {
        return imagesRepository.findById(id).orElseThrow(()->new RuntimeException("id=" + id + "のイメージ画像は存在しません。"));
    }
}
