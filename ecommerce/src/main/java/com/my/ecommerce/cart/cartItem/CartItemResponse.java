package com.my.ecommerce.cart.cartItem;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {

  private Long cartItemId;
  private Long productId;
  private String name;
  private String imageUrl;
  private int price;
  private int quantity;
  private int totalPrice;

  public static CartItemResponse from(CartItem cartItem) {
    return CartItemResponse.builder()
            .cartItemId(cartItem.getId())
            .productId(cartItem.getProduct().getId())
            .name(cartItem.getProduct().getName())
            .imageUrl(cartItem.getProduct().getImageUrl())
            .price(cartItem.getProduct().getPrice())
            .quantity(cartItem.getQuantity())
            .totalPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity())
            .build();
  }

}
