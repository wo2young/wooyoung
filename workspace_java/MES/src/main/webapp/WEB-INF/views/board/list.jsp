<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>게시판</title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <style>
    :root{
      --bg-color:#0a0f1a; --panel-bg:#0f1626; --line:#1f2b45; --text-color:#e6ebff;
      --muted:#a7b0c5; --input-bg:#1a202c; --accent:#f59e0b; --accent-light:#d97706; --hover:#1a2333;
    }
    *{box-sizing:border-box}
    html, body {
      margin:0; padding:0; background:var(--bg-color); color:var(--text-color);
      font-family:system-ui, Segoe UI, Arial, Apple SD Gothic Neo, 'Noto Sans KR', sans-serif; height:100dvh;
    }
    main{ overflow:auto; -webkit-overflow-scrolling:touch; }
    .wrap{max-width:1100px;margin:24px auto;padding:0 16px}

    .page-head{display:flex;align-items:end;justify-content:space-between;gap:12px;margin:12px 0 16px}
    .page-head h2{margin:0;font-size:22px;font-weight:800}
    .muted{color:var(--muted);font-size:13px;margin:6px 0 0}

    .tabs{display:flex;gap:8px;flex-wrap:wrap;margin:8px 0 10px}
    .tab{ padding:8px 14px; border:1px solid var(--line); border-radius:999px; text-decoration:none;
      color:var(--muted); background:var(--panel-bg); transition:.15s; line-height:1; white-space:nowrap; }
    .tab:hover{ background:var(--hover); color:#fff; transform:translateY(-1px); }
    .tab.active{ background:var(--accent); color:#111827; border-color:var(--accent); }

    .toolbar{
      display:flex;gap:8px;align-items:center;flex-wrap:wrap;
      background:var(--panel-bg); border:1px solid var(--line); border-radius:12px; padding:10px;
    }
    .toolbar label{display:flex;align-items:center;gap:8px;color:var(--muted)}
    .toolbar input[type="text"]{
      padding:8px 10px; border:1px solid var(--line); border-radius:8px;
      background:var(--input-bg); color:var(--text-color); min-height:36px;
    }

    .btn{ display:inline-block; padding:8px 14px; border:1px solid var(--line); border-radius:10px;
      text-decoration:none; color:var(--text-color); background:var(--panel-bg);
      transition:background .15s,transform .15s,border-color .15s; min-width:70px; text-align:center;
    }
    .btn:hover { background:var(--hover); transform:translateY(-1px); }
    .btn-search { background:var(--accent); border-color:var(--accent); color:#111827; font-weight:600; }
    .btn-search:hover { background:var(--accent-light); border-color:var(--accent-light); color:#111827; }
    .btn-reset { background:#1e3a8a; border-color:#1e3a8a; color:#e6ebff; font-weight:600; }
    .btn-reset:hover { background:#2563eb; border-color:#2563eb; color:#fff; }

    .card{ background:var(--panel-bg); border:1px solid var(--line); border-radius:12px; overflow:hidden }
    table{ width:100%; border-collapse:collapse; table-layout:fixed }
    th,td{
      border-top:1px solid var(--line); padding:10px 12px; text-align:left; vertical-align:middle;
      background:var(--input-bg); color:var(--text-color);
    }
    thead th{ border-top:none; background:var(--panel-bg); font-weight:700 }
    tbody tr:hover{ background:#1c2538 }
    .right{text-align:right}

    .title-cell{display:flex;align-items:center;gap:8px;min-width:240px}
    .title-link{
      color:var(--text-color); text-decoration:none; display:inline-block; max-width:100%;
      white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
    }
    .badge{ display:inline-block; background:#f87171; color:#fff; padding:2px 8px; border-radius:999px; font-size:12px; flex-shrink:0; }
    .cat-pill{ display:inline-block; padding:4px 8px; border:1px solid var(--accent); border-radius:999px; font-size:12px; color:var(--accent); background:transparent; white-space:nowrap; }

    .pagination{
      display:flex;gap:8px;margin:14px 0; align-items:center;flex-wrap:wrap;justify-content:center;
    }
    .pagination .page-numbers{ display:flex; gap:8px; }
    .pagination button, .page-number-btn{
      padding:6px 12px; border:1px solid var(--line); border-radius:8px; background:var(--input-bg);
      color:var(--text-color); cursor:pointer; min-width:34px; text-align:center; line-height:1;
    }
    .pagination button:hover:not(:disabled), .page-number-btn:hover:not(:disabled){ background:var(--hover); }
    .page-number-btn.active{ background:var(--accent); border-color:var(--accent); color:#111827; }
    .pagination button:disabled{ opacity:.5; cursor:not-allowed; }

    @media (max-width:640px){
      .page-head{flex-direction:column;align-items:flex-start}
      .title-cell{min-width:160px}
      thead th:nth-child(2){width:120px}
    }
  </style>
</head>
<body>
  <main class="wrap">
    <div class="page-head">
      <h2>게시판</h2>
      <p class="muted">※ 각 카테고리 목록에도 <b>공지</b> 글은 항상 함께 표시됩니다.</p>
    </div>

    <!-- 탭 -->
    <div class="tabs">
      <a class="tab ${empty selectedCategoryId ? 'active' : ''}" href="${ctx}/board">전체</a>
      <c:forEach var="c" items="${categories}">
        <c:if test="${c.categoryName ne '공지'}">
          <a class="tab ${selectedCategoryId == c.categoryId ? 'active' : ''}"
             href="${ctx}/board?categoryId=${c.categoryId}">
            <c:out value="${c.categoryName}"/>
          </a>
        </c:if>
      </c:forEach>
    </div>

    <!-- 검색/행동 바 -->
    <form class="toolbar" method="get" action="${ctx}/board">
      <label>카테고리
        <input type="text" name="categoryId" value="${param.categoryId}" placeholder="예: 1" style="width:90px"/>
      </label>
      <label>검색
        <input type="text" name="keyword" value="${param.keyword}" placeholder="제목 또는 본문 키워드"/>
      </label>
      <button class="btn btn-search" type="submit">검색</button>
      <a class="btn btn-reset" href="${ctx}/board">처음으로</a>
      <a class="btn" href="${ctx}/board/write">글쓰기</a>
    </form>

    <!-- ✅ 반드시 '전체 목록'이 여기 렌더링되어야 클라 페이징 가능 -->
    <div class="card" style="margin-top:10px">
      <table id="boardTable">
        <thead>
          <tr>
            <th style="width:90px">ID</th>
            <th style="width:160px">카테고리</th>
            <th>제목</th>
            <th style="width:170px">작성일</th>
            <th style="width:90px" class="right">조회</th>
          </tr>
        </thead>
        <tbody>
          <%-- allPosts가 있으면 우선 사용, 없으면 posts 사용 (둘 중 하나엔 반드시 '전체'가 들어와야 함) --%>
          <c:forEach var="p" items="${empty allPosts ? posts : allPosts}">
            <tr>
              <td>${p.postId}</td>
              <td>
                <c:set var="catName" value="" />
                <c:forEach var="c" items="${categories}">
                  <c:if test="${c.categoryId == p.categoryId}">
                    <c:set var="catName" value="${c.categoryName}" />
                  </c:if>
                </c:forEach>
                <span class="cat-pill">
                  <c:out value="${empty catName ? p.categoryId : catName}" />
                </span>
              </td>
              <td>
                <div class="title-cell">
                  <a class="title-link" href="${ctx}/board/view?id=${p.postId}">
                    <c:out value="${p.title}"/>
                  </a>
                  <c:if test="${p.isNotice == 'Y'}">
                    <span class="badge">공지</span>
                  </c:if>
                </div>
              </td>
              <td><fmt:formatDate value="${p.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
              <td class="right">${p.viewCnt}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <!-- 페이지네이션 (클라 전용) -->
    <div class="pagination" id="boardPager" aria-label="페이지네이션">
      <button type="button" class="page-number-btn" data-act="first" aria-label="첫 페이지">≪</button>
      <button type="button" class="page-number-btn" data-act="prev"  aria-label="이전 페이지">‹</button>
      <div class="page-numbers" data-role="page-numbers"></div>
      <button type="button" class="page-number-btn" data-act="next"  aria-label="다음 페이지">›</button>
      <button type="button" class="page-number-btn" data-act="last"  aria-label="마지막 페이지">≫</button>
    </div>
  </main>

 <script>
  (function(){
    const pager   = document.getElementById('boardPager');
    const table   = document.getElementById('boardTable');
    if(!pager || !table) return;

    const tbody   = table.querySelector('tbody');
    const numsBox = pager.querySelector('[data-role="page-numbers"]');
    const btnFirst= pager.querySelector('[data-act="first"]');
    const btnPrev = pager.querySelector('[data-act="prev"]');
    const btnNext = pager.querySelector('[data-act="next"]');
    const btnLast = pager.querySelector('[data-act="last"]');

    const size = 10; // 페이지당 10개
    const getRows = () => Array.from(tbody.querySelectorAll('tr'));

    // URL ?page= 를 초기 페이지로 사용(옵션)
    function getInitialPage(){
      const p = parseInt(new URLSearchParams(location.search).get('page'), 10);
      return Number.isFinite(p) && p > 0 ? p : 1;
    }
    let page = getInitialPage();

    function calcTotalPages(){
      const rows = getRows();
      const t = Math.ceil((rows.length || 0) / size);
      // ✅ 최소 1페이지는 유지해서 페이징 바가 절대 사라지지 않도록
      return Math.max(1, t);
    }

    function render(){
      const rows = getRows();
      const total = calcTotalPages();

      // ✅ 항상 보이게 (이 줄 때문에 사라졌었음)
      // pager.style.display = (total <= 1) ? 'none' : 'flex';  // 제거!

      // 경계 보정
      if(page > total) page = total;
      if(page < 1) page = 1;

      // 표시/비표시
      const start = (page - 1) * size;
      const end   = start + size;
      rows.forEach((tr, i) => {
        tr.style.display = (i >= start && i < end) ? '' : 'none';
      });

      // 숫자 버튼 생성 (최소 1개는 보장)
      numsBox.innerHTML = '';
      const maxBtns = Math.min(total, 7);
      let startP = Math.max(1, page - Math.floor(maxBtns/2));
      let endP   = Math.min(total, startP + maxBtns - 1);
      if(endP - startP + 1 < maxBtns){
        startP = Math.max(1, endP - maxBtns + 1);
      }
      for(let p = startP; p <= endP; p++){
        const b = document.createElement('button');
        b.type = 'button';
        b.className = 'page-number-btn' + (p === page ? ' active' : '');
        b.textContent = p;
        if(p === page) b.setAttribute('aria-current', 'page');
        b.addEventListener('click', () => { page = p; render(); });
        numsBox.appendChild(b);
      }

      // 화살표 버튼 상태
      btnFirst.disabled = (page === 1);
      btnPrev.disabled  = (page === 1);
      btnNext.disabled  = (page === total);
      btnLast.disabled  = (page === total);
    }

    // 화살표 핸들러
    btnFirst.addEventListener('click', () => { page = 1; render(); });
    btnPrev .addEventListener('click', () => { page -= 1; render(); });
    btnNext .addEventListener('click', () => { page += 1; render(); });
    btnLast .addEventListener('click', () => { page = calcTotalPages(); render(); });

    render();
  })();
</script>
 
</body>
</html>
