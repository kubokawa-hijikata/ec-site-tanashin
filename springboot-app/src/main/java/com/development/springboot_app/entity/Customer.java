package com.development.springboot_app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String prefecture;

    private String city;
    
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @OneToMany(targetEntity = Orders.class, cascade = CascadeType.ALL, mappedBy = "customer")
    @JsonBackReference
    private List<Orders> orders;

    public Customer() {

    }

    public Customer(Integer id, String name, String email, String prefecture, String city, String address, String postalCode, List<Orders> orders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.prefecture = prefecture;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", prefecture='" + prefecture + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
