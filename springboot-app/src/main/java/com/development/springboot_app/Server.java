package com.development.springboot_app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

import com.development.springboot_app.entity.Work;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class Server  {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;
  
    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestParam("totalPrice") int totalPrice,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
      
        Stripe.apiKey = stripeSecretKey;
        String YOUR_DOMAIN = "http://localhost:8080";

        HttpSession ses = request.getSession();

        // ArrayList<Work> cartList = (ArrayList<Work>)ses.getAttribute("cartList");
        // ArrayList<String> cartImages = new ArrayList<>();
        // for (Work cart : cartList) {
        //   cartImages.add("https://65ed-174-7-245-6.ngrok-free.app/works/" + cart.getId() + "/" + cart.getImages().get(0).getName());
        // }
        // ArrayList<Work> cartList = (ArrayList<Work>)ses.getAttribute("cartList");
        // String cartImage = "https://65ed-174-7-245-6.ngrok-free.app/works/" + cartList.get(0).getId() + "/" + cartList.get(0).getImages().get(0).getName();
    
        SessionCreateParams params = SessionCreateParams.builder()
                    .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setInvoiceCreation(SessionCreateParams.InvoiceCreation.builder().setEnabled(true).build())
                    .setReturnUrl(YOUR_DOMAIN + "/session-status?session_id={CHECKOUT_SESSION_ID}")
                    .addLineItem(
                      SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                          SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("jpy")
                            .setUnitAmount(totalPrice * 1L)
                            .setProductData(
                              SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("合計金額")
                                // .addImage(cartImage)
                                // .addAllImage(cartImages)
                                .build()
                            )
                            .build()
                        )
                        .build()
                    )
                    .build();
        
        Session session = Session.create(params);
    
        Map<String, String> res = new HashMap<>();
        res.put("clientSecret", session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString());
    
        return res;
    }
}
