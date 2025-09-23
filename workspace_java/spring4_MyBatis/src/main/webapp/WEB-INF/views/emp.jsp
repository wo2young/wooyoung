<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>emp2 회원 목록</title>
</head>
<body>
<!-- <div> -->
<!-- 	<form method="get" action="search"> -->
<!-- 		ename : <input type="text" name="ename"><br> -->
<!-- 		job : <input type="text" name="job"><br> -->
<!-- 		<input type="submit" value="검색"> -->
<!-- 	</form> -->
<!-- </div> -->
<div>
	<form method="get" action="search">
		<select name="type">
			<option value="1">ename</option>
			<option value="2">job</option>
			<option value="3">ename and job</option>
			<option value="4">sal이하</option>
		</select>
		<input type="text" name="keyword"><br>
		<input type="submit" value="검색">
	</form>
</div>
<hr>
<div>
	<a href="join"><button type="button">회원 가입</button></a>
</div>
<hr>
<table border=1>
	<tr>
		<th>empno</th>
		<th>ename</th>
		<th>sal</th>
		<th>job</th>
	</tr>
<c:if test="${empty list}">
	<tr>
		<td colspan="3">조회 내역이 없습니다</td>
	</tr>
</c:if>
<c:if test="${not empty list}">
	<c:forEach var="empDTO" items="${list }">
	<tr>
		<td>${empDTO.empno }</td>
		<td><a href="empDetail?empno=${empDTO.empno }">${empDTO.ename }</a></td>
		<td>${empDTO.sal }</td>
		<td>${empDTO.job }</td>
	</tr>
	</c:forEach>
</c:if>
</table>


</body>
</html>