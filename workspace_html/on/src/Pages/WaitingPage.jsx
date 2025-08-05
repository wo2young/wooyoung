import React, { useEffect, useRef, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Loader from "../components/Hourglass";
import "../styles/WaitingPage.css";
import Layout from "../components/Layout";

function WaitingPage() {
  const connectingRef = useRef(null);
  const navigate = useNavigate();
  const { state = {} } = useLocation();

  // ===== 모달 상태 =====
  const [isRequestModalOpen, setRequestModalOpen] = useState(false);
  const [isCancelConfirmOpen, setCancelConfirmOpen] = useState(false);

  // ===== 로딩 애니메이션 =====
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

  // ===== 매칭 성공시 이동 =====
  useEffect(() => {
    const interval = setInterval(() => {
      if (localStorage.getItem("matchStatus") === "accepted") {
        // detail이 반드시 전달되도록 상태 통째로 넘겨줌
        navigate("/meeting-wait", { state });
      }
    }, 1000);
    return () => clearInterval(interval);
  }, [navigate, state]);

  // ===== 요청 취소 =====
  const handleCancel = () => {
    if (state?.id) {
      const reqs = JSON.parse(localStorage.getItem("helpRequests") || "[]");
      const filtered = reqs.filter(r => r.id !== state.id);
      localStorage.setItem("helpRequests", JSON.stringify(filtered));
    }
    alert("요청이 취소되었습니다.");
    navigate("/");
  };

  // ===== 추가 요청 =====
  const handleAddRequest = () => {
    navigate("/help");
  };

  // ===== 요청내용 모달 =====
  const detailText =
    state.detail?.trim()
    || state.content?.trim()
    || state.description?.trim()
    || "내용 없음";

  const requestDetails = (
    <div>
      <p><b>제목:</b> {state.title || state.request || "제목 없음"}</p>
      <p><b>상세 내용:</b> {detailText}</p>
      {state.problem && <p><b>불편 사항:</b> {state.problem}</p>}
      {state.time && <p><b>약속 시간:</b> {state.time}</p>}
      {state.place && <p><b>약속 장소:</b> {state.place}</p>}
    </div>
  );

  return (
    <Layout>
      <main>
        <a href="">
          <div className="left" title="협력업체 광고">협력업체 광고</div>
        </a>
        <div className="center">
          <h1>한끼온+</h1>
          <h2>요청이 접수되었습니다</h2>
          <h3>잠시만 기다려 주세요</h3>
          <div className="status-icon">
            <Loader />
          </div>
          <h3 ref={connectingRef}>봉사자 연결중...</h3>
          <p>소요 시간 10분 예상</p>
          <div className="buttons">
            <button onClick={() => setRequestModalOpen(true)}>요청 내용</button>
            <button onClick={() => setCancelConfirmOpen(true)}>요청 취소</button>
            <button onClick={handleAddRequest}>추가 요청</button>
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

      {/* ===== 요청 내용 모달 ===== */}
      {isRequestModalOpen && (
        <div className="modal-overlay" onClick={() => setRequestModalOpen(false)}>
          <div
            className="modal-content"
            onClick={e => e.stopPropagation()}
            style={{
              background: "#fff",
              borderRadius: "14px",
              maxWidth: 340,
              margin: "120px auto",
              padding: "30px 24px",
              boxShadow: "0 6px 32px 0 rgba(0,0,0,0.18)",
              position: "relative",
              zIndex: 1001,
              textAlign: "left"
            }}
          >
            <button
              onClick={() => setRequestModalOpen(false)}
              style={{
                position: "absolute",
                top: 12,
                right: 14,
                background: "none",
                border: "none",
                fontSize: 22,
                cursor: "pointer"
              }}
              aria-label="닫기"
            >
              ×
            </button>
            <h2 style={{
              marginTop: 0,
              marginBottom: 16,
              color: "#24304b",
              fontWeight: 900,
              letterSpacing: "-1px"
            }}>요청 상세 내용</h2>
            {requestDetails}
          </div>
        </div>
      )}

      {/* ===== 요청 취소 확인 모달 ===== */}
      {isCancelConfirmOpen && (
        <div className="modal-overlay" onClick={() => setCancelConfirmOpen(false)}>
          <div
            className="modal-content"
            onClick={e => e.stopPropagation()}
            style={{
              background: "#fff",
              borderRadius: "14px",
              maxWidth: 320,
              margin: "160px auto",
              padding: "26px 20px 18px 20px",
              boxShadow: "0 6px 32px 0 rgba(0,0,0,0.14)",
              position: "relative",
              zIndex: 1000,
              textAlign: "center"
            }}
          >
            <button
              onClick={() => setCancelConfirmOpen(false)}
              style={{
                position: "absolute",
                top: 8,
                right: 12,
                background: "none",
                border: "none",
                fontSize: 22,
                cursor: "pointer"
              }}
              aria-label="닫기"
            >
              ×
            </button>
            <h2 style={{ margin: "0 0 20px 0", fontSize: "1.16rem" }}>정말 요청을 취소하시겠습니까?</h2>
            <div style={{ display: "flex", gap: "18px", justifyContent: "center", marginTop: 12 }}>
              <button
                onClick={() => {
                  setCancelConfirmOpen(false);
                  handleCancel();
                }}
                style={{
                  padding: "6px 22px",
                  fontWeight: "bold",
                  background: "#ff9800",
                  color: "#fff",
                  border: "none",
                  borderRadius: "7px",
                  cursor: "pointer"
                }}
              >
                확인
              </button>
              <button
                onClick={() => setCancelConfirmOpen(false)}
                style={{
                  padding: "6px 22px",
                  fontWeight: "bold",
                  background: "#ddd",
                  color: "#333",
                  border: "none",
                  borderRadius: "7px",
                  cursor: "pointer"
                }}
              >
                취소
              </button>
            </div>
          </div>
        </div>
      )}

      {/* ===== 팝업 오버레이 스타일 ===== */}
      <style>
        {`
        .modal-overlay {
          position: fixed;
          inset: 0;
          background: rgba(0,0,0,0.23);
          z-index: 1000;
          display: flex;
          align-items: flex-start;
          justify-content: center;
        }
        `}
      </style>
    </Layout>
  );
}

export default WaitingPage;
