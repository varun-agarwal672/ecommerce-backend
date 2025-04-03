package com.application.ecommerce.cartItem.dto;

import com.application.ecommerce.product.dto.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    private Long id;
    private ProductResponse productResponse;
    private Integer quantity;
}
