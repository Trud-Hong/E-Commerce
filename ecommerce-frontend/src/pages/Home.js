import React, { useEffect, useState } from 'react';
import Header from '../components/Header';
import Navbar from '../components/Navbar';
import ProductCard from '../components/ProductCard';
import { getProducts } from '../api/productApi';

function Home(){
  const [products, setProducts] = useState([]);
  const [loading, setLoding] = useState(true);

  useEffect(() => {
    getProducts()
      .then((response) => {
        setProducts(response.data);
        setLoding(false);
      })
      .catch((error) => {
        console.error("상품 불러오기 실패", error);
        setLoding(false);
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
    <>
      <Navbar/>
      <Header/>
      
      <section className='py-5'>
        <div className='container px-4 px-lg-5 mt-5'>
          <div className='row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center'>
            {products.map((product) => (
              <ProductCard key={product.id} product={product}/>
            ))}
          </div>
        </div>
      </section>
    </>
  );
};

export default Home;