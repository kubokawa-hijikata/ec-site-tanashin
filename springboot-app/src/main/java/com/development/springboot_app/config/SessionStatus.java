package com.development.springboot_app.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.development.springboot_app.entity.Customer;
import com.development.springboot_app.entity.Images;
import com.development.springboot_app.entity.Orders;
import com.development.springboot_app.entity.Work;
import com.development.springboot_app.services.ContactService;
import com.development.springboot_app.services.CustomerService;
import com.development.springboot_app.services.OrdersService;
import com.development.springboot_app.services.SessionService;
import com.development.springboot_app.services.WorkService;
import com.stripe.model.Charge.PaymentMethodDetails.Card;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.ConfirmationToken.PaymentMethodOptions;
import com.stripe.model.checkout.Session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessionStatus {

    private final SessionService sessionService;
    private final OrdersService ordersService;
    private final ContactService contactService;
    private final WorkService workService;

    @Autowired
    public SessionStatus(SessionService sessionService, OrdersService ordersService, ContactService contactService, WorkService workService) {
        this.sessionService = sessionService;
        this.ordersService = ordersService;
        this.contactService = contactService;
        this.workService = workService;
    }

    @GetMapping("/session-status")
    public String getSessionStatus(
    @RequestParam("session_id") String sessionId,
    HttpServletRequest request, HttpServletResponse response,
    Model model) throws Exception {

        Session session = Session.retrieve(sessionId);

        String status = session.getRawJsonObject().getAsJsonPrimitive("status").getAsString();
        String customerEmail = session.getRawJsonObject().getAsJsonObject("customer_details").getAsJsonPrimitive("email").getAsString();
        
        String paymentIntentId = session.getPaymentIntent();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        String latestChargeId = paymentIntent.getLatestCharge();
        String brand = "";
        String funding = "";

        if (Objects.nonNull(latestChargeId)) {
            Charge latestCharge = Charge.retrieve(latestChargeId);
            Card paymentMethodDetails = latestCharge.getPaymentMethodDetails().getCard();
            brand = paymentMethodDetails.getBrand();
            funding = paymentMethodDetails.getFunding();
        }
  
        Map<String, String> res = new HashMap<>();
        res.put("status", status);
        res.put("customer_email", customerEmail);

        HttpSession ses = request.getSession();
        ArrayList<Work> cartList = (ArrayList<Work>)ses.getAttribute("cartList");
        Customer customerInfo = (Customer)ses.getAttribute("customerInfo");
        customerInfo.setEmail(customerEmail);
        Orders orderInfo = (Orders)ses.getAttribute("orderInfo");
        orderInfo.setPayMethod(brand + " / " + funding);
        orderInfo.getCustomer().setEmail(customerEmail);
        
        for (Work work : cartList) {
            Work updatedWork = workService.getOne(work.getId());
            updatedWork.setOrder(orderInfo);
        }
        
        // customerService.addNew(customerInfo);
        ordersService.addNew(orderInfo);

        StringBuilder sb = new StringBuilder(); 
        for (Work work : cartList) {
            sb.append(work.getName() + "：" + work.getPrice() + "円" + "\n");
        }

        int totalPrice = 0;
        if (Objects.nonNull(cartList)) {
            for (Work work : cartList) {
                totalPrice += work.getPrice();
            }
        }

        contactService.sendMessege(customerInfo.getEmail(), customerInfo, sb, totalPrice, orderInfo.getOrderNumber());

        // カートの中身、お客様情報、購入作品をセッションから削除する
        ses.removeAttribute("cartList");
        ses.removeAttribute("customerInfo");
        ses.removeAttribute("orderInfo");

        model.addAttribute("cartSize", 0);
        model.addAttribute("status", res.get("status"));
        model.addAttribute("customer_email", res.get("customer_email"));

        return "return";
    }
}
