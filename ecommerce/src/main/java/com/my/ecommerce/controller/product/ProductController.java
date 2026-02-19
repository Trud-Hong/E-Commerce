package com.my.ecommerce.controller.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.global.ApiResponse;
import com.my.ecommerce.product.ProductRequest;
import com.my.ecommerce.product.ProductService;
import com.my.ecommerce.user.UserDetail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  //상품 생성
  @PostMapping("/create")
  @PreAuthorize("hasRole('SELLER')")
  public ResponseEntity<?> create(
          @RequestBody @Valid ProductRequest request, 
          @AuthenticationPrincipal UserDetail userDetail) {

      Long productId = productService.create(request, userDetail.getUser());
      
      return ResponseEntity.ok(ApiResponse.success(productId));
  }
  
  //상품 수정
  @PostMapping("/{productId}")
  @PreAuthorize("hasRole('SELLER')")
  public ResponseEntity<?> update(
            @PathVariable Long productId,
            @RequestBody @Valid ProductRequest request,
            @AuthenticationPrincipal UserDetail userDetail) {

      productService.update(productId, request, userDetail.getUser());
      
      return ResponseEntity.ok(ApiResponse.success("상품 수정 완료"));
  }
  
  //상품 삭제
  @DeleteMapping("/{productId}")
  @PreAuthorize("hasRole('SELLER')")
  public ResponseEntity<?> delete(
          @PathVariable Long productId,
          @AuthenticationPrincipal UserDetail userDetail
  ){

    productService.delete(productId, userDetail.getUser());

    return ResponseEntity.ok(ApiResponse.success("상품 삭제 완료"));
  }
  
  //상품 목록 조회
  @GetMapping
  public ResponseEntity<?> list(@RequestParam(defaultValue = "0") int page) {
    return ResponseEntity.ok(ApiResponse.success(productService.getList(page)));
  }

  @GetMapping("/{productId}")
  public ResponseEntity<?> getProduct(@PathVariable Long productId) {
      return ResponseEntity.ok(ApiResponse.success(productService.getProduct(productId)));
  }
  

}
