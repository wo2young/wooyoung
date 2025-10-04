<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<jsp:include page="/WEB-INF/includes/2header.jsp">
  <jsp:param name="activeNav" value="routing"/>
</jsp:include>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공정 등록</title>
<style>
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
  --success: #4caf50;
  --success-hover: #3e8e41;
}

html, body {
  margin: 0;
  padding: 0;
  min-height: 100vh;
  background: var(--bg);
  color: var(--text);
  font-family: Segoe UI, Pretendard, 'Noto Sans KR', Arial, sans-serif;
  font-size: 14px;
}

.wrap {
  max-width: 800px;
  margin: 28px auto;
  padding: 20px;
}

.card {
  background: var(--card);
  border: 1px solid var(--line);
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 6px 18px rgba(0,0,0,.25);
}

h2 {
  margin: 0 0 16px;
  font-size: 22px;
}

/* 입력폼 */
form label {
  font-weight: 600;
  display: block;
  margin: 14px 0 6px;
}
input[type=text],
input[type=number],
select {
  width: 100%;
  height: 36px;
  font-size: 14px;
  padding: 0 10px;
  background: var(--input-bg);
  color: var(--text);
  border: 1px solid var(--line2);
  border-radius: 6px;
}
input::placeholder {
  color: #6b7280;
}

/* 파일 업로드 */
.file-row {
  display: flex;
  align-items: center;
  gap: 10px;
}
.file-label {
  flex: 1;
  background: var(--input-bg);
  border: 1px solid var(--line2);
  border-radius: 6px;
  padding: 6px 10px;
  font-size: 14px;
  color: var(--muted);
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
input[type=file] {
  display: none;
}
.file-btn {
  background: #2563eb;
  color: #fff;
  font-weight: 600;
  border-radius: 6px;
  padding: 0 14px;
  height: 34px;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: background .2s;
}
.file-btn:hover {
  background: #1d4ed8;
}

/* 버튼 공통 */
.btn {
  min-width: 80px;
  height: 34px;
  border: none;
  border-radius: 6px;
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  text-decoration: none; /* 밑줄 제거 */
  transition: background .2s, border .2s;
}

/* 등록 버튼 (신규 등록과 동일한 초록색) */
.btn.success {
  background: var(--success);
  color: #fff;
}
.btn.success:hover {
  background: var(--success-hover);
}

/* 목록 버튼 (노란색) */
.btn.primary {
  background: var(--primary);
  color: #111827;
}
.btn.primary:hover {
  background: var(--primary-hover);
}
</style>
</head>
<body>

<main class="wrap">
  <div class="card">
    <h2>신규 공정 등록</h2>

    <c:if test="${param.msg == 'add_fail'}">
      <script>alert("등록에 실패했습니다. 입력값을 확인하세요.");</script>
    </c:if>

    <!-- ✅ 파일 업로드 정상 동작 유지 -->
    <form method="post" action="${pageContext.request.contextPath}/master" enctype="multipart/form-data">
      <input type="hidden" name="act" value="routing.add"/>

      <label>품명 (완제품만 선택 가능)</label>
      <select name="item_id" required>
        <c:forEach var="it" items="${itemsFG}">
          <option value="${it.item_id}">${it.item_name}</option>
        </c:forEach>
      </select>

      <label>공정 순서</label>
      <input type="number" name="process_step" min="1" step="1" required />

      <label>공정 이미지</label>
      <div class="file-row">
        <span class="file-label" id="fileName">선택된 파일 없음</span>
        <label for="img_file" class="file-btn">파일 선택</label>
        <input type="file" id="img_file" name="img_file" accept="image/*"
               onchange="document.getElementById('fileName').textContent = this.files.length ? this.files[0].name : '선택된 파일 없음';"/>
      </div>

      <label>작업 상세</label>
      <input type="text" name="workstation" placeholder="예: 혼합 → 충전 → 포장" />

      <div style="margin-top: 20px; display:flex; gap:10px;">
        <!-- 등록 버튼 (초록색 적용) -->
        <button type="submit" class="btn success">등록</button>
        <!-- 목록 버튼 -->
        <a href="${pageContext.request.contextPath}/master?act=routing.list" class="btn primary">목록으로</a>
      </div>
    </form>
  </div>
</main>

</body>
</html>