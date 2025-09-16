<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="/WEB-INF/includes/2header.jsp">
  <jsp:param name="activeNav" value="routing"/>
</jsp:include>
<h2>공정관리</h2>

<!-- 품명 필터 -->
<form method="get" action="${pageContext.request.contextPath}/master">
  <input type="hidden" name="act" value="routing.list"/>
  <select name="itemId">
    <option value="-1">전체</option>
    <c:forEach var="it" items="${itemsFG}">
      <option value="${it.item_id}" ${selectedItemId == it.item_id ? "selected" : ""}>
        ${it.item_name}
      </option>
    </c:forEach>
  </select>
  <button type="submit">조회</button>
</form>

<table border="1" width="100%">
  <thead>
    <tr>
     
      <th>품명</th>
      <th>순서</th>
      <th>이미지</th>
      <th>작업상세</th>
      <th>작업</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="r" items="${routings}">
      <tr>
        
        <td>${r.itemName}</td>
        <td>${r.processStep}단계</td>
        <td>
          <c:choose>
            <c:when test="${empty r.imgPath}">-</c:when>
            <c:otherwise>
              <a href="${pageContext.request.contextPath}/file?path=${r.imgPath}" target="_blank">보기</a>
              <a href="${pageContext.request.contextPath}/file?path=${r.imgPath}" download>다운</a>
            </c:otherwise>
          </c:choose>
        </td>
        <td>${r.workstation}</td>
       <td>
  <!-- 수정만 가능 -->
  <form method="get" action="${pageContext.request.contextPath}/master" style="display:inline">
    <input type="hidden" name="act" value="routing.edit"/>
    <input type="hidden" name="routing_id" value="${r.routingId}"/>
    <button type="submit">수정</button>
  </form>
</td>
      </tr>
    </c:forEach>
  </tbody>
</table>

<a class="btn" href="master?act=routing.addForm">신규 등록</a>


</body>
</html>