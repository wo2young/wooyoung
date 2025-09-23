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
<form method="post" action="joinEmp2">
	ename : <input type="text" name="ename"><br>
	job : <input type="text" name="job"><br>
	mgr : <input type="text" name="mgr"><br>
	hiredate : <input type="date" id="hiredate" name="hiredate"><br>
	sal : <input type="text" name="sal"><br>
	comm : <input type="text" name="comm"><br>
	deptno : <input type="text" name="deptno"><br>
	<input type="submit" value="가입">
	<a href="listEmp">취소</a>
</form>

<script>
	document.querySelector('#hiredate').value = new Date().toISOString().split('T')[0]
</script>
</body>
</html>