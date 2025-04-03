package com.application.ecommerce.cart;

import com.application.ecommerce.cart.dto.CartRequest;
import com.application.ecommerce.cart.dto.CartResponse;
import com.application.ecommerce.cartItem.CartItemRepository;
import com.application.ecommerce.cartItem.dto.CartItemResponse;
import com.application.ecommerce.model.Cart;
import com.application.ecommerce.model.CartItem;
import com.application.ecommerce.model.Product;
import com.application.ecommerce.model.User;
import com.application.ecommerce.product.ProductRepository;
import com.application.ecommerce.product.dto.ProductResponse;
import com.application.ecommerce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartResponse addProductToCart(CartRequest cartRequest, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id"));
        Product product = productRepository.findById(cartRequest.getProductId()).orElseThrow(() -> new RuntimeException("Product not found with id"));

        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartRequest.getQuantity());
        cart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);

        Cart updatedCart = cartRepository.save(cart);
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(updatedCart.getId());
        return cartResponse;
    }

    @Override
    public CartResponse getCartDetailOfUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id"));
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        List<CartItem> cartItems = cart.getCartItems();
        for(CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            Integer quantity = cartItem.getQuantity();
            ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(), null, product.getPrice(), product.getImageUrl());
            CartItemResponse cartItemResponse = new CartItemResponse(cartItem.getId(), productResponse, quantity);
            cartResponse.getCartItemResponseList().add(cartItemResponse);
        }
        return cartResponse;
    }

    @Override
    public CartResponse updateCartDetail(Long id, CartRequest cartRequest) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found with given id"));
        CartResponse cartResponse = new CartResponse();
        CartItem filteredCartItem = cart.getCartItems().stream().filter(cartItem -> cartItem.getId().equals(cartRequest.getCartItemId())).findFirst().orElseThrow(() -> new RuntimeException("No cart item found with given id"));
        filteredCartItem.setQuantity(cartRequest.getQuantity());
        CartItem savedCartItem = cartItemRepository.save(filteredCartItem);

        Product product = savedCartItem.getProduct();
        Integer quantity = savedCartItem.getQuantity();
        ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(), null, product.getPrice(), product.getImageUrl());
        CartItemResponse cartItemResponse = new CartItemResponse(savedCartItem.getId(), productResponse, quantity);
        cartResponse.getCartItemResponseList().add(cartItemResponse);

        return cartResponse;
    }

    @Override
    public Boolean isProductPresentInCart(Long id, Long productId) {
        boolean isCartPresent = cartRepository.existsById(id);
        if(!isCartPresent) throw new RuntimeException("No cart found with given id");

        return cartItemRepository.existsByCartIdAndProductId(id, productId);
    }

    @Override
    public Boolean removeItemFromCart(Long id, CartRequest cartRequest) {
        boolean isCartPresent = cartRepository.existsById(id);
        if(!isCartPresent) throw new RuntimeException("No cart found with given id");

        cartItemRepository.deleteById(cartRequest.getCartItemId());
        return true;
    }

    @Override
    public Boolean emptyCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("No cart found for user"));

        cartItemRepository.deleteByCart(cart);
        return true;
    }
}
