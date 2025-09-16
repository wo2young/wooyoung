<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage" value="history" scope="request"/>
<c:set var="cxt" value="${pageContext.request.contextPath}" />
<fmt:setTimeZone value="Asia/Seoul"/>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>입출고 이력</title>
<style>
/* 이 페이지 전용 변수 */
:root{
  --bg-color: #0a0f1a;
  --text-color: #e6ebff;
  --panel-bg: #0f1626;
  --line: #1f2b45;
  --muted: #a7b0c5;
  --input-bg: #1a202c;
  --accent: #f59e0b;
  --accent-light: #d97706;
  --badge-in-border-color: #4ade80;
  --badge-out-border-color: #f87171;
}

/* ===== body에는 색만 — 폰트/레이아웃/스크롤 X ===== */
html, body{
  margin:0; padding:0;
  background: var(--bg-color);
  color: var(--text-color);
}

/* ===== 헤더/탭/본문 래퍼: 여기서 레이아웃 제어 ===== */
.page{
  height: 100dvh;                    /* 전체 높이 */
  display: grid;
  grid-template-rows: auto auto 1fr; /* header / tabs / main */
  overflow: hidden;                  /* 전역 스크롤 금지 → 탭 흔들림 방지 */
}

/* ===== 본문만 스크롤(스크롤바 숨김) + 폰트는 여기만 적용 ===== */
.inv-history{
  min-height: 0;               /* grid 자식 스크롤 핵심 */
  overflow: auto;              /* 본문만 스크롤 */
  -webkit-overflow-scrolling: touch;
  overscroll-behavior: contain;
  scrollbar-width: none;       /* Firefox */

  /* ✅ 폰트는 main에서만 */
  font-family: system-ui, -apple-system, Segoe UI, Roboto, sans-serif;
}
.inv-history::-webkit-scrollbar{ display:none; } /* Chrome/Safari/Edge */

.inv-history .wrap { max-width: 1100px; margin: 24px auto; padding: 0 12px; }
.inv-history h2 { margin: 0 0 14px; }

/* 필터 */
.inv-history .filters { display:flex; gap:12px; align-items:center; flex-wrap:wrap; margin-bottom:14px; }
.inv-history .filters label { font-weight:600; margin-right:6px; color:var(--muted); }
.inv-history .filters input[type="date"],
.inv-history .filters select {
  padding:6px 8px;
  background:var(--input-bg);
  border:1px solid var(--line);
  border-radius:8px;
  color:var(--text-color);
}
.inv-history .filters .btn-group { display:flex; gap:8px; }
.inv-history .filters .btn {
  padding:8px 14px;
  border:1px solid var(--line);
  border-radius:8px;
  background:var(--panel-bg);
  cursor:pointer;
  color:var(--text-color);
  transition:background .15s, border-color .15s, box-shadow .15s;
}
.inv-history .filters .btn:hover { background:var(--line); box-shadow:0 0 0 3px rgba(245,158,11,.18); }
.inv-history .muted { color:#6b7280; font-size:12px; }

/* 알럿 */
.inv-history .error { background:var(--panel-bg); border:1px solid var(--line); padding:10px; border-radius:8px; margin-bottom:12px; }

/* 테이블 */
.inv-history table { width:100%; border-collapse:collapse; }
.inv-history th, .inv-history td { padding:10px; border-bottom:1px solid var(--line); text-align:left; background:var(--input-bg); }
.inv-history th { background:var(--panel-bg); }
.inv-history .right { text-align:right; }
.inv-history .badge { display:inline-block; padding:2px 8px; border-radius:999px; border:1px solid var(--line); font-size:12px; }
.inv-history .badge.in { border-color:var(--badge-in-border-color); color:var(--badge-in-border-color); }
.inv-history .badge.out { border-color:var(--badge-out-border-color); color:var(--badge-out-border-color); }

/* 테이블 내부 가로 스크롤바도 숨김 */
.inv-history table { overflow:auto; }
.inv-history table::-webkit-scrollbar { display:none; }
.inv-history table{ scrollbar-width:none; }

/* 페이저 */
.inv-history .pager {
  display:flex; justify-content:center; align-items:center; margin-top:20px; gap:8px; flex-wrap:wrap;
}
.inv-history .pager .pg {
  padding:8px 12px; border-radius:8px; border:1px solid var(--line);
  background:var(--input-bg); color:var(--text-color); cursor:pointer;
  transition:background .15s, border-color .15s;
  min-width:34px; text-align:center; line-height:1;
}
.inv-history .pager .pg.active { background:var(--accent); color:#111827; border-color:var(--accent); }
.inv-history .pager .pg:hover:not(.active) { background:rgba(255,255,255,.08); border-color:rgba(255,255,255,.08); }
</style>
</head>
<body>

<div class="page">
  <jsp:include page="/WEB-INF/includes/header.jsp"></jsp:include>
  <jsp:include page="/WEB-INF/includes/Inventory_tab.jsp"></jsp:include>

  <main class="inv-history">
    <div class="wrap">

      <h2>입출고 이력</h2>

      <c:if test="${not empty error}"><div class="error"><c:out value="${error}"/></div></c:if>

      <form method="get" action="${cxt}/inventory/history" class="filters">
        <div>
          <label for="start">시작일</label>
          <input type="date" id="start" name="start" value="${start}" required />
        </div>
        <div>
          <label for="end">종료일</label>
          <input type="date" id="end" name="end" value="${end}" required />
        </div>
        <div>
          <label for="type">구분</label>
          <select id="type" name="type">
            <option value="ALL" <c:if test="${type=='ALL'}">selected</c:if>>전체</option>
            <option value="IN"  <c:if test="${type=='IN'}">selected</c:if>>입고</option>
            <option value="OUT" <c:if test="${type=='OUT'}">selected</c:if>>출고</option>
          </select>
        </div>
        <div class="btn-group">
          <button type="button" class="btn" onclick="resetForm()">초기화</button>
          <button type="submit" class="btn">조회</button>
        </div>
        <div class="muted">※ 종료일은 해당 날짜 23:59:59까지 포함됩니다.</div>
      </form>

      <table>
        <thead>
          <tr>
            <th style="width:180px;">거래 시간</th>
            <th style="width:80px;">구분</th>
            <th style="width:200px;">LOT 번호</th>
            <th>품목명</th>
            <th style="width:120px;" class="right">수량</th>
            <th style="width:140px;">창고</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${empty rows}">
              <tr id="noRes"><td colspan="6" class="muted">조회된 이력이 없습니다.</td></tr>
            </c:when>
            <c:otherwise>
              <c:forEach var="r" items="${rows}">
                <tr class="data-row">
                  <td><fmt:formatDate value="${r.tx_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <td>
                    <c:choose>
                      <c:when test="${r.txn_type == 'IN'}"><span class="badge in">입고</span></c:when>
                      <c:when test="${r.txn_type == 'OUT'}"><span class="badge out">출고</span></c:when>
                      <c:otherwise><span class="badge">-</span></c:otherwise>
                    </c:choose>
                  </td>
                  <td><c:out value="${r.lot_no}"/></td>
                  <td><c:out value="${r.item_name}"/></td>
                  <td class="right"><c:out value="${r.quantity}"/></td>
                  <td><c:out value="${r.location_name}"/></td>
                </tr>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>

      <div id="pager" class="pager">
        <button type="button" class="pg" data-act="first" aria-label="첫 페이지">≪</button>
        <button type="button" class="pg" data-act="prev"  aria-label="이전 페이지">‹</button>
        <div class="page-numbers"></div>
        <button type="button" class="pg" data-act="next"  aria-label="다음 페이지">›</button>
        <button type="button" class="pg" data-act="last"  aria-label="마지막 페이지">≫</button>
      </div>

    </div>
  </main>
</div>

<script>
(function(){
  const scope = document.querySelector('.inv-history');

  function resetForm(){
    const f = scope.querySelector('form.filters');
    if(!f) return;
    (f.querySelector('[name="start"]')||{}).value = '';
    (f.querySelector('[name="end"]')||{}).value = '';
    const sel = f.querySelector('[name="type"]');
    if (sel) sel.value = 'ALL';
    f.submit();
  }
  window.resetForm = resetForm;

  // 페이징 (본문만 스크롤 → scope 상단으로 이동)
  (function(){
    const PAGE_SIZE = 20;
    const PAGER_BTN_COUNT = 5;

    const pager = scope.querySelector('#pager');
    const tbodyRows = Array.from(scope.querySelectorAll('tbody tr.data-row'));
    const noRes = scope.querySelector('#noRes');

    const state = { size: PAGE_SIZE, page: 1, matches: tbodyRows.slice() };

    function mkBtn(txt, p, disabled, active){
      const b = document.createElement('button');
      b.type = 'button';
      b.className = 'pg' + (active ? ' active' : '');
      b.textContent = txt;
      b.disabled = !!disabled;
      b.addEventListener('click', function(){
        renderPage(p);
        scope.scrollTo({top:0, behavior:'instant'});  // 본문 컨테이너만 이동
      });
      return b;
    }

    function renderPager(pages){
      if (!pager) return;
      const oldNums = pager.querySelector('.page-numbers');
      let numsWrap = oldNums || document.createElement('div');
      numsWrap.className = 'page-numbers';
      numsWrap.innerHTML = '';

      if(!oldNums){
        pager.insertBefore(numsWrap, pager.querySelector('[data-act="next"]'));
      }

      if (pages <= 1){
        numsWrap.appendChild(Object.assign(mkBtn('1', 1, false, true), {className:'pg active'}));
        pager.querySelector('[data-act="first"]').disabled = true;
        pager.querySelector('[data-act="prev"]').disabled  = true;
        pager.querySelector('[data-act="next"]').disabled  = true;
        pager.querySelector('[data-act="last"]').disabled  = true;
        return;
      }

      const startPage = Math.max(1, state.page - Math.floor(PAGER_BTN_COUNT/2));
      const endPage   = Math.min(pages, startPage + PAGER_BTN_COUNT - 1);
      const s = Math.max(1, endPage - PAGER_BTN_COUNT + 1);
      for (let i = s; i <= endPage; i++){
        const btn = mkBtn(String(i), i, false, i === state.page);
        numsWrap.appendChild(btn);
      }

      pager.querySelector('[data-act="first"]').disabled = (state.page === 1);
      pager.querySelector('[data-act="prev"]').disabled  = (state.page === 1);
      pager.querySelector('[data-act="next"]').disabled  = (state.page === pages);
      pager.querySelector('[data-act="last"]').disabled  = (state.page === pages);
    }

    function renderPage(p){
      const total = state.matches.length;
      const pages = Math.max(1, Math.ceil(total / state.size));
      state.page = Math.min(Math.max(1, p), pages);

      tbodyRows.forEach(tr => tr.style.display = 'none');
      if (noRes) noRes.style.display = (total === 0) ? '' : 'none';

      if (total > 0){
        const start = (state.page - 1) * state.size;
        const end   = Math.min(start + state.size, total);
        for (let i=start; i<end; i++) state.matches[i].style.display = '';
      }
      renderPager(pages);
    }

    (function bindArrows(){
      const first = pager.querySelector('[data-act="first"]');
      const prev  = pager.querySelector('[data-act="prev"]');
      const next  = pager.querySelector('[data-act="next"]');
      const last  = pager.querySelector('[data-act="last"]');
      first.addEventListener('click', ()=>renderPage(1));
      prev .addEventListener('click', ()=>renderPage(state.page-1));
      next .addEventListener('click', ()=>renderPage(state.page+1));
      last .addEventListener('click', ()=>{
        const total = Math.max(1, Math.ceil(state.matches.length / state.size));
        renderPage(total);
      });
    })();

    renderPage(1);
  })();
})();
</script>
</body>
</html>
