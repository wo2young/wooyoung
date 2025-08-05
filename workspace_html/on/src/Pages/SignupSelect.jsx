import React from "react";
import { useNavigate } from "react-router-dom"; // ★ 추가
import "../Styles/SignupSelect.css";

import onLogo from "../assets/logo.png";
import googleLogo from "../assets/google_logo.png";
import kakaoLogo from "../assets/kakao_logo.png";

function SignupSelect() {
  const navigate = useNavigate(); // ★

  // 각 버튼 클릭시 라우팅
  const handleOnSignup = () => navigate("/signup");
  const handleGoogleSignup = () => {
    // 구글 연동이 준비되어 있다면 해당 주소로 이동
    // 일단 알림만
    alert("Google 회원가입(추후 소셜 연동)");
  };
  const handleKakaoSignup = () => {
    // 카카오 연동이 준비되어 있다면 해당 주소로 이동
    // 일단 알림만
    alert("Kakao 회원가입(추후 소셜 연동)");
  };

  return (
    <div className="signup-select-wrapper">
      <header className="signup-header">
        <h1>회원가입</h1>
      </header>
      <section className="signup-section">
        <div className="signup-title-box">
          <span className="signup-title">
            <b style={{ color: "#FF9900" }}>한끼온</b> 회원가입 방식을 선택해주세요.
          </span>
        </div>
        <div className="signup-select-box">
          <button className="signup-btn on" onClick={handleOnSignup}>
            <img src={onLogo} alt="한끼온" className="signup-icon" />
            <span>한끼온으로 회원가입</span>
          </button>
          <button className="signup-btn google" onClick={handleGoogleSignup}>
            <img src={googleLogo} alt="Google" className="signup-icon" />
            <span>Google로 회원가입</span>
          </button>
          <button className="signup-btn kakao" onClick={handleKakaoSignup}>
            <img src={kakaoLogo} alt="Kakao" className="signup-icon" />
            <span>Kakao로 회원가입</span>
          </button>
        </div>
      </section>
    </div>
  );
}

export default SignupSelect;
