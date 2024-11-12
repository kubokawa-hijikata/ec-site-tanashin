package com.development.springboot_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.development.springboot_app.entity.User;
import com.development.springboot_app.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 管理者情報を取得する
    @GetMapping("/{id}")
    public User getUser(
        @PathVariable("id") int id
    ) {

        User user = userService.getOne(id);
        return user;
    }

    // 管理者がログイン/ログアウトするための処理
    
}
