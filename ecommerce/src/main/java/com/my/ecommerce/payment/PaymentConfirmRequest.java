package com.my.ecommerce.payment;

import lombok.Getter;

@Getter
public class PaymentConfirmRequest {

  private String paymentKey;
  private String orderId; //tossOrderId
  private int amount;

}
