package com.application.ecommerce.payment;

import com.application.ecommerce.payment.dto.PaymentRequest;
import com.application.ecommerce.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApi{

    private final PaymentService paymentService;

    @Override
    public ResponseEntity<PaymentResponse> createPayment(PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.createPayment(paymentRequest));
    }
}
