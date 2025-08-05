import { Link, useNavigate } from 'react-router-dom';
import logoImg from '../assets/logo.png';
import { useAuth } from '../contexts/AuthContext';
import { useState } from 'react';
import '../Styles/Header.css';

function Header() {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  // 모바일 메뉴가 열려 있을 때, 메뉴 클릭 시 자동 닫힘
  const handleMenuClick = () => setMobileMenuOpen(false);

  return (
    <>
      <header className="support-header">
        <div className="header-left">
          <Link to="/">
            <img src={logoImg} alt="한끼온 로고" className="header-logo-img" />
          </Link>
        </div>
        {/* PC/태블릿 네비 (1201px 이상에서만 표시) */}
        <div className="header-nav-wrapper">
          <nav className="header-nav">
            <Link to="/matching">매칭페이지</Link>
            <Link to="/community">게시판</Link>
            <Link to="/support">고객지원</Link>
            <Link to="/mileage">마일리지 사용처</Link>
            <Link to="/warmthmap">온기지도</Link>
          </nav>
        </div>
        {/* 햄버거 (1200px 이하에서만 표시) */}
        <button
          className={`header-hamburger${mobileMenuOpen ? ' open' : ''}`}
          aria-label="메뉴"
          type="button"
          onClick={() => setMobileMenuOpen(prev => !prev)}
        >
          <span />
          <span />
          <span />
        </button>
        <div className="header-right">
          {/* 마이페이지 제거! */}
          <button
            className="header-login-btn"
            onClick={() => {
              if (isLoggedIn) {
                logout();
                navigate('/');
              } else {
                navigate('/login');
              }
            }}
          >
            {isLoggedIn ? '로그아웃' : '로그인'}
          </button>
        </div>
      </header>

      {/* 모바일 네비(햄버거 클릭 시 드롭다운) */}
      <nav className={`header-mobile-menu${mobileMenuOpen ? ' show' : ''}`}>
        <Link to="/matching" onClick={handleMenuClick}>매칭페이지</Link>
        <Link to="/community" onClick={handleMenuClick}>게시판</Link>
        <Link to="/support" onClick={handleMenuClick}>고객지원</Link>
        <Link to="/mileage" onClick={handleMenuClick}>마일리지 사용처</Link>
        <Link to="/warmthmap" onClick={handleMenuClick}>온기지도</Link>
        {isLoggedIn && (
          <Link to="/mypage" onClick={handleMenuClick}>마이페이지</Link>
        )}
      </nav>
    </>
  );
}

export default Header;
