package com.development.springboot_app.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.development.springboot_app.entity.Images;
import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.ImagesService;
import com.development.springboot_app.services.OrdersService;
import com.development.springboot_app.services.SessionService;
import com.development.springboot_app.services.WorkService;
import com.development.springboot_app.util.FileUploadUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final WorkService workService;
    private final OrdersService ordersService;
    private final ImagesService imagesService;
    private final SessionService sessionService;

    @Autowired
    public AdminController(WorkService workService, OrdersService ordersService, ImagesService imagesService, SessionService sessionService) {
        this.workService = workService;
        this.ordersService = ordersService;
        this.imagesService = imagesService;
        this.sessionService = sessionService;
    }

    // 新しい作品を投稿する画面へ遷移
    @GetMapping("/post-work")
    public String addWork(Model model,
    HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Work work = new Work();

        // ADMINとしてログインしてる場合、作品投稿フォームに必要な要素を取得
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Images> images = new ArrayList<>();
            images.add(new Images());
            work.setImages(images);
            int cartSize = sessionService.getCartSize(request, response);

            model.addAttribute("cartSize", cartSize);
            model.addAttribute("work", work);
        }
        return "admin/post-work";
    }

    // 新しい作品をDBに保存する
    @PostMapping("/post-work/addNew")
    public String addNew(Model model, Work work,
        @RequestParam("imagesFile") List<MultipartFile> imagesFile) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // ADMINとしてログインしてる場合、作品投稿フォームに入力された情報をDBに保存する
        // 画像ファイルを/static/image/以下に保存する
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Images> images = new ArrayList<>();
            Optional<Work> latestWork = workService.getLatestOne();
            Integer latestWorkId = latestWork.get().getId();
            Integer storeWorkId = latestWorkId + 1;
            
            for (MultipartFile file : imagesFile) {
                String imageName = "";
                String uploadDir = "/app/external/image/works/" + storeWorkId;
                try {
                    if (!Objects.equals(file.getOriginalFilename(), "")) {
                        imageName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                        images.add(new Images(imageName));

                        FileUploadUtil.saveFile(uploadDir, imageName, file);
                    }
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            work.setImages(images);
            for (Images image : work.getImages()) {
                image.setWork(work);
            }
            // 新規作品をDBに追加する
            workService.addNew(work);

        }
        return "redirect:/";
    }

    // 新しい作品を投稿する画面へ遷移
    @GetMapping("/update-work/{workId}")
    public String updateWork(Model model,
    HttpServletRequest request, HttpServletResponse response,
    @PathVariable("workId") int workId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // ADMINとしてログインしてる場合、作品投稿フォームに必要な要素を取得
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Work work = workService.getOne(workId);
            int cartSize = sessionService.getCartSize(request, response);

            model.addAttribute("cartSize", cartSize);
            model.addAttribute("work", work);
        }
        return "admin/post-work";
    }

    // 更新した作品をDBに保存する
    @PostMapping("/update-work")
    public String updateNew(Model model, Work work,
    @RequestParam("imagesFile") List<MultipartFile> imagesFile) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // ADMINとしてログインしてる場合、作品投稿フォームに入力された情報をDBに保存する
        // 画像ファイルを/static/image/以下に保存する
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            Work updatedWork = workService.getOne(work.getId());
            imagesService.deleteImages(updatedWork.getId());

            updatedWork.setName(work.getName());
            updatedWork.setDescription(work.getDescription());
            updatedWork.setVerticalSize(work.getVerticalSize());
            updatedWork.setHorizontalSize(work.getHorizontalSize());
            updatedWork.setPrice(work.getPrice());

            List<Images> images = new ArrayList<>();
            String imageName = "";
            String uploadDir = "/app/external/image/works/" + work.getId();
            File dir = new File(uploadDir);
            File[] files = dir.listFiles();
            for (File file : files) {
                file.delete();
            }

            for (MultipartFile file : imagesFile) {
                try {
                    if (!Objects.equals(file.getOriginalFilename(), "")) {
                        imageName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                        images.add(new Images(imageName));

                        FileUploadUtil.saveFile(uploadDir, imageName, file);
                    }
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            updatedWork.setImages(images);
            for (Images image : updatedWork.getImages()) {
                image.setWork(updatedWork);
            }
            // 更新作品をDBに追加する
            workService.addNew(updatedWork);

        }
        return "redirect:/";
    }

    // 購入情報画面へ
    @GetMapping("/check-orders")
    public String checkOrders(Model model,
    HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            List<Orders> orders = new ArrayList<>();
            orders = ordersService.getAll();
            int cartSize = sessionService.getCartSize(request, response);

            model.addAttribute("cartSize", cartSize);
            model.addAttribute("orders", orders);
        }

        return "admin/orders";
    }

    // 注文詳細画面へ
    @GetMapping("/check-orders/{orderNumber}")
    public String checkDetail(Model model,
    HttpServletRequest request, HttpServletResponse response,
    @PathVariable("orderNumber") String orderNumber) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            Orders order = ordersService.findByOrderNumber(orderNumber);
            List<Work> works = order.getWorks();
            int cartSize = sessionService.getCartSize(request, response);

            model.addAttribute("cartSize", cartSize);
            model.addAttribute("order", order);
            model.addAttribute("works", works);
        }

        return "admin/order-details";
    }

    // 発送手続き状況を更新する
    @PostMapping("/update/{orderNumber}")
    public String update(Model model,
    @PathVariable("orderNumber") String orderNumber,
    @RequestParam("ship") boolean ship) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            Orders order = ordersService.findByOrderNumber(orderNumber);
            order.setShip(ship);

            ordersService.addNew(order);
        }

        return "redirect:/admin/check-orders";

    }
    
}
