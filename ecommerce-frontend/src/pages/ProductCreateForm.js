import { useState } from "react";
import {useNavigate} from "react-router-dom";
import api from "../api/axios";

export default function ProductCreateForm() {
  const navigate = useNavigate();
  const [image, setImage] = useState(null);
  const [form, setForm] = useState({
    name: "",
    description: "",
    price: "",
    stock: ""
  });

  const handleChange = (e) => {
    const {name, value} = e.target;
    setForm((prev) => ({...prev, [name]: value}));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const token = localStorage.getItem("accessToken");

      const formData = new FormData();

      formData.append(
        "product",
        new Blob([JSON.stringify(form)], {type: "application/json"})
      );

      formData.append("image", image);

      await api.post(
        "/api/products/create",
        formData,
        {headers: {Authorization: `Bearer ${token}`}}
      );
      alert("상풍 등록 완료!");
      navigate("/");
    } catch (err) {
      console.error(err);
      alert("상품 등록 실패");
    }
  };

  return (
    <div className="container my-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow-sm border-0">
            <div className="card-header text-center fs-4 fw-bold bg-light">
              상품 등록
            </div>
            <div className="card-body bg-white">
              <form onSubmit={handleSubmit} className="d-flex flex-column gap-3">
                <div className="mb-3">
                  <label className="form-label">상품명</label>
                  <input type="text" name="name" className="form-control"
                  placeholder="상품명" value={form.name} onChange={handleChange} required/>
                </div>
                <div className="mb-3">
                  <label className="form-label">설명</label>
                  <textarea name="description" value={form.description} className="form-control"
                  placeholder="설명" rows={4} onChange={handleChange} required/>
                </div>
                <div className="mb-3">
                  <label className="form-label">가격</label>
                  <input type="number" name="price" className="form-control"
                  placeholder="가격" value={form.price} onChange={handleChange} required/>
                </div>
                <div className="mb-3">
                  <label className="form-label">재고</label>
                  <input type="number" name="stock" className="form-control"
                  placeholder="재고 수량" value={form.stock} onChange={handleChange} required/>
                </div>
                <div className="mb-3">
                  <label className="form-label">상품 이미지</label>
                  <input type="file" className="form-control" accept="image/*"
                  onChange={(e) => setImage(e.target.files[0])} required/>
                </div>
                <button type="submit" className="btn btn-dark fw-bold">등록</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}