import api from "./axios"

//장바구니 조회
export const getCart = async () => {
  const response = await api.get('/api/cart');
  return response.data;
}

//장바구니 추가
export const addCartItem = async (productId, quantity) => {
  const response = await api.post("/api/cart/items", {
    productId,
    quantity
  });
  return response.data;
};

//장바구니 아이템 삭제
export const removeCartItem = async (CartItemId) => {
  await api.delete(`/api/cart/items/${CartItemId}`);
};

//수량 변경
export const updateCartItem = async (CartItemId, quantity) => {
  const response = await api.patch(`/api/cart/items/${CartItemId}`, {
    quantity
  });
  return response.data;
}