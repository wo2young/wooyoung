<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="activeNav" value="codes"/>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>코드관리</title>
<style>
:root{
  --bg:#0a0f1a; --card:#111827; --line:#1f2b45; --line2:#4b5563;
  --text:#e6ebff; --muted:#a7b0c5; --input-bg:#1a202c;
  --primary:#f59e0b; --primary-hover:#d97706; --danger:#dc2626; --danger-hover:#ef4444;
}

/* ✅ 스크롤바 완전 숨김 + 여백 0 */
html{overflow:hidden;height:100%;}
body{
  overflow:auto;height:100%;
  -ms-overflow-style:none; scrollbar-width:none;
  margin:0; padding:0; min-height:100vh;
  background:var(--bg); color:var(--text);
  font-family:Segoe UI,Pretendard,'Noto Sans KR',Arial,sans-serif; font-size:14px;
}
body::-webkit-scrollbar{display:none;}
*,*::before,*::after{box-sizing:border-box;}

.wrap{max-width:1200px; margin:28px auto; padding:20px;}
.card{background:var(--card); border:1px solid var(--line); border-radius:12px; padding:16px; box-shadow:0 6px 18px rgba(0,0,0,.25);}
.section+.section{margin-top:16px;}
h2{margin:0 0 10px; font-size:22px;}
.description{margin:0 0 16px; color:var(--muted); font-size:13px;}

.toolbar{display:flex; gap:10px; align-items:center; justify-content:space-between; flex-wrap:wrap;}
input[type=text], select{
  height:34px; font-size:14px; padding:0 10px;
  background:var(--input-bg); color:var(--text);
  border:1px solid var(--line2); border-radius:6px; appearance:none;
}
input::placeholder{color:#6b7280;}
select{
  background-image:url("data:image/svg+xml;utf8,<svg fill='%23e6ebff' height='20' viewBox='0 0 24 24' width='20' xmlns='http://www.w3.org/2000/svg'><path d='M7 10l5 5 5-5z'/></svg>");
  background-repeat:no-repeat; background-position:right 10px center; background-size:14px; padding-right:28px;
}

/* 테이블: 폭 고정 */
.table-wrap{margin-top:16px;}
table{
  width:100%; border-collapse:collapse; background:var(--card);
  border:1px solid var(--line); border-radius:12px; overflow:hidden; table-layout:fixed;
}
thead th{
  background:#152033; color:#cbd5e1; border-bottom:1px solid var(--line);
  text-align:center; padding:12px 14px; font-size:13px;
}
tbody td{
  color:var(--text); padding:12px 14px; text-align:center; border-bottom:1px solid var(--line);
}
tbody tr:hover{background:#0f1c2d;}
tbody tr:last-child td{border-bottom:none;}
.empty{color:var(--muted);}

/* 컬럼 폭 명시 */
.col-codeid{width:120px;}
.col-detail {width:160px;}
.col-name   {width:auto;}
.col-active {width:120px;}
.col-actions{width:180px;}

/* 긴 텍스트 처리 */
.td-name{ text-align:left; overflow:hidden; text-overflow:ellipsis; white-space:nowrap; }

/* 버튼 */
.btn{min-width:60px; height:32px; border:none; border-radius:6px; font-size:13px; font-weight:600; cursor:pointer; display:inline-flex; align-items:center; justify-content:center; transition:background .2s;}
.btn.edit{background:#1f2937; color:#fff;} .btn.edit:hover{background:#374151;}
.btn.danger{background:var(--danger); color:#fff;} .btn.danger:hover{background:var(--danger-hover);}
.btn.primary{background:#fff; color:#111827; border:1px solid #d1d5db;} .btn.primary:hover{background:var(--primary); color:#111827;}
.btn.query{background:#60a5fa; color:#111827; font-weight:700;} .btn.query:hover{background:#374151; color:#fff;}
.btn-row{display:flex; gap:6px; justify-content:center; align-items:center;}
.btn-row form{margin:0;}
.btn-row,.td-actions,.actions{display:flex!important; flex-direction:row!important; align-items:center!important; justify-content:center!important; gap:8px!important; flex-wrap:nowrap!important; white-space:nowrap!important;}
.btn-row form,.td-actions form,.actions form{display:inline-flex!important; margin:0!important;}
.btn-row form button,.td-actions form button,.actions .btn{margin:0!important; flex:0 0 auto!important;}

/* 페이징 */
.pager{
  display:flex; justify-content:center; align-items:center; gap:8px;
  width:100%; min-height:48px; margin-top:12px;
}
.pager .pg{
  min-width:34px; height:34px; border-radius:10px; font-weight:700;
  border:1px solid var(--line); background:#0e1a2b; color:#cbd5e1; cursor:pointer;
}
.pager .pg.active{ background: var(--primary); color:#111827; }
.pager .pg:hover{ background:#14263e; }
</style>
</head>
<body>
  <jsp:include page="/WEB-INF/includes/2header.jsp">
    <jsp:param name="activeNav" value="codes"/>
  </jsp:include>

  <main class="wrap">
    <h2>코드관리</h2>
    <p class="description">상세코드는 자동으로 생성됩니다. 상세내용만 입력해주세요.</p>

    <!-- 조회/검색 -->
    <div class="toolbar card section">
      <form id="filterForm" class="left" method="get" action="${ctx}/master">
        <input type="hidden" name="act" value="code.detail.list"/>
        <label for="codeId">코드 구분</label>
        <select id="codeId" name="codeId">
          <option value="ALL" ${param.codeId=='ALL'?'selected':''}>전체</option>
          <option value="PCD" ${param.codeId=='PCD'?'selected':''}>PCD</option>
          <option value="FCD" ${param.codeId=='FCD'?'selected':''}>FCD</option>
          <option value="DEF" ${param.codeId=='DEF'?'selected':''}>DEF</option>
        </select>
        <button class="btn query" id="btnQuery" type="submit">조회</button>
      </form>

      <form class="right" onsubmit="return false">
        <input type="text" id="kw" value="${fn:escapeXml(param.kw)}" placeholder="코드ID/상세코드/상세내용 검색"/>
        <button type="button" class="btn" id="btnSearch">검색</button>
      </form>
    </div>

    <!-- 표 -->
    <div class="table-wrap card section">
      <table>
        <colgroup>
          <col class="col-codeid"/><col class="col-detail"/><col class="col-name"/><col class="col-active"/><col class="col-actions"/>
        </colgroup>
        <thead>
          <tr>
            <th>CODE_ID</th><th>상세코드</th><th>상세내용</th><th>활성여부</th><th>수정/삭제</th>
          </tr>
        </thead>
        <tbody id="codes-body">
          <c:if test="${empty details}">
            <tr class="data-row"><td colspan="5" class="empty">데이터가 없습니다.</td></tr>
          </c:if>
          <c:forEach var="d" items="${details}">
            <tr class="data-row"
                data-codeid="${fn:toUpperCase(d.code_id)}"
                data-search="${fn:toUpperCase(d.code_id)} ${fn:toUpperCase(d.detail_code)} ${fn:toUpperCase(d.codeDname)} ${fn:toUpperCase(d.isActive)}">
              <td>${d.code_id}</td>
              <td>${d.detail_code}</td>
              <td class="td-name">${fn:escapeXml(d.codeDname)}</td>
              <td class="td-active">${d.isActive}</td>
              <td class="actions td-actions">
                <form method="get" action="${ctx}/master" class="js-edit-form">
                  <input type="hidden" name="act" value="code.detail.list"/>
                  <input type="hidden" name="edit" value="${d.detail_code}"/>
                  <button class="btn edit" type="submit">수정</button>
                </form>
                <form method="post" action="${ctx}/master" onsubmit="return confirm('정말 삭제하시겠습니까?')">
                  <input type="hidden" name="act" value="code.detail.delete"/>
                  <input type="hidden" name="detailCode" value="${d.detail_code}"/>
                  <button class="btn danger" type="submit">삭제</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <tr id="no-result" style="display:none">
            <td colspan="5" class="empty">검색 결과가 없습니다.</td>
          </tr>
        </tbody>
      </table>
      <div class="pager" id="pager"></div>
    </div>

    <!-- 추가 폼 -->
    <form method="post" action="${ctx}/master" class="card section" id="addForm"
          style="display:grid; grid-template-columns:220px 1fr auto; gap:10px; align-items:end;">
      <input type="hidden" name="act" value="code.detail.add"/>
      <input type="hidden" name="active" value="Y"/>
      <div class="field">
        <label>코드 구분</label>
        <select name="codeId" class="control">
          <option value="PCD">PCD</option><option value="FCD">FCD</option><option value="DEF">DEF</option>
        </select>
      </div>
      <div class="field">
        <label>상세내용</label>
        <input type="text" name="codeDname" class="control" placeholder="상세내용만 입력" required/>
      </div>
      <button class="btn primary" type="submit">추가</button>
    </form>
  </main>

<script>
(function(){
  var CTX = '${ctx}';
  var PAGE_SIZE = 5;

  var body = document.getElementById('codes-body');
  var rows = Array.prototype.slice.call(body.querySelectorAll('tr.data-row'));
  var noRes = document.getElementById('no-result');
  var pager = document.getElementById('pager');
  var selCode = document.getElementById('codeId');
  var kw = document.getElementById('kw');

  function computeMatches(q, codeSel){
    var Q = (q||'').trim().toUpperCase();
    var C = ((codeSel||'ALL')+'').toUpperCase();
    var matches = [];
    rows.forEach(function(tr){
      var hay = (tr.getAttribute('data-search')||tr.textContent||'').toUpperCase();
      var rC  = (tr.getAttribute('data-codeid')||'').toUpperCase();
      var hitQ = !Q || hay.indexOf(Q) !== -1;
      var hitC = (C==='ALL') || (rC===C);
      var hit = hitQ && hitC;
      tr.style.display = hit ? '' : 'none';
      if(hit) matches.push(tr);
    });
    return matches;
  }
  var state = { page:1, size:PAGE_SIZE, matches:computeMatches(kw?kw.value:'', selCode?selCode.value:'ALL') };

  function renderPager(){
    pager.innerHTML = '';
    var total = state.matches.length;
    var pages = Math.max(1, Math.ceil(total/state.size));
    function mk(txt, p, dis, act){
      var b=document.createElement('button');
      b.type='button'; b.className='pg'+(act?' active':''); b.textContent=txt;
      if(dis) b.disabled=true;
      b.onclick=function(){ state.page=p; renderPage(); };
      return b;
    }
    pager.appendChild(mk('<', Math.max(1,state.page-1), state.page===1, false));
    for(var i=1;i<=pages;i++) pager.appendChild(mk(String(i), i, false, i===state.page));
    pager.appendChild(mk('>', Math.min(pages,state.page+1), state.page===pages, false));
  }
  function renderPage(){
    rows.forEach(function(tr){ tr.style.display='none'; });
    var total = state.matches.length;
    if(noRes) noRes.style.display = (total===0) ? '' : 'none';
    if(total>0){
      var start=(state.page-1)*state.size, end=Math.min(start+state.size,total);
      for(var i=start;i<end;i++) state.matches[i].style.display='';
    }
    renderPager();
  }
  function applyFilter(){
    var q = kw ? kw.value : '';
    var c = selCode ? selCode.value : 'ALL';
    state.matches = computeMatches(q,c);
    state.page = 1;
    renderPage();
  }
  document.getElementById('btnSearch')?.addEventListener('click', applyFilter);
  document.getElementById('btnQuery')?.addEventListener('click', function(){ document.getElementById('filterForm').submit(); });

  renderPage();

  /* 인라인 편집 (submit 가로채기) */
  body.addEventListener('submit', function(e){
    var f = e.target.closest('.js-edit-form');
    if(!f) return;
    e.preventDefault();

    var tr = f.closest('tr');
    if(!tr || tr.dataset.editing==='1') return;
    tr.dataset.editing='1';
    tr.dataset.origHtml = tr.innerHTML;

    var tds = tr.querySelectorAll('td');
    var dcd = (tds[1].textContent||'').trim();
    var name= (tds[2].textContent||'').trim();
    var act = (tds[3].textContent||'').trim();

    tds[2].innerHTML =
      '<textarea class="inline" name="codeDname" rows="2" style="width:95%;resize:vertical;">'
      + name.replace(/[&<>"']/g, function(m){return {'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m];})
      + '</textarea>';

    tds[3].innerHTML =
      '<select name="active" class="inline yn">'
      + '<option value="Y"' + (act==='Y'?' selected':'') + '>Y</option>'
      + '<option value="N"' + (act==='N'?' selected':'') + '>N</option>'
      + '</select>';

    tds[4].innerHTML =
      '<button type="button" class="btn edit js-save">저장</button>'
      + '<button type="button" class="btn js-cancel">취소</button>'
      + '<form method="post" action="'+ CTX +'/master" class="inline js-del-form" onsubmit="return confirm(\'정말 삭제하시겠습니까?\')">'
      + '  <input type="hidden" name="act" value="code.detail.delete"/>'
      + '  <input type="hidden" name="detailCode" value="'+ dcd.replace(/"/g,'&quot;') +'"/>'
      + '  <button class="btn danger" type="submit">삭제</button>'
      + '</form>';
  });

  body.addEventListener('click', function(e){
    var saveBtn = e.target.closest('.js-save');
    if(!saveBtn) return;

    var tr = saveBtn.closest('tr');
    var tds = tr.querySelectorAll('td');
    var dcd = (tds[1].textContent||'').trim();
    var name= tr.querySelector('textarea[name=codeDname]').value.trim();
    var act = tr.querySelector('select[name=active]').value;

    var fd = new FormData();
    fd.append('act','code.detail.rename');
    fd.append('detailCode', dcd);
    fd.append('codeDname', name);
    fd.append('active', act);

    fetch(CTX + '/master', { method:'POST', body:fd, credentials:'same-origin' })
      .then(function(resp){
        if(!resp.ok) throw new Error('ERR');
        tds[2].textContent = name;
        tds[3].textContent = act;
        tds[4].innerHTML =
          '<form method="get" action="'+ CTX +'/master" class="js-edit-form">'
          + '  <input type="hidden" name="act" value="code.detail.list"/>'
          + '  <input type="hidden" name="edit" value="'+ dcd.replace(/"/g,'&quot;') +'"/>'
          + '  <button class="btn edit" type="submit">수정</button>'
          + '</form>'
          + '<form method="post" action="'+ CTX +'/master" onsubmit="return confirm(\'정말 삭제하시겠습니까?\')">'
          + '  <input type="hidden" name="act" value="code.detail.delete"/>'
          + '  <input type="hidden" name="detailCode" value="'+ dcd.replace(/"/g,'&quot;') +'"/>'
          + '  <button class="btn danger" type="submit">삭제</button>'
          + '  </form>';
        tr.dataset.editing='0';
        alert('저장 완료');
      })
      .catch(function(){ alert('저장 실패'); });
  });

  body.addEventListener('click', function(e){
    var cancelBtn = e.target.closest('.js-cancel');
    if(!cancelBtn) return;
    var tr = cancelBtn.closest('tr');
    if(tr && tr.dataset.origHtml){
      tr.innerHTML = tr.dataset.origHtml;
      tr.dataset.editing='0';
    }
  });
})();
</script>
</body>
</html>
