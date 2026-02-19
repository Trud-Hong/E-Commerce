package com.my.ecommerce.product;

import org.springframework.stereotype.Service;

import com.my.ecommerce.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  //상품 생성
  @Transactional
  public Long create(ProductRequest request, User seller) {

    Product product = Product.builder()
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .stock(request.getStock())
            .seller(seller)
            .build();
    productRepository.save(product);

    return product.getId();
    
  }

}
