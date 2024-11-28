package com.development.springboot_app.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    private Customer customer;

    @OneToMany(targetEntity = Work.class, cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<Work> works;
    
    private Date date;

    public Orders() {

    }

    public Orders(Integer id, Customer customer, List<Work> works, Date date) {
        this.id = id;
        this.customer = customer;
        this.works = works;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @java.lang.Override
    public java.lang.String toString() {
        return "Orders{" +
                "id=" + id +
                ", customer=" + customer +
                ", date=" + date +
                '}';
    }
}
