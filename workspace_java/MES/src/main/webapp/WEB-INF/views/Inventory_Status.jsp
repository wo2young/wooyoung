<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="cxt" value="${pageContext.request.contextPath}" />
<c:set var="currentPage" value="status" scope="request" />
<jsp:useBean id="now" class="java.util.Date" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>재고 현황</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<style>
/* =========================
   ✅ 전역( body ) 테마 - 다크모드
   ========================= */
:root {
  --bg:#0a0f1a; --panel:#0f1626; --line:#1f2b45; --text:#e6ebff; --muted:#a7b0c5;
  --card:#111827; --line2:#4b5563; --input-bg:#1a202c;
  --primary:#f59e0b; --primary-hover:#d97706; --accent:var(--primary); --accent-light:var(--primary-hover);
}

html, body {
  margin:0; padding:0; min-height:100dvh;
  background:var(--bg); color:var(--text);
  font-family: Segoe UI, Pretendard, 'Noto Sans KR', 'Apple SD Gothic Neo', Arial, sans-serif;
  font-size:16px;
}

/* ✅ 메인 레이아웃: 헤더 고정, main이 남은 높이 차지 & 스크롤 */
body{ display:flex; flex-direction:column; }
main{ flex:1 1 auto; display:block; overflow:auto; }

/* 레이아웃 */
.page {
  padding:28px 20px;
  max-width:1200px;
  margin:0 auto;
}

/* =========================
   ✅ 필터 바 (한 줄 고정)
   ========================= */
.filters{
  display:flex;
  flex-wrap:nowrap;
  align-items:flex-end;
  gap:12px;
  margin-bottom:24px;
}

.filters .field{
  display:flex;
  flex-direction:column;
  min-width:220px;
}

.filters label{
  font-size:14px; font-weight:600; color:var(--muted); margin-bottom:6px;
}

.filters select,
.filters input[type="text"]{
  width:100%; padding:10px 12px;
  border:1px solid var(--line2); border-radius:10px;
  background:var(--input-bg); color:#fff; font-size:16px;
  transition:border-color .15s ease, box-shadow .15s ease;
}

.filters select:focus,
.filters input[type="text"]:focus{
  outline:none; border-color:var(--primary);
  box-shadow:0 0 0 3px rgba(245,158,11,.18);
}

/* 셀렉트 화살표 */
.filters select{
  -webkit-appearance:none; -moz-appearance:none; appearance:none;
  background-image:url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20' fill='%239ca3af'%3E%3Cpath fill-rule='evenodd' d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z' clip-rule='evenodd'/%3E%3C/svg%3E");
  background-repeat:no-repeat; background-position:right .75rem center; padding-right:30px;
}

/* 🔧 LOT 검색 그룹 (입력 + 버튼 나란히, 폭 제한) */
.filters .lot-search-group{
  display:flex; align-items:flex-end; gap:8px;
  flex:0 1 auto;
  width:clamp(260px, 32vw, 420px);
}
.filters .lot-search-group .lot-field{
  display:flex; flex-direction:column; min-width:0; flex:1 1 auto;
}
#lotLike{ width:100%; }

/* 버튼 */
.filters .btn{
  padding:10px 16px; border:none; border-radius:10px;
  background:var(--accent); color:#111827; font-size:14px; font-weight:700;
  cursor:pointer; transition:background .15s, box-shadow .15s, color .15s;
  height:42px; white-space:nowrap;
}
.filters .btn:hover{
  background:var(--accent-light);
  box-shadow:0 0 0 3px rgba(245,158,11,.18);
}

/* 상단 요약 카드 */
.cards{
  display:grid; grid-template-columns:repeat(auto-fit, minmax(160px,1fr));
  gap:16px; margin-bottom:24px;
}
.card{
  border:1px solid var(--line); border-radius:12px; padding:16px;
  background:var(--card); box-shadow:0 6px 16px rgba(0,0,0,.25);
}
.card .label{ font-size:13px; color:var(--muted); margin-bottom:8px; }
.card .value{ font-size:24px; font-weight:700; color:var(--text); }

/* 테이블 */
.table-wrap{ overflow:auto; border:1px solid var(--line); border-radius:12px; background:var(--card); }
table{ width:100%; border-collapse:collapse; }
th,td{ padding:12px 16px; border-bottom:1px solid var(--line); white-space:nowrap; text-align:left; font-size:15px; }
th{ background:var(--panel); font-weight:600; color:var(--muted); }
tbody tr:last-child td{ border-bottom:none; }

/* 뱃지 */
.badge{ display:inline-block; padding:4px 10px; border-radius:999px; font-size:12px; font-weight:600; text-transform:uppercase; }
.badge.ok{ background:#1f362c; color:#4CAF50; }
.badge.warn{ background:#472605; color:#f59e0b; }

/* 페이저 */
.pager{
  display:flex; justify-content:center; align-items:center; margin-top:20px; gap:8px;
}
.pager .pg{
  padding:8px 12px; border-radius:8px; border:1px solid var(--line2);
  background:var(--input-bg); color:var(--text); cursor:pointer;
  transition:background .15s, border-color .15s;
}
.pager .pg.active{ background:var(--accent); color:#111827; border-color:var(--accent); }
.pager .pg:hover:not(.active){ background:rgba(255,255,255,.08); border-color:rgba(255,255,255,.08); }

/* 📱 모바일 대응 */
@media (max-width: 640px){
  .filters{ flex-wrap:wrap; flex-direction:column; align-items:stretch; }
  .filters .field{ min-width:0; width:100%; }
  .filters .lot-search-group{ width:100%; flex-direction:column; align-items:stretch; }
  .filters .lot-search-group .btn{ width:100%; height:auto; }
  .cards { grid-template-columns: 1fr; }
  .table-wrap { overflow-x: auto; }
}
html, body {
  /* 스크롤 동작은 유지, 바만 숨김 */
  overflow-y: auto !important;
  /* 가로 스크롤 강제 차단(테이블 등 길어질 때) */
  overflow-x: hidden !important;

  /* Firefox */
  scrollbar-width: none !important;
}

/* Chrome/Edge/Safari - 창/본문 둘 다 대응 */
html::-webkit-scrollbar,
body::-webkit-scrollbar {
  width: 0 !important;
  height: 0 !important;
  display: none !important;
}

/* 혹시 메인 컨테이너가 스크롤을 만들면 대비 */
.inv-history {
  overflow-x: hidden;
}
</style>
</head>

<body>
  <jsp:include page="/WEB-INF/includes/header.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/includes/Inventory_tab.jsp"></jsp:include>

  <!-- ✅ main 적용 -->
  <main class="page">
    <!-- ========================= 필터 바 ========================= -->
    <form id="filterForm" class="filters" method="get" action="${cxt}/inventory/status">
      <div class="field">
        <label for="locationName">창고</label>
        <select id="locationName" name="locationName" onchange="submitFilters()">
          <option value="">전체 창고</option>
          <c:forEach var="loc" items="${locations}">
            <option value="${loc}" <c:if test="${param.locationName == loc}">selected</c:if>>${loc}</option>
          </c:forEach>
        </select>
      </div>

      <div class="field">
        <label for="itemId">품목</label>
        <select id="itemId" name="itemId" onchange="submitFilters()">
          <option value="">전체 품목</option>
          <c:forEach var="it" items="${items}">
            <option value="${it.item_id}" <c:if test="${param.itemId == (it.item_id)}">selected</c:if>>
              ${it.item_name} (ID:${it.item_id})
            </option>
          </c:forEach>
        </select>
      </div>

      <!-- 🔧 LOT 검색: 입력 + 버튼 나란히 (데스크톱), 모바일에선 세로 -->
      <div class="lot-search-group">
        <div class="lot-field">
          <label for="lotLike">LOT 검색</label>
          <input id="lotLike" name="lotLike" type="text"
                 value="${fn:escapeXml(param.lotLike)}" placeholder="LOT 번호 일부" />
        </div>
        <button type="button" class="btn" onclick="submitFilters()">적용</button>
      </div>
    </form>

    <div class="cards">
      <div class="card">
        <div class="label">총 재고 라인수</div>
        <div class="value" id="sumCountLines"><c:out value="${summary.totalItemCount}" /></div>
      </div>
      <div class="card">
        <div class="label">총 재고 수량</div>
        <div class="value" id="sumTotalQty"><fmt:formatNumber value="${summary.totalQty}" /></div>
      </div>
      <div class="card">
        <div class="label">경고(유통기한 30일 이내)</div>
        <div class="value" id="sumWarnCount"><c:out value="${summary.warningItemCount}" /></div>
      </div>
    </div>

    <!-- ========================= 테이블 ========================= -->
    <div class="table-wrap">
      <table>
        <thead>
          <tr>
            <th>품목명</th>
            <th>LOT 번호</th>
            <th>창고</th>
            <th style="text-align:right;">현재 재고</th>
            <th>입고일</th>
            <th>유통기한</th>
            <th>상태</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="row" items="${inventoryRows}">
            <tr class="data-row">
              <td><c:out value="${row.item_name}" /></td>
              <td><c:out value="${row.lot_no}" /></td>
              <td><c:out value="${row.location_name}" /></td>
              <td style="text-align:right;"><fmt:formatNumber value="${row.qty}" /></td>
              <td>
                <c:if test="${not empty row.in_date}">
                  <fmt:formatDate value="${row.in_date}" pattern="yyyy-MM-dd" />
                </c:if>
                <c:if test="${empty row.in_date}">-</c:if>
              </td>
              <td>
                <c:if test="${not empty row.expire_date}">
                  <fmt:formatDate value="${row.expire_date}" pattern="yyyy-MM-dd" />
                </c:if>
                <c:if test="${empty row.expire_date}">-</c:if>
              </td>
              <td>
                <c:set var="isWarn" value="${not empty row.expire_date and row.expire_date.time le (now.time + 30*24*60*60*1000)}" />
                <c:if test="${isWarn}"><span class="badge warn">경고</span></c:if>
                <c:if test="${not isWarn}"><span class="badge ok">정상</span></c:if>
              </td>
            </tr>
          </c:forEach>

          <c:if test="${empty inventoryRows}">
            <tr id="noRes">
              <td colspan="7" style="text-align:center; color:#9ca3af;">데이터가 없습니다.</td>
            </tr>
          </c:if>
        </tbody>
      </table>
    </div>
    <div id="pager" class="pager"></div>
  </main>

<script>
function submitFilters(){ document.getElementById('filterForm').submit(); }

/* 재고 현황 테이블 클라이언트 페이징 */
(function(){
  var PAGE_SIZE = 20;
  var PAGER_BTN_COUNT = 5;
  var pager = document.getElementById('pager');
  var rows = Array.prototype.slice.call(document.querySelectorAll('tbody tr.data-row'));
  var noRes = document.getElementById('noRes');

  var state = { size: PAGE_SIZE, page: 1, matches: rows.slice() };

  function mkBtn(txt, p, disabled, active, isEnd){
    var b = document.createElement('button');
    b.type = 'button';
    b.className = 'pg' + (active ? ' active' : '');
    if (isEnd) b.className += ' end-btn';
    b.textContent = txt;
    if (disabled) b.disabled = true;
    b.addEventListener('click', function(){ renderPage(p); window.scrollTo(0,0); });
    return b;
  }

  function renderPager(pages){
    if (!pager) return;
    pager.innerHTML = '';
    pager.appendChild(mkBtn('<<', 1, state.page === 1, false, true));
    pager.appendChild(mkBtn('<', state.page - 1, state.page === 1, false));
    var startPage = Math.max(1, state.page - Math.floor(PAGER_BTN_COUNT / 2));
    var endPage = Math.min(pages, startPage + PAGER_BTN_COUNT - 1);
    if (endPage - startPage + 1 < PAGER_BTN_COUNT) startPage = Math.max(1, endPage - PAGER_BTN_COUNT + 1);
    for (var i = startPage; i <= endPage; i++) pager.appendChild(mkBtn(String(i), i, false, i === state.page));
    pager.appendChild(mkBtn('>', state.page + 1, state.page === pages, false));
    pager.appendChild(mkBtn('>>', pages, state.page === pages, false, true));
  }

  function renderPage(p){
    var total = state.matches.length;
    var pages = Math.max(1, Math.ceil(total / state.size));
    state.page = Math.min(Math.max(1, p), pages);

    rows.forEach(function(tr){ tr.style.display='none'; });

    if (noRes) noRes.style.display = (total === 0) ? '' : 'none';

    if (total > 0){
      var start = (state.page-1)*state.size;
      var end = Math.min(start+state.size, total);
      for (var i=start;i<end;i++) state.matches[i].style.display = '';
    }

    renderPager(pages);
  }

  renderPage(1);

  window.TablePager = {
    renderPage: renderPage,
    setPageSize: function(n){ state.size = Math.max(1, Number(n) || PAGE_SIZE); renderPage(1); },
    getState: function(){ return { page: state.page, size: state.size, total: state.matches.length }; },
    applyFilter: function(predicate){
      state.matches = rows.filter(function(tr){ return predicate ? !!predicate(tr) : true; });
      renderPage(1);
    }
  };
})();
</script>
</body>
</html>
