package com.development.springboot_app.services;

import org.springframework.stereotype.Service;

import com.development.springboot_app.repository.ImagesRepository;

@Service
public class ImagesService {

    private final ImagesRepository imagesRepository;

    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public void deleteImages(int workId) {
        imagesRepository.deleteByWorkId(workId);
    }
    
}
