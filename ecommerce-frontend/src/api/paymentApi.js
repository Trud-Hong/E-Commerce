import api from "./axios"

export const confirmPayment = (data) => {
  return api.post("/api/payments/confirm", data);
};

export const failPayment = (tossOrderId) => {
  return api.post("/api/payments/fail", null, {
    params: { tossOrderId }
  });
};