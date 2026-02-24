import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import RelatedItem from '../components/RelatedItem';
import api from '../api/axios';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';

function ProductDetail() {
  const {id} = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [related, setRelated] = useState([]);
  const { addToCart } = useCart();
  const [quantity, setQuantity] =useState(1);
  const { isLoggedIn } = useAuth();
  const navigate = useNavigate();

  const handleAddToCart = async (e) => {
    e.stopPropagation();

    if(!isLoggedIn){
      alert("로그인이 필요합니다.");
      navigate("/login");
      return;
    }

    try {
      await addToCart(product.id,quantity);
      alert("장바구니에 담겼습니다.");
    } catch (error) {
      console.log(error);
      alert("장바구니 추가 실패");
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const productRes = await api.get(`/api/products/${id}`);
        const relatedRes = await api.get("api/products");

        setProduct(productRes.data.data);
        setRelated(relatedRes.data.data.content);
      } catch (error) {
        console.error(error);
      } finally{
        setLoading(false);
      }
    };
    fetchData();
  },[id]);

  if (loading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ height: "60vh" }}>
        <div className="spinner-border text-dark" role="status"></div>
      </div>
    );
  }
  if(!product) return <div className='text-center mt-5'>상품이 없습니다.</div>
  
  return (
    <div>
      {/* Product section */}
        <section className="py-5">
            <div className="container px-4 px-lg-5 my-5">
                <div className="row gx-4 gx-lg-5 align-items-center">
                    <div className="col-md-6"><img className="card-img-top mb-5 mb-md-0" src={product.imageUrl ? `http://localhost:8080${product.imageUrl}` : ''} alt={product.name} /></div>
                    <div className="col-md-6">
                        <h1 className="display-5 fw-bolder">{product.name}</h1>
                        <div className="fs-5 mb-5">
                            <span className="text-decoration-line-through">$45.00</span>
                            <span>{product.price}원</span>
                        </div>
                        <p className="lead">{product.description}</p>
                        <div className="d-flex">
                            <input className="form-control text-center me-3" 
                                    min={1} max={100} value={quantity} type="number" 
                                    onChange={(e) => {
                                      const value = Number(e.target.value);
                                      if(value < 1) return;
                                      setQuantity(value)
                                    }} 
                                    style={{maxWidth:"4rem"}} />
                            <button className="btn btn-outline-dark flex-shrink-0" onClick={handleAddToCart} type="button">
                                <i className="bi-cart-fill me-1"></i>
                                장바구니
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        {/* Related Product Section */}
        <RelatedItem products={related}/>
    </div>
  );
};

export default ProductDetail;