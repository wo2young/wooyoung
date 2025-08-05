import React, { useState } from "react";
import AdminHeader from "../components/AdminHeader";
import AdminSidebar from "../components/AdminSidebar";
import ReactDatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "../Styles/AdminUser.css";

// 사용자 초기 데이터 (7개)
const initialUsers = [
  {
    id: "kimwooyoung",
    name: "김우영",
    role: "admin",
    joinDate: "2025-07-01",
    lastAccess: "2025-08-03",
    warnings: 0,
    mileage: 12000,
  },
  {
    id: "shimjaewon",
    name: "심재원",
    role: "user",
    joinDate: "2025-06-27",
    lastAccess: "2025-08-02",
    warnings: 1,
    mileage: 9500,
  },
  {
    id: "kimdonghyun",
    name: "김동현",
    role: "user",
    joinDate: "2025-06-28",
    lastAccess: "2025-08-01",
    warnings: 2,
    mileage: 1000,
  },
  {
    id: "hayonggoon",
    name: "하용군",
    role: "user",
    joinDate: "2025-07-02",
    lastAccess: "2025-08-03",
    warnings: 0,
    mileage: 4300,
  },
  {
    id: "wowon777",
    name: "오원석",
    role: "user",
    joinDate: "2025-07-01",
    lastAccess: "2025-07-28",
    warnings: 0,
    mileage: 7500,
  },
  {
    id: "dndud1",
    name: "이동현",
    role: "user",
    joinDate: "2025-07-01",
    lastAccess: "2025-07-31",
    warnings: 3,
    mileage: 100,
  },
  {
    id: "woghd_123",
    name: "장문성",
    role: "user",
    joinDate: "2025-07-03",
    lastAccess: "2025-07-30",
    warnings: 1,
    mileage: 500,
  },
];

// 요청 초기 데이터 (7개)
const initialRequests = [
  {
    authorId: "shimjaewon",
    content: "장보기 동행이 필요해요",
    time: "2025-08-01 13:32",
    status: "진행중",
  },
  {
    authorId: "kimdonghyun",
    content: "동네 미용실 추천 좀 해주세요",
    time: "2025-07-30 09:42",
    status: "진행중",
  },
  {
    authorId: "hayonggoon",
    content: "컴퓨터 고장났어요 도와주세요",
    time: "2025-08-02 10:10",
    status: "종료",
  },
  {
    authorId: "kimwooyoung",
    content: "동네 행사 정보 알려주세요",
    time: "2025-08-02 15:20",
    status: "진행중",
  },
  {
    authorId: "wowon777",
    content: "우체국 가는 길 물어봐요",
    time: "2025-08-01 08:33",
    status: "종료",
  },
  {
    authorId: "dndud1",
    content: "쓰레기 버리는 날 문의",
    time: "2025-07-29 21:44",
    status: "진행중",
  },
  {
    authorId: "woghd_123",
    content: "동네에 새로 생긴 가게가 어디에요?",
    time: "2025-08-03 11:10",
    status: "종료",
  },
];

// 요청 상세 객체(샘플: content를 key로 함)
const requestData = {
  "장보기 동행이 필요해요": {
    상태: "진행중",
    아이디: "shimjaewon",
    나이: 27,
    이름: "심재원"
  },
  "동네 미용실 추천 좀 해주세요": {
    상태: "진행중",
    아이디: "kimdonghyun",
    나이: 28,
    이름: "김동현"
  },
  "컴퓨터 고장났어요 도와주세요": {
    상태: "종료",
    아이디: "hayonggoon",
    나이: 25,
    이름: "하용군"
  },
  "동네 행사 정보 알려주세요": {
    상태: "진행중",
    아이디: "kimwooyoung",
    나이: 26,
    이름: "김우영"
  },
  "우체국 가는 길 물어봐요": {
    상태: "종료",
    아이디: "wowon777",
    나이: 24,
    이름: "오원석"
  },
  "쓰레기 버리는 날 문의": {
    상태: "진행중",
    아이디: "dndud1",
    나이: 29,
    이름: "이동현"
  },
  "동네에 새로 생긴 가게가 어디에요?": {
    상태: "종료",
    아이디: "woghd_123",
    나이: 23,
    이름: "장문성"
  }
};

function AdminUser() {
  const [users, setUsers] = useState(initialUsers);
  const [requests, setRequests] = useState(initialRequests);

  const [searchId, setSearchId] = useState("");
  const [modal, setModal] = useState({ open: false, content: "", data: null });
  const [filter, setFilter] = useState("전체 보기");
  const [editingMileageId, setEditingMileageId] = useState(null);
  const [mileageInput, setMileageInput] = useState(0);
  const [userPage, setUserPage] = useState(1);
  const USERS_PER_PAGE = 5;
  const [reqPage, setReqPage] = useState(1);
  const REQ_PER_PAGE = 5;

  // 날짜 필터 state
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);

  const handleLogout = () => { alert("로그아웃 되었습니다."); };
  const handleSearch = () => { setUserPage(1); };
  const handleReset = () => { setSearchId(""); setUserPage(1); };
  const handleMasterReset = () => {
    if (window.confirm("정말로 모든 일반 계정 및 요청 목록을 삭제하시겠습니까?\n관리자 계정은 유지됩니다.")) {
      setUsers(users.filter((user) => user.role === "admin"));
      setRequests([]);
      alert("관리자 계정을 제외한 모든 목록이 삭제되었습니다.");
    }
  };
  const handleSanction = (userId, action) => {
    const user = users.find((u) => u.id === userId);
    if (!user) return;
    if (user.role === "admin" && action === "계정삭제") {
      alert("관리자 계정은 삭제할 수 없습니다.");
      return;
    }
    if (["경고", "1일정지", "7일정지"].includes(action)) {
      setUsers((prev) =>
        prev.map((u) => u.id === userId ? { ...u, warnings: (u.warnings || 0) + 1 } : u)
      );
      alert(`${action} 처리 및 경고 횟수 1회가 추가되었습니다.`);
    } else if (action === "계정삭제") {
      if (window.confirm(`'${userId}' 계정을 정말로 삭제하시겠습니까?\n해당 사용자의 요청 내역도 함께 삭제됩니다.`)) {
        setUsers((prev) => prev.filter((u) => u.id !== userId));
        setRequests((prev) => prev.filter((req) => req.authorId !== userId));
        alert(`'${userId}' 계정이 삭제되었습니다.`);
      }
    }
  };
  const handleMileageEdit = (userId, currentMileage) => {
    setEditingMileageId(userId);
    setMileageInput(currentMileage);
  };
  const handleMileageSave = (userId) => {
    setUsers((prev) =>
      prev.map((u) => u.id === userId ? { ...u, mileage: Number(mileageInput) } : u)
    );
    setEditingMileageId(null);
    alert(`포인트가 ${mileageInput}p로 수정되었습니다.`);
  };
  const handleMileageCancel = () => setEditingMileageId(null);

  const openModal = (content) => {
    setModal({
      open: true,
      content,
      data: requestData[content] || null,
    });
  };
  const closeModal = () => setModal({ open: false, content: "", data: null });

  const handleFilter = (filterText) => {
    setFilter(filterText);
    setReqPage(1);
  };

  // 데이터 필터 + 페이징
  const filteredUsers = users.filter((user) =>
    searchId.trim() === "" ? true : user.id.toLowerCase().includes(searchId.toLowerCase())
  );
  const totalUserPages = Math.ceil(filteredUsers.length / USERS_PER_PAGE) || 1;
  const pagedUsers = filteredUsers.slice(
    (userPage - 1) * USERS_PER_PAGE,
    userPage * USERS_PER_PAGE
  );

  // 날짜 필터까지 적용한 요청 필터링
  const filteredRequests =
    (filter === "전체 보기"
      ? requests
      : requests.filter((req) => {
        if (filter === "현재진행중") return req.status === "진행중";
        if (filter === "종료 목록") return req.status === "종료";
        return true;
      }))
      .filter((req) => {
        if (!startDate && !endDate) return true;
        const reqDateStr = req.time.split(" ")[0];
        const reqDate = new Date(reqDateStr);
        if (startDate && reqDate < startDate) return false;
        if (endDate && reqDate > endDate) return false;
        return true;
      });

  const totalReqPages = Math.ceil(filteredRequests.length / REQ_PER_PAGE) || 1;
  const pagedRequests = filteredRequests.slice(
    (reqPage - 1) * REQ_PER_PAGE,
    reqPage * REQ_PER_PAGE
  );

  function Pagination({ page, total, onChange }) {
    const arr = [];
    for (let i = 1; i <= total; ++i) arr.push(i);
    return (
      <div className="admin-user-pagination">
        <button className="admin-user-page-btn" disabled={page === 1} onClick={() => onChange(page - 1)}>&lt;</button>
        {arr.map(num => (
          <button key={num} className={`admin-user-page-btn${page === num ? " active" : ""}`} onClick={() => onChange(num)}>
            {num}
          </button>
        ))}
        <button className="admin-user-page-btn" disabled={page === total} onClick={() => onChange(page + 1)}>&gt;</button>
      </div>
    );
  }

  return (
    <>
      <AdminHeader onLogout={handleLogout} />
      <div className="admin-user-main">
        <AdminSidebar />
        <main className="admin-user-content">
          <div className="admin-user-content-box">
            <div className="admin-user-search-bar">
              <label htmlFor="admin-user-search-id">아이디</label>
              <input
                type="text"
                id="admin-user-search-id"
                placeholder="아이디를 입력해주세요."
                value={searchId}
                onChange={(e) => setSearchId(e.target.value)}
                onKeyDown={(e) => { if (e.key === "Enter") handleSearch(); }}
              />
              <button className="admin-user-search-btn" onClick={handleSearch}>검색</button>
              <button className="admin-user-reset-btn" onClick={handleReset}>초기화</button>
              <button className="admin-user-master-reset-btn" onClick={handleMasterReset}>전체 초기화</button>
            </div>
            <table className="admin-user-table">
              <thead>
                <tr>
                  <th>아이디</th>
                  <th>가입일</th>
                  <th>최근접속일</th>
                  <th>경고횟수</th>
                  <th>마일리지</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {pagedUsers.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.joinDate}</td>
                    <td>{user.lastAccess}</td>
                    <td>{user.warnings}회</td>
                    <td>
                      {editingMileageId === user.id ? (
                        <div className="admin-user-mileage-edit-container">
                          <div className="admin-user-input-row">
                            <input
                              type="number"
                              className="admin-user-point-input-main"
                              value={mileageInput}
                              onChange={(e) => setMileageInput(e.target.value)}
                            />
                            <button className="admin-user-point-adjust-btn" onClick={() => setMileageInput((m) => Number(m) + 10)}>+10</button>
                            <button className="admin-user-point-adjust-btn" onClick={() => setMileageInput((m) => Number(m) - 10)}>-10</button>
                          </div>
                          <div className="admin-user-button-row">
                            <button className="admin-user-save-btn" onClick={() => handleMileageSave(user.id)}>저장</button>
                            <button className="admin-user-cancel-btn" onClick={handleMileageCancel}>취소</button>
                          </div>
                        </div>
                      ) : (
                        <div className="admin-user-mileage-info">
                          <span className="admin-user-mileage-text">{user.mileage}p</span>
                          <a
                            href="#"
                            className="admin-user-modify-btn"
                            onClick={(e) => {
                              e.preventDefault();
                              handleMileageEdit(user.id, user.mileage);
                            }}
                          >수정</a>
                        </div>
                      )}
                    </td>
                    <td>
                      <SanctionDropdown
                        onSanction={(action) => handleSanction(user.id, action)}
                        isAdmin={user.role === "admin"}
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <Pagination page={userPage} total={totalUserPages} onChange={setUserPage} />
          </div>
          <div className="admin-user-content-box">
            <div
              className="admin-user-request-header"
              style={{
                marginBottom: 12,
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
              }}
            >
              <span style={{ fontWeight: "bold" }}>요청 대기자</span>
              <div style={{ display: "flex", alignItems: "center", gap: 12 }}>
                <div className="admin-user-filter-date-wrap" style={{ display: "flex", alignItems: "center", gap: 6 }}>
                  <ReactDatePicker
                  // popperPlacement="bottom-start"
                    selected={startDate}
                    onChange={(date) => setStartDate(date)}
                    className="admin-user-date-input"
                    popperClassName="custom-datepicker-popper"
                    selectsStart
                    startDate={startDate}
                    endDate={endDate}
                    maxDate={endDate}
                    dateFormat="yyyy-MM-dd"
                    placeholderText="시작일"
                    isClearable
                  />
                  <span>~</span>
                  <ReactDatePicker
                    selected={endDate}
                    onChange={(date) => setEndDate(date)}
                    className="admin-user-date-input"
                    popperClassName="custom-datepicker-popper"
                    selectsEnd
                    startDate={startDate}
                    endDate={endDate}
                    minDate={startDate}
                    dateFormat="yyyy-MM-dd"
                    placeholderText="종료일"
                    isClearable
                  />
                </div>
                <FilterDropdown onFilter={handleFilter} />
              </div>
            </div>
            <table className="admin-user-req-table">
              <thead>
                <tr>
                  <th>아이디</th>
                  <th>요청내용</th>
                  <th>작성시간</th>
                  <th>상태</th>
                </tr>
              </thead>
              <tbody>
                {pagedRequests.map((req, idx) => (
                  <tr key={idx}>
                    <td>{req.authorId}</td>
                    <td className="admin-user-request-content-cell" onClick={() => openModal(req.content)}>
                      {req.content}
                    </td>
                    <td>{req.time}</td>
                    <td>
                      <span className={req.status === "진행중" ? "admin-user-status-progress" : "admin-user-status-completed"}>
                        {req.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <Pagination page={reqPage} total={totalReqPages} onChange={setReqPage} />
          </div>
        </main>
      </div>
      {modal.open && (
        <div id="admin-user-request-modal" className="admin-user-modal-overlay" onClick={closeModal} style={{ display: "flex" }}>
          <div className="admin-user-modal-content" onClick={(e) => e.stopPropagation()}>
            <span className="admin-user-close-btn" onClick={closeModal}>&times;</span>
            <h2>요청 상세 정보</h2>
            <div id="admin-user-modal-request-text">
              {modal.data ? (
                <>
                  <b>요청내용:</b> {modal.content}<br />
                  <b>상태:</b> {modal.data.상태}<br />
                  <b>아이디:</b> {modal.data.아이디}<br />
                  <b>나이:</b> {modal.data.나이}<br />
                  <b>이름:</b> {modal.data.이름}
                </>
              ) : (
                <>
                  <b>요청내용:</b> {modal.content}<br /><br />
                  해당 요청에 대한 상세 정보가 없습니다.
                </>
              )}
            </div>
          </div>
        </div>
      )}
    </>
  );
}

// 제재 드롭다운
function SanctionDropdown({ onSanction, isAdmin }) {
  const [open, setOpen] = useState(false);
  return (
    <div className="admin-user-sanction-dropdown-wrap" style={{ position: "relative" }}>
      <button
        className="admin-user-sanction-btn"
        type="button"
        onClick={(e) => {
          e.stopPropagation();
          setOpen((v) => !v);
        }}
      >제재</button>
      {open && (
        <div className="admin-user-sanction-dropdown" style={{ display: "block", position: "absolute", zIndex: 10 }}>
          <a href="#" onClick={e => { e.preventDefault(); onSanction("경고"); setOpen(false); }}>경고</a>
          <a href="#" onClick={e => { e.preventDefault(); onSanction("1일정지"); setOpen(false); }}>1일정지</a>
          <a href="#" onClick={e => { e.preventDefault(); onSanction("7일정지"); setOpen(false); }}>7일정지</a>
          <a
            href="#"
            onClick={e => { e.preventDefault(); onSanction("계정삭제"); setOpen(false); }}
            style={isAdmin ? { color: "#ccc", pointerEvents: "none" } : {}}
          >계정삭제</a>
        </div>
      )}
    </div>
  );
}

// 필터 드롭다운
function FilterDropdown({ onFilter }) {
  const [open, setOpen] = useState(false);
  return (
    <div className="admin-user-filter-dropdown-wrap" style={{ position: "relative" }}>
      <button
        className="admin-user-filter-btn"
        type="button"
        onClick={e => { e.stopPropagation(); setOpen(v => !v); }}
      >필터</button>
      {open && (
        <div className="admin-user-filter-dropdown" style={{ display: "block", position: "absolute", zIndex: 10 }}>
          <a href="#" onClick={e => { e.preventDefault(); onFilter("전체 보기"); setOpen(false); }}>전체 보기</a>
          <a href="#" onClick={e => { e.preventDefault(); onFilter("현재진행중"); setOpen(false); }}>현재진행중</a>
          <a href="#" onClick={e => { e.preventDefault(); onFilter("종료 목록"); setOpen(false); }}>종료 목록</a>
        </div>
      )}
    </div>
  );
}

export default AdminUser;
