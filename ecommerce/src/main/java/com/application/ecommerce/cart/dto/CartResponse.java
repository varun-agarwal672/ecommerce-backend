package com.application.ecommerce.cart.dto;

import com.application.ecommerce.cartItem.dto.CartItemResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private Long id;
    private List<CartItemResponse> cartItemResponseList = new ArrayList<>();
}
