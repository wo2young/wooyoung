// src/contexts/AuthContext.js
import React, { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();
const USER_KEY = "currentUser"; // 통일된 키 사용

export function AuthProvider({ children }) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  // 앱 로드 시 자동 로그인 처리
  useEffect(() => {
    const savedUser = JSON.parse(localStorage.getItem(USER_KEY) || "null");
    if (savedUser) {
      setUser(savedUser);
      setIsLoggedIn(true);
      console.log("✅ 자동 로그인 상태:", savedUser);
    } else {
      setUser(null);
      setIsLoggedIn(false);
      console.log("🔒 로그인된 사용자 없음");
    }
  }, []);

  // 로그인 함수
  const login = (id, pw) => {
    const users = JSON.parse(localStorage.getItem("users") || "[]");
    const foundUser = users.find(u => u.id === id && u.pw === pw);

    if (foundUser) {
      setIsLoggedIn(true);
      setUser(foundUser);
      localStorage.setItem(USER_KEY, JSON.stringify(foundUser));
      console.log("✅ 로그인 성공:", foundUser);
      return true;
    } else {
      setIsLoggedIn(false);
      setUser(null);
      localStorage.removeItem(USER_KEY);
      console.warn("❌ 로그인 실패: 아이디 또는 비밀번호 불일치");
      return false;
    }
  };

  // 로그아웃 함수
  const logout = () => {
    setIsLoggedIn(false);
    setUser(null);
    localStorage.removeItem(USER_KEY);
    console.log("👋 로그아웃 완료");
  };

  return (
    <AuthContext.Provider value={{ isLoggedIn, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

// 편하게 쓰는 커스텀 훅
export function useAuth() {
  return useContext(AuthContext);
}
