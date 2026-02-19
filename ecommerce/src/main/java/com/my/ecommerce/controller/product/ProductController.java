package com.my.ecommerce.controller.product;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.global.ApiResponse;
import com.my.ecommerce.product.ProductRequest;
import com.my.ecommerce.product.ProductService;
import com.my.ecommerce.user.User;
import com.my.ecommerce.user.UserDetail;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  @PreAuthorize("hasRole('SELLER')")
  public ResponseEntity<?> create(
          @RequestBody @Valid ProductRequest request, 
          Authentication authentication) {
      
      UserDetail userDetail = (UserDetail) authentication.getPrincipal();
      User seller = userDetail.getUser();

      Long productId = productService.create(request, seller);
      
      return ResponseEntity.ok(ApiResponse.success(productId));
  }
  

}
