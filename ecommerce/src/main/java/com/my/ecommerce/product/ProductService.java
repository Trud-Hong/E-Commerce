package com.my.ecommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.my.ecommerce.user.User;
import com.my.ecommerce.user.UserRole;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  //상품 생성
  @Transactional
  public Long create(ProductRequest request, User seller) {

    if(seller.getRole() != UserRole.SELLER) {
      throw new IllegalStateException("판매자만 상품 등록 가능");
    }

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

  //상품 수정
  @Transactional
  public void update(Long productId, ProductRequest request, User seller) {
    Product product = productRepository.findById(productId)
              .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

    if(!product.getSeller().getId().equals(seller.getId())){
      throw new IllegalArgumentException("본인 상품만 수정할 수 있습니다.");
    }
    product.update(request.getName(), request.getDescription(), request.getPrice(), request.getStock());
  }

  //상품 삭제
  @Transactional
  public void delete(Long productId, User seller){
    Product product = productRepository.findById(productId)
              .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

    if(!product.getSeller().getId().equals(seller.getId())){
      throw new IllegalArgumentException("본인 상품만 삭제할 수 있습니다.");
    }
    productRepository.delete(product);
  }

  //상품 목록 조회
  public Page<ProductResponse> getList(int page) {

    Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));

    return productRepository.findAll(pageable).map(ProductResponse::from);
  }

  //상품 단건 조회
  public ProductResponse getProduct(Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
    return ProductResponse.from(product);
  }

}
