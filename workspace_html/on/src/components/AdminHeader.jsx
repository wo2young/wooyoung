import React from "react";
import { useNavigate } from "react-router-dom"; // 추가!
import "../Styles/AdminHeader.css";
import logoImg from '../assets/logo.png';

function AdminHeader({ onLogout }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    // 1. 유저 로그아웃 처리
    localStorage.removeItem('currentUser');
    if (onLogout) onLogout(); // AuthContext 쓰면 전달받은 콜백도 호출
    // 2. 홈 또는 로그인페이지로 이동 (선택)
    navigate('/login');
    // 3. 이로 인해 부모에서 isAdmin/isLoggedIn이 false가 되면서, 일반 Header가 렌더링됨
  };

  return (
    <header className="support-header">
      <div className="header-left">
        <a href="/">
          <img src={logoImg} alt="한끼온 로고" className="header-logo-img" />
        </a>
      </div>
      <div className="header-nav-wrapper">
        <nav className="header-nav">
          <a href="/matching">매칭페이지</a>
          <a href="/community">게시판</a>
          <a href="/support">고객지원</a>
          <a href="/mileage">마일리지 사용처</a>
          <a href="/warmthmap">온기지도</a>
          <a href="/admin" className="admin-nav-link">
            관리자 모드
          </a>
        </nav>
      </div>
      <div className="header-right">
        <button className="header-login-btn" onClick={handleLogout}>
          로그아웃
        </button>
      </div>
    </header>
  );
}

export default AdminHeader;
