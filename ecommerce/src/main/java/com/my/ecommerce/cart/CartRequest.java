package com.my.ecommerce.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequest {

  private Long productId;
  private int quantity;

}
