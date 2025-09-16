<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<jsp:include page="/WEB-INF/includes/2header.jsp">
  <jsp:param name="activeNav" value="bom" />
</jsp:include>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>BOM 관리</title>
<style>
:root{
  --bg:#0a0f1a;
  --card:#111827;
  --line:#374151;
  --text:#e6ebff;
  --primary:#f59e0b;
  --primary-hover:#d97706;
  --danger:#dc2626;
  --danger-hover:#ef4444;
  --edit:#1f2937;
  --edit-hover:#374151;
}

/* 레이아웃/타이포 */
html{ overflow:hidden; height:100%; }
body{
  overflow:auto; height:100%;
  -ms-overflow-style:none; scrollbar-width:none;
  margin:0; padding:0; min-height:100vh;
  background:var(--bg); color:var(--text);
  font-family:Segoe UI,Pretendard,'Noto Sans KR',Arial,sans-serif;
  font-size:14px;
}
body::-webkit-scrollbar{ display:none; }
*,*::before,*::after{ box-sizing:border-box; }

.card{
  background:var(--card); padding:20px; border-radius:10px;
  box-shadow:0 6px 18px rgba(0,0,0,.4);
  margin:20px auto; max-width:1200px;
}

h1{ margin:0 0 12px 2px; font-size:20px; }

/* 툴바 */
.toolbar{
  display:flex; gap:8px; flex-wrap:wrap; align-items:center; margin:8px 0 14px 0;
}
.sel,.ipt{
  height:36px; border:1px solid var(--line);
  background:#0e1a2b; color:var(--text);
  border-radius:8px; padding:0 12px; min-width:160px;
}
.ipt{ min-width:260px; }

.btn{
  min-width:70px; height:36px; border:none; border-radius:8px;
  font-size:13px; font-weight:600; cursor:pointer;
  display:inline-flex; align-items:center; justify-content:center;
  transition:background .2s;
}
.btn.primary{ background:var(--primary); color:#111827; }
.btn.primary:hover{ background:var(--primary-hover); }
.btn.ghost{ background:#0e1a2b; color:#cbd5e1; border:1px solid var(--line); }
.btn.ghost:hover{ background:#14263e; }

/* 테이블 */
.table-wrap{ width:100%; }
table{
  width:100%; border-collapse:collapse; background:var(--card);
  border:1px solid var(--line); border-radius:12px; overflow:hidden; table-layout:fixed;
}
thead th{
  background:#152033; color:#cbd5e1; padding:12px 14px; border-bottom:1px solid var(--line);
}
tbody td{
  padding:12px 14px; text-align:center; border-bottom:1px solid var(--line);
}
tbody tr:hover{ background:#0f1c2d; }

.col-id{ width:110px; }
.col-parent{ width:32%; }
.col-child{ width:32%; }
.col-qty{ width:120px; }
.col-act{ width:180px; }

.td-parent,.td-child{ text-align:left; white-space:normal; overflow-wrap:anywhere; word-break:break-word; }
.td-qty{ white-space:nowrap; }
.td-actions{ white-space:nowrap; }

.btn.edit{ background:var(--edit); color:#fff; }
.btn.edit:hover{ background:var(--edit-hover); }
.btn.danger{ background:var(--danger); color:#fff; }
.btn.danger:hover{ background:var(--danger-hover); }
.btn.save{ background:var(--primary); color:#111827; }
.btn.save:hover{ background:var(--primary-hover); }
.btn.cancel{ background:var(--line); color:#fff; }
.btn.cancel:hover{ background:var(--edit-hover); }

.btn-row{ display:flex; gap:8px; justify-content:center; align-items:center; }
.btn-row form{ margin:0; }

input.inline, textarea.inline{
  width:95%; background:var(--card); color:var(--text);
  border:1px solid var(--line); border-radius:6px; padding:6px 8px; font-size:14px;
}
input.inline:focus, textarea.inline:focus{
  outline:none; border-color:var(--primary); box-shadow:0 0 0 2px rgba(245,158,11,.25);
}

/* 추가 폼 (정리 버전만 사용) */
.addbox{
  display:grid;
  grid-template-columns:110px 1fr 110px 1fr; /* [라벨][입력] × 2세트 */
  gap:12px 16px; align-items:center; padding:16px;
  border:1px dashed var(--line); border-radius:10px; margin-top:14px; background:#0e1a2b;
}
.addbox .lbl{ font-weight:700; justify-self:end; padding-right:6px; }
.addbox .field .sel, .addbox .field .ipt{ width:100%; min-width:0; }
.addbox .actions{
  grid-column:1 / -1; display:flex; gap:10px; justify-content:flex-end;
}
@media (max-width:980px){
  .addbox{ grid-template-columns:110px 1fr; }
  .addbox .actions{ grid-column:1 / -1; }
}

/* 페이징 */
.pager{
  display:flex; justify-content:center; align-items:center; gap:8px;
  width:100%; min-height:48px; margin-top:12px;
}
.pager .pg{
  min-width:34px; height:34px; border-radius:10px; font-weight:700;
  border:1px solid var(--line); background:#0e1a2b; color:#cbd5e1; cursor:pointer;
}
.pager .pg.active{ background:var(--primary); color:#111827; }
.pager .pg:hover{ background:#14263e; }
</style>
</head>

<body>
  <main class="card">
    <h1>BOM 관리</h1>

    <!-- 툴바: 완제품 필터 + 검색어 + 초기화 -->
    <div class="toolbar">
      <select id="filterParent" class="sel" aria-label="완제품 필터">
        <option value="">완제품: 전체</option>
        <c:forEach var="p" items="${products}">
          <option value="${fn:escapeXml(p.itemName)}">${fn:escapeXml(p.itemName)}</option>
        </c:forEach>
      </select>

      <input id="q" class="ipt" type="search" placeholder="완제품/원재료/코드/단위/수량 검색"/>
      <button type="button" id="btnSearch" class="btn primary">검색</button>
      <button type="button" id="btnReset"  class="btn ghost">초기화</button>
    </div>

    <div class="table-wrap">
      <table>
        <colgroup>
          <col class="col-id"/><col class="col-parent"/><col class="col-child"/><col class="col-qty"/><col class="col-act"/>
        </colgroup>
        <thead>
          <tr><th>BOM_ID</th><th>완제품</th><th>원재료</th><th>수량</th><th>작업</th></tr>
        </thead>
        <tbody id="bom-body">
          <c:if test="${empty boms}">
            <tr><td colspan="5">데이터가 없습니다.</td></tr>
          </c:if>

          <c:forEach var="v" items="${boms}">
            <tr data-id="${v.bomId}">
              <td>${v.bomId}</td>
              <td class="td-parent" data-parent="${fn:escapeXml(v.parentName)}">${fn:escapeXml(v.parentName)}</td>
              <td class="td-child"  data-child="${fn:escapeXml(v.childName)}" data-unit="${fn:escapeXml(v.childUnit)}">${fn:escapeXml(v.childName)}</td>
              <td class="td-qty"    data-qty="${v.quantity}">
                <span class="num"><fmt:formatNumber value="${v.quantity}" pattern="#,##0.######"/></span>
                <c:if test="${not empty v.childUnit}"><span class="unit"> ${v.childUnit}</span></c:if>
              </td>
              <td class="td-actions">
                <div class="btn-row">
                  <button class="btn edit js-edit" type="button">수정</button>
                  <form method="post" action="${ctx}/master" onsubmit="return confirm('삭제할까요?')">
                    <input type="hidden" name="act" value="bom.delete"/>
                    <input type="hidden" name="bom_id" value="${v.bomId}"/>
                    <button class="btn danger" type="submit">삭제</button>
                  </form>
                </div>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <div class="pager" id="pager"></div>

      <!-- 추가 폼 -->
      <form class="addbox" method="post" action="${ctx}/master" id="form-insert">
        <input type="hidden" name="act" value="bom.insert"/>

        <div class="lbl">완제품</div>
        <div class="field">
          <select name="parent_item_id" id="ins-parent" class="sel" required>
            <option value="">선택</option>
            <c:forEach var="p" items="${products}">
              <option value="${p.itemId}">
                ${fn:escapeXml(p.itemName)}
                <c:if test="${not empty p.itemCode}"> (${fn:escapeXml(p.itemCode)})</c:if>
              </option>
            </c:forEach>
          </select>
        </div>

        <div class="lbl">원재료</div>
        <div class="field">
          <select name="child_item_id" id="ins-child" class="sel" required>
            <option value="">선택</option>
            <c:forEach var="m" items="${materials}">
              <option value="${m.itemId}">
                ${fn:escapeXml(m.itemName)}
                <c:if test="${not empty m.unitName}"> - ${fn:escapeXml(m.unitName)}</c:if>
                <c:if test="${not empty m.itemCode}"> (${fn:escapeXml(m.itemCode)})</c:if>
              </option>
            </c:forEach>
          </select>
        </div>

        <div class="lbl">수량</div>
        <div class="field">
          <input type="number" step="0.000001" min="0" name="quantity" id="ins-qty" class="ipt" placeholder="예) 1, 0.5, 50 등" required/>
        </div>

        <div class="actions">
          <button type="submit" class="btn primary" id="btn-insert">추가</button>
          <button type="button" class="btn ghost" id="btn-insert-reset">초기화</button>
        </div>
      </form>
    </div>
  </main>

<script>
var CTX='${ctx}';
function esc(s){ if(s==null) return ''; return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;').replace(/'/g,'&#39;'); }

/* 인라인 편집 */
document.addEventListener('click', function(e){
  if(e.target.classList.contains('js-edit')){
    var tr = e.target.closest('tr');
    if(!tr || tr.getAttribute('data-editing')==='1') return;
    tr.setAttribute('data-editing','1');
    tr.setAttribute('data-orig', tr.innerHTML);

    var bomId  = tr.getAttribute('data-id')||'';
    var parent = (tr.querySelector('.td-parent')?.textContent||'').trim();
    var child  = (tr.querySelector('.td-child') ?.textContent||'').trim();
    var qtyTxt = (tr.querySelector('.td-qty .num')?.textContent||'').trim();
    var qty    = qtyTxt.replace(/,/g,'');

    var formId = 'f_bom_'+bomId;

    var tdP = tr.querySelector('.td-parent');
    if(tdP) tdP.innerHTML = '<textarea class="inline" name="parent" rows="1" form="'+formId+'">'+esc(parent)+'</textarea>';

    var tdC = tr.querySelector('.td-child');
    if(tdC) tdC.innerHTML = '<textarea class="inline" name="child" rows="1" form="'+formId+'">'+esc(child)+'</textarea>';

    var tdQ = tr.querySelector('.td-qty');
    if(tdQ) tdQ.innerHTML = '<input class="inline" type="number" step="0.000001" name="quantity" value="'+esc(qty)+'" form="'+formId+'">';

    var act = tr.querySelector('td:last-child');
    if(act){
      act.innerHTML =
        '<div class="btn-row">'
        + '  <form method="post" action="'+CTX+'/master" id="'+formId+'">'
        + '    <input type="hidden" name="act" value="bom.update">'
        + '    <input type="hidden" name="bom_id" value="'+esc(bomId)+'">'
        + '    <button type="submit" class="btn save">저장</button>'
        + '  </form>'
        + '  <button type="button" class="btn cancel js-cancel">취소</button>'
        + '</div>';
    }
  }

  if(e.target.classList.contains('js-cancel')){
    var tr = e.target.closest('tr');
    if(tr && tr.hasAttribute('data-orig')){
      tr.innerHTML = tr.getAttribute('data-orig');
      tr.setAttribute('data-editing','0');
    }
  }
});

/* 페이징 + 필터링 */
(function(){
  const tbody = document.getElementById('bom-body'); if(!tbody) return;
  const allRows  = Array.from(tbody.querySelectorAll('tr'));
  const realRows = allRows.filter(tr => !tr.querySelector('td[colspan]'));
  const rows     = realRows.length ? realRows : allRows;

  const pager = document.getElementById('pager');
  const PAGE_SIZE = 10;
  const state = { page:1, size:PAGE_SIZE, filtered: rows.slice() };

  function buildPager($c, page, pages, go){
    $c.innerHTML='';
    const mk=(txt,p,dis,act)=>{ const b=document.createElement('button');
      b.type='button'; b.className='pg'+(act?' active':''); b.textContent=txt;
      if(dis) b.disabled=true; b.onclick=()=>go(p); return b; };
    $c.appendChild(mk('<', Math.max(1,page-1), page===1, false));
    for(let i=1;i<=pages;i++) $c.appendChild(mk(String(i), i, false, i===page));
    $c.appendChild(mk('>', Math.min(pages,page+1), page===pages, false));
  }

  function render(){
    rows.forEach(tr => tr.style.display='none');
    const start=(state.page-1)*state.size, end=Math.min(start+state.size, state.filtered.length);
    for(let i=start;i<end;i++) state.filtered[i].style.display='';
    const pages = Math.max(1, Math.ceil(state.filtered.length/state.size));
    buildPager(pager, state.page, pages, p=>{ state.page=p; render(); });
  }

  function norm(s){ return (s||'').toString().toLowerCase(); }
  function applyFilter(){
    const sel = document.getElementById('filterParent');
    const q   = document.getElementById('q');
    const parentVal = norm(sel?.value);
    const kw  = norm(q?.value);

    state.filtered = rows.filter(tr=>{
      const p = norm(tr.querySelector('.td-parent')?.dataset.parent);
      const c = norm(tr.querySelector('.td-child') ?.dataset.child);
      const u = norm(tr.querySelector('.td-child') ?.dataset.unit);
      const qty = norm(tr.querySelector('.td-qty')   ?.dataset.qty);
      if(parentVal && p !== parentVal) return false;
      if(!kw) return true;
      return (p.includes(kw) || c.includes(kw) || u.includes(kw) || qty.includes(kw));
    });
    state.page=1; render();
  }

  // 완제품 옵션 미제공 시 테이블에서 생성
  (function hydrate(){
    const sel = document.getElementById('filterParent'); if(!sel) return;
    if(sel.options.length>1) return;
    const set = new Set();
    rows.forEach(tr=>{ const name=tr.querySelector('.td-parent')?.dataset.parent||''; if(name) set.add(name);});
    Array.from(set).sort().forEach(name=>{ const o=document.createElement('option'); o.value=o.textContent=name; sel.appendChild(o); });
  })();

  document.getElementById('btnSearch')?.addEventListener('click', applyFilter);
  document.getElementById('btnReset') ?.addEventListener('click', ()=>{
    const sel=document.getElementById('filterParent'); const q=document.getElementById('q');
    if(sel) sel.value=''; if(q){ q.value=''; q.focus(); } applyFilter();
  });
  document.getElementById('q')?.addEventListener('keydown', e=>{ if(e.key==='Enter') applyFilter(); });

  applyFilter();
})();

/* 추가 폼 유틸 */
(function(){
  const f=document.getElementById('form-insert'); if(!f) return;
  const parentSel=document.getElementById('ins-parent');
  const childSel =document.getElementById('ins-child');
  const qtyIpt   =document.getElementById('ins-qty');
  const btnSubmit=document.getElementById('btn-insert');

  const hasLists = (parentSel && parentSel.options.length>1 && childSel && childSel.options.length>1);
  if(!hasLists && btnSubmit) btnSubmit.disabled=true;

  document.getElementById('btn-insert-reset')?.addEventListener('click', ()=>{
    if(parentSel) parentSel.value=''; if(childSel) childSel.value='';
    if(qtyIpt){ qtyIpt.value=''; qtyIpt.focus(); }
  });

  f.addEventListener('submit', function(e){
    if(!parentSel.value || !childSel.value || !qtyIpt.value){
      e.preventDefault(); alert('완제품, 원재료, 수량을 모두 입력하세요.'); return;
    }
    if(Number(qtyIpt.value) < 0){
      e.preventDefault(); alert('수량은 0 이상이어야 합니다.'); return;
    }
  });
})();
</script>
</body>
</html>
