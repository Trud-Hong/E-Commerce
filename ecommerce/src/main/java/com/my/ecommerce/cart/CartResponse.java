package com.my.ecommerce.cart;

import java.util.List;

import com.my.ecommerce.cart.cartItem.CartItemResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartResponse {

  private List<CartItemResponse> items;
  private int totalAmount;

}
