<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<c:set var="uri" value="${pageContext.request.requestURI}" />
<c:set var="cxt" value="${pageContext.request.contextPath}" />

<div class="wrap dark">

  <!-- 상단 탭 -->
  <nav class="ppage-tabs" aria-label="목표 탭">
    <a href="${cxt}/productionPlan"
       class="ppage-tab ${fn:endsWith(uri, '/productionPlan') ? 'active' : ''}"
       <c:if test="${fn:endsWith(uri, '/productionPlan')}">aria-current="page"</c:if>>
      생산 목표 등록
    </a>
    <a href="${cxt}/productionPlan/list"
       class="ppage-tab ${fn:endsWith(uri, '/list') ? 'active' : ''}"
       <c:if test="${fn:endsWith(uri, '/list')}">aria-current="page"</c:if>>
      생산 목표 조회
    </a>
  </nav>

  <main class="container">
    <h2 class="page-title">목표 조회</h2>

    <!-- 알림 -->
    <c:if test="${not empty sessionScope.msg}">
      <script>(m=>{ if(m) alert(m); })("<c:out value='${sessionScope.msg}'/>");</script>
      <c:remove var="msg" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.errorMsg}">
      <script>(m=>{ if(m) alert(m); })("<c:out value='${sessionScope.errorMsg}'/>");</script>
      <c:remove var="errorMsg" scope="session"/>
    </c:if>

    <!-- 검색 -->
    <form method="get" action="${cxt}/productionPlan/list" class="search-bar">
      <div class="field">
        <label>시작일</label>
        <input type="date" name="searchStart" value="${param.searchStart}">
      </div>
      <div class="field">
        <label>종료일</label>
        <input type="date" name="searchEnd" value="${param.searchEnd}">
      </div>
      <div class="actions">
        <button type="submit" class="btn btn-primary">조회</button>
        <a class="btn btn-ghost" href="${cxt}/productionPlan/list">초기화</a>
      </div>
    </form>

    <!-- 목록 -->
    <c:choose>
      <c:when test="${empty targetList}">
        <p class="empty">등록된 목표가 없습니다.</p>
      </c:when>
      <c:otherwise>
        <div class="table-wrap">
          <table class="data-table">
            <colgroup>
              <col style="width:76px">
              <col style="width:220px">
              <col style="width:128px">
              <col style="width:128px">
              <col style="width:120px">
              <col style="width:120px">
              <col style="width:140px">
              <col style="width:160px">
            </colgroup>
            <thead>
              <tr>
                <th>ID</th>
                <th>품목</th>
                <th>시작일</th>
                <th>종료일</th>
                <th class="qty">목표 수량</th>
                <th class="status-col">진행 상태</th>
                <th>등록자</th>
                <th class="actions">관리</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="row" items="${targetList}">
                <tr>
                  <td><c:out value="${row.target_id}"/></td>
                  <td class="tl"><c:out value="${row.item_name}"/></td>
                  <td><c:out value="${row.period_start}"/></td>
                  <td><c:out value="${row.period_end}"/></td>
                  <td class="qty"><c:out value="${row.target_quantity}"/>개</td>

                  <td class="status-col">
                    <c:choose>
                      <c:when test="${row.status eq '완료'}"><span class="status done">완료</span></c:when>
                      <c:when test="${row.status eq '진행중'}"><span class="status progress">진행중</span></c:when>
                      <c:when test="${row.status eq '대기'}"><span class="status wait">대기</span></c:when>
                      <c:otherwise><span class="status wait"><c:out value="${row.status}"/></span></c:otherwise>
                    </c:choose>
                  </td>

                  <td>
                    <c:out value="${row.created_by_name}"/>
                    <c:if test="${row.created_by_role eq 'ADMIN'}">(관리자)</c:if>
                    <c:if test="${row.created_by_role eq 'MANAGER'}">(매니저)</c:if>
                  </td>

                  <td class="actions">
                    <c:choose>
                      <c:when test="${row.status eq '완료'}">
                        <span class="done-text">완료</span>
                      </c:when>
                      <c:when test="${row.status eq '대기' or row.status eq '진행중'}">
                        <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.user_role eq 'ADMIN'}">
                          <span class="btn-group">
                            <button type="button" class="btn btn-sm"
                                    data-target-id="${row.target_id}"
                                    data-item-id="${row.item_id}"
                                    data-item-name="<c:out value='${row.item_name}'/>"
                                    data-start="${row.period_start}"
                                    data-end="${row.period_end}"
                                    data-qty="${row.target_quantity}"
                                    data-sum="${row.results_sum}"
                                    onclick="openFromButton(this)">수정</button>

                            <form action="${cxt}/productionPlan/delete" method="post"
                                  onsubmit="return confirm('정말 삭제하시겠습니까?');">
                              <input type="hidden" name="target_id" value="${row.target_id}">
                              <button type="submit" class="btn btn-sm btn-danger">삭제</button>
                            </form>
                          </span>
                        </c:if>
                      </c:when>
                      <c:otherwise>-</c:otherwise>
                    </c:choose>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>

        <!-- 페이징 (1~5만 노출, 콤팩트) -->
        <div class="pagination pagination-compact" role="navigation" aria-label="페이지 이동">
          <c:set var="hasPrev" value="${currentPage > 1}" />
          <c:set var="hasNext" value="${currentPage < totalPage}" />
          <c:set var="prevPage" value="${currentPage - 1}" />
          <c:set var="nextPage" value="${currentPage + 1}" />
          <c:set var="endPage" value="${totalPage > 5 ? 5 : totalPage}" />

          <c:url var="firstUrl" value="/productionPlan/list">
            <c:param name="page" value="1"/>
            <c:param name="searchStart" value="${param.searchStart}"/>
            <c:param name="searchEnd" value="${param.searchEnd}"/>
          </c:url>
          <c:url var="prevUrl" value="/productionPlan/list">
            <c:param name="page" value="${prevPage}"/>
            <c:param name="searchStart" value="${param.searchStart}"/>
            <c:param name="searchEnd" value="${param.searchEnd}"/>
          </c:url>
          <c:url var="nextUrl" value="/productionPlan/list">
            <c:param name="page" value="${nextPage}"/>
            <c:param name="searchStart" value="${param.searchStart}"/>
            <c:param name="searchEnd" value="${param.searchEnd}"/>
          </c:url>
          <c:url var="lastUrl" value="/productionPlan/list">
            <c:param name="page" value="${totalPage}"/>
            <c:param name="searchStart" value="${param.searchStart}"/>
            <c:param name="searchEnd" value="${param.searchEnd}"/>
          </c:url>

          <a class="page-link square ${!hasPrev ? 'disabled first' : 'first'}" href="${hasPrev ? firstUrl : '#'}" aria-label="처음">&laquo;</a>
          <a class="page-link square ${!hasPrev ? 'disabled prev' : 'prev'}"  href="${hasPrev ? prevUrl  : '#'}" aria-label="이전">&lsaquo;</a>

          <c:forEach var="p" begin="1" end="${endPage}">
            <c:url var="pageUrl" value="/productionPlan/list">
              <c:param name="page" value="${p}"/>
              <c:param name="searchStart" value="${param.searchStart}"/>
              <c:param name="searchEnd" value="${param.searchEnd}"/>
            </c:url>
            <a class="page-link square ${p == currentPage ? 'active' : ''}" href="${pageUrl}">${p}</a>
          </c:forEach>

          <a class="page-link square ${!hasNext ? 'disabled next' : 'next'}" href="${hasNext ? nextUrl : '#'}" aria-label="다음">&rsaquo;</a>
          <a class="page-link square ${!hasNext ? 'disabled last' : 'last'}" href="${hasNext ? lastUrl : '#'}" aria-label="마지막">&raquo;</a>
        </div>
      </c:otherwise>
    </c:choose>
  </main>
</div>

<!-- 모달 -->
<div id="editModal" class="modal" aria-hidden="true">
  <div class="modal-content" role="dialog" aria-modal="true" aria-labelledby="editTitle">
    <h3 id="editTitle">목표 수정</h3>
    <form action="${cxt}/productionPlan/edit" method="post"
          onsubmit="return confirm('정말 수정하시겠습니까?');">
      <input type="hidden" name="target_id" id="modal_target_id">
      <input type="hidden" name="item_id" id="modal_item_id">
      <input type="hidden" name="page" value="${currentPage}">
      <input type="hidden" name="searchStart" value="${param.searchStart}">
      <input type="hidden" name="searchEnd" value="${param.searchEnd}">

      <label>품목</label>
      <input type="text" id="modal_item_name" readonly>

      <div class="grid-2">
        <div>
          <label>시작일</label>
          <input type="date" name="period_start" id="modal_period_start">
        </div>
        <div>
          <label>종료일</label>
          <input type="date" name="period_end" id="modal_period_end">
        </div>
      </div>

      <label>목표 수량</label>
      <input type="number" name="target_quantity" id="modal_target_quantity" min="0">

      <div class="meta">
        <span>양품 합계: <strong id="modal_results_sum"></strong> 개</span>
      </div>

      <div class="modal-actions">
        <button type="submit" class="btn btn-primary">수정 완료</button>
        <button type="button" class="btn btn-ghost" onclick="closeEditModal()">닫기</button>
      </div>
    </form>
    <button class="modal-close" aria-label="닫기" onclick="closeEditModal()">×</button>
  </div>
</div>

<!-- 모달 제어 (강화 버전) -->
<script>
  // 유틸: 포커스 가능한 요소
  const FOCUSABLE = 'a[href], button, textarea, input, select, [tabindex]:not([tabindex="-1"])';

  function openFromButton(btn){
    const d = btn.dataset;
    openEditModal(d.targetId, d.itemId, d.itemName, d.start, d.end, d.qty, d.sum);
  }

  function openEditModal(targetId, itemId, itemName, periodStart, periodEnd, targetQty, resultsSum) {
    // 값 주입
    document.getElementById("modal_target_id").value = targetId || '';
    document.getElementById("modal_item_id").value = itemId || '';
    document.getElementById("modal_item_name").value = itemName || '';
    document.getElementById("modal_period_start").value = (periodStart || '').slice(0,10);
    document.getElementById("modal_period_end").value = (periodEnd || '').slice(0,10);
    document.getElementById("modal_target_quantity").value = targetQty || 0;
    document.getElementById("modal_results_sum").textContent = resultsSum || 0;

    const modal = document.getElementById("editModal");
    const panel = modal.querySelector('.modal-content');

    // 레이어/표시
    modal.classList.add("show");
    modal.setAttribute('aria-hidden','false');
    document.body.classList.add('modal-open');

    // 포커스 이동 및 트랩
    panel.setAttribute('tabindex','-1');
    panel.focus();

    const focusables = panel.querySelectorAll(FOCUSABLE);
    const first = focusables[0], last = focusables[focusables.length - 1];
    function trap(e){
      if(e.key !== 'Tab') return;
      if(e.shiftKey && document.activeElement === first){ e.preventDefault(); last.focus(); }
      else if(!e.shiftKey && document.activeElement === last){ e.preventDefault(); first.focus(); }
    }
    modal._trap = trap;
    modal.addEventListener('keydown', trap);
  }

  function closeEditModal() {
    const modal = document.getElementById("editModal");
    if(!modal) return;
    modal.classList.remove("show");
    modal.setAttribute('aria-hidden','true');
    document.body.classList.remove('modal-open');
    modal.removeEventListener('keydown', modal._trap || (()=>{}));
  }

  // 바깥 클릭/ESC 닫기 + 초기 강제 숨김
  (function(){
    const modal = document.getElementById('editModal');
    if(!modal) return;

    modal.addEventListener('click', (e)=>{ if(e.target === modal) closeEditModal(); });

    document.addEventListener('keydown', (e)=>{
      if(e.key === 'Escape') closeEditModal();
    });

    document.addEventListener('DOMContentLoaded', function(){
      modal.classList.remove('show');
      modal.setAttribute('aria-hidden','true');
      // 혹시 모를 상위 스택킹 컨텍스트 문제 대비
      modal.style.position = 'fixed';
    });
  })();
</script>

<style>
  /* ===== 테마 ===== */
  .dark{
    --bg:#0b111c; --surface:#0f1626; --surface-2:#0d1423;
    --line:#253150; --line-2:#2b385c;
    --text:#e6ebff; --muted:#9fb0d6;
    --accent:#60a5fa; --accent-2:#f59e0b;
    --danger:#ef4444;
    --header-h:56px; --tab-h:48px;
  }
  html,body{margin:0;padding:0;background:var(--bg);color:var(--text);overflow-x:hidden;}
  .wrap.dark{min-height:100vh;background:var(--bg)!important;}
  .wrap.dark .container{background:var(--bg)!important;}
  .container{max-width:1100px;margin:0 auto;padding:20px 16px 48px;}
  .page-title{margin:16px 0 12px;color:#fff;}

  /* 스크롤바 (기본 스타일은 남겨두되, 맨 아래에서 전역 숨김으로 덮어씀) */
  ::-webkit-scrollbar{width:10px;height:10px}
  ::-webkit-scrollbar-track{background:var(--bg)}
  ::-webkit-scrollbar-thumb{background:#2a385a;border-radius:6px}
  ::-webkit-scrollbar-thumb:hover{background:#3a4d7f}
  ::-webkit-scrollbar-corner{background:var(--bg)}
  .table-wrap::-webkit-scrollbar-corner{background:var(--surface)}

  /* 탭 */
  .ppage-tabs{display:flex;gap:16px;padding:0 16px;border-bottom:1px solid var(--line);position:sticky;top:var(--header-h);z-index:5;}
  .ppage-tab{display:inline-block;padding:12px 2px;color:var(--muted);font-weight:600;border-bottom:3px solid transparent;transition:color .15s,border-color .15s;line-height:1;text-decoration:none;}
  .ppage-tab:hover{color:#e7ecff;border-bottom-color:#3b4e78}
  .ppage-tab.active{color:#fff;border-bottom-color:var(--accent)}

  /* 검색 바 */
  .search-bar{display:flex;flex-wrap:wrap;gap:12px;align-items:flex-end;margin:14px 0 16px;padding:12px;background:var(--surface);border:1px solid var(--line);border-radius:12px;}
  .search-bar .field{display:flex;flex-direction:column;gap:6px}
  .search-bar label{font-size:12px;color:#cfe0ff;font-weight:800}
  .search-bar input[type="date"]{height:38px;padding:6px 10px;border:1px solid var(--line-2);border-radius:10px;background:var(--surface-2);color:var(--text);outline:none;transition:border-color .15s,box-shadow .15s;}
  .search-bar input[type="date"]:focus{border-color:var(--accent);box-shadow:0 0 0 4px rgba(96,165,250,.2)}
  .search-bar .actions{margin-left:auto;display:flex;gap:8px}

  /* 버튼 */
  .btn{display:inline-flex;align-items:center;justify-content:center;height:38px;padding:0 14px;border-radius:10px;border:1px solid var(--line);background:var(--surface-2);color:var(--text);cursor:pointer;text-decoration:none;font-size:14px;font-weight:800;transition:transform .05s,background .15s,border-color .15s;}
  .btn:hover{background:#101a2e;border-color:#375083}
  .btn:active{transform:translateY(1px)}
  .btn-primary{background:var(--accent);color:#0b1020;border-color:transparent}
  .btn-ghost{background:transparent}
  .btn-sm{height:32px;padding:0 10px;font-size:13px;white-space:nowrap}
  .btn-danger{background:var(--danger);color:#fff;border-color:transparent}
  .btn-group{display:inline-flex;gap:8px;align-items:center;flex-wrap:nowrap;white-space:nowrap;}
  .data-table td.actions form{display:inline-block;margin:0;}
  .data-table td.actions form .btn{display:inline-flex;}

  /* 테이블 */
  .table-wrap{position:relative;margin-top:6px;border:1px solid var(--line);border-radius:12px;background:var(--surface);box-shadow:0 10px 28px rgba(3,7,18,.35);overflow:auto;z-index:0;}
  .data-table{width:100%;border-collapse:separate;border-spacing:0;table-layout:fixed;color:var(--text);}
  .data-table thead th{position:sticky;top:0;z-index:1!important;background:#0e1830;color:#ffffff;padding:12px 10px;text-align:center;font-weight:800;border-bottom:1px solid var(--line);white-space:nowrap;letter-spacing:0;box-shadow:0 1px 0 rgba(37,49,80,.9);overflow:hidden;text-overflow:ellipsis;}
  .data-table td,.data-table th{white-space:nowrap;overflow:hidden;text-overflow:ellipsis;vertical-align:middle;}
  .data-table tbody td{padding:10px;border-top:1px solid var(--line);text-align:center}
  .data-table tbody tr:first-child td{border-top-color:transparent}
  .data-table tbody tr:nth-child(odd){background:#0e1527}
  .data-table tbody tr:hover{background:#101a2e}
  .data-table td.tl{text-align:left}

  /* 정렬 규칙 */
  .data-table th.qty,.data-table td.qty{ text-align:right!important; padding-right:12px; }
  .data-table th.status-col,.data-table td.status-col{ text-align:center!important; }
  .data-table th.actions,.data-table td.actions{ text-align:center!important; padding-right:0; }

  /* 상태 뱃지 */
  .status{padding:4px 10px;border-radius:999px;font-weight:800;font-size:12px;display:inline-block;}
  .status.done{background:rgba(52,211,153,.15);color:#34d399;border:1px solid rgba(52,211,153,.35);}
  .status.progress{background:rgba(96,165,250,.15);color:#93c5fd;border:1px solid rgba(96,165,250,.35);}
  .status.wait{background:rgba(148,163,184,.12);color:#cbd5e1;border:1px solid rgba(148,163,184,.28);}
  .done-text{color:#34d399;font-weight:800;}

  /* 페이징 (콤팩트) */
  .pagination{display:flex;justify-content:center;align-items:center;gap:5px;margin-top:16px;flex-wrap:wrap;}
  .page-link.square{
    min-width:24px;height:28px;padding:4px 8px;
    display:inline-flex;align-items:center;justify-content:center;
    border:1px solid var(--line);border-radius:6px;
    background:var(--surface);color:#e6ebff;text-decoration:none;
    font-size:12px;font-weight:700;line-height:1;
    transition:background .15s,border-color .15s,transform .05s,color .15s;
  }
  .page-link.square:hover{ background:#101a2e; border-color:#375083; }
  .page-link.square:active{ transform:translateY(1px); }
  .page-link.square.active{ background:var(--accent-2); color:#0b1020; border-color:transparent; }
  .page-link.square.disabled{ opacity:.5; pointer-events:none; }

  /* === MODAL (강화본) === */
  .modal{
    position:fixed;
    inset:0;
    display:flex;
    align-items:center;
    justify-content:center;
    padding:24px;
    background:rgba(0,0,0,.72);
    backdrop-filter: blur(3px);
    -webkit-backdrop-filter: blur(3px);
    z-index: 99999 !important;
    opacity:0;
    pointer-events:none;
    transition: opacity .18s ease;
  }
  .modal[aria-hidden="true"]{ display:flex; }
  .modal.show{ opacity:1; pointer-events:auto; }

  .modal-content{
    position:relative;
    width:min(760px, 96vw);
    max-height:min(82vh, 720px);
    overflow:auto;
    background: linear-gradient(180deg, #141c2f, #0f1729);
    border-radius:18px;
    box-shadow:
      0 24px 100px rgba(0,0,0,.75),
      0 0 0 1px rgba(96,165,250,.4),
      0 0 0 6px rgba(96,165,250,.08);
    border:1px solid rgba(96,165,250,.35);
    color:#ffffff;
    padding:22px;
    transform: translateY(12px) scale(.985);
    transition: transform .18s ease, box-shadow .18s ease;
  }
  .modal.show .modal-content{ transform:none; }

  .modal-content h3{
    margin:2px 0 14px;
    padding-bottom:10px;
    font-size:18px;
    font-weight:900;
    color:#ffffff;
    border-bottom:1px solid rgba(148,163,184,.28);
    letter-spacing:.2px;
  }

  .modal-content label{
    display:block; font-weight:800; margin:12px 0 6px; color:#0b1b34;
  }

  /* 🔆 모달 input: 밝은 배경 + 진한 글자색 */
  .modal-content input[type="text"],
  .modal-content input[type="date"],
  .modal-content input[type="number"]{
    width:100%; height:42px; padding:8px 12px;
    border:1px solid var(--line-2);
    border-radius:12px;
    background:#ffffff;            /* 밝게 */
    color:#111827;                 /* 진하게 */
    font-size:14px;
    outline:none;
    transition:border-color .15s, box-shadow .15s, background .15s, color .15s;
  }
  .modal-content input:focus{
    border-color:var(--accent);
    box-shadow:0 0 0 4px rgba(96,165,250,.3);
    background:#f9fafb;
    color:#111827;
  }

  .grid-2{ display:grid; grid-template-columns:1fr 1fr; gap:12px; }
  @media (max-width: 560px){ .grid-2{ grid-template-columns:1fr; } }

  .meta{ margin:10px 0 4px; color:#cfe0ff; font-weight:700; }

  .modal-actions{ display:flex; gap:10px; justify-content:flex-end; margin-top:16px; }

  .modal-close{
    position:absolute; top:10px; right:12px;
    width:38px; height:38px;
    border:none; background:transparent;
    font-size:24px; font-weight:900;
    cursor:pointer; color:#c9d9ff;
    border-radius:10px;
    transition:background .15s, color .15s, transform .05s;
  }
  .modal-close:hover{ color:#fff; background:rgba(255,255,255,.08); }
  .modal-close:active{ transform:translateY(1px); }

  /* 내부 스크롤바 기본 스타일(아래 전역 숨김이 덮어씀) */
  .modal-content::-webkit-scrollbar{ width:10px; height:10px; }
  .modal-content::-webkit-scrollbar-track{ background:transparent; }
  .modal-content::-webkit-scrollbar-thumb{ background:#334a7d; border-radius:6px; }
  .modal-content::-webkit-scrollbar-thumb:hover{ background:#3e5ca0; }

  body.modal-open{ overflow: hidden; }

  @media (prefers-contrast: more){
    .modal{ background: rgba(0,0,0,.82); }
    .modal-content{
      box-shadow: 0 24px 100px rgba(0,0,0,.85), 0 0 0 2px #60a5fa;
      border: 2px solid #60a5fa;
    }
  }

  /* 빈 상태 */
  .empty{
    color:#cbd5e1; background:rgba(148,163,184,.08); border:1px dashed var(--line);
    padding:16px; border-radius:12px;
  }

  /* =========================
     🚫 전역 스크롤바 숨김(스크롤은 동작)
     ========================= */
  * {
    scrollbar-width: none !important;      /* Firefox */
    -ms-overflow-style: none !important;   /* IE/Edge */
  }
  *::-webkit-scrollbar {
    width: 0 !important;
    height: 0 !important;
  }
  /* 표/모달 내부도 강제로 숨김 */
  .table-wrap,
  .modal-content {
    scrollbar-width: none !important;
    -ms-overflow-style: none !important;
  }
  .table-wrap::-webkit-scrollbar,
  .modal-content::-webkit-scrollbar {
    width: 0 !important;
    height: 0 !important;
  }
  /* 필요 시 완전 차단하려면 아래 주석 해제
  html, body { overflow: hidden !important; }
  */
</style>
