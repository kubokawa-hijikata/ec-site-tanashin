package com.development.springboot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.development.springboot_app.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    
}
