package com.application.ecommerce.order.dto;

import com.application.ecommerce.orderItem.dto.OrderItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private ZonedDateTime orderDate;
    private String status;
    private List<OrderItemResponse> orderItems;
}
