import React, { useState } from "react";
import AdminHeader from "../components/AdminHeader";
import AdminSidebar from "../components/AdminSidebar";
import "../Styles/AdminInquiryPage.css";

const inquiriesData = [
  {
    reporterId: "dndud1", badUserId: "ewqws1", role: "user",
    title: "매칭이 되었는데 이럴수있나.",
    content: "매칭이 되었는데 상대방이 약속 장소를 계속 바꾸고, 연락을 받지 않았습니다.",
    date: "2025-8-01 T 08:14"
  },
  { reporterId: "wowon777", badUserId: "soo2321", role: "user", title: "너무 불친절했습니다.", content: "말투가 너무 불편했습니다.", date: "2025-8-01 T 12:33" },
  { reporterId: "woghd_123", badUserId: "asd223", role: "user", title: "요청사항말고 다른걸 요구했습니다.", content: "다른 부탁을 들어달라고 했습니다.", date: "2025-8-02 T 17:52" },
  { reporterId: "reporterA", badUserId: "userB", role: "user", title: "개인정보 요구", content: "전화번호와 주민번호를 요구함", date: "2025-8-02 T 18:00" },
  { reporterId: "reporterC", badUserId: "userD", role: "user", title: "약속시간 불이행", content: "시간 안 지킴", date: "2025-8-03 T 09:10" },
];

const inquiriesPerPage = 5;

function AdminInquiryPage() {
  const filteredInquiries = inquiriesData.filter(q => q.role !== "admin");
  const [currentPage, setCurrentPage] = useState(1);
  const [selectedIdx, setSelectedIdx] = useState(0);
  const [showReply, setShowReply] = useState(false);
  const [replyText, setReplyText] = useState("");
  const [openSanction, setOpenSanction] = useState(false);

  const start = (currentPage - 1) * inquiriesPerPage;
  const end = start + inquiriesPerPage;
  const pageList = filteredInquiries.slice(start, end);
  const totalPages = Math.ceil(filteredInquiries.length / inquiriesPerPage);
  const selectedInquiry = pageList[selectedIdx] || {};

  function handleRowClick(idx) {
    setSelectedIdx(idx);
    setShowReply(false);
    setOpenSanction(false);
  }

  function handleReplySend() {
    if (replyText.trim() === "") {
      alert("답변 내용을 입력해주세요.");
      return;
    }
    alert(`${selectedInquiry.reporterId}님께 답변이 전송되었습니다.`);
    setShowReply(false);
    setReplyText("");
  }

  function handlePage(p) {
    setCurrentPage(p);
    setSelectedIdx(0);
    setShowReply(false);
    setOpenSanction(false);
  }

  return (
    <>
      <AdminHeader />
      <div className="admin-inquiry-root">
        <AdminSidebar />
        <div className="admin-inquiry-main-content-vertical">
          <div className="admin-inquiry-list-box">
            <div className="admin-inquiry-list-title">문의 내역</div>
            <table className="admin-inquiry-list-table">
              <thead>
                <tr>
                  <th>신고자 아이디</th>
                  <th>유저신고</th>
                  <th>누적</th>
                  <th>문의 제목</th>
                  <th>문의날짜</th>
                </tr>
              </thead>
              <tbody>
                {pageList.length === 0 ? (
                  <tr><td colSpan={5}>문의 내역이 없습니다.</td></tr>
                ) : (
                  pageList.map((inquiry, i) => (
                    <tr
                      key={i}
                      className={selectedIdx === i ? "selected-row" : ""}
                      onClick={() => handleRowClick(i)}
                      style={{ cursor: "pointer" }}
                    >
                      <td>{inquiry.reporterId}</td>
                      <td>{inquiry.badUserId}</td>
                      <td>0회</td>
                      <td className="admin-inquiry-list-title-cell">{inquiry.title}</td>
                      <td>{inquiry.date}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
            {totalPages > 1 && (
              <div className="admin-inquiry-pagination">
                <a href="#" onClick={e => { e.preventDefault(); if (currentPage > 1) handlePage(currentPage - 1); }} className={currentPage === 1 ? "disabled" : ""}>&lt;</a>
                {[...Array(totalPages)].map((_, i) => (
                  <a href="#" key={i + 1} onClick={e => { e.preventDefault(); handlePage(i + 1); }} className={currentPage === i + 1 ? "active" : ""}>{i + 1}</a>
                ))}
                <a href="#" onClick={e => { e.preventDefault(); if (currentPage < totalPages) handlePage(currentPage + 1); }} className={currentPage === totalPages ? "disabled" : ""}>&gt;</a>
              </div>
            )}

            <div className="admin-inquiry-detail-box">
              <div className="admin-inquiry-detail-title">상세내용</div>
              <div className="admin-inquiry-detail-row">
                <span className="detail-label">신고자</span>
                <span className="detail-value">{selectedInquiry.reporterId || ""}</span>
                <span className="detail-label">제목</span>
                <span className="detail-value detail-title-value">{selectedInquiry.title || ""}</span>
                <button className="detail-reply-btn" onClick={() => setShowReply(v => !v)}>답장</button>
              </div>
              <div className="admin-inquiry-detail-content-area">
                <div className="detail-label" style={{ marginBottom: 4 }}>내용</div>
                <div className="detail-content">{selectedInquiry.content || ""}</div>
              </div>
              {showReply && (
                <div className="detail-reply-form show">
                  <div style={{ marginBottom: 7, fontWeight: "bold" }}>답변</div>
                  <textarea
                    className="detail-reply-textarea"
                    placeholder="답변 내용을 입력하세요."
                    value={replyText}
                    onChange={e => setReplyText(e.target.value)}
                    autoFocus
                  />
                  <div className="detail-reply-actions">
                    <button className="detail-reply-cancel-btn" onClick={() => { setShowReply(false); setReplyText(""); }}>취소</button>
                    <button className="detail-reply-submit-btn" onClick={handleReplySend}>전송</button>
                  </div>
                </div>
              )}
              <div className="admin-inquiry-detail-action-row">
                <span className="bad-user-label">불량 유저 아이디</span>
                <span className="bad-user-value">{selectedInquiry.badUserId || ""}</span>

                <div className="admin-user-sanction-container">
                  <button
                    className="detail-sanction-btn"
                    onClick={() => setOpenSanction(!openSanction)}
                  >
                    제재
                  </button>

                  {openSanction && (
                    <div className="admin-user-sanction-dropdown">
                      <a href="#" onClick={e => { e.preventDefault(); setOpenSanction(false); }}>경고</a>
                      <a href="#" onClick={e => { e.preventDefault(); setOpenSanction(false); }}>1일정지</a>
                      <a href="#" onClick={e => { e.preventDefault(); setOpenSanction(false); }}>7일정지</a>
                      <a href="#" onClick={e => { e.preventDefault(); setOpenSanction(false); }}>계정삭제</a>
                    </div>
                  )}
                </div>
              </div>
            </div>

          </div>
        </div>
      </div>
    </>
  );
}

export default AdminInquiryPage;
