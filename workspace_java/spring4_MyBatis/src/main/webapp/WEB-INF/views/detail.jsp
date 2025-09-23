<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>emp2 상세 정보</title>
</head>
<body>

empno : ${empDTO.empno }<br>
ename : ${empDTO.ename }<br>
job : ${empDTO.job }<br>
mgr : ${empDTO.mgr }<br>
hiredate : ${empDTO.hiredate }<br>
sal : ${empDTO.sal }<br>
comm : ${empDTO.comm }<br>
deptno : ${empDTO.deptno }<br>
<a href="modify?empno=${empDTO.empno }">수정</a>
<a href="removeEmp?empno=${empDTO.empno }">삭제</a>
<a href="listEmp">목록</a>
</body>
</html>