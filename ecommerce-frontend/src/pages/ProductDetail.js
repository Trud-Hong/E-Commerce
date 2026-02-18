import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import RelatedItem from '../components/RelatedItem';
import api from '../api/axios';

function ProductDetail() {
  const {id} = useParams();
  const [proudct, setProduct] = useState(null);
  //const [loading, setLoding] = useState(true);
  const [related, setRelated] = useState([]);

  useEffect(() => {
    api.get("/api/products")
      .then(res => setRelated(res.data));
  }, []);

  useEffect(() => {
    api.get(`/api/products/${id}`)
      .then(res => {
        setProduct(res.data);
        //setLoading(false);
      })
      .catch(err => {
        console.error(err);
        //setLoading(false);
      });
  }, [id]);

  // if (loading) {
  //   return (
  //     <div className="d-flex justify-content-center align-items-center" style={{ height: "60vh" }}>
  //       <div className="spinner-border text-dark" role="status"></div>
  //     </div>
  //   );
  // }
  if(!proudct) return <div className='text-center mt-5'>상품이 없습니다.</div>
  
  return (
    <div>
      {/* Product section */}
        <section className="py-5">
            <div className="container px-4 px-lg-5 my-5">
                <div className="row gx-4 gx-lg-5 align-items-center">
                    <div className="col-md-6"><img className="card-img-top mb-5 mb-md-0" src="https://dummyimage.com/600x700/dee2e6/6c757d.jpg" alt="..." /></div>
                    <div className="col-md-6">
                        <div className="small mb-1">ID: {proudct.id}</div>
                        <h1 className="display-5 fw-bolder">{proudct.name}</h1>
                        <div className="fs-5 mb-5">
                            <span className="text-decoration-line-through">$45.00</span>
                            <span>{proudct.price}원</span>
                        </div>
                        <p className="lead">{proudct.description}</p>
                        <div className="d-flex">
                            <input className="form-control text-center me-3" id="inputQuantity" type="number" defaultValue="1" style={{maxwidth:"3rem"}} />
                            <button className="btn btn-outline-dark flex-shrink-0" type="button">
                                <i class="bi-cart-fill me-1"></i>
                                Add to cart
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