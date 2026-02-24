import React, { useEffect, useState } from 'react';
import api from "../api/axios" ;
import { useCart } from '../context/CartContext';

const clientKey = "test_ck_Poxy1XQL8RxwekyXja9lV7nO5Wml"

function Cart(){
  const [cart, setCart] = useState(null);
  const { setCartCount } = useCart();

  useEffect(() => {
    fetchCart();
  },[]);
  //장바구니 생성
  const fetchCart = async () => {
    const res = await api.get("/api/cart", {
      withCredentials: true
    });
    setCart(res.data.data);
    const totalQuantity = res.data.data.items.reduce((sum, item) => sum + item.quantity, 0);
    setCartCount(totalQuantity);
  };

  //장바구니 아이템 수량 변경
  const updateQuantitiy = async (cartItemId, quantity) => {
    try {
      await api.patch(`/api/cart/items/${cartItemId}`, null, {
      params: {quantity},
      withCredentials: true,
    });
    fetchCart();  
    } catch (error) {
      if(error.response?.data?.message === "재고 부족"){
        alert("재고가 부족합니다.");
      }else{
        alert("수량 변경 실패");
      }
    }
    
  };
  //장바구니 삭제
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
    <div className='container mt-5'>
      <h2 className='mb-4'>장바구니</h2>

      {cart.items.length === 0 ? (
        <div className='alert alet-secondary text-center'>
          장바구니가 비어있습니다.
        </div>
      ) : (
        <>
          {cart.items.map((item) => (
            <div key={item.cartItemId} className='card mb-3 shadow-sm'>
              <div className='row g-0 align-items-center'>
                {/* 상품 이미지 */}
                <div className='col-md-3 text-center p-3'>
                  <img src={item.imageUrl ? `http://localhost:8080${item.imageUrl}` : ''} alt={item.name} className='img-fluid rounded' style={{maxHeight: "120px"}}/>
                </div>
                {/* 상품 정보 */}
                <div className='col-md-6'>
                  <div className='card-body'>
                    <h5 className='card-title'>{item.name}</h5>
                    <p className='card-text text-muted'>가격: {item.price}원</p>
                    <div className='d-flex align-items-center'>
                      <button className='btn btn-outline-secondary btn-sm' onClick={() => updateQuantitiy(item.cartItemId, item.quantity - 1)}>-</button>
                      <span className='mx-3 fw-bold'>{item.quantity}</span>
                      <button className='btn btn-outline-secondary btn-sm' onClick={() => updateQuantitiy(item.cartItemId, item.quantity + 1)}>+</button>
                    </div>
                  </div>
                </div>
              {/* 합계 + 삭제 */}
              </div>
              <div className='col-md-3 text-center'>
                <div className='card-body'>
                  <h6 className='fw-bold'>
                    {item.totalPrice.toLocaleString()}원
                  </h6>
                  <button className='btn btn-danger btn-sm mt-2' onClick={() => removeItem(item.cartItemId)}>삭제</button>
                </div>
              </div>
            </div>
          ))}
          {/* 총 금액 */}
          <div className='card p-4 mt-4 shadow-sm'>
            <h4>총 금액: {cart.totalAmount.toLocaleString()}원</h4>
          </div>
            <button className='btn btn-dark mt-3' onClick={handlePayment}>
              주문하기
            </button>
        </>
      )}
    </div>
  );
};

export default Cart;