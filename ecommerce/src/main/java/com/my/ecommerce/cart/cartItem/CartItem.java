package com.my.ecommerce.cart.cartItem;

import com.my.ecommerce.cart.Cart;
import com.my.ecommerce.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  private int quantity;

  public CartItem(Cart cart, Product product, int quantity){
    this.cart = cart;
    this.product = product;
    this.quantity = quantity;
  }

  public void increaseQuantity(int quantity){
    this.quantity = quantity;
  }

  public void decreaseQuantity(int quantity){
    this.quantity = quantity;
  }

  public void updateQuantity(int quantity) {
    this.quantity = quantity;
  }

}
