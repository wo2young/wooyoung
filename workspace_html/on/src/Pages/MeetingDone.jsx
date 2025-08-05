import React from "react";
import { useNavigate } from "react-router-dom"; // 추가
import "../Styles/MeetingDone.css"; // css 경로는 맞게 수정
import heart from "../assets/heart.png"
import Layout from "../components/Layout";

function MeetingDone() {
  const navigate = useNavigate(); // 추가

  return (
    <Layout>
      <div className="DONEB">
        <main className="DONEM">
          {/* 좌측 광고 */}
          <a href="#">
            <div className="DONEleft" title="협력업체 광고">
              협력업체 광고
            </div>
          </a>

          {/* 중앙 타이머 페이지 */}
          <div className="DONEcenter">
            <h1>한끼온+</h1>
            <div className="DONEstatus">
              <span id="DONEstatic">
                한끼온을 이용해주셔서<br />감사합니다!
              </span>
              <br />
              <span className="DONEclickAd" id="DONEclickAd">
                광고 클릭시 20P 추가지급 ▷{" "}
              </span>
            </div>

            <div className="DONEicon-box">
              <img
                alt="광고 아이콘"
                src={heart}
                style={{ width: "230px", height: "230px" }}
              />
            </div>

            <p className="DONEmsg">요청완료</p>
            <span className="DONEpoint">272</span>
            <span style={{ fontSize: "32px", fontWeight: 700 }}>P 지급</span>

            <div className="DONEbuttons">
              <br />
              <button id="DONEMainBtn" onClick={() => navigate("/")}>메인</button>
              <button id="DONEaddBtn" onClick={() => navigate("/matching-wait")}>추가 매칭</button>
              {/* /waiting 경로가 아니라면 원하는 경로로 수정 */}
            </div>
          </div>

          {/* 우측 빈 박스 */}
          {/* <div className="DONEright"></div> */}
        </main>
      </div>
    </Layout>
  );
}

export default MeetingDone;
