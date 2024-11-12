package com.development.springboot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.development.springboot_app.entity.Work;

public interface WorkRepository extends JpaRepository<Work, Integer> {
    
}
