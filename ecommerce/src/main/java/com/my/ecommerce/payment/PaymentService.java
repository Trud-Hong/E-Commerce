package com.my.ecommerce.payment;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.my.ecommerce.cart.Cart;
import com.my.ecommerce.cart.CartRepository;
import com.my.ecommerce.order.Order;
import com.my.ecommerce.order.OrderRepository;
import com.my.ecommerce.order.OrderStatus;
import com.my.ecommerce.product.StockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

  @Value("${toss.secret-key}")
  private String secretKey;

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;
  private final CartRepository cartRepository;
  private final PaymentLogRepository paymentLogRepository;
  private final StockService stockService;

  //결제 요청
  @Transactional
  public PaymentResponse requestPayment(Long orderId){
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("주문 정보 없음"));
    if(order.getStatus() != OrderStatus.CREATED) {
      throw new IllegalArgumentException("결제 요청 불가 상태");
    }

    int totalAmount = order.getTotalAmount();

    String tossOrderId = "ORDER_" + order.getId()+ "_" + UUID.randomUUID().toString();

    Payment payment = Payment.create(order, totalAmount, tossOrderId);
    paymentRepository.save(payment);

    order.changeStatus(OrderStatus.PAYMENT_PENDING);

    paymentLogRepository.save(
      PaymentLog.builder()
        .order(order)
        .amount(totalAmount)
        .status(PaymentLogStatus.REQUESTED)
        .message("결제 요청 생성")
        .build()
    );

    return new PaymentResponse(
      tossOrderId, 
      totalAmount, 
      "주문번호 " + order.getId(), 
      order.getUser().getName());
  }

  //결제 승인 완료 + 재고 차감
  @Transactional
  public void confirmPayment(String tossOrderId, String paymentKey, int amount) {
    
    Payment payment = paymentRepository.findByTossOrderId(tossOrderId)
        .orElseThrow(() -> new IllegalArgumentException("결제 정보 없음"));
    
    //금액 검증
    if(payment.getAmount() != amount) {
      throw new IllegalArgumentException("결제 금액 불일치");
    }

    if(payment.isSuccess()){
      return;
    }

    //토스 서버 검증
    verifyWithToss(paymentKey, tossOrderId, amount);
    payment.success(paymentKey);

    Order order = payment.getOrder();

    //동시성 충돌 시 재시도 3회
    stockService.process(order, paymentKey, amount);

    //CartItem 비우기
    Cart cart = cartRepository.findByUser(order.getUser())
        .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

    cart.getCartItems().clear();
  }

  //결제 실패 로직
  @Transactional
  public void failPayment(String tossOrderId){
    Payment payment = paymentRepository.findByTossOrderId(tossOrderId)
        .orElseThrow(() -> new IllegalArgumentException("결제 정보 없음"));

    Order order = payment.getOrder();
    //주문 상태 조회
    if(order.getStatus() != OrderStatus.PAYMENT_PENDING){
      throw new IllegalArgumentException("실패 처리 불가 상태");
    }

    payment.fail();
    order.changeStatus(OrderStatus.FAILED);
    
    //결제 실패 로그
    paymentLogRepository.save(
      PaymentLog.builder()
        .order(order)
        .status(PaymentLogStatus.FAILED)
        .message("결제 실패")
        .build()
    );
  }

  //토스 서버 간 검증
  private void verifyWithToss(String paymentKey, String tossOrderId, int amount){
    RestTemplate restTemplate = new RestTemplate();

    String credentials = secretKey + ":";
    String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Basic " + encoded);
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> body = new HashMap<>();
    body.put("paymentKey", paymentKey);
    body.put("orderId", tossOrderId);
    body.put("amount", amount);

    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

    restTemplate.postForEntity(
      "https://api.tosspayments.com/v1/payments/confirm", 
      request, 
      String.class
    );
  }
}
