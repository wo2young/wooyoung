<!-- html 주석 -->
<%-- jsp 주석 --%>


<!--  디렉티브 태그 -->
<%-- 
	"<%@"로 시작하는 태그 
	1. page
	2. include
--%>
<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    
    import="java.util.List"
    import="java.util.ArrayList"
    
%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.onload = function(){
		console.log('jsp');
	}
</script>
</head>
<body>
	<%@ include file="header.jsp" %>
	
	
	<h1>jsp.jsp</h1>
<!-- 스크립틀릿 Scriptlet -->
<%
	// 여기는 자바입니다
	System.out.println("test");
	out.println("<strong>한국 남자</strong>");
%>

<table border=1>
<%
	for(int i=0; i<5; i++){
%>
	<tr>
		<td>제목</td>
		<td>내용</td>
	</tr>		
<% 		
	}
%>
</table>
<%
	int a = 10;
	String name = request.getParameter("name");
%>
<br>
<h1>여기서 a : <% out.println(a); %></h1><br>
<h1>여기서 name : <% out.println(name); %></h1><br>

<%-- 표현식 --%>
<%= a %><br>
<%= name %><br>

<%-- 선언문 --%>
<%! 
	String title="F1";  
	String getTitle(){
		return this.title;
	}
%>

<h1>9X9단 만들기</h1>
<%
int b = 5; // 구하고 싶은 단
out.println(b+"단");%> <br>
<%
for(int i=1; i<10; i++){
	out.println(b + "X" + i + "=" + (b*i)); %><br>
<%	
}	
%>


</body>
</html>