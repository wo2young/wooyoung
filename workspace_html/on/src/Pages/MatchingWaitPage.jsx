import { useState, useRef, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import Header from "../components/Header";
import "../Styles/MatchingWaitPage.css";
import Layout from "../components/Layout";

function getTimeDiffText(dateStr) {
  const now = new Date();
  const written = new Date(dateStr);
  const diffMs = now - written;
  const diffMin = Math.floor(diffMs / 60000);
  if (diffMin < 1) return "방금 전";
  if (diffMin < 60) return `${diffMin}분 전`;
  const diffHour = Math.floor(diffMin / 60);
  if (diffHour < 24) return `${diffHour}시간 전`;
  const diffDay = Math.floor(diffHour / 24);
  if (diffDay < 7) return `${diffDay}일 전`;
  return `${written.getFullYear()}.${String(written.getMonth() + 1).padStart(2, '0')}.${String(written.getDate()).padStart(2, '0')} ${String(written.getHours()).padStart(2, '0')}:${String(written.getMinutes()).padStart(2, '0')}`;
}

function extractDong(detailAddress) {
  const match = detailAddress && detailAddress.match(/([가-힣0-9]+동)/);
  return match ? match[1] : "";
}

function sortRequests(list, order) {
  return [...list].sort((a, b) => {
    return order === "asc"
      ? new Date(a.date) - new Date(b.date)
      : new Date(b.date) - new Date(a.date);
  });
}

function MatchingWaitPage() {
  const [location, setLocation] = useState("전체");
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [sortOrder, setSortOrder] = useState("desc");
  const [requests, setRequests] = useState([]);
  const dropdownRef = useRef();

  const navigate = useNavigate();
  const routerLocation = useLocation();
  const selectedHelp = routerLocation.state?.selectedHelp;

  useEffect(() => {
    const saved = JSON.parse(localStorage.getItem("helpRequests") || "[]");
    setRequests(saved);
  }, []);

  const dongSet = new Set();
  requests.forEach(req => {
    const dong = extractDong(req.detailAddress || "");
    if (dong) dongSet.add(dong);
  });
  const locations = ["전체", ...Array.from(dongSet)];

  const filteredRequests = sortRequests(
    requests.filter(req => {
      const dong = extractDong(req.detailAddress || "");
      return location === "전체" || dong === location;
    }),
    sortOrder
  );

  let displayRequests = filteredRequests;
  if (selectedHelp) {
    displayRequests = [
      selectedHelp,
      ...filteredRequests.filter(
        req => (selectedHelp.id ? req.id !== selectedHelp.id : req !== selectedHelp)
      ),
    ];
  }

  const handleSortClick = () => setSortOrder((cur) => (cur === "asc" ? "desc" : "asc"));

  useEffect(() => {
    const handleClick = (e) => {
      if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
        setDropdownOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClick);
    return () => document.removeEventListener("mousedown", handleClick);
  }, []);

  const handleAccept = (req) => {
    localStorage.setItem("matchStatus", "accepted");
    navigate("/meeting-wait", {
      state: {
        id: req.id,
        request: req.request || '',
        problem: req.problem || '',
        time: req.time || '',
        place: req.detailAddress || '',
        role: 'helper'
      }
    });
  };

  return (
    <Layout>
      <div className="mhbody">
        <h1 className="mhHeader">당신의 도움이 필요합니다.</h1>
        <div className="mhtop-bar">
          <div style={{ position: "relative" }} ref={dropdownRef}>
            <button
              className={`mhbtn${dropdownOpen ? " active" : ""}`}
              onClick={() => setDropdownOpen((open) => !open)}
            >
              {location}
            </button>
            <div className={`mhdropdown${dropdownOpen ? " show" : ""}`}>
              {locations.map((loc) => (
                <p key={loc} onClick={() => { setLocation(loc); setDropdownOpen(false); }}>
                  {loc}
                </p>
              ))}
            </div>
          </div>
          <button className="mhbtn" onClick={handleSortClick}>
            등록 시간순 {sortOrder === "asc" ? "▲" : "▼"}
          </button>
        </div>
        <div className="mhcontainer">
          <div className="mhrequest-list">
            {displayRequests.length === 0 ? (
              <div style={{ padding: "40px 0", textAlign: "center", color: "#bbb" }}>요청이 없습니다.</div>
            ) : (
              displayRequests.map((req, idx) => {
                const dong = extractDong(req.detailAddress || "");
                return (
                  <div className="mhcard" key={req.id || idx}>
                    <div className="mhprofile"><img src="person.jpg" alt="" /></div>
                    <div className="mhinfo">
                      <p><strong>이름:</strong> {req.name || "사용자"}</p>
                      <p>
                        <strong>요청사항:</strong>
                        <span className="mhrequest-text">{req.request}</span>
                      </p>
                      <div className="mhtag-box">
                        {dong && <p className="mhtag">{dong}</p>}
                        {req.time && (
                          <span className="mhduration">예상 : {req.time}</span>
                        )}
                      </div>
                      {req.problem && <p>불편사항: {req.problem}</p>}
                    </div>
                    <div className="mhtime-box">
                      {req.date ? getTimeDiffText(req.date) : ""}
                    </div>
                    <button
                      className="mhaccept-btn"
                      onClick={() => handleAccept(req)}
                    >
                      수락
                    </button>
                  </div>
                );
              })
            )}
          </div>
          <div className="mhsidebar">
            <a href="#"><div className="mhad-box">협력업체 광고</div></a>
          </div>
        </div>
      </div>
    </Layout>
  );
}

export default MatchingWaitPage;
