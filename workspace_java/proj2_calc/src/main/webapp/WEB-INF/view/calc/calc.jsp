<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 폼화면 -->
<h1>계산기</h1>

<form method="post" action="../calc/CalcServlet">

	<input type="text" name="num1">
	<input type="text" name="num2">
	<input type="submit" name="SEND">

</form>

</body>
</html>