package com.development.springboot_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.development.springboot_app.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
}
