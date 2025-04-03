package com.application.ecommerce.cart;

import com.application.ecommerce.cart.dto.CartRequest;
import com.application.ecommerce.cart.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController implements CartApi{

    private final CartService cartService;

    @Override
    public ResponseEntity<CartResponse> getCartDetailOfUser(Long userId) {
        return ResponseEntity.ok(cartService.getCartDetailOfUser(userId));
    }

    @Override
    public ResponseEntity<CartResponse> addProductToCart(CartRequest cartRequest, Long userId) {
        return ResponseEntity.ok(cartService.addProductToCart(cartRequest, userId));
    }

    @Override
    public ResponseEntity<CartResponse> updateCartDetail(Long id, CartRequest cartRequest) {
        return ResponseEntity.ok(cartService.updateCartDetail(id, cartRequest));
    }

    @Override
    public ResponseEntity<Boolean> isProductPresentInCart(Long id, Long productId) {
        return ResponseEntity.ok(cartService.isProductPresentInCart(id, productId));
    }

    @Override
    public ResponseEntity<Boolean> removeItemFromCart(Long id, CartRequest cartRequest) {
        return ResponseEntity.ok(cartService.removeItemFromCart(id, cartRequest));
    }

    @Override
    public ResponseEntity<Boolean> emptyCart(Long id) {
        return ResponseEntity.ok(cartService.emptyCart(id));
    }
}
