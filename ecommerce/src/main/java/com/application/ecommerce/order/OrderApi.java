package com.application.ecommerce.order;

import com.application.ecommerce.order.dto.OrderRequest;
import com.application.ecommerce.order.dto.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RequestMapping(value = "/order")
public interface OrderApi {

    @PostMapping
    ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest, @RequestAttribute("userId") Long userId);
}
