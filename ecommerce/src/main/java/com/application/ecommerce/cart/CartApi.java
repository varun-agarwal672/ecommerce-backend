package com.application.ecommerce.cart;

import com.application.ecommerce.cart.dto.CartRequest;
import com.application.ecommerce.cart.dto.CartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*")
@RequestMapping(value = "/cart")
public interface CartApi {

    @GetMapping(value = "/user")
    ResponseEntity<CartResponse> getCartDetailOfUser(@RequestAttribute("userId") Long userId);

    @PostMapping
    ResponseEntity<CartResponse> addProductToCart(@RequestBody CartRequest cartRequest, @RequestAttribute("userId") Long userId);

    @PutMapping(value = "/{id}")
    ResponseEntity<CartResponse> updateCartDetail(@PathVariable(value = "id") Long id, @RequestBody CartRequest cartRequest);

    @GetMapping(value = "/{id}")
    ResponseEntity<Boolean> isProductPresentInCart(@PathVariable(value = "id") Long id, @RequestParam(value = "product-id") Long productId);

    @DeleteMapping(value = "/{id}")
    ResponseEntity<Boolean> removeItemFromCart(@PathVariable(value = "id") Long id, @RequestBody CartRequest cartRequest);

    @DeleteMapping(value = "/{id}/empty")
    ResponseEntity<Boolean> emptyCart(@PathVariable(value = "id") Long id);

}
