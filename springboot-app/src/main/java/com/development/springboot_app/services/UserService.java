package com.development.springboot_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.User;
import com.development.springboot_app.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOne(int id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("id=" + id + "のユーザは存在しません。"));
    }
}
