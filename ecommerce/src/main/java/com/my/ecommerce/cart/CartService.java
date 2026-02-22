package com.my.ecommerce.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.ecommerce.cart.cartItem.CartItem;
import com.my.ecommerce.cart.cartItem.CartItemRepository;
import com.my.ecommerce.cart.cartItem.CartItemResponse;
import com.my.ecommerce.exception.OutOfStockException;
import com.my.ecommerce.product.Product;
import com.my.ecommerce.product.ProductRepository;
import com.my.ecommerce.user.User;
import com.my.ecommerce.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;

  //CartItem 생성
  @Transactional
  public void addToCart(Long userId, Long productId, int quantity) {
    User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
    Cart cart = cartRepository.findByUser(user)
          .orElseGet(() -> cartRepository.save(new Cart(user)));
    Product product = productRepository.findById(productId)
          .orElseThrow(() -> new IllegalArgumentException("상품 없음"));
    
    if(product.getStock() < quantity) {
      throw new OutOfStockException("재고 부족");
    }

    Optional<CartItem> existingItem = 
          cartItemRepository.findByCartAndProduct(cart, product);

    if(existingItem.isPresent()) {
      existingItem.get().increaseQuantity(quantity);
    }else {
      CartItem cartItem = new CartItem(cart, product, quantity);
      cartItemRepository.save(cartItem);
    }
  }

  //CartItem 수량 수정
  @Transactional
  public void updateQuantity(Long userId, Long cartItemId, int quantity){

    CartItem cartItem = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new IllegalArgumentException("장바구니 상품 없음"));
    if(!cartItem.getCart().getUser().getId().equals(userId)){
      throw new IllegalArgumentException("본인 장바구니 상품만 수정 가능");
    }

    if(quantity <= 0) {
      cartItemRepository.delete(cartItem);
      return;
    }

    Product product = cartItem.getProduct();

    if(product.getStock() < quantity) {
      throw new OutOfStockException("재고 부족");
    }

    cartItem.updateQuantity(quantity);
  }

  //CartItem 삭제
  @Transactional
  public void removeItem(Long userId, Long cartItemId){

    CartItem cartItem = cartItemRepository.findById(cartItemId)
        .orElseThrow(() -> new IllegalArgumentException("장바구니 상품 없음"));
    if(!cartItem.getCart().getUser().getId().equals(userId)){
      throw new IllegalArgumentException("본인 장바구니만 삭제 가능");
    }
    cartItemRepository.delete(cartItem);
  }

  //Cart, CartItem 조회
  @Transactional(readOnly = true)
  public CartResponse getCart(Long userId) {

    User user = userRepository.findById(userId)
          .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
    Cart cart = cartRepository.findByUser(user)
          .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));
    
    List<CartItemResponse> items = cart.getCartItems()
        .stream()
        .map(CartItemResponse::from)
        .toList();
    
    int totalAmount = items.stream()
        .mapToInt(CartItemResponse::getTotalPrice)
        .sum();

    return CartResponse.builder()
            .items(items)
            .totalAmount(totalAmount)
            .build();

  }

}
