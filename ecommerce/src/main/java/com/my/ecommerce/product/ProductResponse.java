package com.my.ecommerce.product;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {

  private Long id;
  private String name;
  private String description;
  private int price;
  private int stock;
  private String sellerEmail;
  private LocalDateTime createdAt;

}
