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
<title>emp2 상세</title>
</head>
<body>
<c:forEach var="e" items="${list}">
  empno   : ${e.empno}<br>
  ename   : ${e.ename}<br>
  job     : ${e.job}<br>
  mgr     : ${e.mgr}<br>
  hiredate: ${e.hiredate}<br>
  sal     : ${e.sal}<br>
  comm    : ${e.comm}<br>
  deptno  : ${e.deptno}<br>
</c:forEach>
</body>
</html>