import React from 'react';

const ProductCard = ({product}) => {
  return (
      <div className="col mb-5">
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
                    <button className="btn btn-outline-dark mt-auto">
                      장바구니
                    </button>
                  </div>
              </div>
          </div>
      </div>
  );
};

export default ProductCard;