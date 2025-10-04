<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<jsp:include page="/WEB-INF/includes/2header.jsp">
	<jsp:param name="activeNav" value="routing" />
</jsp:include>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공정 수정</title>
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
}

/* 기본 레이아웃 */
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

/* 입력폼 스타일 */
form p {
  margin: 12px 0;
}

label {
  font-weight: 600;
  display: block;
  margin-bottom: 6px;
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

/* 파일 업로드 영역 */
.file-wrap {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

/* 파일명 + 보기 버튼 같은 줄 */
.file-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

#file-name {
  font-size: 13px;
  color: var(--muted);
}

/* 파일 선택 버튼 */
.file-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 36px;
  background: #374151;
  color: #fff;
  font-size: 13px;
  font-weight: 600;
  border-radius: 6px;
  cursor: pointer;
  transition: background .2s;
}
.file-btn:hover {
  background: #4b5563;
}

/* 보기 버튼 */
.view-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 70px;
  height: 28px;
  background: #2563eb;
  color: #fff;
  font-size: 13px;
  font-weight: 600;
  border-radius: 6px;
  text-decoration: none;
  transition: background .2s;
}
.view-btn:hover {
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
  transition: background .2s, border .2s;
}

/* 수정 완료 버튼 */
.btn.edit {
  background: #111827;
  color: #fff;
  border: 2px solid #374151;
  border-radius: 20px;
  padding: 0 16px;
}
.btn.edit:hover {
  background: #1f2937;
  border-color: #4b5563;
}

/* 삭제 버튼 */
.btn.danger {
  background: #dc2626;
  color: #fff;
}
.btn.danger:hover {
  background: #ef4444;
}

/* 목록 버튼 */
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
    <h2>공정 수정</h2>

    <c:if test="${param.msg == 'upd_ok'}"><script>alert("수정 완료");</script></c:if>
    <c:if test="${param.msg == 'upd_fail'}"><script>alert("수정 실패");</script></c:if>
    <c:if test="${param.msg == 'del_ok'}"><script>alert("삭제 완료");</script></c:if>
    <c:if test="${param.msg == 'del_fail'}"><script>alert("삭제 실패");</script></c:if>

    <form method="post" action="${pageContext.request.contextPath}/master" enctype="multipart/form-data">
      <input type="hidden" name="act" value="routing.update" />
      <input type="hidden" name="routing_id" value="${routing.routing_id}" />

      <p>
        <label>품명 (FG)</label>
        <select name="item_id" required>
          <c:forEach var="it" items="${itemsFG}">
            <option value="${it.item_id}" <c:if test="${it.item_id == routing.item_id}">selected</c:if>>
              ${it.item_name} [${it.item_id}]
            </option>
          </c:forEach>
        </select>
      </p>

      <p>
        <label>공정 순서</label>
        <input type="number" name="process_step" value="${routing.process_step}" min="1" required />
      </p>

      <p>
        <label>이미지</label>
        <div class="file-wrap">
          <!-- 파일명 + 보기 버튼 같은 줄 -->
          <div class="file-info">
            <span id="file-name">선택된 파일 없음</span>
            <c:if test="${not empty routing.img_path}">
              <a href="${pageContext.request.contextPath}/file?path=${routing.img_path}" 
                 target="_blank" class="view-btn">보기</a>
            </c:if>
          </div>

          <!-- 파일 선택 버튼 -->
          <label for="img_file" class="file-btn">파일 선택</label>
          <input type="file" id="img_file" name="img_file" accept="image/*" hidden />
          <input type="hidden" name="img_path" value="${routing.img_path}" />
        </div>
      </p>

      <p>
        <label>작업 상세</label>
        <input type="text" name="workstation" value="${routing.workstation}" />
      </p>

      <div style="margin-top: 20px; display: flex; gap: 10px;">
        <button type="submit" class="btn edit">수정 완료</button>
      </div>
    </form>

    <div style="margin-top: 16px; display:flex; gap:10px;">
      <form method="post" action="${pageContext.request.contextPath}/master"
            onsubmit="return confirm('정말 삭제하시겠습니까?');" style="display:inline">
        <input type="hidden" name="act" value="routing.delete" />
        <input type="hidden" name="routing_id" value="${routing.routing_id}" />
        <button type="submit" class="btn danger">삭제</button>
      </form>

      <form method="get" action="${pageContext.request.contextPath}/master" style="display:inline">
        <input type="hidden" name="act" value="routing.list" />
        <button type="submit" class="btn primary">목록으로</button>
      </form>
    </div>
  </div>
</main>

<script>
document.getElementById("img_file").addEventListener("change", function() {
  const fileName = this.files.length > 0 ? this.files[0].name : "선택된 파일 없음";
  document.getElementById("file-name").textContent = fileName;
});
</script>
</body>
</html>