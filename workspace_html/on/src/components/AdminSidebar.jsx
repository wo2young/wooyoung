import React from "react";
import { Link, useLocation } from "react-router-dom";
import '../Styles/AdminSidebar.css'

function AdminSidebar() {
  const location = useLocation();
  return (
    <aside className="admin-user-sidebar">
      <div className="admin-user-mode">관리자 모드</div>
      <nav className="admin-user-side-nav">
        <ul>
          <li className={location.pathname === "/admin" ? "active" : ""}>
            <Link to="/admin">사용자 관리</Link>
          </li>
          <li className={location.pathname === "/admin/inquiry" ? "active" : ""}>
            <Link to="/admin/inquiry">문의 내역</Link>
          </li>
          <li className={location.pathname === "/admin/post" ? "active" : ""}>
            <Link to="/admin/post">게시글 관리</Link>
          </li>
          <li className={location.pathname === "/admin/setting" ? "active" : ""}>
            <Link to="/admin/setting">시스템 설정</Link>
          </li>
          <li className={location.pathname === "/admin/stats" ? "active" : ""}>
            <Link to="/admin/stats">통계</Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
}

export default AdminSidebar;
