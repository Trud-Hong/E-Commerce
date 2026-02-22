package com.my.ecommerce.cart.cartItem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {

  private Long cartItemId;
  private Long productId;
  private String name;
  private int price;
  private int quantity;
  private int totalPrice;

  public static CartItemResponse from(CartItem cartItem) {
    return CartItemResponse.builder()
            .cartItemId(cartItem.getId())
            .productId(cartItem.getProduct().getId())
            .name(cartItem.getProduct().getName())
            .price(cartItem.getProduct().getPrice())
            .quantity(cartItem.getQuantity())
            .totalPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity())
            .build();
  }

}
