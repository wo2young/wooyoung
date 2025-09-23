<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fnt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>emp2 리스트</title>

<table border=1>
	<tr>
		<th>empno</th>
		<th>ename</th>
		<th>sal</th>
	</tr>
	<c:if test="${empty list}">
			<tr>
				<td colspan="3">조회내역이 없습니다</td>
			</tr>
	</c:if>
	<c:if test="${not empty list}">
		<c:forEach var="empDTO" items="${list }">
		<tr>
			<td>${empDTO.empno}</td>
			<td><a href=empDetail?empno=${empDTO.empno}>${empDTO.ename}</a></td>
			<td>${empDTO.sal}</td>
		</tr>
		</c:forEach>
	</c:if>
</table>
</body>
</html>