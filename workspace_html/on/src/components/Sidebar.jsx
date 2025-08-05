import '../Styles/Sidebar.css';
import { useAuth } from '../contexts/AuthContext'; // AuthContext 경로 맞게!
import { useNavigate } from 'react-router-dom';

function Sidebar({ selectedMenu, setSelectedMenu }) {
  const menus = ['자유 게시글', '칭찬합니다', '신고합니다', '동네 사건 사고', '후기 공유'];
  const { isLoggedIn } = useAuth();
  const navigate = useNavigate();

  // 메뉴 클릭 시 처리
  const handleMenuClick = (menu) => {
    if (menu === '자유 게시글') {
      setSelectedMenu(menu);
    } else {
      if (!isLoggedIn) {
        alert('로그인 후 이용 가능한 메뉴입니다.');
        navigate('/login');
      } else {
        setSelectedMenu(menu);
      }
    }
  };

  return (
    <nav className="comm-sidebar">
      <div className="comm-menu-list">
        {menus.map((menu) => (
          <button
            key={menu}
            className={`comm-sidebar-btn${selectedMenu === menu ? ' active' : ''}`}
            onClick={() => handleMenuClick(menu)}
          >
            {menu}
          </button>
        ))}
      </div>
      <div className="comm-sidebar-divider" />
      <div className="comm-sidebar-ad">협력 업체 광고</div>
    </nav>
  );
}

export default Sidebar;
