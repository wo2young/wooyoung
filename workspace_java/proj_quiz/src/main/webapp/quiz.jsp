<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%-- <h2>환영합니다, ${sessionScope.userName}!</h2> --%>
<form action="login" method="post">
	<button type="submit">로그인</button>
</form>

<c:choose>
	<c:when test="${sessionScope.level == 1}">
		<!--     <p>비회원 전용 페이지입니다.</p> -->
		<script>
			window.onload = function() {
				alert("로그인하십시오");
			}
		</script>
	</c:when>

	<c:when test="${sessionScope.level == 2}">
		<p>${sessionScope.userName}회원님,환영합니다!</p>
		<form action="logout" method="post">
			<button type="submit">로그아웃</button>
		</form>
	</c:when>

	<c:otherwise>
		<p>관리자 페이지입니다.</p>
		<form action="logout" method="post">
			<button type="submit">로그아웃</button>
		</form>
	</c:otherwise>
</c:choose>


</body>
</html>