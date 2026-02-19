package com.my.ecommerce.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @Min(0)
  private int price;
  @Min(0)
  private int stock;

}
