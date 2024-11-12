package com.development.springboot_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.development.springboot_app.entity.Customer;
import com.development.springboot_app.services.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // 全ての顧客情報を取得する
    @GetMapping("")
    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();
        customers = customerService.getAll();
        return customers;
    }

    // 1人の顧客情報を取得する
    @GetMapping("/{id}")
    public Customer getCustomer(
        @PathVariable("id") int id
    ) {

        Customer customer = customerService.getOne(id);
        return customer;
    }
    
}
