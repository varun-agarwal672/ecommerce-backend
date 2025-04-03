package com.application.ecommerce.order;

import com.application.ecommerce.order.dto.OrderRequest;
import com.application.ecommerce.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest, Long userId);
}
