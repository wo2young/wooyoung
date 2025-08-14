<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 결과 화면 -->
<h1>계산 결과</h1>

<h1>num1 : ${num1}</h1>
<h1>num2 : ${num2}</h1>
<hr>
<h1>합계 : <strong>${sum}</strong></h1>

<h2><a href="${pageContext.request.contextPath}/calc/CalcServlet">다시 계산하기</a></h2>
</body>
</html>