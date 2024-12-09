package com.development.springboot_app.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import com.development.springboot_app.services.WorkService;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;
    private final CustomerService customerService;
    private final WorkService workService;

    @Autowired
    public OrdersController(OrdersService ordersService, CustomerService customerService, WorkService workService) {
        this.ordersService = ordersService;
        this.customerService = customerService;
        this.workService = workService;
    }

    @GetMapping("/{workId}")
    public String workOrder(Model model,
    @PathVariable("workId") int workId) {

        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("workId", workId);

        return "order";
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

        return "/";
    }
    
}
