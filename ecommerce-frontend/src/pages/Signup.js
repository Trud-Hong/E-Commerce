import React, { useState } from 'react';
import api from "../api/axios";

const Signup = () => {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [name, setName] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if(password !== passwordConfirm) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      await api.post("api/users/signup", {
        email,
        password,
        name
      });

      alert("회원가입 성공!");
    } catch (error) {
      console.error(error);
      alert("회원가입 실패");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>회원가입</h2>

      <input
        type='email'
        placeholder='이메일'
        value={email}
        onChange={(e)=>setEmail(e.target.value)}
      />

      <input
        type='password'
        placeholder='비밀번호'
        value={password}
        onChange={(e)=>setPassword(e.target.value)}
      />

      <input
        type='password'
        placeholder='비밀번호 확인'
        value={passwordConfirm}
        onChange={(e)=>setPasswordConfirm(e.target.value)}
      />

      <input
        type='text'
        placeholder='이름'
        value={name}
        onChange={(e)=>setName(e.target.value)}
      />

      <button type='submit'>회원가입</button>
    </form>
  );
};

export default Signup;