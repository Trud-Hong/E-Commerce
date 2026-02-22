package com.my.ecommerce.product;

import java.time.LocalDateTime;

import com.my.ecommerce.exception.OutOfStockException;
import com.my.ecommerce.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private int stock;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id", nullable = false)
  private User seller;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Version
  private Long version = 0L;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }

  public void update(String name, String description, int price, int stock) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.updatedAt = LocalDateTime.now();
  }

  public void decreaseStock(int quantity) {
    if(this.stock < quantity) {
      throw new OutOfStockException("재고 부족");
    }
    this.stock -= quantity;
  }

  public void increaseStock(int quantity){
    this.stock += quantity;
  }

}
