import React, { useEffect, useState } from 'react';
import api from "../api/axios" ;

const clientKey = "test_ck_Poxy1XQL8RxwekyXja9lV7nO5Wml"

function Cart(){
  const [cart, setCart] = useState(null);

  useEffect(() => {
    fetchCart();
  },[]);

  const fetchCart = async () => {
    const res = await api.get("/api/cart", {
      withCredentials: true
    });
    setCart(res.data.data);
  };

  const updateQuantitiy = async (cartItemId, quantity) => {
    await api.patch(`/api/cart/items/${cartItemId}`, null, {
      params: {quantity},
      withCredentials: true,
    });
    fetchCart();
  };

  const removeItem = async (cartItemId) => {
    await api.delete(`/api/cart/items/${cartItemId}`,{
      withCredentials: true,
    });
    fetchCart();
  };

  //주문하기 버튼
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

  if(!cart) return <div>로딩중...</div>

  return (
    <div>
      <h2>장바구니</h2>

      {cart.items.length === 0 ? (
        <p>장바구니가 비어있습니다.</p>
      ) : (
        <>
          {cart.items.map((item) => (
            <div key={item.cartItemId}>
              <h4>{item.productName}</h4>
              <p>가격: {item.price}원</p>
              <p>
                수량:
                <button onClick={() => updateQuantitiy(item.cartItemId, item.quantity - 1)}>-</button>
                {item.quantity}
                <button onClick={() => updateQuantitiy(item.cartItemId, item.quantity + 1)}>+</button>
              </p>
              <p>합계: {item.totalPrice}원</p>
              <button onClick={() => removeItem(item.cartItemId)}>삭제</button>
              <hr/>
            </div>
          ))}

          <h3>총 금액: {cart.totalAmount}원</h3>

          <button onClick={handlePayment}>
            주문하기
          </button>
        </>
      )}
    </div>
  );
};

export default Cart;