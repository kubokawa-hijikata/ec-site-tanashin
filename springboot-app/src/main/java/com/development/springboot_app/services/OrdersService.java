package com.development.springboot_app.services;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.repository.OrdersRepository;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void addNew(Orders order) {
        ordersRepository.save(order);
    }

    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    public Orders findByOrderNumber(int orderNumber) {
        return ordersRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new UsernameNotFoundException("注文が存在しません。"));
    }
    
}
