package com.development.springboot_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.development.springboot_app.entity.Images;
import com.development.springboot_app.entity.Work;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // 新しい作品を投稿する画面へ遷移
    @GetMapping("/post-work")
    public String addWork(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Work work = new Work();

        // ADMINとしてログインしてる場合、作品投稿フォームに必要な要素を取得
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Images> images = new ArrayList<>();
            images.add(new Images());
            work.setImages(images);
    
            model.addAttribute("work", work);
        }
        return "/admin/post-work";
    }

    @PostMapping("/post-work/addNew")
    public String addNew(Model model, Work work,
        @RequestParam("imagesFile") List<MultipartFile> imagesFile) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // ADMINとしてログインしてる場合、作品投稿フォームに入力された情報をDBに保存する
            // 画像ファイルを/static/image/以下に保存する
            if (!(authentication instanceof AnonymousAuthenticationToken)) {

            }

        return "index";
    }
    
}
