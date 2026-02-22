package com.my.ecommerce.controller.cart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.cart.CartRequest;
import com.my.ecommerce.cart.CartResponse;
import com.my.ecommerce.cart.CartService;
import com.my.ecommerce.global.ApiResponse;
import com.my.ecommerce.user.UserDetail;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

  private final CartService cartService;

  //장바구니 생성
  @PostMapping("/items")
  public ResponseEntity<?> addToCart(
          @RequestBody CartRequest request,
          @AuthenticationPrincipal UserDetail userDetail) {
      
      cartService.addToCart(
          userDetail.getUser().getId(), 
          request.getProductId(), 
          request.getQuantity()
        );
      
      return ResponseEntity.ok(ApiResponse.success("장바구니 생성 성공"));
  }

  //장바구니 조회 
  @GetMapping
  public ResponseEntity<?> getCart(@AuthenticationPrincipal UserDetail userDetail) {

    CartResponse response = cartService.getCart(userDetail.getUser().getId());

    return ResponseEntity.ok(ApiResponse.success(response));
  }

  //장바구니 아이템 추가
  @PatchMapping("/items/{cartItemId}")
  public ResponseEntity<?> updateQuantity(
                  @PathVariable Long cartItemId,
                  @RequestParam int quantity,
                  @AuthenticationPrincipal UserDetail userDetail){

    cartService.updateQuantity(userDetail.getUser().getId(), cartItemId, quantity);
    return ResponseEntity.ok(ApiResponse.success("장바구니 물품 수량 변경"));
  }

  //장바구니 아이템 삭제
  @DeleteMapping("items/{cartItemId}")
  public ResponseEntity<?> removeItem(
                  @PathVariable Long cartItemId,
                  @AuthenticationPrincipal UserDetail userDetail) {
    cartService.removeItem(userDetail.getUser().getId(), cartItemId);
    return ResponseEntity.ok(ApiResponse.success("장바구니 물품 삭제 성공"));
  }
  
  

}
