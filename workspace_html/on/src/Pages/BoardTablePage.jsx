import { useState } from 'react';
import WriteModal from '../Pages/WriteModalPage.jsx';
import '../Styles/BoardTablePage.css';

function BoardTablePage({
  selectedMenu,
  posts,
  onPostClick,
  onAddPost,
  noticePosts = [],
  onAddNotice,
  isAdmin,
  onNoticeClick
}) {
  const [showModal, setShowModal] = useState(false);
  const [showNoticeModal, setShowNoticeModal] = useState(false);

  // 현재 로그인 유저 정보 가져오기
  const currentUser = JSON.parse(localStorage.getItem('currentUser'));
  // 글쓰기 권한: 로그인만
  const canWrite = !!currentUser;
  const reversedPosts = [...posts].reverse();

  return (
    <div className="comm-board">
      <div className="comm-board-header">
        <span className="comm-board-title">{selectedMenu}</span>
        {selectedMenu === '자유 게시글' && isAdmin && (
          <button
            className="comm-notice-btn"
            style={{ marginLeft: 12, height: 36, minWidth: 90 }}
            onClick={() => setShowNoticeModal(true)}
          >
            공지사항 등록
          </button>
        )}
        {selectedMenu !== '운영자 공지사항' && canWrite && (
          <button className="comm-write-btn" onClick={() => setShowModal(true)}>
            글쓰기
          </button>
        )}
        {selectedMenu !== '운영자 공지사항' && !canWrite && (
          <span style={{ color: '#bbb', fontSize: '0.99rem' }}>
            로그인 후 글쓰기 가능
          </span>
        )}
      </div>
      {showModal && (
        <WriteModal
          onClose={() => setShowModal(false)}
          onSubmit={(post) => {
            onAddPost({
              ...post,
              authorId: currentUser?.id || 'guest', // 오로지 아이디만!
            });
            setShowModal(false);
          }}
        />
      )}
      {showNoticeModal && (
        <WriteModal
          onClose={() => setShowNoticeModal(false)}
          onSubmit={(post) => {
            onAddNotice({
              ...post,
              authorId: currentUser?.id || 'admin', // 오로지 아이디만!
            });
            setShowNoticeModal(false);
          }}
        />
      )}

      <table className="comm-board-table">
        <thead>
          <tr>
            <th style={{ width: '60px' }}>번호</th>
            <th>게시글 제목</th>
            <th style={{ width: '90px' }}>글쓴이</th>
            <th style={{ width: '70px' }}>추천수</th>
            <th style={{ width: '70px' }}>조회수</th>
          </tr>
        </thead>
        <tbody>
          {/* 공지사항(여러 개) */}
          {selectedMenu === '자유 게시글' &&
            noticePosts &&
            noticePosts.length > 0 &&
            noticePosts.map((notice, idx) => (
              <tr className="comm-board-notice-row" key={notice.id}>
                <td>🚨</td>
                <td>
                  <button
                    className="comm-board-title-btn"
                    onClick={() => onNoticeClick(notice.id)}
                  >
                    {notice.title}
                  </button>
                </td>
                <td>{notice.authorId}</td>
                <td>{notice.likes}</td>
                <td>{notice.views}</td>
              </tr>
            ))}
          {/* 일반글 */}
          {reversedPosts.length === 0 ? (
            <tr>
              <td colSpan="5" className="comm-board-empty">
                게시글이 없습니다.
              </td>
            </tr>
          ) : (
            reversedPosts.map((post, index) => (
              <tr key={post.id}>
                <td>{reversedPosts.length - index}</td>
                <td>
                  <button
                    className="comm-board-title-btn"
                    onClick={() => onPostClick(post.id)}
                  >
                    {post.title}
                  </button>
                </td>
                <td>{post.authorId}</td>
                <td>{post.likes}</td>
                <td>{post.views}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default BoardTablePage;
