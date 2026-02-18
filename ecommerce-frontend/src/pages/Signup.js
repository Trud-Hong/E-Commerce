import React, { useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import api from "../api/axios";

const Signup = () => {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [name, setName] = useState("");
  const [errors, setErrors] =useState({});
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    setErrors({});

    try {
      const res = await api.post("/api/user/signup", {
        email,
        password1: password,
        password2: passwordConfirm,
        name
      });

      if(res.data.success){
        alert("회원가입이 완료되었습니다.")
        navigate("/login");
      }

    } catch (error) {
      if(error.response?.data?.errors) {
        setErrors(error.response.data.errors);
      }else {
        alert("서버 오류 발생");
      }
    }
  };

  return (
    // 회원가입 양식
    <div className='container mt-5' style={{maxWidth: "400px"}}>
      <h2 className='mb-4 text-center'>회원가입</h2>
    
    <form onSubmit={handleSubmit}>
      <div className='mb-3'>
        <label className='form-label'>아이디</label>
        <input className='form-control' type='email' placeholder='이메일' value={email} 
        onChange={(e)=>{setEmail(e.target.value); 
        setErrors(prev => ({ ...prev, email: null }));}}/>
        {errors.email && (<p style={{color: "red"}}>{errors.email}</p>)}
      </div>
      <div className='mb-3'>
        <label className='form-label'>비밀번호</label>
        <input className='form-control' type='password' placeholder='비밀번호' value={password} 
        onChange={(e)=>{setPassword(e.target.value);
        setErrors(prev => ({ ...prev, password1: null}));}}/>
        {errors.password1 && (<p style={{color: "red"}}>{errors.password1}</p>)}
      </div>

      <div className='mb-3'>
        <label className='form-label'>비밀번호 확인</label>
        <input className='form-control' type='password' placeholder='비밀번호 확인' value={passwordConfirm} 
        onChange={(e)=>{setPasswordConfirm(e.target.value);
        setErrors(prev => ({...prev, password2: null}));}}/>
        {errors.password2 && (<p style={{color: "red"}}>{errors.password2}</p>)}
      </div>

      <div className='mb-3'>
        <label className='form-label'>이름</label>
        <input className='form-control' type='text' placeholder='이름' value={name} 
        onChange={(e)=>{setName(e.target.value);
        setErrors(prev => ({...prev, name: null}));}}/>
        {errors.name && (<p style={{color: "red"}}>{errors.name}</p>)}
      </div>

      <button className='btn btn-dark w-100 mb-2' type='submit'>회원가입</button>
    </form>
    <Link to="/" className='btn btn-outline-secondary w-100'>취소</Link>
    </div>
  );
};

export default Signup;