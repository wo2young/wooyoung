<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공정관리</title>
<style>
/* ========================= ✅ 코드관리와 동일한 다크 테마 ========================= */
:root {
  --bg: #0a0f1a;
  --card: #111827;
  --line: #1f2b45;
  --line2: #4b5563;
  --text: #e6ebff;
  --muted: #a7b0c5;
  --input-bg: #1a202c;
  --primary: #f59e0b;
  --primary-hover: #d97706;
}

html, body {
  margin: 0;
  padding: 0;
  min-height: 100vh;
  background: var(--bg);
  color: var(--text);
  font-family: Segoe UI, Pretendard, 'Noto Sans KR', Arial, sans-serif;
  font-size: 14px;
  overflow-x: hidden;
}

/* 레이아웃 */
.wrap {
  max-width: 1200px;
  margin: 28px auto;
  padding: 20px;
}
.card {
  background: var(--card);
  border: 1px solid var(--line);
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 6px 18px rgba(0,0,0,.25);
}
.section+.section {
  margin-top: 16px;
}

/* 제목 */
h2 {
  margin: 0 0 10px;
  font-size: 22px;
}

/* 입력/선택 */
form {
  margin-bottom: 16px;
  display: flex;
  gap: 10px;
  align-items: center;
}
input[type=text], select {
  height: 34px;
  font-size: 14px;
  padding: 0 10px;
  background: var(--input-bg);
  color: var(--text);
  border: 1px solid var(--line2);
  border-radius: 6px;
}
select {
  appearance: none;
  background-image: url("data:image/svg+xml;utf8,<svg fill='%23e6ebff' height='20' viewBox='0 0 24 24' width='20' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>");
  background-repeat: no-repeat;
  background-position: right 10px center;
  background-size: 14px;
  padding-right: 28px;
}
input::placeholder {
  color: #6b7280;
}

/* 버튼 공통 */
.btn, button {
  min-width: 70px;
  height: 32px;
  border: none;
  border-radius: 6px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: background .2s;
}

/* 기본 버튼 */
button {
  background: var(--primary);
  color: #111827;
}
button:hover {
  background: var(--primary-hover);
}

/* ✅ 조회 버튼 전용 */
.btn.query {
  background: #60a5fa;   /* 기본 파랑 */
  color: #111827;
  font-weight: 600;
}
.btn.query:hover {
  background: #374151;   /* 수정 버튼 hover 색 */
  color: #fff;
}

/* 보기 버튼 */
a.view-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 60px;
  height: 28px;
  background: #2563eb;
  color: var(--text); /* 흰 글씨 */
  font-size: 13px;
  font-weight: 600;
  border-radius: 6px;
  text-decoration: none;
  padding: 0 10px;
  transition: background .2s;
}
a.view-btn:hover {
  background: #1d4ed8;
}

/* 신규등록 버튼 */
a.btn {
  background: #4caf50;
  color: white;
  text-decoration: none;
  padding: 0 12px;
  line-height: 32px;
}
a.btn:hover {
  background: #3e8e41;
}

/* 수정 버튼 (네이비 배경 + 흰 글씨 + 흰색 테두리) */
button.edit {
  background: #111827;
  color: #fff;
  border: 2px solid #374151;
  border-radius: 20px;
  padding: 0 14px;
}
button.edit:hover {
  background: #1f2937;
  border-color: #4b5563;
}

/* 테이블 */
.table-wrap {
  margin-top: 16px;
}
table {
  width: 100%;
  border-collapse: collapse;
  background: var(--card);
  border: 1px solid var(--line);
  border-radius: 12px;
  overflow: hidden;
}
thead th {
  background: #152033;
  color: #cbd5e1;
  border-bottom: 1px solid var(--line);
  text-align: center;
  padding: 12px 14px;
  font-size: 13px;
}
tbody td {
  color: var(--text);
  padding: 12px 14px;
  text-align: center;
  border-bottom: 1px solid var(--line);
}
tbody tr:hover {
  background: #0f1c2d;
}
tbody tr:last-child td {
  border-bottom: none;
}
.empty {
  color: var(--muted);
}

/* 스크롤 숨김 */
::-webkit-scrollbar {
  display: none;
}
</style>
</head>
<body>
<jsp:include page="/WEB-INF/includes/2header.jsp">
  <jsp:param name="activeNav" value="routing"/>
</jsp:include>

<main class="wrap">
  <h2>공정관리</h2>

  <!-- 품명 필터 -->
  <form method="get" action="${pageContext.request.contextPath}/master" class="card section">
    <input type="hidden" name="act" value="routing.list"/>
    <select name="itemId">
      <option value="-1">전체</option>
      <c:forEach var="it" items="${itemsFG}">
        <option value="${it.item_id}" ${selectedItemId == it.item_id ? "selected" : ""}>
          ${it.item_name}
        </option>
      </c:forEach>
    </select>
    <!-- ✅ 조회 버튼에 query 클래스 적용 -->
    <button class="btn query" id="btnQuery" type="submit">조회</button>
  </form>

  <!-- 테이블 -->
  <div class="table-wrap card section">
    <table>
      <thead>
        <tr>
          <th>품명</th>
          <th>순서</th>
          <th>이미지</th>
          <th>작업상세</th>
          <th>작업</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="r" items="${routings}">
          <tr>
            <td>${r.itemName}</td>
            <td>${r.processStep}단계</td>
            <td>
              <c:choose>
                <c:when test="${empty r.imgPath}">-</c:when>
                <c:otherwise>
                  <a href="${pageContext.request.contextPath}/file?path=${r.imgPath}" 
                     target="_blank" class="view-btn">보기</a>
                </c:otherwise>
              </c:choose>
            </td>
            <td>${r.workstation}</td>
            <td>
              <!-- 수정 버튼 -->
              <form method="get" action="${pageContext.request.contextPath}/master" style="display:inline">
                <input type="hidden" name="act" value="routing.edit"/>
                <input type="hidden" name="routing_id" value="${r.routingId}"/>
                <button type="submit" class="edit">수정</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- 신규 등록 버튼 -->
  <a class="btn" href="master?act=routing.addForm">신규 등록</a>
</main>
</body>
</html>