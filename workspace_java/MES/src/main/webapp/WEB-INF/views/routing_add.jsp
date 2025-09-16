<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<jsp:include page="/WEB-INF/includes/2header.jsp">
  <jsp:param name="activeNav" value="routing"/>
</jsp:include>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>공정 등록</title>
<style>
  body { font-family: Arial, sans-serif; margin: 20px; }
  h2 { margin-bottom: 15px; }
  form { max-width: 600px; padding: 15px; border: 1px solid #ccc; background: #fafafa; }
  label { display: block; margin-top: 10px; font-weight: bold; }
  input, select { width: 100%; padding: 6px; margin-top: 4px; }
  button { margin-top: 15px; padding: 8px 16px; cursor: pointer; }
  .actions { margin-top: 20px; display:flex; gap:10px; }
</style>
</head>
<body>

<h2>신규 공정 등록</h2>

<c:if test="${param.msg == 'add_fail'}">
  <script>alert("등록에 실패했습니다. 입력값을 확인하세요.");</script>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/master" enctype="multipart/form-data">
  <input type="hidden" name="act" value="routing.add"/>

  <label>품명 (완제품만 선택 가능)
    <select name="item_id" required>
      <c:forEach var="it" items="${itemsFG}">
        <option value="${it.item_id}">${it.item_name}</option>
      </c:forEach>
    </select>
  </label>

  <label>공정 순서
    <input type="number" name="process_step" min="1" step="1" required />
  </label>

  <label>공정 이미지
    <input type="file" name="img_file" accept="image/*" />
  </label>

  <label>작업 상세
    <input type="text" name="workstation" placeholder="예: 혼합 → 충전 → 포장" />
  </label>

  <div class="actions">
    <button type="submit">등록</button>
    <form method="get" action="${pageContext.request.contextPath}/master" style="display:inline">
      <input type="hidden" name="act" value="routing.list"/>
      <button type="submit">목록으로</button>
    </form>
  </div>
</form>

</body>
</html>
