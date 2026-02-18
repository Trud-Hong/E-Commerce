import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

export const getProducts = () => {
  return axios.get(`${API_BASE_URL}/products`);
};