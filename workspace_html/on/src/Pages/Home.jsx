import { useState, useEffect, useRef, useMemo } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import logoImg from '../assets/logo.png';
import matchingIcon from '../assets/icons/matching.png';
import mileageIcon from '../assets/icons/mileage.png';
import boardIcon from '../assets/icons/board.png';
import mypageIcon from '../assets/icons/mypage.png';
import adminIcon from '../assets/icons/admin.png';
import supportIcon from '../assets/icons/support.png';
import maptIcon from '../assets/icons/map.png';
import AdminGearIcon from '../components/AdminGearIcon';
import '../Styles/Home.css';
import '../Styles/App.css';
import { useAuth } from '../contexts/AuthContext';
import airplaneImg from '../assets/icons/airplane.png';

function Home() {
  const [isMobile, setIsMobile] = useState(
    () => typeof window !== 'undefined' ? window.innerWidth <= 480 : false
  );
  const navigate = useNavigate();
  const { isLoggedIn, logout, user } = useAuth();

  // 관리자/일반 메뉴 단어, 색상
  const isAdmin = user?.role === 'admin';
  const userWords = ['봉사를', '나눔을', '참여를', '손길을'];
  const adminWords = ['개발을', '수정을', '설정을', '관리를'];
  const words = useMemo(() => (isAdmin ? adminWords : userWords), [isAdmin]);
  const [idx, setIdx] = useState(0);
  const [anim, setAnim] = useState(false);
  const [visibleItems, setVisibleItems] = useState([]);
  const timeoutsRef = useRef([]);
  const prevIsAdmin = useRef(isAdmin);

  // ★ 기어 hover 상태(비행기 trigger용)
  const [adminHover, setAdminHover] = useState(false);

  // isAdmin(즉, words 종류)이 바뀌었을 때만 idx 리셋
  useEffect(() => {
    if (prevIsAdmin.current !== isAdmin) {
      setIdx(0);
      prevIsAdmin.current = isAdmin;
    }
  }, [isAdmin]);

  // 슬라이드 애니메이션 (words 길이만 반응)
  useEffect(() => {
    const timer = setInterval(() => {
      setAnim(true);
      setTimeout(() => {
        setIdx(prev => (prev + 1) % words.length);
        setAnim(false);
      }, 400);
    }, 2500);
    return () => clearInterval(timer);
  }, [words.length]);

  // 메뉴 순차 등장이펙트
  const menuList = [
    { label: '매칭', route: '/matching', icon: matchingIcon, className: '' },
    { label: '마일리지', route: '/mileage', icon: mileageIcon, className: '' },
    { label: '게시판', route: '/community', icon: boardIcon, className: '' },
    isAdmin
      ? { label: '관리자', route: '/admin', icon: adminIcon, className: 'admin-menu' }
      : { label: '마이페이지', route: '/mypage', icon: mypageIcon, className: '' },
    { label: '온기지도', route: '/warmthmap', icon: maptIcon, className: '' },
    { label: '고객지원', route: '/support', icon: supportIcon, className: '' },
  ];

  useEffect(() => {
    setVisibleItems([]);
    timeoutsRef.current = menuList.map((_, i) =>
      setTimeout(() => {
        setVisibleItems(prev => [...prev, i]);
      }, i * 100)
    );
    return () => timeoutsRef.current.forEach(clearTimeout);
  }, [isAdmin]);

  // 로그인 여부 체크 후 이동 막기
  const handleMenuClick = (menuLabel) => (e) => {
    if (
      !isLoggedIn &&
      (
        menuLabel === '매칭' ||
        menuLabel === '마일리지' ||
        menuLabel === '온기지도' ||
        menuLabel === '마이페이지'
      )
    ) {
      e.preventDefault();
      navigate('/login');
    }
  };

  const nextIdx = (idx + 1) % words.length;
  const adminWordColor = "#B70820";
  const SlideWords = (
    <span className="slide-wordbox">
      <span
        className={`slide-word absolute ${anim ? 'up' : ''} current`}
        key={words[idx] + '-' + idx}
        aria-hidden={anim}
        style={isAdmin ? { color: adminWordColor, fontWeight: 700 } : undefined}
      >
        {words[idx]}
      </span>
      <span
        className={`slide-word absolute ${anim ? 'in' : ''} next`}
        key={words[nextIdx] + '-' + nextIdx}
        aria-hidden={!anim}
        style={isAdmin ? { color: adminWordColor, fontWeight: 700 } : undefined}
      >
        {words[nextIdx]}
      </span>
    </span>
  );

  const Welcome = (
    <h1 className="welcome" style={isAdmin ? { color: '#222' } : {}}>
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
          당신의 {SlideWords} <span style={{ whiteSpace: "nowrap" }}>환영 합니다!</span>
        </>
      )}
    </h1>
  );

  return (
    <div className="mainpage">
      <div className="top-bar">
        <div className="header-left">
          <img src={logoImg} alt="한끼온 로고" className="home-logo-img" />
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
        {menuList.map((menu, i) => (
          <Link
            key={menu.label}
            to={menu.route}
            className={`menu-item ${menu.className} ${visibleItems.includes(i) ? 'fly-in' : ''}`}
            aria-label={`${menu.label} 페이지로 이동`}
            // ✅ 여기 onClick 추가!
            onClick={handleMenuClick(menu.label)}
            onMouseEnter={menu.label === '관리자' ? () => setAdminHover(true) : undefined}
            onMouseLeave={menu.label === '관리자' ? () => setAdminHover(false) : undefined}
          >
            {/* 관리자 메뉴에만 비행기+줄 추가 */}
            {isAdmin && menu.label === '관리자' && (
              <div style={{
                position: "relative",
                width: "120px",
                height: "44px",
                margin: "0 auto",
                marginBottom: "-8px",
                pointerEvents: "none",
                userSelect: "none",
              }}>
                <img
                  src={airplaneImg}
                  alt="비행기"
                  className={adminHover ? "airplane-fly" : ""}
                  style={{
                    position: "absolute",
                    left: "50%",
                    top: "0",
                    transform: "translate(-50%, 0)",
                    width: "54px",
                    height: "auto",
                    zIndex: 1,
                    filter: "drop-shadow(0 2px 2px #eee)",
                  }}
                />
                <div
                  style={{
                    position: "absolute",
                    left: 0,
                    top: "38px",
                    width: "100%",
                    height: "5px",
                    background: "#bcbcbc",
                    borderRadius: "2px"
                  }}
                />
              </div>
            )}
            <div className="icon-box">
              {menu.label === '매칭' ? (
                <>
                  <div
                    className="icon-bg"
                    style={{ backgroundImage: `url(${menu.icon})` }}
                  />
                  <span className="icon-text">ON+</span>
                </>
              ) : menu.label === '관리자' ? (
                <AdminGearIcon />
              ) : (
                <>
                  <img
                    src={menu.icon}
                    alt={`${menu.label} 아이콘`}
                    className="icon-img"
                  />
                  <span className="icon-text" />
                </>
              )}
            </div>
            <p className={menu.label === '관리자' ? 'admin-menu-label' : ''}>{menu.label}</p>
          </Link>
        ))}
      </div>
      <div className="footer">광고문의</div>
    </div>
  );
}

export default Home;
