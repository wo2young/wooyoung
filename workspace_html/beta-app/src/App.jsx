import { useState, useEffect } from 'react';
import './App.css';
import Button from '@mui/material/Button';

function App() {
  const [isMobile, setIsMobile] = useState(window.innerWidth <= 480);

  // 애니메이션 단어 배열 및 상태
  const words = ['봉사를', '나눔을', '참여를', '손길을'];
  const [index, setIndex] = useState(0);
  const [fade, setFade] = useState(false);

  // 모바일 여부 판단
  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth <= 480);
    };
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  // 글자 교체 애니메이션
  useEffect(() => {
    const interval = setInterval(() => {
      setFade(true); // 먼저 사라지게
      setTimeout(() => {
        setIndex((prev) => (prev + 1) % words.length); // 단어 교체
        setFade(false); // 다시 보이게
      }, 500); // 사라지는 애니메이션 시간
    }, 3000); // 3초마다

    return () => clearInterval(interval);
  }, []);

  return (
    <div className="mainpage">
      {/* 상단 네비게이션 */}
      <div className="top-bar">
        <span className="logo">한끼온 +</span>
        <Button className="login-btn">로그인</Button>
      </div>

      {/* 인사말 */}
      <h1 className="welcome">
        {isMobile ? (
          <>
            당신의<br />
            <span className={`fade-word ${fade ? 'exit' : ''}`}>{words[index]}</span>를<br />
            환영<br />
            합니다!
          </>
        ) : (
          <>
            당신의 <span className={`fade-word ${fade ? 'exit' : ''}`}>{words[index]}</span> 환영 합니다!
          </>
        )}
      </h1>

      {/* 기능 버튼들 */}
     <div className="menu-items">
       {['매칭', '마일리지', '게시판', '마이페이지', '고객지원'].map((label, index) => (
         <div key={index} className="menu-item fly-in">
            <div className="icon-box"></div>
            <p>{label}</p>
         </div>
         ))}
    </div>

      {/* 하단 푸터 */}
      <div className="footer">광고문의</div>
    </div>
  );
}

export default App;
