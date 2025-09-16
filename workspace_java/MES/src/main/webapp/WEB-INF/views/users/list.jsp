<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>사용자 관리</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/users.css">

<style>
/* 카드 패널 */
.panel{background:#fff;border:1px solid #e5e7eb;border-radius:12px;box-shadow:0 6px 18px rgba(17,24,39,.06);padding:12px 12px 4px}
/* 테이블 */
.table{width:100%;border-collapse:separate;border-spacing:0;font-size:14px}
.table th,.table td{padding:12px 14px;border-bottom:1px solid #e5e7eb;text-align:left}
.table th{background:#f9fafb;font-weight:700}
.table tbody tr:nth-child(even){background:#fbfbfd}
.table tbody tr:hover{background:#f3f4f6}
.table thead th:first-child{border-top-left-radius:10px}
.table thead th:last-child{border-top-right-radius:10px}
/* 열 */
.col-id{width:80px;text-align:right;color:#6b7280}
.col-login{font-family:ui-monospace,SFMono-Regular,Menlo,monospace}
.col-role{text-transform:uppercase}
/* 액션 */
.actions{white-space:nowrap;display:flex;gap:10px;align-items:center}
.link-chip{display:inline-block;padding:6px 10px;border:1px solid #dbe1ea;border-radius:8px;text-decoration:none;color:#2563eb;background:#fff}
.link-chip:hover{background:#eef2ff}
.btn-mini{height:30px;padding:0 10px;border:1px solid #e5e7eb;border-radius:8px;background:#111;color:#fff;cursor:pointer}
.btn-mini.secondary{background:#fff;color:#111}
/* 툴바: 좌/우 분리 */
.toolbar{display:flex;align-items:center;gap:12px;margin:8px 0 16px}
.toolbar .left{display:flex;align-items:center;gap:8px;flex:1}
.input{height:36px;border:1px solid #e5e7eb;border-radius:8px;padding:0 10px}
.btn{height:36px;padding:0 12px;border:1px solid #e5e7eb;border-radius:8px;background:#111;color:#fff;cursor:pointer;text-decoration:none;display:inline-flex;align-items:center}
.btn.sec{background:#fff;color:#111}
.btn.primary{background:#2563eb;border-color:#2563eb}
.btn.primary:hover{filter:brightness(0.95)}
</style>
</head>
<body>

<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="wrap">
  <h1 class="page-title">사용자 관리</h1>

  <!-- 좌: 검색 / 우: 사용자 추가 버튼 -->
  <div class="toolbar">
    <form class="left" method="get" action="${pageContext.request.contextPath}/users">
      <input class="input" type="text" name="q" value="<c:out value='${q}'/>" placeholder="로그인ID/이름 검색">
      <button class="btn" type="submit">검색</button>
    </form>
    <a class="btn primary" href="${pageContext.request.contextPath}/users/new">+ 사용자 추가</a>
  </div>

  <div class="panel">
    <table class="table">
      <thead>
        <tr>
          <th class="col-id">#</th>
          <th>로그인ID</th>
          <th>이름</th>
          <th class="col-role">권한</th>
          <th>액션</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="u" items="${list}">
          <tr>
            <td class="col-id">${u.user_id}</td>
            <td class="col-login"><c:out value="${u.login_id}" /></td>
            <td><c:out value="${u.name}" /></td>
            <td class="col-role"><c:out value="${u.user_role}" /></td>
            <td class="actions">
              <a class="link-chip" href="${pageContext.request.contextPath}/users/edit?id=${u.user_id}">수정</a>
              <form method="post" action="${pageContext.request.contextPath}/users/reset-pw" style="display:inline">
                <input type="hidden" name="id" value="${u.user_id}">
                <button class="btn-mini secondary" onclick="return confirm('비밀번호 리셋 코드를 발급할까요?')">비번초기화</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <div class="pagination"><!-- 페이지네이션 있으면 여기에 --></div>
</div>

</body>
</html>
