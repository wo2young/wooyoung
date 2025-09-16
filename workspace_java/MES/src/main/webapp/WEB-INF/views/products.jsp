<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>제품 관리</title>
<style>
:root {
  --bg: #0a0f1a; --card: #111827; --line: #1f2b45; --line2: #4b5563;
  --text: #e6ebff; --muted: #a7b0c5; --input-bg: #1a202c;
  --primary: #f59e0b; --primary-hover: #d97706;
}

/* ✅ 스크롤바 완전 숨김 + 여백 0 */
html{overflow:hidden;height:100%;}
body{
  overflow:auto;height:100%;
  -ms-overflow-style:none; scrollbar-width:none;
  margin:0; padding:0; min-height:100dvh;
  background: var(--bg); color: var(--text);
  font-family: Segoe UI, Pretendard, 'Noto Sans KR', 'Apple SD Gothic Neo', Arial, sans-serif;
  font-size: 14px;
}
body::-webkit-scrollbar{display:none;}
*,*::before,*::after{box-sizing:border-box;}

.wrap { max-width: 1200px; margin: 28px auto; padding: 20px; }
h2, h3 { margin: 0 0 12px; }

/* 카드 */
.card {
  background: var(--card); border: 1px solid var(--line);
  border-radius: 12px; padding: 16px; box-shadow: 0 6px 18px rgba(0,0,0,.25);
  margin-bottom: 20px;
}

/* input/select */
input[type=text], input[type=number], select {
  background: var(--input-bg); border: 1px solid var(--line2);
  border-radius: 8px; color: var(--text); padding: 8px 10px; font-size: 14px;
}
input::placeholder { color: #6b7280; }
select:focus, input:focus { outline: none; border-color: var(--primary); box-shadow: 0 0 0 2px rgba(245,158,11,.25); }

/* 버튼 공통 */
.btn { min-width: 60px; height: 32px; padding: 0 12px; border-radius: 6px; font-size: 14px; font-weight: 600; cursor: pointer; transition: background .2s, border-color .2s; border: none; }
.btn.edit { background: #1f2937; color: #fff; font-size: 13px; padding: 0 8px; border-radius: 4px; }
.btn.edit:hover { background: #374151; }
.btn.danger { background: #dc2626; color: #fff; }
.btn.danger:hover { background: #ef4444; }

/* 등록폼 */
.add-form {
  display: grid; grid-template-columns: repeat(2,1fr);
  gap: 20px 40px; align-items: center;
}
.add-form label { display: flex; flex-direction: column; font-weight: 600; font-size: 14px; color: #e6ebff; gap: 6px; }
.add-form input, .add-form select { height: 32px; max-width: 520px; padding: 4px 8px; border-radius: 6px; border: 1px solid #4b5563; background: #1a202c; color: #e6ebff; font-size: 14px; }
.add-form button[type=submit]{
  grid-column: span 2; justify-self: center; width: 240px; height: 40px; border-radius: 10px;
  background: #fff; color: #111827; border: 1px solid #d1d5db; font-size: 16px; font-weight: 700;
}
.add-form button[type=submit]:hover{ background:#d97706; color:#111827; }

/* 테이블: 폭 고정 */
.table-wrap { margin-top: 12px; }
table {
  width: 100%; border-collapse: collapse; background: var(--card);
  border: 1px solid var(--line); border-radius: 12px; overflow: hidden; table-layout: fixed;
}
thead th { background: #152033; color: #cbd5e1; padding: 12px 14px; border-bottom: 1px solid var(--line); }
tbody td { padding: 12px 14px; text-align: center; border-bottom: 1px solid var(--line); }
tbody tr:hover { background: #0f1c2d; }
tbody tr:last-child td { border-bottom: none; }

/* 컬럼 고정폭 */
.col-id{width:90px;} .col-name{width:160px;} .col-lot{width:120px;}
.col-life{width:110px;} .col-kind{width:90px;} .col-detail{width:130px;}
.col-spec{width:auto;} .col-unit{width:90px;} .col-act{width:160px;}

/* 긴 텍스트 처리 */
.td-name{ overflow:hidden; text-overflow:ellipsis; white-space:nowrap; }
.td-spec{ white-space:normal; overflow-wrap:anywhere; word-break:break-word; }

/* 작업 버튼줄 */
.actions { display: flex; gap: 6px; justify-content: center; }

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

<script>
function confirmDelete(id){
  if(confirm("삭제하시겠습니까?")){
    location.href="master?act=item.delete&itemId=" + id;
  }
}
function toggleDetailCodes(){
  var kind=document.getElementById("kindInput").value;
  var opts=document.querySelectorAll("#detailSelect option");
  opts.forEach(o=>{ o.style.display=(o.dataset.kind===kind)?"block":"none"; });
  var first=document.querySelector("#detailSelect option[data-kind='"+kind+"']");
  if(first) first.selected=true;
}
window.addEventListener('load', toggleDetailCodes);

/* ===== 페이징 ===== */
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
  const tbody = document.querySelector('table tbody');
  if(!tbody) return;
  const rows = Array.prototype.slice.call(tbody.querySelectorAll('tr'));
  const pager = document.getElementById('pager');
  const PAGE_SIZE = 5; // 한 페이지 행 수

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
</head>
<body>
  <jsp:include page="/WEB-INF/includes/2header.jsp">
    <jsp:param name="activeNav" value="products"/>
  </jsp:include>

  <div class="wrap">
    <h2>제품 관리</h2>

    <!-- 신규 등록 -->
    <div class="card">
      <h3>신규 등록</h3>
      <form method="post" action="master" class="add-form">
        <input type="hidden" name="act" value="item.add"/>
        <label>ID <input type="number" name="item_id" required></label>
        <label>이름 <input type="text" name="item_name" required></label>
        <label>LOT <input type="text" name="lot_code"></label>
        <label>유통기한 <input type="number" name="self_life_day" min="0"></label>
        <label>종류
          <select name="kind" id="kindInput" onchange="toggleDetailCodes()">
            <option value="FG">FG</option><option value="RM">RM</option>
          </select>
        </label>
        <label>상세코드
          <select name="detail_code" id="detailSelect">
            <c:forEach var="d" items="${pcdDetails}">
              <option value="${d.detail_code}" data-kind="FG">${d.codeDname}</option>
            </c:forEach>
            <c:forEach var="d" items="${fcdDetails}">
              <option value="${d.detail_code}" data-kind="RM">${d.codeDname}</option>
            </c:forEach>
          </select>
        </label>
        <label>규격 <input type="text" name="item_spec"></label>
        <label>단위 <input type="text" name="unit"></label>
        <button type="submit" class="btn primary-btn">등록</button>
      </form>
    </div>

    <!-- 목록 -->
    <div class="card">
      <h3>목록</h3>
      <div class="table-wrap">
        <table>
          <colgroup>
            <col class="col-id"/><col class="col-name"/><col class="col-lot"/><col class="col-life"/><col class="col-kind"/><col class="col-detail"/><col class="col-spec"/><col class="col-unit"/><col class="col-act"/>
          </colgroup>
          <thead>
            <tr><th>ID</th><th>이름</th><th>LOT</th><th>유통기한</th><th>종류</th><th>상세코드</th><th>규격</th><th>단위</th><th>작업</th></tr>
          </thead>
          <tbody>
            <c:forEach var="it" items="${items}">
              <tr>
                <td>${it.item_id}</td>
                <td class="td-name">${it.item_name}</td>
                <td>${it.lot_code}</td>
                <td>${it.self_life_day}</td>
                <td>${it.kind}</td>
                <td>${it.detail_code}</td>
                <td class="td-spec">${it.item_spec}</td>
                <td>${it.unit}</td>
                <td class="actions">
                  <form method="get" action="master">
                    <input type="hidden" name="act" value="item.edit"/>
                    <input type="hidden" name="itemId" value="${it.item_id}"/>
                    <button type="submit" class="btn edit">수정</button>
                  </form>
                  <button type="button" class="btn danger" onclick="confirmDelete(${it.item_id})">삭제</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="pager" id="pager"></div>
    </div>
  </div>
</body>
</html>
