<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<jsp:include page="/WEB-INF/includes/2header.jsp">
  <jsp:param name="activeNav" value="bom"/>
</jsp:include>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8"/>
<title>BOM 관리</title>
<style>
:root{
  --bg:#0a0f1a; --card:#111827; --line:#374151; --text:#e6ebff;
  --primary:#f59e0b; --primary-hover:#d97706;
  --danger:#dc2626; --danger-hover:#ef4444;
  --edit:#1f2937; --edit-hover:#374151;
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

.card{
  background:var(--card);padding:20px;border-radius:10px;box-shadow:0 6px 18px rgba(0,0,0,.4);
  margin:20px auto;max-width:1200px;
}

/* 테이블 고정 */
table{
  width:100%;border-collapse:collapse;background:var(--card);border:1px solid var(--line);
  border-radius:12px;overflow:hidden;table-layout:fixed;
}
thead th{background:#152033;color:#cbd5e1;padding:12px 14px;border-bottom:1px solid var(--line);}
tbody td{padding:12px 14px;text-align:center;border-bottom:1px solid var(--line);}
tbody tr:hover{background:#0f1c2d;}

/* 컬럼 폭 */
.col-id{width:110px;}
.col-parent{width:32%;}
.col-child{width:32%;}
.col-qty{width:120px;}
.col-act{width:180px;}

/* 긴 텍스트 처리 */
.td-parent,.td-child{
  text-align:left; white-space:normal; overflow-wrap:anywhere; word-break:break-word;
}
.td-qty{white-space:nowrap;}
.td-actions{white-space:nowrap;}

/* 버튼 */
.btn{min-width:70px;height:32px;border:none;border-radius:6px;font-size:13px;font-weight:600;cursor:pointer;
  display:inline-flex;align-items:center;justify-content:center;transition:background .2s;}
.btn.edit{background:var(--edit);color:#fff}.btn.edit:hover{background:var(--edit-hover)}
.btn.danger{background:var(--danger);color:#fff}.btn.danger:hover{background:var(--danger-hover)}
.btn.save{background:var(--primary);color:#111827}.btn.save:hover{background:var(--primary-hover)}
.btn.cancel{background:var(--line);color:#fff}.btn.cancel:hover{background:var(--edit-hover)}
.btn-row{display:flex;gap:8px;justify-content:center;align-items:center;}
.btn-row form{margin:0;}

/* 인라인 입력 */
input.inline, textarea.inline{
  width:95%;background:var(--card);color:var(--text);border:1px solid var(--line);
  border-radius:6px;padding:6px 8px;font-size:14px;
}
input.inline:focus, textarea.inline:focus{
  outline:none;border-color:var(--primary);box-shadow:0 0 0 2px rgba(245,158,11,.25);
}

/* 페이징 */
.pager{
  display:flex;justify-content:center;align-items:center;gap:8px;
  width:100%;min-height:48px;margin-top:12px;
}
.pager .pg{
  min-width:34px;height:34px;border-radius:10px;font-weight:700;
  border:1px solid var(--line);background:#0e1a2b;color:#cbd5e1;cursor:pointer;
}
.pager .pg.active{background:var(--primary);color:#111827;}
.pager .pg:hover{background:#14263e;}
</style>
</head>

<body>
<main class="card">
  <h1>BOM 관리</h1>

  <div class="table-wrap">
    <table>
      <colgroup>
        <col class="col-id"/><col class="col-parent"/><col class="col-child"/><col class="col-qty"/><col class="col-act"/>
      </colgroup>
      <thead>
        <tr>
          <th>BOM_ID</th><th>완제품</th><th>원재료</th><th>수량</th><th>작업</th>
        </tr>
      </thead>
      <tbody id="bom-body">
        <c:if test="${empty boms}">
          <tr><td colspan="5">데이터가 없습니다.</td></tr>
        </c:if>

        <c:forEach var="v" items="${boms}">
          <tr data-id="${v.bomId}">
            <td>${v.bomId}</td>
            <td class="td-parent">${fn:escapeXml(v.parentName)}</td>
            <td class="td-child">${fn:escapeXml(v.childName)}</td>
            <td class="td-qty">
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
  </div>
</main>

<script>
var CTX = '${ctx}';
function esc(s){ if(s==null) return ''; return String(s).replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g,'&quot;').replace(/'/g,'&#39;'); }

/* 인라인 편집 */
document.addEventListener('click', function(e){
  if(e.target.classList.contains('js-edit')){
    var tr = e.target.closest('tr');
    if(!tr || tr.getAttribute('data-editing') === '1') return;
    tr.setAttribute('data-editing','1');
    tr.setAttribute('data-orig', tr.innerHTML);

    var bomId  = tr.getAttribute('data-id') || '';
    var parent = ((tr.querySelector('.td-parent')||{}).textContent||'').trim();
    var child  = ((tr.querySelector('.td-child') ||{}).textContent||'').trim();
    var qtyTxt = ((tr.querySelector('.td-qty .num')||{}).textContent||'').trim();
    var qty    = qtyTxt.replace(/,/g,'');

    var formId = 'f_bom_' + bomId;

    var tdP = tr.querySelector('.td-parent');
    if(tdP) tdP.innerHTML = '<textarea class="inline" name="parent" rows="1" form="'+formId+'">'+ esc(parent) +'</textarea>';

    var tdC = tr.querySelector('.td-child');
    if(tdC) tdC.innerHTML = '<textarea class="inline" name="child" rows="1" form="'+formId+'">'+ esc(child) +'</textarea>';

    var tdQ = tr.querySelector('.td-qty');
    if(tdQ) tdQ.innerHTML = '<input class="inline" type="number" step="0.000001" name="quantity" value="'+ esc(qty) +'" form="'+formId+'">';

    var act = tr.querySelector('td:last-child');
    if(act){
      act.innerHTML =
        '<div class="btn-row">'
        + '  <form method="post" action="' + CTX + '/master" id="'+formId+'">'
        + '    <input type="hidden" name="act" value="bom.update">'
        + '    <input type="hidden" name="bom_id" value="' + esc(bomId) + '">'
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

/* 페이징 */
function buildPager($container, page, pages, go){
  $container.innerHTML = '';
  const mk = (txt, p, dis, act)=>{
    const b=document.createElement('button');
    b.type='button'; b.className='pg'+(act?' active':''); b.textContent=txt;
    if(dis) b.disabled=true;
    b.onclick=()=>go(p);
    return b;
  };
  $container.appendChild(mk('<', Math.max(1,page-1), page===1, false));
  for(let i=1;i<=pages;i++) $container.appendChild(mk(String(i), i, false, i===page));
  $container.appendChild(mk('>', Math.min(pages,page+1), page===pages, false));
}

window.addEventListener('DOMContentLoaded', function(){
  const tbody = document.getElementById('bom-body');
  if(!tbody) return;

  const allRows = Array.prototype.slice.call(tbody.querySelectorAll('tr'));
  const realRows = allRows.filter(function(tr){ return tr.querySelector('td[colspan]') == null; });
  const rows = (realRows.length ? realRows : allRows);

  const pager = document.getElementById('pager');
  const PAGE_SIZE = 10;

  const state = { page:1, size:PAGE_SIZE, total:rows.length };

  function render(){
    const start = (state.page-1)*state.size;
    const end   = Math.min(start+state.size, state.total);
    rows.forEach((tr, i)=>{ tr.style.display = (i>=start && i<end) ? '' : 'none'; });

    const pages = Math.max(1, Math.ceil(state.total/state.size));
    buildPager(pager, state.page, pages, (p)=>{ state.page=p; render(); });
  }
  render();
});
</script>
</body>
</html>
