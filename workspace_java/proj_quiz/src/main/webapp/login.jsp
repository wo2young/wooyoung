<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="login" method="post">
		아이디: <input type="text" name="empno"
			value="${cookie.rememberId.value}"> 비밀번호: <input
			type="password" name="ename"> <label><input
			type="checkbox" name="remember"> 아이디 저장</label>
		<button type="submit">로그인</button>
	</form>
</body>
</html>