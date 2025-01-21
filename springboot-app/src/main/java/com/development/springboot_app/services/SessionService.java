package com.development.springboot_app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.development.springboot_app.entity.Customer;
import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.entity.Work;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class SessionService {

    private final WorkService workService;

    public SessionService(WorkService workService) {
        this.workService = workService;
    }
    
    public int getCartSize(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        ArrayList<Work> cartList = (ArrayList<Work>)session.getAttribute("cartList");
        
        // カートに入っている商品の数を取得する
        int cartSize = 0;
        if (Objects.nonNull(cartList)) {
            cartSize = cartList.size();
        }

        return cartSize;
    }

    public void addCart(int workId, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        ArrayList<Work> cartList = (ArrayList<Work>)session.getAttribute("cartList");

        if (Objects.isNull(cartList)) {
            cartList = new ArrayList<Work>();
        }
        Work work = workService.getOne(workId);
        cartList.add(work);

        session.setAttribute("cartList", cartList);
    }

    public void addCustomerInfo(Customer customer, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        Customer customerInfo = (Customer)session.getAttribute("customerInfo");

        if (Objects.isNull(customerInfo)) {
            customerInfo = new Customer();
        }
        customerInfo.setLastName(customer.getLastName());
        customerInfo.setFirstName(customer.getFirstName());
        // customerInfo.setEmail(customer.getEmail());
        customerInfo.setPrefecture(customer.getPrefecture());
        customerInfo.setCity(customer.getCity());
        customerInfo.setAddress(customer.getAddress());
        customerInfo.setBuilding(customer.getBuilding());
        customerInfo.setPostalCode(customer.getPostalCode());

        session.setAttribute("customerInfo", customerInfo);
    }

    public void addOrderInfo(Orders order, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        Orders orderInfo = (Orders)session.getAttribute("orderInfo");

        if (Objects.isNull(orderInfo)) {
            orderInfo = new Orders();
        }
        orderInfo.setOrderNumber(order.getOrderNumber());
        orderInfo.setCustomer(order.getCustomer());
        orderInfo.setDate(order.getDate());
        // orderInfo.setPayMethod(order.getPayMethod());
        orderInfo.setShip(order.getShip());

        session.setAttribute("orderInfo", orderInfo);
    }

    public boolean getCartFlag(int workId, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        ArrayList<Work> cartList = (ArrayList<Work>)session.getAttribute("cartList");

        // その商品がカートに入っているかを判断するフラグ
        boolean cartFlag = false;
        if (Objects.nonNull(cartList)) {
            for (Work cart : cartList) {
                if (cart.getId().equals(workId)) {
                    cartFlag = true;
                    break;
                }
            }
        }

        return cartFlag;
    }
}
