package com.application.ecommerce.cart;

import com.application.ecommerce.cart.dto.CartRequest;
import com.application.ecommerce.cart.dto.CartResponse;

public interface CartService {
    CartResponse addProductToCart(CartRequest cartRequest, Long userId);

    CartResponse getCartDetailOfUser(Long userId);

    CartResponse updateCartDetail(Long id, CartRequest cartRequest);

    Boolean isProductPresentInCart(Long id, Long productId);

    Boolean removeItemFromCart(Long id, CartRequest cartRequest);

    Boolean emptyCart(Long id);
}
