<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div class="wrap dark">
<nav class="ppage-tabs" aria-label="품질 관리 탭">
  <a href="${pageContext.request.contextPath}/quality/new"
     class="ppage-tab">
    불량 등록
  </a>
  <a href="${pageContext.request.contextPath}/quality/new?action=status"
     class="ppage-tab active"> <!-- 불량 현황 페이지니까 active -->
    불량 현황
  </a>
</nav>

    
    <main>
      <h2 class="page-title">불량 현황</h2>

      <form method="get" action="${pageContext.request.contextPath}/quality/new" class="search-bar" style="max-width:none">
        <input type="hidden" name="action" value="status"/>
        <div class="field">
          <label for="searchStart">시작일:</label>
          <input type="date" name="searchStart" id="searchStart" value="${param.searchStart}">
        </div>
        <div class="field">
          <label for="searchEnd">종료일:</label>
          <input type="date" name="searchEnd" id="searchEnd" value="${param.searchEnd}">
        </div>
        <div class="actions">
          <button type="submit" class="btn btn-primary">조회</button>
          <a href="${pageContext.request.contextPath}/quality/new?action=status" class="btn btn-ghost">초기화</a>
        </div>
      </form>

      <div class="summary-card-group">
        <div class="summary-card danger">
          <h3 class="summary-title">총 불량 수량</h3>
          <p class="summary-value">
            <c:out value="${summary.totalDefects}" /> 개
          </p>
        </div>
        <div class="summary-card info">
          <h3 class="summary-title">평균 불량률</h3>
          <p class="summary-value">
            <c:out value="${summary.defectRate}" /> %
          </p>
        </div>
      </div>

      <c:choose>
        <c:when test="${empty defectStats}">
          <p class="empty">등록된 불량 데이터가 없습니다.</p>
        </c:when>
        <c:otherwise>
          <div class="table-wrap">
            <table class="data-table">
              <thead>
                <tr>
                  <th>불량코드</th>
                  <th>불량명</th>
                  <th>발생건수</th>
                  <th>총 불량수량</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="row" items="${defectStats}">
                  <tr>
                    <td><c:out value="${row.defect_code}"/></td>
                    <td><c:out value="${row.defect_name}"/></td>
                    <td><c:out value="${row.occurrence_count}"/>건</td>
                    <td class="text-danger"><c:out value="${row.total_quantity}"/>개</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:otherwise>
      </c:choose>
    </main>
  </div>
</div>

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
  position: sticky;
  top: 56px;
  z-index: 5;
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
.search-bar{
  display:flex;
  flex-wrap:wrap;
  gap:12px;
  align-items:flex-end;
  margin:14px 0 16px;
  padding:12px;
  background:var(--panel);
  border:1px solid var(--panel-line);
  border-radius:12px;
}
.search-bar .field{
  display:flex;
  flex-direction:column;
  gap:6px
}
.search-bar label{
  font-size:12px;
  color:var(--muted);
  font-weight:800;
}
.search-bar input[type="date"]{
  height:38px;
  padding:6px 10px;
  border:1px solid var(--input-line);
  border-radius:10px;
  background:var(--input-bg);
  color:var(--text);
  outline:none;
  transition:border-color .15s,box-shadow .15s;
  box-sizing: border-box;
}
.search-bar input[type="date"]:focus{
  border-color:var(--accent);
  box-shadow:0 0 0 4px rgba(96,165,250,.2);
}
.search-bar .actions{
  margin-left:auto;
  display:flex;
  gap:8px;
}
.btn{
  display:inline-flex;
  align-items:center;
  justify-content:center;
  height:38px;
  padding:0 14px;
  border-radius:10px;
  border:1px solid var(--input-line);
  background:var(--input-bg);
  color:var(--text);
  cursor:pointer;
  text-decoration:none;
  font-size:14px;
  font-weight:800;
  transition:transform .05s,background .15s,border-color .15s;
}
.btn:hover{
  background:#101a2e;
  border-color:#375083;
}
.btn.btn-primary{
  background:var(--accent);
  color:#0b1020;
  border-color:transparent;
}
.btn.btn-primary:hover{
  background:var(--accent-hover);
}
.btn.btn-ghost{
  background:transparent;
}
.btn.btn-ghost:hover{
background:white;
}

.table-wrap{
  position:relative;
  margin-top:6px;
  border:1px solid var(--panel-line);
  border-radius:12px;
  background:var(--panel);
  box-shadow:0 10px 28px rgba(3,7,18,.35);
  overflow:auto;
}
.data-table{
  width:100%;
  border-collapse:separate;
  border-spacing:0;
  color:var(--text);
}
.data-table thead th{
  position:sticky;
  top:0;
  z-index:1;
  background:#0e1830;
  color:#fff;
  padding:12px 10px;
  text-align:center;
  font-weight:800;
  border-bottom:1px solid var(--panel-line);
}
.data-table tbody td{
  padding:10px;
  border-top:1px solid var(--panel-line);
  text-align:center;
}
.data-table tbody tr:nth-child(odd){
  background:#0e1527;
}
.data-table tbody tr:hover{
  background:#101a2e;
}
.summary-card-group{
  display: flex;
  gap: 16px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}
.summary-card{
  flex: 1;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  background: var(--panel);
  border: 1px solid var(--panel-line);
}
.summary-card.danger {
  background: rgba(239, 68, 68, .15);
  border-color: rgba(239, 68, 68, .28);
}
.summary-card.info {
  background: rgba(96, 165, 250, .15);
  border-color: rgba(96, 165, 250, .28);
}
.summary-title{
  margin:0;
  font-size:16px;
  color:var(--muted);
}
.summary-value{
  margin:8px 0 0;
  font-size:20px;
  font-weight:bold;
  color:var(--text);
}
.summary-card.danger .summary-value { color:var(--danger); }
.summary-card.info .summary-value { color:var(--accent); }
.text-danger { color:var(--danger); font-weight:bold; }
.empty{
  color:#cbd5e1;
  background:rgba(148,163,184,.08);
  border:1px dashed var(--line);
  padding:16px;
  border-radius:12px;
  text-align:center;
}
</style>