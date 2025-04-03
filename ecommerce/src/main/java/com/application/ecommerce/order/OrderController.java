package com.application.ecommerce.order;

import com.application.ecommerce.order.dto.OrderRequest;
import com.application.ecommerce.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi{

    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest, Long userId) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest, userId));
    }
}
