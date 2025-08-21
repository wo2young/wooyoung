<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="header.jsp"/>
	<jsp:include page="jsp_1.jsp">
		<jsp:param name="name" value="acc"/>
	</jsp:include>
	
<!-- 	주소가 안바뀐다 와 request를 전달한다 -->
	<jsp:forward page="header.jsp"/>
</body>
</html>