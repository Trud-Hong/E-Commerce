package com.my.ecommerce.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {

  private String orderId;
  private int amount;
  private String orderName;
  private String customerName;

}
