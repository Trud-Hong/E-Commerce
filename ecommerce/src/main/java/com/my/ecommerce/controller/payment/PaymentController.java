package com.my.ecommerce.controller.payment;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.ecommerce.global.ApiResponse;
import com.my.ecommerce.payment.PaymentConfirmRequest;
import com.my.ecommerce.payment.PaymentResponse;
import com.my.ecommerce.payment.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/request")
  public ResponseEntity<?> requestPayment(@RequestParam Long orderId) {
      PaymentResponse paymentResponse = paymentService.requestPayment(orderId);
      return ResponseEntity.ok(ApiResponse.success(paymentResponse));
  }

  @PostMapping("/confirm")
  public ResponseEntity<?> confirmPayment(@RequestBody PaymentConfirmRequest request) {
      paymentService.confirmPayment(request.getOrderId(), request.getPaymentKey(), request.getAmount());
      return ResponseEntity.ok(ApiResponse.success("결제 완료"));
  }
  
  

}
