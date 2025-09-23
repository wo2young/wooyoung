<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>emp2 가입</title>
</head>
<body>
<form method="post" action="modifyEmp">
	empno : ${empDTO.empno }<br>
	<input type="hidden" name="empno" value="${empDTO.empno }">
	
	ename : <input type="text" name="ename" value="${empDTO.ename}"><br>
	job : <input type="text" name="job" value="${empDTO.job}"><br>
	mgr : <input type="text" name="mgr" value="${empDTO.mgr}"><br>
	hiredate : <input type="date" id="hiredate" name="hiredate" value="${empDTO.hiredate}"><br>
	sal : <input type="text" name="sal" value="${empDTO.sal}"><br>
	comm : <input type="text" name="comm" value="${empDTO.comm}"><br>
	deptno : <input type="text" name="deptno" value="${empDTO.deptno}"><br>
	
	<input type="submit" value="수정">
	<a href="listEmp">취소</a>
</form>
</body>
</html>