package com.my.ecommerce.order;


public enum OrderStatus {

  CREATED,        //주문 생성(결제 전) 
  PAYMENT_PENDING,//결제 대기
  PAID,           //결제 완료
  FAILED,         //결제 실패
  PREPARING,      //배송 준비
  SHIPPED,        //배송 중
  DELIVERED,      //배송 완료
  CANCELLED,      //주문 취소
  REFUNDED        //환불 완료

}
