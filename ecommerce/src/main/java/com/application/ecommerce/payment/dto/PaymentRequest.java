package com.application.ecommerce.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class PaymentRequest {
    private Long orderId;
    private String paymentMethod;
    private String status;
    private ZonedDateTime paymentDate;
}
