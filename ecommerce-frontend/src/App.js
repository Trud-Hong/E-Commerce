import { BrowserRouter, Routes, Route } from "react-router-dom";
import Signup from './pages/Signup';
import Home from "./pages/Home";
import ProductDetail from "./pages/ProductDetail";
import Login from "./pages/Login";
import { AuthProvider } from "./context/AuthContext";
import ProductCreateForm from "./pages/ProductCreateForm";
import Navbar from "./components/Navbar";
import AllProduct from "./components/AllProduct";

function App() {
  return (
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
      </Routes>
    </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
