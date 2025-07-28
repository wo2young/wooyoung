import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  // 최초 로딩 시 localStorage에서 값 복구
  useEffect(() => {
    const logged = localStorage.getItem("isLoggedIn") === "true";
    const savedUser = JSON.parse(localStorage.getItem("loggedUser") || "null");
    setIsLoggedIn(logged);
    setUser(savedUser);

    // 디버깅용 로그
    console.log("AuthProvider mount: isLoggedIn =", logged, "user =", savedUser);
  }, []);

  const login = (id, pw) => {
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const foundUser = users.find(u => u.id === id && u.pw === pw);

    // 디버깅용 로그
    console.log("로그인 시도:", id, pw, "users:", users, "foundUser:", foundUser);

    if (foundUser) {
      setIsLoggedIn(true);
      setUser(foundUser);
      localStorage.setItem("isLoggedIn", "true");
      localStorage.setItem("loggedUser", JSON.stringify(foundUser));
      return true;
    } else {
      // 로그인 실패 시 로컬스토리지 정리
      setIsLoggedIn(false);
      setUser(null);
      localStorage.removeItem("isLoggedIn");
      localStorage.removeItem("loggedUser");
      return false;
    }
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("loggedUser");
    console.log("로그아웃 완료");
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
