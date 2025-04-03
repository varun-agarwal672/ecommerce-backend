package com.application.ecommerce.payment;

import com.application.ecommerce.payment.dto.PaymentRequest;
import com.application.ecommerce.payment.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest);
}
