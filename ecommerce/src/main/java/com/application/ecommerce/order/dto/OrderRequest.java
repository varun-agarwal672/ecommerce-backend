package com.application.ecommerce.order.dto;

import com.application.ecommerce.orderItem.dto.OrderItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;
}
