import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';

const ProductCard = ({product}) => {

    const navigate = useNavigate();
    const { addToCart } = useCart();

    const handleClick = () => {
        navigate(`/products/${product.id}`);
    };

    const handleAddToCart = async (e) => {
        e.stopPropagation();
    
        try {
            await addToCart(product.id,1);
            alert("장바구니에 담겼습니다.");
        } catch (error) {
            console.error(error);
            alert("장바구니 추가 실패");
        }
    };

  return (
      <div className="col mb-5" onClick={handleClick} style={{cursor:'pointer'}}>
          <div className="card h-100">
              {/* Product image */}
              <img className="card-img-top" 
              src={product.imageUrl} 
              alt={product.name} />
              {/* Product details */}
              <div className="card-body p-4">
                  <div className="text-center">
                      {/* Product name */}
                      <h5 className="fw-bolder">{product.name}</h5>
                      {/* Product price */}
                      {product.price.toLocaleString()}원
                  </div>
              </div>
              {/* Product actions */}
              <div className="card-footer p-4 pt-0 border-top-0 bg-transparent">
                  <div className="text-center">
                    <button className="btn btn-outline-dark mt-auto" onClick={handleAddToCart}>
                      장바구니
                    </button>
                  </div>
              </div>
          </div>
      </div>
  );
};

export default ProductCard;