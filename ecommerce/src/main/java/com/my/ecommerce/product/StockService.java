package com.my.ecommerce.product;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.ecommerce.order.Order;
import com.my.ecommerce.order.OrderStatus;
import com.my.ecommerce.order.orderItem.OrderItem;
import com.my.ecommerce.payment.PaymentLog;
import com.my.ecommerce.payment.PaymentLogRepository;
import com.my.ecommerce.payment.PaymentLogStatus;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

  private final PaymentLogRepository paymentLogRepository;

  //동시성 충돌 시 재시도
  @Retryable(
    retryFor = {
      OptimisticLockException.class,
      ObjectOptimisticLockingFailureException.class
    },
    maxAttempts = 3,
    backoff = @Backoff(delay = 200)
  )
  @Transactional
  public void process(Order order, String paymentKey, int amount){
    if(order.getStatus() != OrderStatus.PAYMENT_PENDING){
      throw new IllegalArgumentException("이미 처리된 주문");
    }

    for(OrderItem item : order.getOrderItems()){
      Product product = item.getProduct();

      if(product.getStock() < item.getQuantity()){
        throw new IllegalArgumentException("재고 부족");
      }
      product.decreaseStock(item.getQuantity());
    }
    order.markAsPaid();

    paymentLogRepository.save(
      PaymentLog.builder()
          .order(order)
          .paymentKey(paymentKey)
          .amount(amount)
          .status(PaymentLogStatus.CONFIRMED)
          .message("토스 결제 승인 완료")
          .build()
    );
  }

}
