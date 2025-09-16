<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div style="max-width:1100px; margin:24px auto; font-family:Arial,sans-serif;">

  <c:if test="${param.msg == 'create_ok'}"><script>alert("지시 생성 완료");</script></c:if>
  <c:if test="${param.msg == 'upd_ok'}"><script>alert("지시 수정 완료");</script></c:if>
  <c:if test="${param.msg == 'create_fail' || param.msg == 'upd_fail' || param.msg == 'del_fail'}">
    <script>alert("작업 실패. 다시 시도하세요.");</script>
  </c:if>

  <h2>📌 생산목표 목록</h2>

  <form method="get" action="${ctx}/order" style="margin-bottom:10px;">
    <input type="hidden" name="act" value="order.list"/>
    <label>완제품:</label>
    <select name="targetItemId" onchange="this.form.submit()">
      <option value="0" ${targetItemSel == 0 ? 'selected' : ''}>전체</option>
      <c:forEach var="it" items="${itemList}">
        <option value="${it.item_id}" ${targetItemSel == it.item_id ? 'selected' : ''}>
          ${it.item_name}
        </option>
      </c:forEach>
    </select>
  </form>

  <table border="1" width="100%" style="margin-bottom:20px;">
    <thead>
      <tr>
        <th>제품명</th>
        <th>목표 수량</th>
        <th>생산된 수량</th>
        <th>기간</th>
        <th>상태</th>
        <th>액션</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="t" items="${targets}">
        <tr>
          <td>${t.item_name}</td>
          <td>${t.target_quantity}</td>
          <td>${t.results_sum}</td>
          <td>${t.period_start} ~ ${t.period_end}</td>
          <td>${t.status}</td>
          <td>
            <button type="button" onclick="openCreateModal('${t.target_id}')">지시 생성</button>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <hr/>

  <h2>📌 생산지시 목록</h2>

  <form method="get" action="${ctx}/order" style="margin-bottom:10px;">
    <input type="hidden" name="act" value="order.list"/>
    <label>완제품:</label>
    <select name="itemId">
      <option value="0" ${itemSel == 0 ? 'selected' : ''}>전체</option>
      <c:forEach var="it" items="${itemList}">
        <option value="${it.item_id}" ${itemSel == it.item_id ? 'selected' : ''}>
          ${it.item_name}
        </option>
      </c:forEach>
    </select>

    <label>상태:</label>
    <select name="status">
      <option value="ALL" ${statusSel eq 'ALL' ? 'selected' : ''}>전체</option>
      <option value="PLANNED" ${statusSel eq 'PLANNED' ? 'selected' : ''}>계획중</option>
      <option value="DONE" ${statusSel eq 'DONE' ? 'selected' : ''}>완료</option>
    </select>

    <button type="submit">조회</button>
  </form>

  <table border="1" width="100%">
    <thead>
      <tr>
        <th>공정보기</th>
        <th>제품명</th>
        <th>지시 수량</th>
        <th>납기일</th>
        <th>상태</th>
        <th>등록자</th>
        <th>액션</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="o" items="${orders}">
        <tr>
          <td>
            <form method="get" action="${ctx}/routing" style="display:inline">
              <input type="hidden" name="order_id" value="${o.order_id}"/>
              <button type="submit">공정보기</button>
            </form>
          </td>
          <td>${o.target_name}</td>
          <td>${o.quantity}</td>
          <td>${o.due_date}</td>
          <td>${o.status}</td>
          <td>${o.created_by_name}</td>
          <td>
            <button type="button"
                    onclick="openEditModal('${o.order_id}','${o.quantity}','${o.due_date}')">
              수정
            </button>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

</div>

<div id="createModal" class="modal" style="display:none;">
  <div class="modal-content">
    <span class="close" onclick="closeCreateModal()">&times;</span>
    <h3>지시 생성</h3>
    <form method="post" action="${ctx}/order">
      <input type="hidden" name="act" value="order.create"/>
      <input type="hidden" id="create_target_id" name="target_id"/>

      <label>지시 수량</label><br/>
      <input type="number" name="quantity" min="1" required/><br/><br/>

      <label>납기일</label><br/>
      <input type="date" name="due_date" required/><br/><br/>

      <button type="submit">생성</button>
      <button type="button" onclick="closeCreateModal()">취소</button>
    </form>
  </div>
</div>

<div id="editModal" class="modal" style="display:none;">
  <div class="modal-content">
    <span class="close" onclick="closeEditModal()">&times;</span>
    <h3>지시 수정</h3>
    <form id="editForm" method="post" action="${ctx}/order" style="margin-bottom:10px;">
      <input type="hidden" name="act" value="order.update"/>
      <input type="hidden" id="edit_order_id" name="order_id"/>

      <label>지시 수량</label><br/>
      <input type="number" id="edit_quantity" name="quantity" min="1" required/><br/><br/>

      <label>납기일</label><br/>
      <input type="date" id="edit_due_date" name="due_date" required/><br/><br/>

      <button type="submit">저장</button>
    </form>
  </div>
</div>

<style>
.modal {
  position: fixed; top:0; left:0; width:100%; height:100%;
  background: rgba(0,0,0,0.4); display:flex; justify-content:center; align-items:center;
}
.modal-content {
  background:#fff; padding:20px; border-radius:8px; width:400px;
}
.close { float:right; cursor:pointer; font-size:20px; }
</style>

<script>
function openCreateModal(targetId){
  document.getElementById("create_target_id").value = targetId;
  document.getElementById("createModal").style.display="flex";
}
function closeCreateModal(){
  document.getElementById("createModal").style.display="none";
}

function openEditModal(id, qty, due){
  document.getElementById("edit_order_id").value = id;
  document.getElementById("edit_quantity").value = qty;
  document.getElementById("edit_due_date").value = due;
  document.getElementById("editModal").style.display="flex";
}
function closeEditModal(){
  document.getElementById("editModal").style.display="none";
}
</script>
