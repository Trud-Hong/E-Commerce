import React, { createContext, useContext, useEffect, useState } from 'react';
import api from '../api/axios';
import { addCartItem, getCart } from '../api/cartApi';

const CartContext = createContext();

export const CartProvider = ({ children }) => {

  const [cartCount, setCartCount] = useState(0);

  //장바구니 조회
  const fetchCart = async () => {
    try {
      const res = await getCart();
      const items = res.data.items;

      const totalQuantity = items.reduce(
        (sum, item) => sum + item.quantity,
        0
      );
      setCartCount(totalQuantity);
    } catch (error) {
      console.error("장바구니 조회 실패", error);
      setCartCount(0);
    }
  };

  //장바구니 추가
  const addToCart = async (productId, quantity = 1) => {
    try {
      await addCartItem(productId, quantity);
      await fetchCart();
    } catch (error) {
      console.error("장바구니 추가 실패", error);
    }
  };

  useEffect(() => {
    fetchCart();
  },[]);

  return (
    <CartContext.Provider value={{
      cartCount,
      fetchCart,
      setCartCount,
      addToCart
    }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
