package com.development.springboot_app.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number")
    private int orderNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @OneToMany(targetEntity = Work.class, cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<Work> works;
    
    private Date date;

    @Column(name = "pay_method")
    private String payMethod;

    private boolean ship;

    public Orders() {

    }

    public Orders(Integer id, int orderNumber, Customer customer, List<Work> works, Date date, String payMethod, boolean ship) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.works = works;
        this.date = date;
        this.payMethod = payMethod;
        this.ship = ship;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Work> getWorks() {
        return works;
    }

    public void setWorks(List<Work> works) {
        this.works = works;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public boolean getShip() {
        return ship;
    }

    public void setShip(boolean ship) {
        this.ship = ship;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Orders{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", customer=" + customer +
                ", date=" + date +
                ", payMethod=" + payMethod +
                ", ship=" + ship +
                '}';
    }
}
