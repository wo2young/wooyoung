<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
	const count = 3
	const sum = 30
	console.log()
</script>
</head>
<body>

\${ 10 } : #${ 10 }#<br>
\${ null } : *${null}*<br>
\${ 10 + 5 } : #${10+5}*<br>
\${ "15" + 1 } : ${ "15" + 1 } : EL은 계산할 때 문자를 숫자로 바꾼다<br>
<%-- \${ "a" + "b" } : ${ "a" + "b"}<br> --%>
<%-- \${ "a" + 1 } : ${ "a" + 1 }<br> --%>
\${ "a" += 1 } : ${ "a" += 1 }<br>
<%
	String a = "a";
	a = a + 1;
%>
\${ 10 % 4 } : ${ 10 % 4 }*<br>
\${ 10 mod 4 } : ${ 10 mod 4 }*<br>

\${ 10 / 4 } : ${ 10 / 4 }*<br>
\${ 10 div 4 } : ${ 10 div 4 }*<br>

\${ 10 == 4 } : ${ 10 == 4 }*<br>
\${	"a" == "a" } : ${ "a" == "a" }*<br>
\${	"a" eq "a" } : ${ "a" eq "a" }*<br>
\${	"a" eq null } : ${ "a" eq null }*<br>

\${	"a" != "a" } : ${ "a" != "a" }*<br>
\${	"a" ne "a" } : ${ "a" ne "a" }*<br>

\${	!("a" == "a") } : ${ !("a" == "a") }*<br>
\${	not("a" eq "a") } : ${ not("a" eq "a") }*<br>

\${	100 > 10 } : ${ 100 > 10 }*<br>
\${	100 gt 10 } : ${ 100 gt 10 } : greater then<br>
\${	"abc" < "abd" } : ${ "abc" < "abd" }<br>


&lt;/body&gt;
<span><</span>/body<span>></span>

\${	100 >= 10 } : ${ 100 >= 10 }<br>
\${	100 ge 10 } : ${ 100 ge 10 } : greater or equal<br>

\${	100 <= 10 } : ${ 100 <= 10 }<br>
\${	100 le 10 } : ${ 100 le 10 } : less or equal<br>

\${	100 <= 10 eq "false" } : ${ 100 <= 10 eq "false" }<br>

\${ (100 > 10) && ( 7 != 3 ) } : ${ (100 > 10) && ( 7 != 3 ) }<br>
\${ (100 > 10) and ( 7 != 3 ) } : ${ (100 > 10) and ( 7 != 3 ) }<br>

\${ (100 gt 10) || ( 7 ne 3 ) } : ${ (100 gt 10) || ( 7 ne 3 ) }<br>
\${ (100 gt 10) or ( 7 ne 3 ) } : ${ (100 gt 10) or ( 7 ne 3 ) }<br>

<hr>
\${ empty "글씨" } : ${ empty "글씨" }<br>
\${ empty null } : ${ empty null }<br>
\${ empty "" } : ${ empty "" }<br>
출력할 <strong>꺼리가 있으면</strong> empty는 false<br>
출력할 <strong>꺼리가 없으면</strong> empty는 true<br>
<hr>
request.getParameter("a") : <%= request.getParameter("a") %><br>
\${ param.a } : #${ param.a }#<br>
\${ param.b } : #${ param.b }#<br>

\${ paramValues.a } : ${ paramValues.a }<br>
\${ paramValues.a[0] } : ${ paramValues.a[0] }<br>
\${ paramValues.a[1] } : ${ paramValues.a[1] }<br>
\${ paramValues.a[100] } : ${ paramValues.a[100] }<br>

<hr>
request.getAttribute("a") : <%= request.getAttribute("a") %> 
\${ a } : ${ a } <br> 
\${ b } : *${ b }* <br> 

\${ list } : ${ list } <br> 
\${ list[0] } : *${ list[0] }* <br> 

\${ arr } : ${ arr } <br> 
\${ arr[0] } : *${ arr[0] }* <br> 

\${ map } : ${ amp } <br> 
\${ map["k1"] } : *${ map["k1"] }* <br> 
\${ map.k1] } : *${ map.k1 }* <br> 
 
\${ dto } : *${ dto }* <br> 
\${ dto.ename } : ${ dto.ename }<br>

</body>
</html>