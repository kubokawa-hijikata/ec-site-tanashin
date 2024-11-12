package com.development.springboot_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.services.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    // 全ての注文情報を取得する
    @GetMapping("")
    public List<Orders> getAllOrders() {

        List<Orders> orders = new ArrayList<>();
        orders = ordersService.getAll();
        return orders;
    }

    // 1つの注文情報を取得する
    @GetMapping("/{id}")
    public Orders getOrder(
        @PathVariable("id") int id
    ) {

        Orders order = ordersService.getOne(id);
        return order;
    }
    
}
