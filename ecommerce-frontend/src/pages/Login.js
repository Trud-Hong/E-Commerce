import React, { useState } from 'react';
import {Link, useNavigate} from "react-router-dom";
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';

const Login = () => {
  const navigate = useNavigate();
  const {login} = useAuth();

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [error, setError] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try{
      const response = await api.post("/api/auth/login", formData);
      const token = response.data.data.accessToken;
      login(token);
      navigate("/");
    }catch(err){
      if(err.response?.data?.errors){
        const firstError = Object.values(err.response.data.errors)[0];
        setError(firstError);
      }else {
        setError("로그인 실패");
      }
    }
  };

  return (
    <div className='container mt-5' style={{maxWidth: "400px"}}>
      <h2 className='mb-4 text-center'>로그인</h2>

      <form onSubmit={handleSubmit}>
        <div className='mb-3'>
          <label className='form-label'>이메일</label>
          <input 
            type='text' 
            className='form-control' 
            name='email' 
            value={formData.email} 
            onChange={handleChange}/>
        </div>
        
        <div className='mb-3'>
          <label className='form-label'>비밀번호</label>
          <input 
            type='password' 
            className='form-control' 
            name='password' 
            value={formData.password} 
            onChange={handleChange}/>
        </div>

        {error && <p style={{color:"red"}}>{error}</p>}

        <button type='submit' className='btn btn-outline-secondary w-100 mb-2'>로그인</button>
        <Link to="/signup" className="btn btn-dark w-100">회원가입</Link>
      </form>
    </div>
  );
};

export default Login;