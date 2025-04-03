package com.application.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private String paymentMethod;
    private String status;
}
