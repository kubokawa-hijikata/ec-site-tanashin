package com.development.springboot_app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.development.springboot_app.entity.Images;
import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.OrdersService;
import com.development.springboot_app.services.WorkService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final WorkService workService;
    private final OrdersService ordersService;

    @Autowired
    public AdminController(WorkService workService, OrdersService ordersService) {
        this.workService = workService;
        this.ordersService = ordersService;
    }

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

                List<Images> images = new ArrayList<>();

                for (MultipartFile file : imagesFile) {

                    String imageName = "";

                    if (!Objects.equals(file.getOriginalFilename(), "")) {
                        imageName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                        images.add(new Images(imageName));
                    }
                }

                work.setImages(images);

                for (Images image : work.getImages()) {
                    image.setWork(work);
                }

                // 新規作品をDBに追加する
                workService.addNew(work);
            }

        return "index";
    }

    @GetMapping("/check-orders")
    public String checkOrders(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            List<Orders> orders = new ArrayList<>();
            orders = ordersService.getAll();
    
            model.addAttribute("orders", orders);
        }

        return "/admin/orders";
    }
    
}
