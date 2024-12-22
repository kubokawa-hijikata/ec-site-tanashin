package com.development.springboot_app.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.development.springboot_app.entity.Customer;
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
    private final SessionService sessionService;

    @Autowired
    public OrdersController(CustomerService customerService, WorkService workService, SessionService sessionService) {
        this.customerService = customerService;
        this.workService = workService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{workId}")
    public String workOrder(Model model,
    @PathVariable("workId") int workId,
    HttpServletRequest request, HttpServletResponse response) {

        Customer customer = new Customer();
        int cartSize = sessionService.getCartSize(request, response);
        
        model.addAttribute("cartSize", cartSize);
        model.addAttribute("customer", customer);
        model.addAttribute("workId", workId);

        return "order";
    }

    @GetMapping("/add/{workId}")
    public String workCartAdd(Model model,
    @PathVariable("workId") int workId,
    HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        sessionService.addCart(workId, request, response);

        return "redirect:/";
    }

    @PostMapping("/{workId}")
    public String addCustomerInfo(Model model, Customer customer,
    @PathVariable("workId") int workId) {

        Orders order = new Orders();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        order.setOrderNumber(1234567866);
        order.setDate(timestamp);
        order.setPayMethod("支払い処理はまた後で実装");
        order.setShip(false);
        order.setCustomer(customer);

        Work work = workService.getOne(workId);
        work.setOrder(order);

        customerService.addNew(customer);

        // 商品購入完了場面を表示したい
        return "redirect:/";
    }
    
}
