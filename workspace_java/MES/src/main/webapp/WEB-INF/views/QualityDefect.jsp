<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div style="max-width:800px; margin:24px auto; font-family:Arial, sans-serif;">

  <!-- ✅ 탭 네비게이션 -->
  <div class="tab-container">
    <a href="${pageContext.request.contextPath}/quality/new"
       class="tab ${empty param.action ? 'active' : ''}">
      불량 등록
    </a>
    <a href="${pageContext.request.contextPath}/quality/new?action=status"
       class="tab ${param.action eq 'status' ? 'active' : ''}">
      불량 현황
    </a>
  </div>

  <h2>품질 불량 등록</h2>

  <!-- ✅ 메시지 출력 -->
  <c:if test="${not empty sessionScope.msg}">
    <p style="color:green;">${sessionScope.msg}</p>
    <c:remove var="msg" scope="session"/>
  </c:if>
  <c:if test="${not empty errorMsg}">
    <p style="color:red;">${errorMsg}</p>
  </c:if>

  <!-- ✅ 등록 폼 -->
  <form action="${pageContext.request.contextPath}/quality/new" method="post"
        style="background:#fff; padding:20px; border:1px solid #ddd; border-radius:8px;">

    <!-- 품목 -->
    <label>품목:</label>
    <select name="item_name" required style="width:100%; padding:6px; margin-bottom:12px;">
      <option value="">-- 선택하세요 --</option>
      <option value="COKE">콜라</option>
      <option value="CIDER">사이다</option>
      <option value="FANTA">환타</option>
      <option value="ETC">기타</option>
    </select>

    <!-- 실적번호 -->
    <label>실적번호 (RESULT_ID):</label>
    <select id="resultSelect" name="result_id" required style="width:100%; padding:6px; margin-bottom:12px;"
            onchange="setWorkerName()">
      <option value="">-- 선택하세요 --</option>
      <c:forEach var="r" items="${thisWeekResults}">
        <option value="${r.result_id}" data-worker="${r.worker_name}">
          ${r.result_id}
        </option>
      </c:forEach>
    </select>

    <!-- 불량 코드 -->
    <label>불량 코드 (DETAIL_CODE):</label>
    <select name="detail_code" required style="width:100%; padding:6px; margin-bottom:12px;">
      <option value="">-- 선택하세요 --</option>
      <c:forEach var="cd" items="${defectCodes}">
        <option value="${cd.detail_code}">[${cd.detail_code}] ${cd.code_dname}</option>
      </c:forEach>
    </select>

    <!-- 불량 수량 -->
    <label>불량 수량:</label>
    <input type="number" name="quantity" min="1" required
           style="width:100%; padding:6px; margin-bottom:12px;" />

    <!-- 작업자 이름 -->
    <label>작업자:</label>
    <input type="text" id="workerName" readonly
           style="width:100%; padding:6px; margin-bottom:12px; background:#f9f9f9;" />

    <!-- 등록자 -->
    <input type="hidden" name="registered_by" value="${loginUser.user_id}" />

    <!-- 등록 버튼 -->
    <button type="submit"
            style="display:block; width:100%; padding:10px; background:#1f2937; color:#fff; border:none; border-radius:6px; cursor:pointer;">
      등록
    </button>
  </form>
</div>

<!-- ✅ 스크립트 -->
<script>
  function setWorkerName() {
    const select = document.getElementById("resultSelect");
    const worker = select.options[select.selectedIndex].getAttribute("data-worker");
    document.getElementById("workerName").value = worker || "";
  }
</script>

<!-- ✅ 스타일 -->
<style>
  .tab-container {
    display: flex;
    gap: 8px;
    margin-bottom: 20px;
  }
  .tab {
    flex: 1;
    text-align: center;
    padding: 10px;
    border-radius: 6px;
    text-decoration: none;
    font-weight: bold;
    background: #f3f3f3;
    color: black;
    transition: all 0.2s ease-in-out;
  }
  .tab:hover {
    background: #e5e7eb;
  }
  .tab.active {
    background: #1f2937;
    color: white;
  }
</style>
