import { useEffect, useState } from 'react';
import Layout from '../components/Layout';
import Sidebar from '../components/Sidebar.jsx';
import BoardTable from '../Pages/BoardTablePage.jsx';
import PostDetail from '../pages/PostDetailPage.jsx';
import '../Styles/CommunityPage.css';

function CommunityPage() {
  // 공지사항 여러개 저장
  const [noticePosts, setNoticePosts] = useState(() => {
    const saved = localStorage.getItem('noticePosts');
    return saved ? JSON.parse(saved) : [
      {
        id: 'notice_1',
        title: '운영자 공지사항입니다.',
        content: '사이트 이용 시 규칙을 지켜주세요.\n운영자 공지사항입니다.',
        author: '운영자',
        authorId: 'admin',
        views: 4427,
        likes: 74,
        image: null,
      }
    ];
  });

  const [selectedMenu, setSelectedMenu] = useState(() =>
    localStorage.getItem('selectedMenu') || '자유 게시글'
  );
  const [selectedPostId, setSelectedPostId] = useState(() => {
    const stored = localStorage.getItem('selectedPostId');
    return stored ? JSON.parse(stored) : null;
  });

  const [postsByMenu, setPostsByMenu] = useState(() => {
    const stored = localStorage.getItem('boardPosts');
    return stored
      ? JSON.parse(stored)
      : {
          '자유 게시글': [],
          '칭찬합니다': [],
          '신고합니다': [],
          '동네 사건 사고': [],
          '후기 공유': [],
        };
  });

  useEffect(() => {
    localStorage.setItem('selectedMenu', selectedMenu);
  }, [selectedMenu]);
  useEffect(() => {
    localStorage.setItem('selectedPostId', JSON.stringify(selectedPostId));
  }, [selectedPostId]);
  useEffect(() => {
    localStorage.setItem('boardPosts', JSON.stringify(postsByMenu));
  }, [postsByMenu]);
  useEffect(() => {
    localStorage.setItem('noticePosts', JSON.stringify(noticePosts));
  }, [noticePosts]);

  // 새 일반글 등록
  const handleAddPost = (newPost) => {
    const newId = Date.now();
    const newPostObj = {
      id: newId,
      title: newPost.title,
      content: newPost.content,
      image: newPost.image || null,
      author: newPost.author || '익명',
      authorId: newPost.authorId || 'guest',
      likes: 0,
      views: 0
    };
    setPostsByMenu((prev) => ({
      ...prev,
      [selectedMenu]: [...(prev[selectedMenu] || []), newPostObj]
    }));
    setSelectedPostId(newId);
  };

  // 새 공지 등록
  const handleAddNotice = (notice) => {
    const newId = `notice_${Date.now()}`;
    const noticeObj = {
      id: newId,
      title: notice.title,
      content: notice.content,
      image: notice.image || null,
      author: notice.author || '운영자',
      authorId: notice.authorId || 'admin',
      likes: 0,
      views: 0
    };
    setNoticePosts(prev => [noticeObj, ...prev]);
    setSelectedPostId(newId);
  };

  // 게시글/공지사항 업데이트
  const handleUpdatePost = (id, updatedPost) => {
    if (id.toString().startsWith('notice')) {
      setNoticePosts(prev =>
        prev.map(n => n.id === id ? { ...n, ...updatedPost } : n)
      );
    } else {
      setPostsByMenu(prev => ({
        ...prev,
        [selectedMenu]: (prev[selectedMenu] || []).map(p =>
          p.id === id ? { ...p, ...updatedPost } : p
        )
      }));
    }
  };

  // 게시글 삭제
  const handleDeletePost = (id) => {
    if (id.toString().startsWith('notice')) {
      setNoticePosts(prev => prev.filter(n => n.id !== id));
      setSelectedPostId(null);
    } else {
      setPostsByMenu(prev => ({
        ...prev,
        [selectedMenu]: (prev[selectedMenu] || []).filter(p => p.id !== id)
      }));
      setSelectedPostId(null);
    }
  };

  // 상세에서 공지 클릭시 해당 공지 객체 넘김
  const handleNoticeClick = (id) => setSelectedPostId(id);

  // 상세에 넘길 데이터
  const currentPosts = postsByMenu[selectedMenu] || [];
  const currentNotice = noticePosts.find(n => n.id === selectedPostId);

  return (
    <Layout>
      <div className="comm-root">
        <main className="comm-main">
          <aside className="comm-sidebar-wrap">
            <Sidebar
              selectedMenu={selectedMenu}
              setSelectedMenu={(menu) => {
                setSelectedMenu(menu);
                setSelectedPostId(null);
              }}
            />
          </aside>
          <section className="comm-board-section">
            <div className="comm-board-card">
              {selectedPostId ? (
                <PostDetail
                  postId={selectedPostId}
                  menu={selectedMenu}
                  goBack={() => setSelectedPostId(null)}
                  posts={currentPosts}
                  onUpdatePost={handleUpdatePost}
                  onDeletePost={handleDeletePost}
                  // 공지사항 상세
                  noticePost={currentNotice}
                  setNoticePost={notice =>
                    setNoticePosts(prev => prev.map(n => n.id === notice.id ? notice : n))
                  }
                />
              ) : (
                <BoardTable
                  selectedMenu={selectedMenu}
                  posts={currentPosts}
                  onPostClick={(id) => setSelectedPostId(id)}
                  onAddPost={handleAddPost}
                  noticePosts={noticePosts}
                  onAddNotice={handleAddNotice}
                  isAdmin={JSON.parse(localStorage.getItem('currentUser') || '{}')?.role === 'admin'}
                  onNoticeClick={handleNoticeClick}
                />
              )}
            </div>
          </section>
        </main>
      </div>
    </Layout>
  );
}

export default CommunityPage;
