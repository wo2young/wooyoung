import React, { useEffect, useRef } from "react";
import Header from "../components/Header"; // 헤더 위치에 맞게 경로 수정!
import "../styles/WaitingPage.css";

function WaitingPage() {
  const connectingRef = useRef(null);

  useEffect(() => {
    const textBase = "봉사자 연결중";
    const dots = ["", ".", "..", "..."];
    let dotIndex = 0;
    const interval = setInterval(() => {
      if (connectingRef.current) {
        connectingRef.current.textContent = textBase + dots[dotIndex];
        dotIndex = (dotIndex + 1) % dots.length;
      }
    }, 500);
    return () => clearInterval(interval);
  }, []);

  return (
    <div>
      <Header />
      <main>
        <a href="">
          <div className="left" title="협력업체 광고">
            협력업체 광고
          </div>
        </a>

        <div className="center">
          <h1>한끼온+</h1>
          <h2>요청이 접수되었습니다</h2>
          <h3>잠시만 기다려 주세요</h3>

          <div className="status-icon">
            {/* 모래시계 SVG */}
            <svg aria-label="loader being flipped clockwise and circled by three white curves fading in and out"
              role="img" height="56px" width="56px" viewBox="0 0 56 56" className="loader">
              <clipPath id="sand-mound-top">
                <path
                  d="M 14.613 13.087 C 15.814 12.059 19.3 8.039 20.3 6.539 C 21.5 4.789 21.5 2.039 21.5 2.039 L 3 2.039 C 3 2.039 3 4.789 4.2 6.539 C 5.2 8.039 8.686 12.059 9.887 13.087 C 11 14.039 12.25 14.039 12.25 14.039 C 12.25 14.039 13.5 14.039 14.613 13.087 Z"
                  className="loader__sand-mound-top"></path>
              </clipPath>
              <clipPath id="sand-mound-bottom">
                <path
                  d="M 14.613 20.452 C 15.814 21.48 19.3 25.5 20.3 27 C 21.5 28.75 21.5 31.5 21.5 31.5 L 3 31.5 C 3 31.5 3 28.75 4.2 27 C 5.2 25.5 8.686 21.48 9.887 20.452 C 11 19.5 12.25 19.5 12.25 19.5 C 12.25 19.5 13.5 19.5 14.613 20.452 Z"
                  className="loader__sand-mound-bottom"></path>
              </clipPath>
              <g transform="translate(2,2)">
                <g transform="rotate(-90,26,26)" strokeLinecap="round" strokeDashoffset="153.94"
                  strokeDasharray="153.94 153.94" stroke="hsl(0,0%,100%)" fill="none">
                  <circle transform="rotate(0,26,26)" r="24.5" cy="26" cx="26" strokeWidth="2.5"
                    className="loader__motion-thick"></circle>
                  <circle transform="rotate(90,26,26)" r="24.5" cy="26" cx="26" strokeWidth="1.75"
                    className="loader__motion-medium"></circle>
                  <circle transform="rotate(180,26,26)" r="24.5" cy="26" cx="26" strokeWidth="1"
                    className="loader__motion-thin"></circle>
                </g>
                <g transform="translate(13.75,9.25)" className="loader__model">
                  <path
                    d="M 1.5 2 L 23 2 C 23 2 22.5 8.5 19 12 C 16 15.5 13.5 13.5 13.5 16.75 C 13.5 20 16 18 19 21.5 C 22.5 25 23 31.5 23 31.5 L 1.5 31.5 C 1.5 31.5 2 25 5.5 21.5 C 8.5 18 11 20 11 16.75 C 11 13.5 8.5 15.5 5.5 12 C 2 8.5 1.5 2 1.5 2 Z"
                    fill="hsl(var(--hue),90%,85%)"></path>
                  <g strokeLinecap="round" stroke="hsl(35,90%,90%)">
                    <line y2="20.75" x2="12" y1="15.75" x1="12" strokeDasharray="0.25 33.75"
                      strokeWidth="1" className="loader__sand-grain-left"></line>
                    <line y2="21.75" x2="12.5" y1="16.75" x1="12.5" strokeDasharray="0.25 33.75"
                      strokeWidth="1" className="loader__sand-grain-right"></line>
                    <line y2="31.5" x2="12.25" y1="18" x1="12.25" strokeDasharray="0.5 107.5"
                      strokeWidth="1" className="loader__sand-drop"></line>
                    <line y2="31.5" x2="12.25" y1="14.75" x1="12.25" strokeDasharray="54 54"
                      strokeWidth="1.5" className="loader__sand-fill"></line>
                    <line y2="31.5" x2="12" y1="16" x1="12" strokeDasharray="1 107" strokeWidth="1"
                      stroke="hsl(35,90%,83%)" className="loader__sand-line-left"></line>
                    <line y2="31.5" x2="12.5" y1="16" x1="12.5" strokeDasharray="12 96"
                      strokeWidth="1" stroke="hsl(35,90%,83%)" className="loader__sand-line-right"></line>
                    <g strokeWidth="0" fill="hsl(35,90%,90%)">
                      <path
                        d="M 12.25 15 L 15.392 13.486 C 21.737 11.168 22.5 2 22.5 2 L 2 2.013 C 2 2.013 2.753 11.046 9.009 13.438 L 12.25 15 Z"
                        clipPath="url(#sand-mound-top)"></path>
                      <path
                        d="M 12.25 18.5 L 15.392 20.014 C 21.737 22.332 22.5 31.5 22.5 31.5 L 2 31.487 C 2 31.487 2.753 22.454 9.009 20.062 Z"
                        clipPath="url(#sand-mound-bottom)"></path>
                    </g>
                  </g>
                  <g strokeWidth="2" strokeLinecap="round" opacity="0.7" fill="none">
                    <path
                      d="M 19.437 3.421 C 19.437 3.421 19.671 6.454 17.914 8.846 C 16.157 11.238 14.5 11.5 14.5 11.5"
                      stroke="hsl(0,0%,100%)" className="loader__glare-top"></path>
                    <path transform="rotate(180,12.25,16.75)"
                      d="M 19.437 3.421 C 19.437 3.421 19.671 6.454 17.914 8.846 C 16.157 11.238 14.5 11.5 14.5 11.5"
                      stroke="hsla(0,0%,100%,0)" className="loader__glare-bottom"></path>
                  </g>
                  <rect height="2" width="24.5" fill="hsl(var(--hue),90%,50%)"></rect>
                  <rect height="1" width="19.5" y="0.5" x="2.5" ry="0.5" rx="0.5"
                    fill="hsl(var(--hue),90%,57.5%)"></rect>
                  <rect height="2" width="24.5" y="31.5" fill="hsl(var(--hue),90%,50%)"></rect>
                  <rect height="1" width="19.5" y="32" x="2.5" ry="0.5" rx="0.5"
                    fill="hsl(var(--hue),90%,57.5%)"></rect>
                </g>
              </g>
            </svg>
          </div>

          <h3 ref={connectingRef}>봉사자 연결중...</h3>
          <p>소요 시간 10분 예상</p>

          <div className="buttons">
            <button>요청 내용</button>
            <button>요청 취소</button>
            <button>추가 요청</button>
          </div>
        </div>

        <div className="right">
          요청 대기중인 목록
          <br />
          <a href="">병원</a>
          <br />
          <a href="">산책</a>
          <br />
          <a href="">병원</a>
          <br />
          <a href="">뽀뽀</a>
        </div>
      </main>
    </div>
  );
}

export default WaitingPage;
