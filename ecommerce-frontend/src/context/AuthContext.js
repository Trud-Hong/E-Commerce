import { createContext, useContext, useEffect, useState } from "react";
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext();

export const AuthProvider = ({children}) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const parseRole = (role) => role?.replace("ROLE_", "") ?? null;

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if(token){
      try {
        const decoded = jwtDecode(token);
        setUser({id: decoded.sub, email: decoded.email, role: parseRole(decoded.role)});
        setIsLoggedIn(true);
      } catch (error) {
        console.error("유효하지 않는 토큰", error);
        localStorage.removeItem("accessToken");
      }
    }
  },[]);

  const login = (token) => {
    localStorage.setItem("accessToken", token);
    try {
      const decoded = jwtDecode(token);
      setUser({id: decoded.sub, email: decoded.email, role: parseRole(decoded.role)});
      setIsLoggedIn(true);  
    } catch (error) {
      console.error("유효하지 않는 토큰", error);
    }
  };

  const logout = () => {
    localStorage.removeItem("accessToken");
    setUser(null);
    setIsLoggedIn(false);
  };

  return (
    <AuthContext.Provider value={{isLoggedIn, user, login, logout}}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);