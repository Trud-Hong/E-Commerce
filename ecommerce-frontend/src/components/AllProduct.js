import React, { useEffect, useState } from 'react';
import { getProducts } from '../api/productApi';
import ProductCard from './ProductCard';

function AllProduct(){
  const [products, setProducts] =useState([]);
  const [loading, setLodaing] = useState(true);

  useEffect(() => {
    getProducts()
      .then((response) => {
        const productArray = Array.isArray(response.data.data?.content)
        ? response.data.data.content
        :[];
        setProducts(productArray)
        setLodaing(false);
      })
      .catch((error) => {
        console.error("상품 불러오기 실패", error);
        setLodaing(false);
      });
  },[]);

  // if (loading) {
  //   return (
  //     <div className="d-flex justify-content-center align-items-center" style={{ height: "60vh" }}>
  //       <div className="spinner-border text-dark" role="status"></div>
  //     </div>
  //   );
  // }

  return (
    <section className='py-5'>
      <div className='container px-4 px-lg-5 mt-5'>
        <div className='row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center'>
          {products.map((product) => (
            <ProductCard key={product.id} product={product}/>
          ))}
        </div>
      </div>
    </section>
  );
};

export default AllProduct;