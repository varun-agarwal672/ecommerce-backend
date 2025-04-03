package com.application.ecommerce.cartItem;

import com.application.ecommerce.model.Cart;
import com.application.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    boolean existsByCartIdAndProductId(Long id, Long productId);

    @Transactional
    void deleteByCart(Cart cart);
}
