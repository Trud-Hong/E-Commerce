package com.my.ecommerce.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.my.ecommerce.order.orderItem.OrderItem;
import com.my.ecommerce.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  private int totalAmount;

  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(unique = true)
  private String paymentKey;

  @Version
  private Long version = 0L;

  //생성자
  public Order(User user) {
    this.user = user;
    this.status = OrderStatus.CREATED;
    this.createdAt = LocalDateTime.now();
  }

  //연관관계 편의 메서드
  public void addOrderItem(OrderItem orderItem){
    orderItems.add(orderItem);
    orderItem.setOrder(this);
    this.totalAmount += orderItem.getTotalPrice();
  }

  //상태 변경
  public void changeStatus(OrderStatus status) {
    this.status = status;
  }

  //결제 완료 상태
  public void markAsPaid(){
    if(this.status != OrderStatus.PAYMENT_PENDING){
      throw new IllegalArgumentException("결제 불가 상태");
    }
    this.status = OrderStatus.PAID;
  }
  //취소 상태
  public void cancel() {
    if(this.status == OrderStatus.SHIPPED || this.status == OrderStatus.DELIVERED){
      throw new IllegalArgumentException("배송 이후 취소 불가");
    }
    this.status = OrderStatus.CANCELLED;
  }

}
