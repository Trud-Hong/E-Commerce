package com.my.ecommerce.global.exception;

public class OutOfStockException extends RuntimeException {
  public OutOfStockException(String message) {
    super(message);
  }
}
