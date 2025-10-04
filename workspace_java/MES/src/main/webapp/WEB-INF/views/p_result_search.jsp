<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="cxt" value="${pageContext.request.contextPath}" />

<c:set var="from"      value="${empty from      ? '' : from}" />
<c:set var="to"        value="${empty to        ? '' : to}" />
<c:set var="orderId"   value="${empty orderId   ? '' : orderId}" />

<c:set var="s_from"    value="${empty s_from    ? '' : s_from}" />
<c:set var="s_to"      value="${empty s_to      ? '' : s_to}" />
<c:set var="s_orderId" value="${empty s_orderId ? '' : s_orderId}" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8"/>
<title>실적 조회</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />

<style>
/* 🌑 다크 모드 테마 */
:root{
  --bg:#0a0f1a; --panel:#0f1626; --line:#1f2b45;
  --text:#e6ebff; --muted:#a7b0c5;
  --card:#111827; --line2:#4b5563; --input-bg:#1a202c;
  --primary:#f59e0b; --primary-hover:#d97706; --accent:var(--primary); --accent-light:var(--primary-hover);
  --ctl-h:34px; --ctl-r:8px; --ctl-fs:13px; --tab-h:44px; --tab-r:12px; --tab-fs:15px;
}

/* 기본 레이아웃 */
html, body{ 
  margin:0; padding:0; min-height:100dvh;
  background:var(--bg); color:var(--text);
  font-family:Segoe UI,Pretendard,'Noto Sans KR','Apple SD Gothic Neo',Arial,sans-serif; 
}
.wrap{ max-width:1200px; margin:28px auto; padding:0 20px; }
h2{ margin:0 0 14px 0; font-size:20px; }

/* Panel */
.panel{ 
  background:var(--panel); border:1px solid var(--line); border-radius:16px;
  padding:18px 16px 12px; margin-bottom:22px; box-shadow:0 6px 16px rgba(0,0,0,.25);
  overflow-x:auto; overscroll-behavior-x:contain;
}

/* Toolbar */
.toolbar{ display:flex; flex-wrap:wrap; gap:6px; align-items:center; margin-bottom:6px; }
.toolbar input[type="date"], .toolbar input[type="text"]{
  background:var(--input-bg); border:1px solid var(--line); border-radius:var(--ctl-r); color:var(--text);
  padding:6px 10px; min-width:130px; height:var(--ctl-h); font-size:var(--ctl-fs);
}
.toolbar .btn:first-of-type{ margin-left:auto; }

/* Button */
.btn{ 
  background:var(--accent); border:none; color:#111827; padding:6px 12px; 
  border-radius:var(--ctl-r); cursor:pointer;
  transition:background .15s, box-shadow .15s, color .15s;
  height:var(--ctl-h); font-size:var(--ctl-fs); font-weight:700; 
}
.btn:hover{ background:var(--accent-light); box-shadow:0 0 0 3px rgba(245,158,11,.18); color:#111827; }
.btn-secondary{ background:transparent; color:var(--accent); border:1px solid var(--accent); }
.btn-secondary:hover{ background:var(--accent); color:#111827; border-color:var(--accent); }
.btn-reset{ background:white; color:#fff; border:1px solid #2563EB; }
.btn-reset:hover{ background:white; border-color:white; color:white; box-shadow:0 0 0 3px rgba(37,99,235,.25); }

/* Table */
table{ width:100%; border-collapse:collapse; margin-top:6px; table-layout:fixed; }
th, td{ border:1px solid var(--line); padding:12px 10px; text-align:center; background:var(--input-bg); }
th{ background:var(--panel); font-weight:600; }
td.num{ text-align:right; }
td.lot{ min-width:140px; }
#resultsTable tbody tr:hover, #summaryTable tbody tr:hover{ background:#111a2b; }

/* Pager */
.pager{ display:flex; gap:8px; align-items:center; margin:10px 0; justify-content:center; }
.pager button, .pager select{
  padding:6px 10px; border-radius:var(--ctl-r); border:1px solid var(--line);
  background:var(--input-bg); color:var(--text); cursor:pointer;
  height:calc(var(--ctl-h) - 2px); font-size:var(--ctl-fs);
}
.pager .info{ display:none !important; }
.pager .page-numbers{ display:flex; gap:4px; align-items:center; flex-wrap:wrap; }
.pager .page-number-btn{ 
  padding:6px 10px; border-radius:var(--ctl-r); border:1px solid var(--line);
  background:var(--input-bg); color:var(--text); cursor:pointer; 
  height:calc(var(--ctl-h) - 2px); font-size:var(--ctl-fs); 
}
.pager .page-number-btn.active{ background:var(--accent); color:#111827; border-color:var(--accent); }
.pager button[disabled]{ opacity:.5; cursor:not-allowed; }

/* Tabs (다른 페이지와 동일 높이/크기) */
.p-result-tab{ 
  display:flex; gap:12px; padding:6px; margin:10px 0 18px; background:var(--card);
  border:1px solid var(--line); border-radius:var(--tab-r); font-size:0;
}
.p-result-tab a{ 
  width:100%; height:var(--tab-h); padding:0 14px; display:flex; align-items:center; justify-content:center;
  border-radius:calc(var(--tab-r) - 2px); font-weight:600; font-size:var(--tab-fs); text-decoration:none;
  color:var(--muted); background:transparent; border:1px solid transparent; transition:all .15s; 
}
.p-result-tab a.active{ background:var(--panel); color:var(--primary); border-color:var(--line2); box-shadow:0 1px 3px rgba(0,0,0,.15); }
.p-result-tab a:hover:not(.active){ background:var(--input-bg); color:#fff; border-color:var(--line2); }

/* Cell wrap */
#resultsTable th, #resultsTable td, #summaryTable th, #summaryTable td{
  white-space:nowrap; overflow:hidden; text-overflow:ellipsis; 
}
#resultsTable td.lot { white-space:normal; word-break:break-word; line-height:1.4; }
#resultsTable td.dt  span { display:block; line-height:1.4; }

/* 반응형 */
@media (max-width: 1024px){
  #resultsTable th, #resultsTable td, #summaryTable th, #summaryTable td{ 
    padding:10px 8px; font-size:12.5px; 
  }
}

/* ===== 스크롤바 숨김 ===== */
html, body { -ms-overflow-style: none; scrollbar-width: none; }
html::-webkit-scrollbar, body::-webkit-scrollbar { display: none; }

/* 패널 내부 스크롤도 동일 적용 */
.panel { -ms-overflow-style: none; scrollbar-width: none; }
.panel::-webkit-scrollbar { display: none; }
</style>
</head>
<body>
  <jsp:include page="/WEB-INF/includes/header.jsp" />
  <c:set var="currentPage" value="search" scope="request"/>

  <main class="wrap">
    <jsp:include page="/WEB-INF/includes/p-result-tab.jsp" />

    <!-- 상세 실적 내역 -->
    <div class="panel" id="section-results">
      <h2>상세 실적 내역</h2>
      <form id="formR" class="toolbar" method="get" action="${cxt}/result/search">
        <input type="hidden" name="tab" value="results"/>
        <input type="date" name="from" value="${from}"/>
        <input type="date" name="to" value="${to}"/>
        <input type="text" name="orderId" placeholder="지시번호" value="${orderId}"/>

        <input type="hidden" name="s_from" value="${s_from}"/>
        <input type="hidden" name="s_to" value="${s_to}"/>
        <input type="hidden" name="s_orderId" value="${s_orderId}"/>
        <button type="button" class="btn btn-secondary btn-reset" onclick="resetFormR()">초기화</button>
        <button type="submit" class="btn">조회</button>
      </form>

      <table id="resultsTable">
        <thead>
          <tr>
            <th>지시일</th>
            <th>지시번호</th>
            <th>시간</th>
            <th>양품</th>
            <th>불량</th>
            <th>로트 번호</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${not empty results}">
              <c:forEach var="row" items="${results}">
                <tr class="result-row">
                  <td><fmt:formatDate value="${row.order_date}" pattern="yyyy-MM-dd"/></td>
                  <td>${row.order_id}</td>
                  <td class="dt">
                    <span><fmt:formatDate value="${row.work_time}" pattern="yyyy-MM-dd"/></span>
                    <span><fmt:formatDate value="${row.work_time}" pattern="HH:mm:ss"/></span>
                  </td>
                  <td class="num"><fmt:formatNumber value="${row.good_qty}"/></td>
                  <td class="num"><fmt:formatNumber value="${row.fail_qty}"/></td>
                  <td class="lot"><c:out value="${row.lot_no}" default="-"/></td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr><td class="empty" colspan="6">데이터가 없습니다.</td></tr>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>

      <div class="pager" id="resultsPager">
        <button type="button" data-act="first" aria-label="첫 페이지">≪</button>
        <button type="button" data-act="prev"  aria-label="이전 페이지">‹</button>
        <div class="page-numbers" data-role="page-numbers"></div>
        <span class="info" data-role="info" aria-live="polite">1 / 1</span>
        <button type="button" data-act="next" aria-label="다음 페이지">›</button>
        <button type="button" data-act="last" aria-label="마지막 페이지">≫</button>
        <select data-role="size" aria-label="페이지 크기">
          <option>10</option><option selected>20</option><option>50</option><option>100</option>
        </select>
      </div>
    </div>

    <!-- 지시대비 달성률 -->
    <div class="panel" id="section-summary">
      <h2>지시대비 달성률</h2>
      <form id="formS" class="toolbar" method="get" action="${cxt}/result/search">
        <input type="hidden" name="tab" value="summary"/>
        <input type="date" name="s_from" value="${s_from}"/>
        <input type="date" name="s_to" value="${s_to}"/>
        <input type="text" name="s_orderId" placeholder="지시번호" value="${s_orderId}"/>
        <button type="button" class="btn btn-secondary btn-reset" onclick="resetFormS()">초기화</button>
        <button type="submit" class="btn">조회</button>
      </form>

      <table id="summaryTable">
        <thead>
          <tr>
            <th>지시번호</th>
            <th>지시일</th>
            <th>총 만든량</th>
            <th>지시수량</th>
            <th>달성률 %</th>
          </tr>
        </thead>
        <tbody>
          <c:choose>
            <c:when test="${not empty summary}">
              <c:forEach var="s" items="${summary}">
                <c:set var="total"    value="${empty s.total_good_qty ? 0 : s.total_good_qty}" />
                <c:set var="orderQty" value="${empty s.order_qty      ? 0 : s.order_qty}" />
                <c:set var="rate"     value="${orderQty gt 0 ? (total * 100.0) / orderQty : 0}" />
                <tr class="summary-row">
                  <td>${s.order_id}</td>
                  <td><fmt:formatDate value="${s.order_date}" pattern="yyyy-MM-dd"/></td>
                  <td class="num"><fmt:formatNumber value="${total}"/></td>
                  <td class="num"><fmt:formatNumber value="${orderQty}"/></td>
                  <td class="num"><fmt:formatNumber value="${rate}" pattern="#0.##"/></td>
                </tr>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <tr><td class="empty" colspan="5">데이터가 없습니다.</td></tr>
            </c:otherwise>
          </c:choose>
        </tbody>
      </table>

      <div class="pager" id="summaryPager">
        <button type="button" data-act="first" aria-label="첫 페이지">≪</button>
        <button type="button" data-act="prev"  aria-label="이전 페이지">‹</button>
        <div class="page-numbers" data-role="page-numbers"></div>
        <span class="info" data-role="info" aria-live="polite">1 / 1</span>
        <button type="button" data-act="next" aria-label="다음 페이지">›</button>
        <button type="button" data-act="last" aria-label="마지막 페이지">≫</button>
        <select data-role="size" aria-label="페이지 크기">
          <option>10</option><option selected>20</option><option>50</option><option>100</option>
        </select>
      </div>
    </div>
  </main>

<script>
/* ======= 초기화: 값 비우고 바로 재조회(submit) ======= */
function resetFormR(){
  const f = document.getElementById('formR');
  if(!f) return;
  ['from','to','orderId'].forEach(n => {
    const el = f.querySelector('[name="'+n+'"]'); if(el) el.value='';
  });
  f.submit();
}
function resetFormS(){
  const f = document.getElementById('formS');
  if(!f) return;
  ['s_from','s_to','s_orderId'].forEach(n => {
    const el = f.querySelector('[name="'+n+'"]'); if(el) el.value='';
  });
  f.submit();
}

/* ======= 클라이언트 페이징 ======= */
(function(){
  function setupPager(pagerId, tableId, storageKey){
    const pager   = document.getElementById(pagerId);
    const table   = document.getElementById(tableId);
    if(!pager || !table) return;

    const tbody   = table.querySelector('tbody');
    const sizeSel = pager.querySelector('[data-role="size"]');
    const infoEl  = pager.querySelector('[data-role="info"]');
    const numsBox = pager.querySelector('[data-role="page-numbers"]');
    const btnFirst= pager.querySelector('[data-act="first"]');
    const btnPrev = pager.querySelector('[data-act="prev"]');
    const btnNext = pager.querySelector('[data-act="next"]');
    const btnLast = pager.querySelector('[data-act="last"]');

    function getRows(){
      return Array.from(tbody.querySelectorAll('tr')).filter(tr => !tr.querySelector('.empty'));
    }

    let page = 1;
    let size = parseInt(localStorage.getItem(storageKey + ':size') || sizeSel.value, 10);
    sizeSel.value = String(size);

    function totalPages(){
      const rows = getRows();
      return rows.length === 0 ? 1 : Math.ceil(rows.length / size);
    }

    function render(){
      const rows = getRows();
      const total = totalPages();
      if(page < 1) page = 1;
      if(page > total) page = total;

      const start = (page - 1) * size;
      const end   = start + size;

      rows.forEach((tr, i) => {
        tr.style.display = (i >= start && i < end) ? '' : 'none';
      });

      // 페이지 숫자 버튼 갱신
      numsBox.innerHTML = '';
      const maxBtns = Math.min(total, 7);
      let startP = Math.max(1, page - Math.floor(maxBtns/2));
      let endP   = Math.min(total, startP + maxBtns - 1);
      if(endP - startP + 1 < maxBtns){
        startP = Math.max(1, endP - maxBtns + 1);
      }
      for(let p=startP; p<=endP; p++){
        const b = document.createElement('button');
        b.type = 'button';
        b.className = 'page-number-btn' + (p === page ? ' active' : '');
        b.textContent = p;
        b.addEventListener('click', () => { page = p; render(); });
        numsBox.appendChild(b);
      }

      // 정보/버튼 상태
      if(infoEl) infoEl.textContent = page + ' / ' + total;
      btnFirst.disabled = (page === 1);
      btnPrev .disabled = (page === 1);
      btnNext .disabled = (page === total);
      btnLast .disabled = (page === total);
    }

    sizeSel.addEventListener('change', () => {
      size = parseInt(sizeSel.value, 10);
      localStorage.setItem(storageKey + ':size', size);
      page = 1; render();
    });
    btnFirst.addEventListener('click', () => { page = 1; render(); });
    btnPrev .addEventListener('click', () => { page -= 1; render(); });
    btnNext .addEventListener('click', () => { page += 1; render(); });
    btnLast .addEventListener('click', () => { page = totalPages(); render(); });

    render();
  }

  document.addEventListener('DOMContentLoaded', function(){
    setupPager('resultsPager', 'resultsTable', 'presult:results');
    setupPager('summaryPager', 'summaryTable', 'presult:summary');
  });
})();
</script>
</body>
</html>
