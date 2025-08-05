import { useEffect, useState, useRef } from 'react';
import WriteModal from '../Pages/WriteModalPage.jsx';
import '../Styles/PostDetailPage.css';

function PostDetailPage({
  postId,
  menu,
  goBack,
  posts,
  onUpdatePost,
  onDeletePost,
  noticePost,
  setNoticePost,
}) {
  const [isEdit, setIsEdit] = useState(false);
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const commentsPerPage = 10;
  const commentInputRef = useRef(null);

  const currentUser = JSON.parse(localStorage.getItem('currentUser'));
  const isAdmin = currentUser?.role === 'admin';
  const didViewRef = useRef(false);

  useEffect(() => {
    const stored = localStorage.getItem('comments');
    const allComments = stored ? JSON.parse(stored) : {};
    setComments(allComments[postId] || []);
    setCurrentPage(1);
  }, [postId]);

  const saveComments = (updated) => {
    const stored = localStorage.getItem('comments');
    const all = stored ? JSON.parse(stored) : {};
    all[postId] = updated;
    localStorage.setItem('comments', JSON.stringify(all));
  };

  const handleAddComment = () => {
    if (!currentUser) {
      alert('로그인 후 댓글 작성이 가능합니다.');
      return;
    }
    if (!newComment.trim()) return;
    const updated = [
      ...comments,
      {
        user: currentUser.id, // 아이디만!
        authorId: currentUser.id,
        text: newComment,
        createdAt: new Date().toLocaleString(),
      },
    ];
    setComments(updated);
    saveComments(updated);
    setNewComment('');
    setCurrentPage(1);
    if (commentInputRef.current) commentInputRef.current.focus();
  };

  const handleCommentKeyDown = (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      handleAddComment();
    }
  };

  useEffect(() => {
    if (didViewRef.current) return;
    didViewRef.current = true;
    if (postId?.toString().startsWith('notice')) {
      if (!noticePost) {
        setPost(null);
        return;
      }
      const updated = { ...noticePost, views: (noticePost.views ?? 0) + 1 };
      setPost(updated);
      setNoticePost(updated);
    } else {
      const found = posts.find((p) => p.id === Number(postId));
      if (found) {
        const updated = { ...found, views: found.views + 1 };
        setPost(updated);
        onUpdatePost(updated.id, { views: updated.views });
      } else {
        setPost(null);
      }
    }
    // eslint-disable-next-line
  }, [postId]);

  if (!post) {
    return (
      <div className="board-detail-root">
        <div className="board post-detail">게시글을 찾을 수 없습니다.</div>
      </div>
    );
  }

  const isOwner = currentUser && post.authorId === currentUser.id;

  const handleLike = () => {
    const updated = { ...post, likes: post.likes + 1 };
    setPost(updated);
    if (postId?.toString().startsWith('notice')) {
      setNoticePost(updated);
    } else {
      onUpdatePost(post.id, { likes: updated.likes });
    }
  };

  const handleReport = () => {
    if (window.confirm('이 게시글을 신고하시겠습니까?')) {
      alert('🚨 신고가 완료되었습니다.');
    }
  };

  const sortedComments = [...comments].reverse();
  const totalPages = Math.ceil(sortedComments.length / commentsPerPage);
  const startIndex = (currentPage - 1) * commentsPerPage;
  const currentComments = sortedComments.slice(startIndex, startIndex + commentsPerPage);

  return (
    <div className="board-detail-root">
      <div className="board post-detail">
        <div className="post-title">{post.title}</div>

        {/* 작성자/조회/추천수 한줄, 오른쪽 정렬 */}
        <div className="post-meta-row">
          <div className="post-meta-writer">
            작성자: {post.authorId}
          </div>
          <div className="post-meta-right">
            <span>
              조회수: <span className="post-meta-views">{post.views}</span>
            </span>
            <span>
              추천: <span className="post-meta-likes">{post.likes}</span>
            </span>
          </div>
        </div>

        {(isOwner || isAdmin) && postId !== 'notice' && (
          <div className="post-btn-right">
            {isOwner && (
              <button className="action-button" onClick={() => setIsEdit(true)}>
                ✏ 수정
              </button>
            )}
            <button
              className="action-button"
              onClick={() => {
                if (window.confirm('정말 삭제하시겠습니까?')) {
                  onDeletePost(post.id);
                  goBack();
                }
              }}
            >
              🗑 삭제
            </button>
          </div>
        )}

        <div className="post-content">
          {post.content
            ? post.content.split('\n').map((line, i) => <p key={i}>{line}</p>)
            : <p>내용이 없습니다.</p>}
          {post.image && (
            <div className="post-image">
              <img src={post.image} alt="게시글 이미지" />
            </div>
          )}
        </div>

        <div className="post-actions">
          <button className="action-button post-like-btn" onClick={handleLike}>
            👍 추천
          </button>
          {postId !== 'notice' && (
            <button className="action-button post-report-btn" onClick={handleReport}>
              🚨 신고하기
            </button>
          )}
        </div>

        <div className="back-area">
          <button className="back-button" onClick={goBack}>
            ← 목록으로
          </button>
        </div>

        {isEdit && (
          <WriteModal
            initialTitle={post.title}
            initialContent={post.content}
            initialImage={post.image}
            onClose={() => setIsEdit(false)}
            onSubmit={(updatedPost) => {
              const final = { ...post, ...updatedPost };
              onUpdatePost(final.id, final);
              setPost(final);
              setIsEdit(false);
            }}
          />
        )}

        {/* 댓글 */}
        <div className="comment-section">
          <h3 className="comment-title">💬 댓글</h3>
          <div className="comment-input">
            <textarea
              ref={commentInputRef}
              placeholder={
                currentUser ? "댓글을 입력하세요" : "로그인 후 댓글 작성 가능"
              }
              value={newComment}
              onChange={(e) => setNewComment(e.target.value)}
              onKeyDown={handleCommentKeyDown}
              disabled={!currentUser}
            />
            <button
              className="comment-register-btn"
              onClick={handleAddComment}
              type="button"
              disabled={!currentUser}
            >
              등록
            </button>
          </div>
          <ul className="comment-list">
            {currentComments.length === 0 ? (
              <li className="no-comments">아직 댓글이 없습니다.</li>
            ) : (
              currentComments.map((c, i) => {
                const originalIndex = comments.length - 1 - (startIndex + i);
                const isCommentOwner = currentUser && c.authorId === currentUser.id;
                const isEditing = c.isEditing || false;
                return (
                  <li key={i} className="comment-item">
                    <div className="comment-meta">
                      <strong>{c.user}</strong>
                      <span className="comment-date">{c.createdAt}</span>
                    </div>
                    {isEditing ? (
                      <div className="comment-edit-area">
                        <textarea
                          value={c.editValue ?? c.text}
                          onChange={(e) => {
                            const updated = [...comments];
                            updated[originalIndex].editValue = e.target.value;
                            updated[originalIndex].isEditing = true;
                            setComments(updated);
                          }}
                        />
                        <div className="comment-buttons">
                          <button
                            onClick={() => {
                              const updated = [...comments];
                              updated[originalIndex].text = updated[originalIndex].editValue || updated[originalIndex].text;
                              delete updated[originalIndex].editValue;
                              delete updated[originalIndex].isEditing;
                              setComments(updated);
                              saveComments(updated);
                            }}
                          >저장</button>
                          <button
                            onClick={() => {
                              const updated = [...comments];
                              delete updated[originalIndex].editValue;
                              delete updated[originalIndex].isEditing;
                              setComments(updated);
                            }}
                          >취소</button>
                        </div>
                      </div>
                    ) : (
                      <>
                        <div className="comment-text">{c.text}</div>
                        <div className="comment-buttons">
                          {(isCommentOwner || isAdmin) && (
                            <button
                              className="comment-delete"
                              onClick={() => {
                                if (window.confirm('댓글을 삭제할까요?')) {
                                  const updated = comments.filter((_, idx) => idx !== originalIndex);
                                  setComments(updated);
                                  saveComments(updated);
                                }
                              }}
                            >삭제</button>
                          )}
                          {isCommentOwner && (
                            <button
                              className="comment-edit"
                              onClick={() => {
                                const updated = [...comments];
                                updated[originalIndex].isEditing = true;
                                updated[originalIndex].editValue = updated[originalIndex].text;
                                setComments(updated);
                              }}
                            >수정</button>
                          )}
                        </div>
                      </>
                    )}
                  </li>
                );
              })
            )}
          </ul>
          {totalPages > 1 && (
            <div className="comment-pagination">
              {Array.from({ length: totalPages }, (_, i) => (
                <button
                  key={i}
                  className={currentPage === i + 1 ? 'active' : ''}
                  onClick={() => setCurrentPage(i + 1)}
                >
                  {i + 1}
                </button>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default PostDetailPage;
