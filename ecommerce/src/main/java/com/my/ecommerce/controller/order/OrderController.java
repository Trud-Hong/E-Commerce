package com.my.ecommerce.controller.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.global.ApiResponse;
import com.my.ecommerce.order.OrderService;
import com.my.ecommerce.user.UserDetail;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/from-cart")
  public ResponseEntity<?> createOrderFromCart(@AuthenticationPrincipal UserDetail userDetail) {
      
      if(userDetail == null) {
        return ResponseEntity.status(401).body("인증 필요");
      }
      Long orderId = orderService.createOrderFromCart(userDetail.getUser().getId());
      return ResponseEntity.ok(ApiResponse.success(orderId));
  }
  

}
