package com.development.springboot_app.services;

import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Customer;
import com.development.springboot_app.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addNew(Customer customer) {
        customerRepository.save(customer);
    }
    
}
