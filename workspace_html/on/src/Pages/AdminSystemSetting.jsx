import React, { useState } from "react";
import "../Styles/AdminSystemSetting.css";
import AdminHeader from "../components/AdminHeader";
import AdminSidebar from "../components/AdminSidebar";  // 추가

export default function AdminSystemSetting() {
  // 공지 상태
  const [noticeId, setNoticeId] = useState("");
  const [noticeAll, setNoticeAll] = useState(false);
  const [noticeMsg, setNoticeMsg] = useState("");

  // 팝업 상태
  const [popupId, setPopupId] = useState("");
  const [popupAll, setPopupAll] = useState(false);
  const [popupMsg, setPopupMsg] = useState("");

  // 배너 상태
  const [banners, setBanners] = useState([
    { file: null, preview: "" },
    { file: null, preview: "" },
    { file: null, preview: "" },
  ]);

  // 챗봇 상태
  const [chatbotTemplate, setChatbotTemplate] = useState("");
  const [chatbotAnswer, setChatbotAnswer] = useState("");

  // 공지 전송
  const handleNoticeSend = () => {
    if (noticeAll) {
      window.alert("전체에게 공지를 전송했습니다.");
    } else if (noticeId.trim()) {
      window.alert(`아이디 "${noticeId}"에게 공지를 전송했습니다.`);
    } else {
      window.alert("아이디를 입력하거나 전체를 선택해주세요.");
      return;
    }
    setNoticeId("");
  };

  // 팝업 전송
  const handlePopupSend = () => {
    if (popupAll) {
      window.alert("전체에게 팝업을 설정했습니다.");
    } else if (popupId.trim()) {
      window.alert(`아이디 "${popupId}"에게 팝업을 설정했습니다.`);
    } else {
      window.alert("아이디를 입력하거나 전체를 선택해주세요.");
      return;
    }
    setPopupId("");
  };

  // 배너 파일 업로드
  const handleBannerChange = (idx, e) => {
    const file = e.target.files[0];
    if (!file) return;
    if (!file.type.startsWith("image/")) {
      window.alert("이미지 파일만 업로드할 수 있습니다.");
      return;
    }
    const reader = new FileReader();
    reader.onload = (ev) => {
      setBanners((prev) => {
        const copy = [...prev];
        copy[idx] = { file, preview: ev.target.result };
        return copy;
      });
    };
    reader.readAsDataURL(file);
  };

  // 배너 삭제
  const handleBannerRemove = (idx) => {
    setBanners((prev) => {
      const copy = [...prev];
      copy[idx] = { file: null, preview: "" };
      return copy;
    });
  };

  // 챗봇 템플릿 선택
  const handleChatbotTemplate = (e) => {
    setChatbotTemplate(e.target.value);
    setChatbotAnswer(e.target.value);
  };

  // 챗봇 답변 수정
  const handleChatbotApply = () => {
    if (chatbotAnswer.trim()) {
      window.alert("챗봇 답변이 수정되었습니다.");
    } else {
      window.alert("내용을 입력해주세요.");
    }
  };

  return (
    <>
      <AdminHeader />
      <div className="ass-admin-root" style={{ display: "flex", width: "100%", minHeight: "100vh", background: "#f9f9f9" }}>
        <div className="ass-admin-sidebar-wrap" style={{ minWidth: 220, background: "transparent" }}>
          <AdminSidebar />
        </div>
        <div className="ass-admin-main-content" style={{ flex: 1, maxWidth: 1080, margin: "0 auto", padding: "44px 0 0 0" }}>
          {/* 공지 발송 */}
          <div className="ass-section ass-notice-section">
            <div className="ass-header">
              <span className="ass-icon" role="img" aria-label="메일">📧</span>공지 발송
            </div>
            <div className="ass-id-filter">
              <label htmlFor="ass-notice-id">아이디</label>
              <input
                id="ass-notice-id"
                type="text"
                placeholder="아이디를 입력하세요"
                value={noticeId}
                onChange={(e) => setNoticeId(e.target.value)}
                disabled={noticeAll}
              />
              <label>
                <input
                  type="radio"
                  name="ass-notice-scope"
                  value="all"
                  checked={noticeAll}
                  onChange={() => setNoticeAll(true)}
                />
                전체
              </label>
              <button
                className="ass-btn ass-notice-send-btn"
                onClick={handleNoticeSend}
              >전송</button>
            </div>
            <textarea
              className="ass-notice-msg"
              placeholder="내용을 입력하세요..."
              value={noticeMsg}
              onChange={(e) => setNoticeMsg(e.target.value)}
            ></textarea>
          </div>

          {/* 팝업 설정 */}
          <div className="ass-section ass-popup-section">
            <div className="ass-header">
              <span className="ass-icon" role="img" aria-label="팝업">📋</span>팝업 설정
            </div>
            <div className="ass-id-filter">
              <label htmlFor="ass-popup-id">아이디</label>
              <input
                id="ass-popup-id"
                type="text"
                placeholder="아이디를 입력하세요"
                value={popupId}
                onChange={(e) => setPopupId(e.target.value)}
                disabled={popupAll}
              />
              <label>
                <input
                  type="radio"
                  name="ass-popup-scope"
                  value="all"
                  checked={popupAll}
                  onChange={() => setPopupAll(true)}
                />
                전체
              </label>
              <button
                className="ass-btn ass-popup-send-btn"
                onClick={handlePopupSend}
              >전송</button>
            </div>
            <textarea
              className="ass-popup-msg"
              placeholder="팝업 메시지를 입력하세요..."
              value={popupMsg}
              onChange={(e) => setPopupMsg(e.target.value)}
            ></textarea>
          </div>

          {/* 배너 등록 */}
          <div className="ass-section ass-banner-section">
            <div className="ass-header">
              <span className="ass-icon" role="img" aria-label="배너">🏷️</span>배너 등록
            </div>
            <div className="ass-banner-list">
              {banners.map((banner, idx) => (
                <div className="ass-banner-item" data-index={idx} key={idx}>
                  <div className="ass-banner-placeholder">
                    <div className="ass-banner-label">{idx + 1}</div>
                    <div className="ass-banner-preview">
                      {banner.preview && (
                        <img src={banner.preview} alt={`배너${idx + 1}`} />
                      )}
                    </div>
                  </div>
                  <div className="ass-banner-controls">
                    <input
                      type="file"
                      accept="image/*"
                      className="ass-banner-input"
                      onChange={(e) => handleBannerChange(idx, e)}
                    />
                    <button
                      type="button"
                      className="ass-btn ass-banner-remove-btn"
                      onClick={() => handleBannerRemove(idx)}
                    >
                      삭제
                    </button>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* 챗봇 답 수정 */}
          <div className="ass-section ass-chatbot-section">
            <div className="ass-header">
              <span className="ass-icon" role="img" aria-label="챗봇">🤖</span>챗봇 답 수정
            </div>
            <div className="ass-chatbot-controls">
              <label htmlFor="ass-chatbot-template">템플릿</label>
              <select
                id="ass-chatbot-template"
                value={chatbotTemplate}
                onChange={handleChatbotTemplate}
              >
                <option value="">선택하세요</option>
                <option value="안녕하세요! 무엇을 도와드릴까요?">인사말</option>
                <option value="아이디/비밀번호 찾기 안내입니다.">id/pw찾기</option>
                <option value="제휴업체 문의는 support@example.com 으로 연락주세요.">
                  제휴업체 문의
                </option>
                <option value="건의사항을 보내주시면 검토 후 답변드리겠습니다.">
                  건의하기
                </option>
              </select>
              <button
                className="ass-btn ass-chatbot-apply-btn"
                onClick={handleChatbotApply}
              >수정</button>
            </div>
            <textarea
              className="ass-chatbot-answer"
              placeholder="챗봇 답변을 수정하세요..."
              value={chatbotAnswer}
              onChange={(e) => setChatbotAnswer(e.target.value)}
            ></textarea>
          </div>
        </div>
      </div>
    </>
  );
}
