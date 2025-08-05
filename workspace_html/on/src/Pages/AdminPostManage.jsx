import { useState } from 'react';
import '../Styles/AdminPostManage.css';
import AdminHeader from '../components/AdminHeader';
import AdminSidebar from '../components/AdminSidebar';

const INIT_POSTS = [
  // ... 기존 4개 + 예시로 12개로 늘려서 테스트할 수 있음
  { author: "ehdgus12", title: "동현이 바보.", content: "************욕설 ***************", date: "2025-7-31 T 14:42" },
  { author: "dndud1", title: "바보바보바보바보", content: "내용 예시 2", date: "2025-8-01 T 08:14" },
  { author: "wowon777", title: "안녕하세요 반갑습니다", content: "내용 예시 3", date: "2025-8-01 T 12:33" },
  { author: "woghd_123", title: "이거 어떻게함", content: "내용 예시 4", date: "2025-8-02 T 17:52" },
  { author: "user5", title: "다섯번째글", content: "내용5", date: "2025-8-03 T 10:12" },
  { author: "user6", title: "여섯번째글", content: "내용6", date: "2025-8-03 T 11:30" },
  { author: "user7", title: "일곱번째글", content: "내용7", date: "2025-8-03 T 12:50" },
  { author: "user8", title: "여덟번째글", content: "내용8", date: "2025-8-03 T 13:02" },
  { author: "user9", title: "아홉번째글", content: "내용9", date: "2025-8-03 T 13:10" },
  { author: "user10", title: "열번째글", content: "내용10", date: "2025-8-03 T 13:11" },
  { author: "user11", title: "열한번째글", content: "내용11", date: "2025-8-03 T 13:12" },
  { author: "user12", title: "열두번째글", content: "내용12", date: "2025-8-03 T 13:13" },
];

const POSTS_PER_PAGE = 10;

export default function AdminPostManage() {
  const [posts, setPosts] = useState(INIT_POSTS);
  const [selectedIdx, setSelectedIdx] = useState(null);
  const [sanctionOpen, setSanctionOpen] = useState(false);
  const [sanctionOpt, setSanctionOpt] = useState('warning');
  const [page, setPage] = useState(1);

  // 페이징 계산
  const totalPages = Math.ceil(posts.length / POSTS_PER_PAGE);
  const pagedPosts = posts.slice(
    (page - 1) * POSTS_PER_PAGE,
    page * POSTS_PER_PAGE
  );

  // 페이지 바뀔 때 상세선택 해제(UX 개선)
  const handlePageChange = (p) => {
    setPage(p);
    setSelectedIdx(null);
    setSanctionOpen(false);
  };

  // index를 전체 posts 기준이 아니라, 현재 페이지 posts에서의 index로 사용
  const handleRowClick = (idx) => {
    setSelectedIdx((page - 1) * POSTS_PER_PAGE + idx);
    setSanctionOpen(false);
  };
  const handleDelete = (idx, e) => {
    e.stopPropagation();
    if (!window.confirm('정말 삭제하시겠습니까?')) return;
    const realIdx = (page - 1) * POSTS_PER_PAGE + idx;
    const newPosts = posts.filter((_, i) => i !== realIdx);
    setPosts(newPosts);
    if (selectedIdx === realIdx) setSelectedIdx(null);
    else if (selectedIdx > realIdx) setSelectedIdx(selectedIdx - 1);
  };
  const handleDetailDelete = () => {
    if (selectedIdx == null) return;
    if (!window.confirm('정말 삭제하시겠습니까?')) return;
    setPosts(posts.filter((_, i) => i !== selectedIdx));
    setSelectedIdx(null);
    setSanctionOpen(false);
  };
  const handleSanctionBtn = () => setSanctionOpen((open) => !open);
  const handleSanctionApply = () => {
    if (selectedIdx == null) {
      alert('먼저 게시글을 선택해주세요.');
      return;
    }
    const opt = sanctionOpt;
    const title = posts[selectedIdx].title;
    let msg = '';
    switch (opt) {
      case 'warning': msg = '경고 처리되었습니다.'; break;
      case '1day': msg = '1일 정지 처리되었습니다.'; break;
      case '7days': msg = '7일 정지 처리되었습니다.'; break;
      case 'delete': msg = '계정이 삭제 처리되었습니다.'; break;
      default: break;
    }
    alert(`"${title}" 게시글 작성자에게 ${msg}`);
    handleDetailDelete();
  };

  const detail = selectedIdx != null ? posts[selectedIdx] : null;

  // 페이지네이션 UI
  const Pagination = () => {
    const arr = [];
    for (let i = 1; i <= totalPages; ++i) arr.push(i);
    return (
      <div className="admin-post-pagination">
        <a href="#" onClick={e => { e.preventDefault(); if (page > 1) handlePageChange(page - 1); }}>&lt;</a>
        {arr.map(num => (
          <a
            href="#"
            key={num}
            className={num === page ? "admin-post-current" : ""}
            onClick={e => { e.preventDefault(); handlePageChange(num); }}
          >{num}</a>
        ))}
        <a href="#" onClick={e => { e.preventDefault(); if (page < totalPages) handlePageChange(page + 1); }}>&gt;</a>
      </div>
    );
  };

  return (
    <>
      <AdminHeader />
      <div className="admin-post-container">
        <div className="admin-post-layout">
          <AdminSidebar />
          <div className="admin-post-main">
            <h1 className="admin-post-title">게시글 목록</h1>

            <table className="admin-post-table">
              <thead>
                <tr>
                  <th>아이디</th>
                  <th>게시글 제목</th>
                  <th>글작성날짜</th>
                  <th>삭제</th>
                </tr>
              </thead>
              <tbody>
                {pagedPosts.map((row, i) => (
                  <tr
                    key={i}
                    className={selectedIdx === (page - 1) * POSTS_PER_PAGE + i ? 'admin-post-selected' : ''}
                    onClick={() => handleRowClick(i)}
                    style={{ cursor: 'pointer' }}
                  >
                    <td>{row.author}</td>
                    <td>{row.title}</td>
                    <td>{row.date}</td>
                    <td>
                      <button
                        className="admin-post-btn-delete"
                        onClick={(e) => handleDelete(i, e)}
                      >삭제</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            {/* 페이지네이션 */}
            <Pagination />

            {/* 상세보기 */}
            <div
              className="admin-post-detail"
              style={{ display: detail ? 'block' : 'none' }}
            >
              <div className="admin-post-row">
                <label>작성자</label>
                <span className="admin-post-value" id="admin-post-detail-author">{detail?.author}</span>
                <label>제목</label>
                <span className="admin-post-value" id="admin-post-detail-title">{detail?.title}</span>
                <button
                  className="admin-post-btn-delete"
                  id="admin-post-detail-delete-btn"
                  onClick={handleDetailDelete}
                >삭제</button>
                <button
                  className="admin-post-btn-block"
                  id="admin-post-sanction-btn"
                  onClick={handleSanctionBtn}
                >제재</button>
              </div>
              <div
                id="admin-post-sanction-container"
                style={{
                  display: sanctionOpen ? 'flex' : 'none',
                  gap: 8,
                  alignItems: 'center',
                }}
              >
                <label htmlFor="admin-post-sanction-select">제재 옵션:</label>
                <select
                  id="admin-post-sanction-select"
                  value={sanctionOpt}
                  onChange={e => setSanctionOpt(e.target.value)}
                >
                  <option value="warning">경고</option>
                  <option value="1day">1일 정지</option>
                  <option value="7days">7일 정지</option>
                  <option value="delete">계정 삭제</option>
                </select>
                <button
                  id="admin-post-sanction-apply-btn"
                  onClick={handleSanctionApply}
                >적용</button>
              </div>
              <div className="admin-post-content">
                <label>내용</label>
                <textarea
                  readOnly
                  id="admin-post-detail-content"
                  value={detail?.content || ''}
                ></textarea>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
