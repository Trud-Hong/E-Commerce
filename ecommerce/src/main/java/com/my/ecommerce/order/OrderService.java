package com.my.ecommerce.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.ecommerce.cart.Cart;
import com.my.ecommerce.cart.CartRepository;
import com.my.ecommerce.cart.cartItem.CartItem;
import com.my.ecommerce.exception.OutOfStockException;
import com.my.ecommerce.order.orderItem.OrderItem;
import com.my.ecommerce.order.orderItem.OrderItemRepository;
import com.my.ecommerce.product.Product;
import com.my.ecommerce.user.User;
import com.my.ecommerce.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final OrderItemRepository orderItemRepository;


  //주문 생성 로직
  @Transactional
  public Long createOrderFromCart(Long userId) {
    //회원 조회
    User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
    //Cart 조회
    Cart cart = cartRepository.findByUser(user)
          .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));
    if(cart.getCartItems().isEmpty()){
      throw new IllegalArgumentException("장바구니 비어있음");
    }
    //주문 생성
    Order order = new Order(user);

    for(CartItem cartItem : cart.getCartItems()){
      Product product =cartItem.getProduct();

      if(product.getStock() < cartItem.getQuantity()){
        throw new OutOfStockException(product.getName() + " 재고 부족");
      }

      OrderItem orderItem = new OrderItem(
        product,
        cartItem.getQuantity()
      );
      order.addOrderItem(orderItem);
    }
    Order savedOrder = orderRepository.save(order);

    return savedOrder.getId();
  }

  //CartItem 비우기 로직
  @Transactional
  public void clearCart(User user) {

    Cart cart = cartRepository.findByUser(user)
        .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));
    cart.getCartItems().clear();
  }

  //주문 취소 로직
  @Transactional
  public void cancelOrder(Long orderId){
    //주문 조회
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("주문 정보 없음"));

    if(order.getStatus() == OrderStatus.CANCELLED){
      throw new IllegalArgumentException("이미 취소된 주문");
    }

    if(order.getStatus() == OrderStatus.SHIPPED ||
        order.getStatus() == OrderStatus.DELIVERED){
      throw new IllegalArgumentException("배송 이후 취소 불가");
    }
    //재고 복구
    for(OrderItem item : order.getOrderItems()){
      Product product = item.getProduct();
      product.increaseStock(item.getQuantity());
    }
    order.cancel();
  }

}
