import { BrowserRouter, Routes, Route } from "react-router-dom";
import Signup from './pages/Signup';
import Home from "./pages/Home";
import ProductDetail from "./pages/ProductDetail";
import Login from "./pages/Login";
import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <BrowserRouter>
    <AuthProvider>
      <Routes>
        <Route path="/signup" element={<Signup />} />
        <Route path="/" element={<Home/>}/>
        <Route path="/prdoucts/:id" element={<ProductDetail/>}/>
        <Route path="/login" element={<Login/>}/>
      </Routes>
    </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
