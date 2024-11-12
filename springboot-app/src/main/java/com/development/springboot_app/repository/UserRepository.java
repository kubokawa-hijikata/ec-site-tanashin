package com.development.springboot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.development.springboot_app.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
