<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="wrap dark">
<nav class="ppage-tabs" aria-label="품질 관리 탭">
  <a href="${pageContext.request.contextPath}/quality/new"
     class="ppage-tab active">불량 등록</a>
  <a href="${pageContext.request.contextPath}/quality/new?action=status"
     class="ppage-tab">불량 현황</a>
</nav>

  <div class="container">
    <main>
      <h2 class="page-title">품질 불량 등록</h2>

      <c:if test="${not empty sessionScope.msg}">
        <p class="success">${sessionScope.msg}</p>
        <c:remove var="msg" scope="session"/>
      </c:if>
      <c:if test="${not empty errorMsg}">
        <p class="error">${errorMsg}</p>
      </c:if>

      <div class="form-card">
        <form action="${pageContext.request.contextPath}/quality/new" method="post" accept-charset="UTF-8" autocomplete="off">
          
          <!-- 품목 -->
          <div class="field">
            <label for="item_name">품목:</label>
            <select name="item_name" id="item_name" required>
              <option value="">-- 선택하세요 --</option>
              <option value="COKE">콜라</option>
              <option value="CIDER">사이다</option>
              <option value="FANTA">환타</option>
              <option value="ETC">기타</option>
            </select>
          </div>

          <!-- 실적번호 + 남은 불량수량 표시 -->
          <div class="field">
            <label for="resultSelect">실적번호 (RESULT_ID):</label>
            <select id="resultSelect" name="result_id" required onchange="setWorkerName()">
              <option value="">-- 선택하세요 --</option>
              <c:forEach var="r" items="${thisWeekResults}">
                <option value="${r.result_id}" data-worker="${r.worker_name}">
                  ${r.result_id} (남은 불량수량: ${r.remain_fail_qty}개)
                </option>
              </c:forEach>
            </select>
          </div>

          <!-- 불량 코드 -->
          <div class="field">
            <label for="detail_code">불량 코드 (DETAIL_CODE):</label>
            <select name="detail_code" id="detail_code" required>
              <option value="">-- 선택하세요 --</option>
              <c:forEach var="cd" items="${defectCodes}">
                <option value="${cd.detail_code}">[${cd.detail_code}] ${cd.code_dname}</option>
              </c:forEach>
            </select>
          </div>

          <!-- 불량 수량 -->
          <div class="field">
            <label for="quantity">불량 수량:</label>
            <input type="number" name="quantity" id="quantity" min="1" required />
          </div>

          <!-- 작업자 -->
          <div class="field">
            <label for="workerName">작업자:</label>
            <input type="text" id="workerName" readonly />
          </div>

          <input type="hidden" name="registered_by" value="${loginUser.user_id}" />

          <div class="btns">
            <button type="submit" class="btn btn-primary">등록</button>
          </div>
        </form>
      </div>
    </main>
  </div>
</div>

<script>
  function setWorkerName() {
    const select = document.getElementById("resultSelect");
    const worker = select.options[select.selectedIndex].getAttribute("data-worker");
    document.getElementById("workerName").value = worker || "";
  }
</script>

<style>
/* ===== 다크 네이비 팔레트 & 기본 ===== */
:root{
  --bg:#0a0f1a;
  --panel:#0f1626;
  --panel-line:#2a3757;
  --text:#e6ebff;
  --muted:#a7b0c5;
  --line:#1f2b45;
  --accent:#f59e0b;
  --accent-hover:#d97706;
  --input-bg:#1a202c;
  --input-line:#4b5563;
  --danger:#ef4444;
  --success:#34d399;
}
html,body{
  margin:0;
  padding:0;
  background:var(--bg);
  color:var(--text);
  font-family:system-ui,-apple-system,Segoe UI,Roboto,Pretendard,sans-serif;
  overflow-x:hidden;
}
.page-title{ color:var(--text); }
.container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 20px 16px 48px;
}
.ppage-tabs {
  display: flex;
  gap: 16px;
  padding: 0 16px;
  border-bottom: 1px solid var(--line);
  margin-top: 0;
}
.ppage-tab {
  display: inline-block;
  padding: 12px 2px;
  color: var(--muted);
  font-weight: 600;
  border-bottom: 3px solid transparent;
  transition: color 0.15s, border-color 0.15s;
  line-height: 1;
  text-decoration: none;
}
.ppage-tab:hover {
  color: #e7ecff;
  border-bottom-color: #3b4e78;
}
.ppage-tab.active {
  color: #fff;
  border-bottom-color: var(--accent);
}
.form-card{
  background:var(--panel);
  border:1px solid var(--panel-line);
  border-radius:14px;
  padding:20px;
  max-width:720px;
  box-shadow:0 8px 24px rgba(3,7,18,.25);
  margin: 0 auto;
}
.form-card .field{
  display: grid;
  grid-template-columns: 140px 1fr;
  align-items: center;
  gap: 16px;
  margin: 16px 0;
}
@media (max-width: 720px){ 
  .form-card .field{
    grid-template-columns: 1fr;
    gap: 0;
  }
  .form-card label{
    margin-bottom: 8px;
  }
}
.form-card label{
  margin-bottom: 0;
  color:var(--muted);
}
.form-card input, .form-card select{
  width:100%;
  height:42px;
  border:1px solid var(--input-line);
  border-radius:10px;
  padding:0 12px;
  background:var(--input-bg);
  color:var(--text);
  outline:none;
  transition:border-color .15s, box-shadow .15s;
  box-sizing: border-box;
}
input:focus, select:focus{
  border-color:var(--accent);
  box-shadow:0 0 0 4px rgba(96,165,250,.2);
}
input[readonly]{
  background: #282f3d;
  color: #9ca3af;
  border-color: #4b5563;
  cursor: default;
}
.btns{
  display:flex;
  gap:8px;
  align-items:center;
  margin-top:24px;
}
.btn{
  background:var(--accent);
  color:#111;
  border:none;
  border-radius:10px;
  padding:12px 18px;
  cursor:pointer;
  font-size:14px;
  font-weight:700;
  transition:background .15s, box-shadow .15s, color .15s;
  text-decoration:none;
  display:inline-flex;
  align-items:center;
  justify-content: center;
  flex:1;
}
.btn.btn-primary{
    background:var(--accent);
    color:#0b1020;
    border-color:transparent;
}
.btn:hover{
  background:var(--accent-hover);
  box-shadow:0 0 0 3px rgba(245,158,11,.18);
  color:#111;
}
.error{
  background:rgba(239, 68, 68, .15);
  color:var(--danger);
  border:1px solid rgba(239, 68, 68, .28);
  border-radius:10px;
  padding:10px 12px;
  margin-bottom:12px;
  font-size: 14px;
}
.success{
  background:rgba(52,211,153,.15);
  color:var(--success);
  border:1px solid rgba(52,211,153,.35);
  border-radius:10px;
  padding:10px 12px;
  margin-bottom:12px;
  font-size: 14px;
}
</style>
