import { Link, useNavigate } from 'react-router-dom';
import logoImg from '../assets/로고.png';
import { useAuth } from '../contexts/AuthContext';
import '../Styles/Header.css';

function Header() {
  const { isLoggedIn, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="support-header">
      <div className="header-left">
        <Link to="/">
          <img src={logoImg} alt="한끼온 로고" className="logo-img" style={{ cursor: 'pointer' }} />
        </Link>
      </div>
      <nav className="header-center">
        <Link to="/matching">매칭페이지</Link>
        <span className="divider"></span>
        <Link to="/board">게시판</Link>
        <span className="divider"></span>
        <Link to="/support">고객지원</Link>
        <span className="divider"></span>
        <Link to="/mileage">마일리지 사용처</Link>
      </nav>
      <div className="header-right">
        {isLoggedIn && (
          <Link to="/mypage">마이페이지</Link>
        )}
        {isLoggedIn ? (
          <button
            className="login-btn"
            onClick={() => {
              logout();
              navigate('/');
            }}
          >
            로그아웃
          </button>
        ) : (
          <button
            className="login-btn"
            onClick={() => navigate('/login')}
          >
            로그인
          </button>
        )}
      </div>
    </header>
  );
}

export default Header;
