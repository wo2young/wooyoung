import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import '../Styles/Meeting.css';
import holly from '../assets/holly.png';
import Layout from "../components/Layout";

function Meeting() {
  const [totalSeconds, setTotalSeconds] = useState(59 * 60); // 59분 0초
  const [dotsIndex, setDotsIndex] = useState(0);
  const dotsArr = ["", ".", "..", "..."];
  const navigate = useNavigate();

  // 🔴 신고 모달 상태 추가
  const [reportModalOpen, setReportModalOpen] = useState(false);
  const [reportReason, setReportReason] = useState("");

  // 타이머 증가
  useEffect(() => {
    const timer = setInterval(() => {
      setTotalSeconds(sec => sec + 1);
    }, 1000);
    return () => clearInterval(timer);
  }, []);

  // 점 애니메이션
  useEffect(() => {
    const dotTimer = setInterval(() => {
      setDotsIndex(idx => (idx + 1) % dotsArr.length);
    }, 500);
    return () => clearInterval(dotTimer);
  }, []);

  // 시간 포맷
  function formatTime(sec) {
    const h = Math.floor(sec / 3600);
    const m = Math.floor((sec % 3600) / 60);
    const s = sec % 60;
    const mm = String(m).padStart(2, "0");
    const ss = String(s).padStart(2, "0");
    return h > 0 ? `${h}시간 ${mm}분 ${ss}초` : `${mm}분 ${ss}초`;
  }

  // 버튼 핸들러 (페이지 이동)
  const handleComplete = () => {
    navigate("/meeting-done");
  };
  // const handleCancel = () => alert('요청 취소 로직 구현');
  // 신고하기 버튼 클릭 시 모달 오픈
  const handleReport = () => setReportModalOpen(true);

  // 🔴 신고 제출
  const handleReportSubmit = (e) => {
    e.preventDefault();
    if (!reportReason.trim()) {
      alert("신고 사유를 입력해주세요!");
      return;
    }
    console.log("🟠 신고 내용:", reportReason);
    alert("신고가 접수되었습니다. 감사합니다.");
    setReportModalOpen(false);
    setReportReason("");
  };

  return (
    <Layout>
      <main className="INGM">
        {/* 좌측 광고 */}
        <a href="#">
          <div className="INGleft" title="협력업체 광고">
            협력업체 광고
          </div>
        </a>

        {/* 중앙 타이머 */}
        <div className="INGcenter">
          <h1>한끼온+</h1>
          <div className="INGstatus">
            <span id="INGstatic">요청 진행중</span>
            <span id="INGdots">{dotsArr[dotsIndex]}</span>
          </div>

          <div className="INGicon-box">
            <img
              src={holly}
              alt="봉사자 아이콘"
            />
          </div>

          <p className="INGvolunteer">
            봉사자: <span className="INGname">김동현</span>
          </p>
          <h2>현재 경과 시간</h2>
          <div className="INGtimer" id="INGtimer">
            {formatTime(totalSeconds)}
          </div>

          <div className="INGbuttons">
            <button id="INGcompleteBtn" onClick={handleComplete}>요청 완료</button>
            {/* <button id="INGcancelBtn" onClick={handleCancel}>요청 취소</button> */}
            <button id="INGreportBtn" onClick={handleReport}>신고 하기</button>
          </div>
        </div>

        {/* 우측 빈 박스 */}
        <div className="INGright" style={{ display: "none" }}></div>
      </main>

      {/* 🔴 신고 모달 */}
      {reportModalOpen && (
        <div className="modal-overlay" onClick={() => setReportModalOpen(false)}>
          <div
            className="modal-content"
            onClick={e => e.stopPropagation()}
            style={{
              background: "#fff",
              borderRadius: "14px",
              maxWidth: 350,
              margin: "130px auto",
              padding: "30px 26px 24px 26px",
              boxShadow: "0 6px 32px 0 rgba(0,0,0,0.13)",
              position: "relative",
              zIndex: 1002,
              textAlign: "left"
            }}
          >
            <button
              onClick={() => setReportModalOpen(false)}
              style={{
                position: "absolute",
                top: 12,
                right: 15,
                background: "none",
                border: "none",
                fontSize: 22,
                cursor: "pointer"
              }}
              aria-label="닫기"
            >
              ×
            </button>
            <h2 style={{ margin: "0 0 18px 0", color: "#e34d4d", fontWeight: "bold" }}>
              신고하기
            </h2>
            <form onSubmit={handleReportSubmit}>
              <label htmlFor="report-reason" style={{ fontWeight: "bold" }}>
                신고 사유를 입력해주세요
              </label>
              <textarea
                id="report-reason"
                value={reportReason}
                onChange={e => setReportReason(e.target.value)}
                placeholder="예시) 욕설, 불쾌한 행동, 사기, 기타 등"
                rows={4}
                style={{ width: "100%", marginTop: "7px", marginBottom: "14px" }}
                autoFocus
                required
              />
              <div style={{ textAlign: "right", marginTop: 8 }}>
                <button
                  type="submit"
                  style={{
                    background: "#ff7f4d",
                    color: "#fff",
                    border: "none",
                    borderRadius: "8px",
                    padding: "7px 22px",
                    fontWeight: "bold",
                    fontSize: "15px",
                    marginRight: 8,
                    cursor: "pointer"
                  }}
                >
                  신고 제출
                </button>
                <button
                  type="button"
                  onClick={() => setReportModalOpen(false)}
                  style={{
                    background: "#ccc",
                    color: "#333",
                    border: "none",
                    borderRadius: "8px",
                    padding: "7px 18px",
                    fontSize: "15px",
                    cursor: "pointer"
                  }}
                >
                  닫기
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* 모달 오버레이 스타일 */}
      <style>
        {`
          .modal-overlay {
            position: fixed;
            inset: 0;
            background: rgba(0,0,0,0.18);
            z-index: 1001;
            display: flex;
            align-items: flex-start;
            justify-content: center;
          }
        `}
      </style>
    </Layout>
  );
}

export default Meeting;
