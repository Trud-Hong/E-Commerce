package com.my.ecommerce.payment;

public enum PaymentLogStatus {

  REQUESTED, //결제 요청 생성
  CONFIRMED, //결제 승인 생성
  FAILED,    //결제 실패
  WEBHOOK    //웹훅 수신
}
