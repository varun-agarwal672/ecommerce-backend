package com.application.ecommerce.orderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private Long productId;
    private int quantity;
    private double totalPrice;
}