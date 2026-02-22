package com.my.ecommerce.payment;

import com.my.ecommerce.order.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String paymentKey;

  private int amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  private Order order;

  private String tossOrderId;

  //엔티티 무결성을 위한 Builder 접근 제한, 정적 팩토리 메서드로 상태 초기화
  @Builder(access = AccessLevel.PRIVATE)
  private Payment(Order order, int amount, String tossOrderId){
    this.order = order;
    this.amount = amount;
    this.tossOrderId = tossOrderId;
    this.status = PaymentStatus.READY;
  }

  public static Payment create(Order order, int amount, String tossOrderId){
    return Payment.builder()
            .order(order)
            .amount(amount)
            .tossOrderId(tossOrderId)
            .build();
  }

  public void success(String paymentKey) {
    this.paymentKey = paymentKey;
    this.status = PaymentStatus.SUCCESS;
  }

  public void fail() {
    this.status = PaymentStatus.FAILED;
  }

  public boolean isSuccess(){
    return this.status == PaymentStatus.SUCCESS;
  }

}
