import { Link, useNavigate } from 'react-router-dom';
import logoImg from '../assets/logo.png';
import { useAuth } from '../contexts/AuthContext';
import '../Styles/Header.css';

function Header() {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="support-header">
      <div className="header-left">
        <Link to="/">
          <img src={logoImg} alt="한끼온 로고" className="header-logo-img" />
        </Link>
      </div>
      <div className="header-nav-wrapper">
        <nav className="header-nav">
          <Link to="/matching">매칭페이지</Link>
          <Link to="/board">게시판</Link>
          <Link to="/support">고객지원</Link>
          <Link to="/mileage">마일리지 사용처</Link>
          <Link to="/warmthmap">온기지도</Link>
        </nav>
      </div>
      <div className="header-right">
        {isLoggedIn && (
          <Link to="/mypage" className="mypage-link">마이페이지</Link>
        )}
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
  );
}

export default Header;