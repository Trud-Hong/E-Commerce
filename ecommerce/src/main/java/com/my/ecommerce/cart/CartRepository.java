package com.my.ecommerce.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.ecommerce.user.User;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByUser(User user);

}
