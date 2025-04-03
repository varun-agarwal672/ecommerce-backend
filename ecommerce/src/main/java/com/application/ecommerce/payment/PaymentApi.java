package com.application.ecommerce.payment;

import com.application.ecommerce.payment.dto.PaymentRequest;
import com.application.ecommerce.payment.dto.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

//@CrossOrigin(origins = "*")
@RequestMapping(value = "/payment")
public interface PaymentApi {

    @PostMapping
    ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest);
}
