package com.my.ecommerce.controller.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.my.ecommerce.global.ApiResponse;
import com.my.ecommerce.global.storage.FileStorageService;
import com.my.ecommerce.product.ProductRequest;
import com.my.ecommerce.product.ProductService;
import com.my.ecommerce.user.UserDetail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  //상품 생성
  @PostMapping(value = "/create", consumes = "multipart/form-data")
  @PreAuthorize("hasRole('SELLER')")
  public ResponseEntity<?> create(
          @RequestPart("product") @Valid ProductRequest request,
          @RequestPart(value = "image", required = false) MultipartFile image,
          @AuthenticationPrincipal UserDetail userDetail) throws IOException {


      Long productId = productService.create(request,image, userDetail.getUser());
      
      return ResponseEntity.ok(ApiResponse.success(productId));
  }
  
  //상품 수정
  @PutMapping(value = "/{productId}", consumes = "multipart/form-data")
  @PreAuthorize("hasRole('SELLER')")
  public ResponseEntity<?> update(
            @PathVariable Long productId,
            @RequestPart("product") @Valid ProductRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @AuthenticationPrincipal UserDetail userDetail) throws IOException {

      productService.update(productId, request,image, userDetail.getUser());
      
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
