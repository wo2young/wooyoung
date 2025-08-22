<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>JSTL</h1>
<c:set var="a" value="10"></c:set>
\${ a } : ${ a }<br>
<%
	String name = "휴먼";
%>
<c:set var="name2" value="<%= name %>"/>
\${ name2 } : ${ name2 }<br>
<c:set var="name3" value="${ a }"/>
\${ name3 } : ${ name3 }<br>
<hr>
scope : page > request > session > application<br>
<c:set var="a1" value="1" scope="page"/>
scope 생략하면 page<br>

<c:set var="game" value="LOL" scope="page"/>
<c:set var="game" value="마비노기" scope="request"/>
<c:set var="game" value="서든어택" scope="session"/>
<c:set var="game" value="세븐나이츠" scope="application"/>
\${ game } : ${ game }<br>

pageScope : ${ pageScope.game }<br>
requestScope : ${ requestScope.game }<br>
sessionScope : ${ sessionScope.game }<br>
applicationScope : ${ applicationScope.game }<br>

<hr>
<c:if test="true">
	항상 참
</c:if>

<c:if test="${a eq 10 }">
	a는 10입니다
</c:if>
<c:if test="${ !(a eq 10) }">
	a는 10이 아닙니다
</c:if>
<c:choose>
	<c:when test="a eq 8">
		a는 8입니다
	</c:when>
	<c:when test="a eq 9">
		a는 9입니다
	</c:when>
	<c:otherwise>
		a가 뭐에요?
	</c:otherwise>
</c:choose>

<hr>
<%
	List list = new ArrayList();
	for(int i=0; i<=5; i++){
		Map map = new HashMap();
		map.put("title", "song"+i);
		map.put("singer", "singer1"+i);

		list.add(map);
	}
%>
<c:set var="list2" value="<%= list %>" scope="page"/>
그냥 첫번쨰 제목 : ${ list2[0].title }<br>

<c:forEach var="song" items="${ list2 }">
	title : ${song.title}<br>
	singer : ${song.singer}<br>
</c:forEach>
<hr>
<c:forEach var="i" begin="0" end="5">
	${i},${list2[i].title}<br>
</c:forEach>
<c:forEach var="i" begin="0" end="5" step="2">
	${i},${list2[i].title}<br>
</c:forEach>
varStatus<br>
<c:forEach var="i" begin="0" end="8" step="2" varStatus="loop">
	${i}
	<c:if test="${ not loop.last }">
		,
	</c:if>
	loop.index : ${loop.index}<br>
	loop.count : ${loop.count}<br>
	loop.first : ${loop.first}<br>
	loop.last  : ${loop.last}<br>
	<br>
</c:forEach>
<hr>
items, begin등 같이 사용<br>
<c:forEach var="song" items="${list2 }" begin="1" end="2">
	${song.title }
</c:forEach>

<hr>
2~9단 출력 단, 5단 건너뛰기
<c:forEach var="i" begin="2" end="9">
	<c:if test="${ !(i eq 5) }">
		<h1>${i}<span>단</span></h1>
	</c:if>
	<c:forEach var="j" begin="1" end="9">
		<c:if test="${ !(i eq 5) }">
			${i} X ${j} = ${i * j }
			<br>
		</c:if>	
	</c:forEach>
</c:forEach>
<hr>
c:url
사용 이유<br>
1. 영어나 숫자 외 한글이나 특수문자를 enconding 해줌<br>
2. context path 자동 추가 (/pro4_jsp)<br>
3. 쿠키 금지일 때 ";jsessionid="를 자동으로 붙여줌<br>

<c:url var="url" value="jsp_1.jsp">
	<c:param name="name" value="vvvv"/>
</c:url>
<a href="${url }" target="_blank">jsp_1.jsp</a>
<hr>
param.addr : ${param.addr }<br>
c:out :::: <c:out value="${param.addr }"/><br>
특수 문자를 치환해서 문자 그 자체로 출력
<!--  
	< : &lt;
	> : &gt;
   " ": &nbsp; (공백)
	& : &amp;
 -->

 
</body>
</html>