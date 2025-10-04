<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="wrap">

  <!-- ===== 메시지 처리 ===== -->
  <c:if test="${param.msg == 'create_ok'}">
    <script>
      alert("지시 생성 완료");
      if(window.history.replaceState){
        const url = new URL(window.location);
        url.searchParams.delete('msg');
        window.history.replaceState({}, document.title, url.pathname + url.search);
      }
    </script>
  </c:if>

  <c:if test="${param.msg == 'upd_ok'}">
    <script>
      alert("지시 수정 완료");
      if(window.history.replaceState){
        const url = new URL(window.location);
        url.searchParams.delete('msg');
        window.history.replaceState({}, document.title, url.pathname + url.search);
      }
    </script>
  </c:if>

  <c:if test="${param.msg == 'create_fail' || param.msg == 'upd_fail' || param.msg == 'del_fail'}">
    <script>
      alert("재고 수량이 부족합니다.");
      if(window.history.replaceState){
        const url = new URL(window.location);
        url.searchParams.delete('msg');
        window.history.replaceState({}, document.title, url.pathname + url.search);
      }
    </script>
  </c:if>

  <!-- ===== 생산목표 목록 ===== -->
  <h2 class="page-title">생산목표 목록</h2>
  <form method="get" action="${ctx}/order" class="search-bar">
    <input type="hidden" name="act" value="order.list"/>
    <label>완제품:</label>
    <select name="targetItemId" onchange="this.form.submit()">
      <option value="0" ${targetItemSel == 0 ? 'selected' : ''}>전체</option>
      <c:forEach var="it" items="${itemList}">
        <option value="${it.item_id}" ${targetItemSel == it.item_id ? 'selected' : ''}>${it.item_name}</option>
      </c:forEach>
    </select>
  </form>

  <div class="table-wrap">
    <table class="data-table">
      <thead>
        <tr>
          <th>제품명</th><th>목표 수량</th><th>생산된 수량</th>
          <th>기간</th><th>상태</th><th>지시생성</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="t" items="${targets}">
          <tr>
            <td>${t.item_name}</td>
            <td>${t.target_quantity}</td>
            <td>${t.results_sum}</td>
            <td><fmt:formatDate value="${t.period_start}" pattern="yyyy-MM-dd"/> ~ <fmt:formatDate value="${t.period_end}" pattern="yyyy-MM-dd"/></td>
            <td><span class="status">${t.status}</span></td>
            <td><button type="button" class="btn btn-sm" onclick="openCreateModal('${t.target_id}')">지시 생성</button></td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- 생산목표 페이징 -->
  <c:if test="${totalTargetPages >= 1}">
    <div class="pagination">
      <c:set var="tWindow" value="5"/>
      <c:set var="tStart" value="${targetPage-2}"/>
      <c:if test="${tStart<1}"><c:set var="tStart" value="1"/></c:if>
      <c:set var="tEnd" value="${tStart+tWindow-1}"/>
      <c:if test="${tEnd>totalTargetPages}"><c:set var="tEnd" value="${totalTargetPages}"/></c:if>

      <a class="page-link ${targetPage==1?'disabled':''}"
         href="${ctx}/order?act=order.list&targetPage=1&targetItemId=${targetItemSel}&page=${page}&itemId=${itemSel}&status=${statusSel}">&laquo;</a>

      <c:forEach var="p" begin="${tStart}" end="${tEnd}">
        <a class="page-link ${p==targetPage?'active':''}"
           href="${ctx}/order?act=order.list&targetPage=${p}&targetItemId=${targetItemSel}&page=${page}&itemId=${itemSel}&status=${statusSel}">${p}</a>
      </c:forEach>

      <a class="page-link ${targetPage==totalTargetPages?'disabled':''}"
         href="${ctx}/order?act=order.list&targetPage=${totalTargetPages}&targetItemId=${targetItemSel}&page=${page}&itemId=${itemSel}&status=${statusSel}">&raquo;</a>
    </div>
  </c:if>


  <!-- ===== 생산지시 목록 ===== -->
  <h2 class="page-title">생산지시 목록</h2>
  <form method="get" action="${ctx}/order" class="search-bar">
    <input type="hidden" name="act" value="order.list"/>
    <input type="hidden" name="targetPage" value="${targetPage}"/>
    <input type="hidden" name="targetItemId" value="${targetItemSel}"/>

    <label>완제품:</label>
    <select name="itemId">
      <option value="0" ${itemSel == 0 ? 'selected' : ''}>전체</option>
      <c:forEach var="it" items="${itemList}">
        <option value="${it.item_id}" ${itemSel == it.item_id ? 'selected' : ''}>${it.item_name}</option>
      </c:forEach>
    </select>

    <label>상태:</label>
    <select name="status">
      <option value="ALL" ${statusSel eq 'ALL' ? 'selected' : ''}>전체</option>
      <option value="PLANNED" ${statusSel eq 'PLANNED' ? 'selected' : ''}>계획중</option>
      <option value="DONE" ${statusSel eq 'DONE' ? 'selected' : ''}>완료</option>
    </select>

    <button type="submit" class="btn btn-primary">조회</button>
  </form>

  <div class="table-wrap">
    <table class="data-table">
      <thead>
        <tr>
          <th>공정보기</th><th>제품명</th><th>지시 수량</th>
          <th>납기일</th><th>상태</th><th>등록자</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="o" items="${orders}">
          <tr>
            <td><button type="button" class="btn btn-sm" onclick="openRoutingModal(${o.order_id})">공정보기</button></td>
            <td>${o.target_name}</td>
            <td>${o.quantity}</td>
            <td><fmt:formatDate value="${o.due_date}" pattern="yyyy-MM-dd"/></td>
            <td>${o.status}</td>
            <td>${o.created_by_name}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>

  <!-- 생산지시 페이징 -->
  <c:if test="${totalPages >= 1}">
    <div class="pagination">
      <c:set var="oWindow" value="5"/>
      <c:set var="oStart" value="${page-2}"/>
      <c:if test="${oStart<1}"><c:set var="oStart" value="1"/></c:if>
      <c:set var="oEnd" value="${oStart+oWindow-1}"/>
      <c:if test="${oEnd>totalPages}"><c:set var="oEnd" value="${totalPages}"/></c:if>

      <a class="page-link ${page==1?'disabled':''}"
         href="${ctx}/order?act=order.list&page=1&itemId=${itemSel}&status=${statusSel}&targetPage=${targetPage}&targetItemId=${targetItemSel}">&laquo;</a>

      <c:forEach var="p" begin="${oStart}" end="${oEnd}">
        <a class="page-link ${p==page?'active':''}"
           href="${ctx}/order?act=order.list&page=${p}&itemId=${itemSel}&status=${statusSel}&targetPage=${targetPage}&targetItemId=${targetItemSel}">${p}</a>
      </c:forEach>

      <a class="page-link ${page==totalPages?'disabled':''}"
         href="${ctx}/order?act=order.list&page=${totalPages}&itemId=${itemSel}&status=${statusSel}&targetPage=${targetPage}&targetItemId=${targetItemSel}">&raquo;</a>
    </div>
  </c:if>
</div>


<!-- ===== 모달 영역 ===== -->
<div id="createModal" class="modal">
  <div class="modal-content">
    <span class="close" onclick="closeCreateModal()">&times;</span>
    <h3>지시 생성</h3>
    <form method="post" action="${ctx}/order">
      <input type="hidden" name="act" value="order.create"/>
      <input type="hidden" id="create_target_id" name="target_id"/>

      <label>지시 수량</label>
      <input type="number" name="quantity" min="1" required/>

      <label>납기일</label>
      <input type="date" name="due_date" required/>

      <div class="modal-actions">
        <button type="submit" class="btn btn-primary">생성</button>
        <button type="button" class="btn" onclick="closeCreateModal()">취소</button>
      </div>
    </form>
  </div>
</div>

<div id="routingModal" class="modal">
  <div class="modal-content wide">
    <span class="close" onclick="closeRoutingModal()">&times;</span>
    <h3>BOM 정보</h3>
    <div id="bomCardContainer" class="bom-cards"></div>

    <h3>공정 정보</h3>
    <table class="data-table">
      <thead><tr><th>순서</th><th>작업장</th><th>이미지</th></tr></thead>
      <tbody id="routingTableBody"></tbody>
    </table>
  </div>
</div>

<!-- ✅ 이미지 확대 모달 -->
<div id="imageModal" class="modal">
  <div class="modal-content wide" style="max-width:90%;text-align:center">
    <span class="close" onclick="closeImageModal()">&times;</span>
    <img id="modalImage" src="" style="max-width:100%;max-height:80vh;border-radius:8px"/>
  </div>
</div>

<!-- ===== CSS ===== -->
<style>
:root {
  --bg:#0f1720; 
  --panel:#1a202c; 
  --text:#fff;
  --accent:#f59e0b;        
  --accent-dark:#d97706;
}
body{background:var(--bg);color:var(--text);font-family:Arial,sans-serif}
.wrap{max-width:1100px;margin:24px auto}
.page-title{margin:20px 0 10px;color:#fff}
.table-wrap{margin:12px 0;border:1px solid #2a3757;border-radius:10px;overflow:auto;background:var(--panel)}
.data-table{width:100%;border-collapse:collapse;font-size:14px}
.data-table th{background:#0d1320;padding:10px;font-weight:800}
.data-table td{padding:10px;border-top:1px solid #2a3757;text-align:center}
.data-table tr:nth-child(odd){background:#151c2b}

.btn{padding:6px 12px;border-radius:6px;background:var(--panel);color:var(--text);border:1px solid #2a3757;cursor:pointer}
.btn:hover{background:#2a3757}
.btn-primary{background:#60a5fa;color:#0f1720;font-weight:700;border:none}
.btn-primary:hover{background:#3b82f6}
.btn-sm{padding:4px 8px;font-size:12px}

.pagination{display:flex;justify-content:center;gap:6px;margin:16px 0}
.page-link{min-width:28px;height:32px;display:flex;align-items:center;justify-content:center;border:1px solid #2a3757;border-radius:6px;background:var(--panel);color:var(--text);text-decoration:none;font-size:13px;font-weight:700}
.page-link.active{background:var(--accent);color:#0f1720}
.page-link.disabled{opacity:.5;pointer-events:none}

/* 모달 */
.modal{display:none;position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,0.6);justify-content:center;align-items:center;z-index:1000}
.modal-content{background:var(--panel);padding:20px;border-radius:10px;min-width:360px;color:var(--text)}
.modal-content.wide{max-width:900px;width:90%}
.close{float:right;font-size:20px;cursor:pointer}
.modal-content h3{margin:0 0 16px;font-size:18px;font-weight:700;color:var(--accent)}

.bom-cards{display:flex;flex-wrap:wrap;gap:12px;margin-bottom:16px}
.bom-card{border:1px solid #2a3757;border-radius:8px;padding:10px;background:#151c2b;flex:1 1 120px;text-align:center}
.bom-name{font-weight:700;margin-bottom:6px;color:var(--accent)}

html, body {margin:0; padding:0; background:var(--bg); color:var(--text); font-family:Arial,sans-serif; overflow-x:hidden;}
* {scrollbar-width:none !important; -ms-overflow-style:none !important;}
*::-webkit-scrollbar {width:0 !important; height:0 !important;}
/* ===== Select 박스 스타일 (업데이트) ===== */
select {
  appearance: none;
  background-color: var(--panel);   /* 패널 색상으로 통일 */
  color: var(--text);               /* 글씨색 흰색 */
  border: 1px solid #2a3757;
  border-radius: 8px;               /* 조금 더 둥글게 */
  padding: 8px 32px 8px 12px;       /* 좌측여백 넉넉하게, 우측 아이콘 공간 확보 */
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: border-color .2s, box-shadow .2s;

  /* 화살표 아이콘 */
  background-image: url("data:image/svg+xml,%3Csvg width='12' height='8' viewBox='0 0 12 8' xmlns='http://www.w3.org/2000/svg'%3E%3Cpath fill='%23e6ebff' d='M0 0l6 8 6-8z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 10px center;
  background-size: 12px 8px;
}

select:hover, 
select:focus {
  border-color: var(--accent);
  outline: none;
  box-shadow: 0 0 0 2px rgba(245,158,11,0.25); /* 포커스 강조 */
}

</style>

<!-- ===== JS ===== -->
<script>
const ctx = '${ctx}';

function openCreateModal(targetId){
  document.getElementById("create_target_id").value = targetId;
  document.getElementById("createModal").style.display="flex";
}
function closeCreateModal(){
  document.getElementById("createModal").style.display="none";
}

function openRoutingModal(orderId){
  fetch(ctx + "/master?act=routing.list.ajax&orderId=" + orderId)
    .then(res=>res.json())
    .then(data=>{
      let bomC=document.getElementById("bomCardContainer");
      bomC.innerHTML="";
      if(!data.boms || data.boms.length===0){
        bomC.innerHTML="<p>등록된 BOM 정보 없음</p>";
      }else{
        data.boms.forEach(b=>{
          let c=document.createElement("div");
          c.className="bom-card";
          c.innerHTML="<div class='bom-name'>"+b.childName+"</div><div>"+b.quantity+b.childUnit+"</div>";
          bomC.appendChild(c);
        });
      }

      let tbody=document.getElementById("routingTableBody");
      tbody.innerHTML="";
      if(!data.routings || data.routings.length===0){
        tbody.innerHTML="<tr><td colspan='3'>등록된 공정 없음</td></tr>";
      }else{
        data.routings.forEach(r=>{
          let tr=document.createElement("tr");
          if(r.imgPath){
            tr.innerHTML="<td>"+r.processStep+"</td>"
                        +"<td>"+r.workstation+"</td>"
                        +"<td><img src='"+ctx+"/file?path="+r.imgPath+"' style='max-width:80px;cursor:pointer' "
                        +"onclick=\"openImageModal('"+ctx+"/file?path="+r.imgPath+"')\"></td>";
          }else{
            tr.innerHTML="<td>"+r.processStep+"</td><td>"+r.workstation+"</td><td>-</td>";
          }
          tbody.appendChild(tr);
        });
      }
      document.getElementById("routingModal").style.display="flex";
    })
    .catch(err=>{alert("데이터 로드 실패");});
}
function closeRoutingModal(){
  document.getElementById("routingModal").style.display="none";
}

function openImageModal(src){
  document.getElementById("modalImage").src = src;
  document.getElementById("imageModal").style.display="flex";
}
function closeImageModal(){
  document.getElementById("imageModal").style.display="none";
}
</script>
