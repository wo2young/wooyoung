import { useState, useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logoImg from '../assets/로고.png';
import matchingIcon from '../assets/icons/matching.png';
import mileageIcon from '../assets/icons/mileage.png';
import boardIcon from '../assets/icons/board.png';
import mypageIcon from '../assets/icons/mypage.png';
import supportIcon from '../assets/icons/support.png';
import '../Styles/Home.css';
import '../Styles/App.css';
import { useAuth } from '../contexts/AuthContext';

function Home() {
  const [isMobile, setIsMobile] = useState(() =>
    typeof window !== 'undefined' ? window.innerWidth <= 480 : false
  );
  const words = ['봉사를', '나눔을', '참여를', '손길을'];
  const [idx, setIdx] = useState(0);
  const [anim, setAnim] = useState(false);
  const [visibleItems, setVisibleItems] = useState([]);
  const navigate = useNavigate();
  const { isLoggedIn, logout } = useAuth();
  const routeMap = {
    '매칭': '/matching',
    '마일리지': '/mileage',
    '게시판': '/board',
    '마이페이지': '/mypage',
    '고객지원': '/support',
  };
  const iconMap = {
    '매칭': matchingIcon,
    '마일리지': mileageIcon,
    '게시판': boardIcon,
    '마이페이지': mypageIcon,
    '고객지원': supportIcon,
  };
  const timeoutsRef = useRef([]);

  useEffect(() => {
    const timer = setInterval(() => {
      setAnim(true);
      setTimeout(() => {
        setIdx((prev) => (prev + 1) % words.length);
        setAnim(false);
      }, 400);
    }, 2500);
    return () => clearInterval(timer);
  }, []);

  useEffect(() => {
    timeoutsRef.current = Object.keys(routeMap).map((_, i) =>
      setTimeout(() => {
        setVisibleItems((prev) => [...prev, i]);
      }, i * 100)
    );
    return () => timeoutsRef.current.forEach(clearTimeout);
  }, []);

  const nextIdx = (idx + 1) % words.length;
  const SlideWords = (
    <span className="slide-wordbox">
      <span
        className={`slide-word absolute ${anim ? 'up' : ''} current`}
        key={idx}
        aria-hidden={anim}
      >
        {words[idx]}
      </span>
      <span
        className={`slide-word absolute ${anim ? 'in' : ''} next`}
        key={nextIdx}
        aria-hidden={!anim}
      >
        {words[nextIdx]}
      </span>
    </span>
  );

  const Welcome = (
    <h1 className="welcome">
      {isMobile ? (
        <>
          당신의<br />
          {SlideWords}
          <br />
          환영<br />
          합니다!
        </>
      ) : (
        <>
          당신의 {SlideWords} 환영 합니다!
        </>
      )}
    </h1>
  );

  return (
    <div className="mainpage">
      <div className="top-bar">
        <div className="header-left">
          <img src={logoImg} alt="한끼온 로고" className="logo-img" />
        </div>
        <div className="header-right">
          {isLoggedIn ? (
            <button className="login-btn" onClick={logout}>
              로그아웃
            </button>
          ) : (
            <button className="login-btn" onClick={() => navigate('/login')}>
              로그인
            </button>
          )}
        </div>
      </div>
      {Welcome}
      <div className="menu-items">
        {Object.keys(routeMap).map((label, i) => (
          <Link
            key={label}
            to={routeMap[label]}
            className={`menu-item ${visibleItems.includes(i) ? 'fly-in' : ''}`}
            aria-label={`${label} 페이지로 이동`}
          >
            <div className="icon-box">
              {label === '매칭' ? (
                <>
                  <div
                    className="icon-bg"
                    style={{ backgroundImage: `url(${iconMap[label]})` }}
                  />
                  <span className="icon-text">ON+</span>
                </>
              ) : (
                <>
                  <img
                    src={iconMap[label]}
                    alt={`${label} 아이콘`}
                    className="icon-img"
                  />
                  <span className="icon-text" />
                </>
              )}
            </div>
            <p>{label}</p>
          </Link>
        ))}
      </div>
      <div className="footer">광고문의</div>
    </div>
  );
}

export default Home;
