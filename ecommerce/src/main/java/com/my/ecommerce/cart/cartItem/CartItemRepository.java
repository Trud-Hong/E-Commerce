package com.my.ecommerce.cart.cartItem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.ecommerce.cart.Cart;
import com.my.ecommerce.product.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}
