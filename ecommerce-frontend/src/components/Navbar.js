import { useAuth } from '../context/AuthContext';

const Navbar = () => {
    const {isLoggedIn, logout, user} = useAuth();

  return (
    <div>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container px-4 px-lg-5">
            <a className="navbar-brand" href="/">쇼핑몰 프로젝트</a>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span className="navbar-toggler-icon"></span></button>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                    {!isLoggedIn && (<li className="nav-item"><a className="nav-link" href="/login">로그인</a></li>)}
                    {isLoggedIn && (
                        <>
                            <li className="nav-item">
                                <a className="nav-link" onClick={logout} style={{cursor:'pointer'}}>로그아웃</a>
                            </li>
                            {user?.role === "SELLER" && (
                                <li className="nav-item"><a className="nav-link active" aria-current="page" href="/products/create">상품 등록</a></li>
                            )}
                        </>
                    )}
                    <li className="nav-item"><a className="nav-link active" aria-current="page" href="/">홈으로</a></li>
                    <li className="nav-item"><a className="nav-link" href="#!">About</a></li>
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">쇼핑</a>
                        <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a className="dropdown-item" href="/products/allproducts">모든 상품</a></li>
                            <li><hr className="dropdown-divider" /></li>
                            <li><a className="dropdown-item" href="#!">Popular Items</a></li>
                            <li><a className="dropdown-item" href="#!">New Arrivals</a></li>
                        </ul>
                    </li>
                </ul>
                <form className="d-flex">
                    <button className="btn btn-outline-dark" type="submit">
                        <i className="bi-cart-fill me-1"></i>
                        Cart
                        <span className="badge bg-dark text-white ms-1 rounded-pill">0</span>
                    </button>
                </form>
            </div>
        </div>
      </nav>
    </div>
  );
};

export default Navbar;