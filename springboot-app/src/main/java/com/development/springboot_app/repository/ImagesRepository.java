package com.development.springboot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.development.springboot_app.entity.Images;

public interface ImagesRepository extends JpaRepository<Images, Integer> {
    
}
