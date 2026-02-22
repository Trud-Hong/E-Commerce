import { BrowserRouter, Routes, Route } from "react-router-dom";
import Signup from './pages/Signup';
import Home from "./pages/Home";
import ProductDetail from "./pages/ProductDetail";
import Login from "./pages/Login";
import { AuthProvider } from "./context/AuthContext";
import ProductCreateForm from "./pages/ProductCreateForm";
import Navbar from "./components/Navbar";
import AllProduct from "./components/AllProduct";
import PaymentPage from "./pages/payment/PaymentPage";
import SuccessPage from "./pages/payment/SuccessPage";
import FailPage from "./pages/payment/FailPage";
import Cart from "./pages/Cart";
import { CartProvider } from "./context/CartContext";

function App() {
  return (
    <CartProvider>
    <BrowserRouter>
      <AuthProvider>
        <Navbar/>
        <Routes>
          <Route path="/signup" element={<Signup />} />
          <Route path="/" element={<Home/>}/>
          <Route path="/products/:id" element={<ProductDetail/>}/>
          <Route path="/login" element={<Login/>}/>
          <Route path="/products/create" element={<ProductCreateForm/>}/>
          <Route path="/products/allproducts" element={<AllProduct/>}/>
          <Route path="/payments" element={<PaymentPage/>}/>
          <Route path="/payment/success" element={<SuccessPage/>}/>
          <Route path="/payment/fail" element={<FailPage/>}/>
          <Route path="/cart" element={<Cart/>}/>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
    </CartProvider>
  );
}

export default App;
