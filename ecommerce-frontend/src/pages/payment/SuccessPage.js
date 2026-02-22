import React, { useEffect, useRef } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useCart } from '../../context/CartContext';
import { confirmPayment } from '../../api/paymentApi';

function SuccessPage(){

  const [searchParams] = useSearchParams();
  const hasConfirmed = useRef(false);
  const { fetchCart } = useCart();

  useEffect(() => {
    if(hasConfirmed.current) return;
    hasConfirmed.current = true;

    const paymentKey = searchParams.get("paymentKey");
    const orderId = searchParams.get("orderId");
    const amount = Number(searchParams.get("amount"));

    const handleConfirm = async () => {
      try {
        await confirmPayment({
          paymentKey,
          orderId,
          amount
        });
        await fetchCart();
        alert("결제 성공");
      } catch (error) {
        console.error(error);
        alert("결제 검증 실패");
      }
    };

    handleConfirm();

    },[searchParams, fetchCart]);
    return (
      <div>결제 완료</div>
    );
};

export default SuccessPage;