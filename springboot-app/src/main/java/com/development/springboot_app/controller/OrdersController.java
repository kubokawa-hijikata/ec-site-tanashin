package com.development.springboot_app.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.development.springboot_app.entity.Customer;
import com.development.springboot_app.entity.Images;
import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.CustomerService;
import com.development.springboot_app.services.OrdersService;
import com.development.springboot_app.services.SessionService;
import com.development.springboot_app.services.WorkService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final CustomerService customerService;
    private final WorkService workService;
    private final OrdersService ordersService;
    private final SessionService sessionService;

    @Autowired
    public OrdersController(CustomerService customerService, WorkService workService, OrdersService ordersService, SessionService sessionService) {
        this.customerService = customerService;
        this.workService = workService;
        this.ordersService = ordersService;
        this.sessionService = sessionService;
    }

    @GetMapping("")
    public String order(Model model,
    HttpServletRequest request, HttpServletResponse response) {

        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        
        boolean cart = false;
        int totalPrice = 0;
        HttpSession session = request.getSession();
        ArrayList<Work> cartList = (ArrayList<Work>)session.getAttribute("cartList");
        int cartSize = sessionService.getCartSize(request, response);
        
        if (Objects.nonNull(cartList)) {
            for (Work work : cartList) {
                List<Images> images = workService.getOne(work.getId()).getImages();
                work.setImages(images);
                totalPrice += work.getPrice();
                cart = true;
            }
        }

        model.addAttribute("cartSize", cartSize);
        model.addAttribute("cart", cart);
        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);
        
        return "order";
    } 

    @GetMapping("/add/{workId}")
    public String workCartAdd(Model model,
    @PathVariable("workId") int workId,
    HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        sessionService.addCart(workId, request, response);

        return "redirect:/orders";
    }

    @PostMapping("")
    public String addCustomerInfo(Model model, Customer customer,
    HttpServletRequest request, HttpServletResponse response) {

        Orders order = new Orders();
        HttpSession session = request.getSession();
        ArrayList<Work> cartList = (ArrayList<Work>)session.getAttribute("cartList");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        order.setDate(timestamp);
        order.setShip(false);
        order.setCustomer(customer);
        // 以下二つの処理がまだ
        order.setOrderNumber(1234567866);
        order.setPayMethod("支払い処理はまた後で実装");

        for (Work work : cartList) {
            Work updatedWork = workService.getOne(work.getId());
            updatedWork.setOrder(order);
        }

        ordersService.addNew(order);
        customerService.addNew(customer);

        // カートの中身を削除する
        session.removeAttribute("cartList");

        // 商品購入完了場面を表示したい
        return "redirect:/";
    }

    @PostMapping("/delete/{workId}")
    public String deleteWork(Model model,
    @PathVariable("workId") int workId,
    HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        ArrayList<Work> cartList = (ArrayList<Work>)session.getAttribute("cartList");

        int index = 0;
        for (Work work : cartList) {
            if (work.getId().equals(workId)) {
                cartList.remove(index);
                break;
            } else {
                index += 1;
            }
        }

        return "redirect:/orders";
    }
    
}
