// pages/Home.jsx
import { useState, useEffect } from 'react';
import '../Styles/Home.css';
import '../Styles/App.css'; // 항상 마지막에!
import Button from '@mui/material/Button';
import { Link } from 'react-router-dom';
import logoImg from '../assets/로고.png'; // ✅ 로고 이미지 import

function Home() {
  const [isMobile, setIsMobile] = useState(window.innerWidth <= 480);
  const words = ['봉사를', '나눔을', '참여를', '손길을'];
  const [index, setIndex] = useState(0);
  const [fade, setFade] = useState(false);

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth <= 480);
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  useEffect(() => {
    const interval = setInterval(() => {
      setFade(true);
      setTimeout(() => {
        setIndex((prev) => (prev + 1) % words.length);
        setFade(false);
      }, 500);
    }, 3000);
    return () => clearInterval(interval);
  }, []);

  return (
    <div className="mainpage">
      {/* ✅ 상단 로고 + 로그인 버튼 */}
      <div className="top-bar">
        <div className="header-left">
          <img src={logoImg} alt="한끼온 로고" className="logo-img" />
        </div>
        <div className="header-right">
          <Button className="login-btn">로그인</Button>
        </div>
      </div>

      {/* 인사말 애니메이션 */}
      <h1 className="welcome">
        {isMobile ? (
          <>
            당신의<br />
            <span className={`fade-word ${fade ? 'exit' : ''}`}>{words[index]}</span><br />
            환영<br />
            합니다!
          </>
        ) : (
          <>
            당신의 <span className={`fade-word ${fade ? 'exit' : ''}`}>{words[index]}</span> 환영 합니다!
          </>
        )}
      </h1>

      {/* 메뉴 아이템 */}
      <div className="menu-items">
        {['매칭', '마일리지', '게시판', '마이페이지', '고객지원'].map((label, index) => {
          const isSupport = label === '고객지원';

          const content = (
            <div className="menu-item fly-in">
              <div className="icon-box"></div>
              <p>{label}</p>
            </div>
          );

          return (
            <div key={index}>
              {isSupport ? <Link to="/support">{content}</Link> : content}
            </div>
          );
        })}
      </div>

      {/* 하단 */}
      <div className="footer">광고문의</div>
    </div>
  );
}

export default Home;
