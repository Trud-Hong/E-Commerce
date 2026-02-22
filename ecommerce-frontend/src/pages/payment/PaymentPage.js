import React from 'react';
import api from "../../api/axios";

const clientKey = "test_ck_Poxy1XQL8RxwekyXja9lV7nO5Wml"

function PaymentPage(){

  const handlePayment = async () =>{
    try {
      //주문 생성
      const orderRes = await api.post(
        "/api/orders/from-cart",
        {},
        {withCredentials: true}
      );
      console.log("전체 응답:", JSON.stringify(orderRes.data))
      const orderId = orderRes.data.data;
      console.log("orderId:", orderId);
      console.log("orderId:", orderId, typeof orderId);

      //결제 요청 정보 조회
      const paymentRes = await api.post(
        "/api/payments/request",
        null,
        {
          params: {orderId: orderId}
        }
      );

      const paymentData = paymentRes.data.data;

      //토스 위젯 실행
      const tossPayments = window.TossPayments(clientKey);

      tossPayments.requestPayment("카드", {
        amount: paymentData.amount,
        orderId: paymentData.orderId,
        orderName: paymentData.orderName,
        customerName: paymentData.customerName,

        successUrl: "http://localhost:3000/payment/success",
        failUrl: "http://localhost:3000/payment/fail",
      });
    } catch (error) {
      console.error("결제 요청 실패:", error);
      alert("결제 요청 중 오류 발생");
    }
  };

  return (
    <div style={{padding:"50px"}}>
      <h2>결제 페이지</h2>
      <button onClick={handlePayment}>
        결제하기
      </button>
    </div>
  );
};

export default PaymentPage;