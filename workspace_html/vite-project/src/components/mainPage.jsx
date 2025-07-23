function MainPage() {
  return (
    <div className="mainpage">
      {/* 상단 네비게이션 */}
      <div className="top-bar">
        <span className="logo">한끼온 +</span>
        <button className="login-btn">로그인</button>
      </div>

      {/* 인사말 */}
      <h2 className="welcome">당신의 봉사를 환영 합니다</h2>

      {/* 기능 버튼들 */}
      <div className="menu-items">
        {['매칭', '마일리지', '게시판', '마이페이지', '고객지원'].map((label, index) => (
          <div key={index} className="menu-item">
            <div className="icon-box"></div>
            <p>{label}</p>
          </div>
        ))}
      </div>

      {/* 하단 푸터 */}
      <div className="footer">
        광고문의
      </div>
    </div>
  );
}

export default MainPage;