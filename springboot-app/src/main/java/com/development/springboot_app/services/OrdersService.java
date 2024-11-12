package com.development.springboot_app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.repository.OrdersRepository;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    public Orders getOne(int id) {
        return ordersRepository.findById(id).orElseThrow(()->new RuntimeException("id=" + id + "の注文は存在しません。"));
    }
    
}
