package com.development.springboot_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Customer;
import com.development.springboot_app.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Customer getOne(int id) {
        return customerRepository.findById(id).orElseThrow(()->new RuntimeException("id=" + id + "のお客様は存在しません。"));
    }
    
}
