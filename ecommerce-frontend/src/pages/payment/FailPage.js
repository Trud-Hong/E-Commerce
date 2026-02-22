import React, { useEffect, useRef } from 'react';
import { useSearchParams } from 'react-router-dom';
import { failPayment } from '../../api/paymentApi';

function FailPage (){

  const [searchParams] = useSearchParams();
  const hasFailed = useRef(false);

  useEffect(() => {
    if(hasFailed.current) return;
    hasFailed.current = true;

    const tossOrderId = searchParams.get("orderId");

    const handleFail = async () => {
      try {
        await failPayment(tossOrderId)
        alert("결제 실패 처리 완료");
      } catch (error) {
        console.error(error);
        alert("실패 처리 중 오류 발생"); 
      }
    };
    handleFail();
  },[searchParams]);

  return <h2>결제 실패</h2>
};

export default FailPage;