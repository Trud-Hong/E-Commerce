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
  private String sellerName;
  private LocalDateTime createdAt;

  public static ProductResponse from(Product product) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .stock(product.getStock())
            .sellerName(product.getSeller().getName())
            .build();
  }

}
