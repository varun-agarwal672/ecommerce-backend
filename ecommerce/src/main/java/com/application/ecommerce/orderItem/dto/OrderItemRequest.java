package com.application.ecommerce.orderItem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemRequest {
    private Long productId;
    private int quantity;
}
