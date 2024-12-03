package com.development.springboot_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User findByEmail(String currentUsername) {
        return userRepository.findByEmail(currentUsername).orElseThrow(() -> new UsernameNotFoundException("ユーザが存在しません。"));
    }

}
