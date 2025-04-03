package com.application.ecommerce.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestAttribute;

@Data
@AllArgsConstructor
public class CartRequest {
    private Long userId;
    private Long productId;
    private Long cartItemId;
    private Integer quantity;
}
