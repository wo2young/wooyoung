import React from "react";
import "./MileageUsagePage.css";
import navLogo from "./logo.png"; // 실제 로고 경로로 교체

// 가게/광고 이미지도 교체 가능(아니면 생략)
export default function MileageUsagePage() {
  return (
    <div className="container">
      {/* 네비게이션 */}
      <nav className="nav-bar">
        <div className="nav-left">
          <img src={navLogo} alt="한끼온" className="nav-logo" />
          <span className="nav-title">한끼온</span>
        </div>
        <ul className="nav-menu">
          <li>매칭페이지</li>
          <li>게시판</li>
          <li>고객지원</li>
          <li className="active">마일리지 사용처</li>
          <li>마이페이지</li>
        </ul>
        <button className="nav-logout">LOG OUT</button>
      </nav>
      {/* 메인 컨텐츠 */}
      <div className="main-content">
        <h2 className="page-title">마일리지 사용처</h2>
        <div className="point-box">
          <span>사용가능 마일리지 : 0000p</span>
        </div>
        <div className="main-layout">
          {/* 가게 사진 첨부 */}
          <section className="store-section">
            <h3>가게 사진 첨부</h3>
            <div className="store-list">
              <div className="store-card">
                <img src="https://i.ibb.co/NpZf5cB/jh1.png" alt="" />
                <div className="store-caption">끝내줘요</div>
                <div className="store-name">재홍이의 인생 족발</div>
              </div>
              <div className="store-card">
                <img src="https://i.ibb.co/g6w2qfc/jm1.png" alt="" />
                <div className="store-name">재민이의 여행 치킨</div>
              </div>
              <div className="store-card">
                <img src="https://i.ibb.co/m06SwxK/jj1.png" alt="" />
                <div className="store-name">재준이의 참 횟집</div>
              </div>
              <div className="store-card small">
                <img src="https://i.ibb.co/y5Hd1Jk/jj2.png" alt="" />
                <div className="store-name">승국 짜장</div>
              </div>
              <div className="store-card small">
                <img src="https://i.ibb.co/PzLh07k/jj3.png" alt="" />
                <div className="store-name">승국 짬뽕</div>
              </div>
            </div>
          </section>
          {/* 광고 */}
          <aside className="ad-section">
            <div className="ad-box">광고</div>
          </aside>
        </div>
        {/* 안내/페이지네이션 */}
        <div className="bottom-info">
          <div className="notice">안내 사항<br />1년 동안 사용하지 않으실 시 마일리지는 자동 소멸 됩니다!</div>
          <div className="pagination">&lt; 1 2 3 4 5 &gt;</div>
        </div>
      </div>
    </div>
  );
}
