<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
	border : 1px solid black;
	}
	td{
	border : 1px solid black;
	width : 100px
	}
	th{
	border : 1px solid black;
	}
	#num {
	text-align : right;
	}
</style>
</head>
<body>
	<h1>emp list</h1>
	<form action="post">
		<table>
			<th>선택</th>
			<th>empno</th>
			<th>ename</th>
			<th>sal</th>
			<th>job</th>
			<c:forEach var="empDTO" items="${list }">
				<tr>
					<td><input type="checkbox" name="empnos"
						value="${empDTO.empno }"></td>
					<td id="num">${empDTO.empno }</td>
					<td><a href="empDetail?empno=${empDTO.empno }">${empDTO.ename }</a></td>
					<td id="num">${empDTO.sal }</td>
					<td>${empDTO.job }</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>