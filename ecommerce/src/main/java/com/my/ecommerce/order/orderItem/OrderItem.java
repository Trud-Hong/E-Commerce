package com.my.ecommerce.order.orderItem;

import com.my.ecommerce.order.Order;
import com.my.ecommerce.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  private int quantity;
  private int orderPrice;

  public OrderItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    this.orderPrice = product.getPrice();//현재 가격 스냅샷
  }

  public void setOrder(Order order){
    this.order = order;
  }

  public int getTotalPrice(){
    return this.orderPrice*this.quantity;
  }

}
