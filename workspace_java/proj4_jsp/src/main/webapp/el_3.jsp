<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script>
	// 이건 JSP/EL과 무관한 단순 JS 예시. 콘솔에 아무것도 출력하지 않음.
	const count = 3
	const sum = 30
	console.log()
</script>
</head>
<body>

<%-- [EL 리터럴 출력 vs 평가] 
     \${ 10 } 처럼 백슬래시를 붙이면 리터럴처럼 보여줄 수 있다고 착각하기 쉬움.
     표준적으로 EL을 "완전히" 무효화하려면 page 지시자 isELIgnored="true"를 쓰거나,
     안전하게는 <c:out value="${'${'} 10 }"/> 또는 <% out.print("${ 10 }"); %> 같은 방법을 권장. --%>
\${ 10 } : #${ 10 }#<br>

<%-- null 출력: EL은 null을 빈 문자열로 렌더링하는 경우가 많다. 디버깅 시 눈으로 구분하기 위해 * * 등 구분자를 함께 출력하는 게 유용. --%>
\${ null } : *${null}*<br>

<%-- 산술 연산: 숫자 덧셈 --%>
\${ 10 + 5 } : #${10+5}*<br>

<%-- 문자열 숫자 → 숫자로 강제변환(coercion) 후 덧셈: "15" + 1 = 16 --%>
\${ "15" + 1 } : ${ "15" + 1 } : EL은 계산할 때 문자를 숫자로 바꾼다<br>

<%-- 문자열 결합은 + 연산자가 아님. EL의 +는 산술 연산이며, 문자열을 숫자로 바꾸려 시도.
     "a" + "b" 같은 건 산술 불가라 예외가 나서 주석 처리해 둔 것으로 보임. --%>
<%-- \${ "a" + "b" } : ${ "a" + "b"}<br> --%>
<%-- \${ "a" + 1 } : ${ "a" + 1 }<br> --%>

<%-- 대입/복합대입 연산자(+= 등)는 EL에서 지원하지 않음. 이 표현은 오류가 될 수 있다. --%>
\${ "a" += 1 } : ${ "a" += 1 }<br>

<%
	// 스크립틀릿(Java)에서의 문자열 결합 예: "a" + 1 = "a1"
	String a = "a";
	a = a + 1;
%>

<%-- 나머지(mod) 연산. %와 mod는 동치. --%>
\${ 10 % 4 } : ${ 10 % 4 }*<br>
\${ 10 mod 4 } : ${ 10 mod 4 }*<br>

<%-- 나눗셈(div) 연산. /와 div는 동치. 정수/정수는 소수 결과가 될 수 있음(EL 스펙에 따라). --%>
\${ 10 / 4 } : ${ 10 / 4 }*<br>
\${ 10 div 4 } : ${ 10 div 4 }*<br>

<%-- 동등/비동등 비교: ==, eq 는 동치 / !=, ne 는 동치. --%>
\${ 10 == 4 } : ${ 10 == 4 }*<br>
\${	"a" == "a" } : ${ "a" == "a" }*<br>
\${	"a" eq "a" } : ${ "a" eq "a" }*<br>
\${	"a" eq null } : ${ "a" eq null }*<br>

\${	"a" != "a" } : ${ "a" != "a" }*<br>
\${	"a" ne "a" } : ${ "a" ne "a" }*<br>

<%-- 부정(not) 연산자: ! 와 not 은 동치. --%>
\${	!("a" == "a") } : ${ !("a" == "a") }*<br>
\${	not("a" eq "a") } : ${ not("a" eq "a") }*<br>

<%-- 대소 비교: >, <, >=, <= 와 gt, lt, ge, le는 각각 동치.
     문자열 비교는 사전식(lexicographical) 비교. --%>
\${	100 > 10 } : ${ 100 > 10 }*<br>
\${	100 gt 10 } : ${ 100 gt 10 } : greater then<br>
\${	"abc" < "abd" } : ${ "abc" < "abd" }<br>

<%-- 아래 두 줄은 단순히 </body> 문자열을 화면에 그대로 보여주는 트릭(브라우저가 태그로 인식하지 않도록).
     &lt; 엔티티나 <span>로 쪼개 쓰면 실제 태그로 닫히지 않음. --%>
&lt;/body&gt;
<span><</span>/body<span>></span>

\${	100 >= 10 } : ${ 100 >= 10 }<br>
\${	100 ge 10 } : ${ 100 ge 10 } : greater or equal<br>

\${	100 <= 10 } : ${ 100 <= 10 }<br>
\${	100 le 10 } : ${ 100 le 10 } : less or equal<br>

<%-- 주의: 연산자 우선순위. (100 <= 10)은 false.
     false eq "false" 는 타입 강제변환 후 true 가 된다. --%>
\${	100 <= 10 eq "false" } : ${ 100 <= 10 eq "false" }<br>

<%-- 논리연산: &&/and, ||/or 는 각각 동치. --%>
\${ (100 > 10) && ( 7 != 3 ) } : ${ (100 > 10) && ( 7 != 3 ) }<br>
\${ (100 > 10) and ( 7 != 3 ) } : ${ (100 > 10) and ( 7 != 3 ) }<br>

\${ (100 gt 10) || ( 7 ne 3 ) } : ${ (100 gt 10) || ( 7 ne 3 ) }<br>
\${ (100 gt 10) or ( 7 ne 3 ) } : ${ (100 gt 10) or ( 7 ne 3 ) }<br>

<hr>

<%-- empty: null, 길이 0 문자열(""), 비어있는 컬렉션/배열/맵에 대해 true.
     내용이 있으면 false. --%>
\${ empty "글씨" } : ${ empty "글씨" }<br>
\${ empty null } : ${ empty null }<br>
\${ empty "" } : ${ empty "" }<br>
출력할 <strong>꺼리가 있으면</strong> empty는 false<br>
출력할 <strong>꺼리가 없으면</strong> empty는 true<br>

<hr>

<%-- request.getParameter vs EL param
     - request.getParameter("a") 는 첫 번째 값 반환.
     - ${param.a} 도 동일하게 첫 번째 값 반환.
     - 여러 값을 받으려면 ${paramValues.a} 사용. --%>
request.getParameter("a") : <%= request.getParameter("a") %><br>
\${ param.a } : #${ param.a }#<br>
\${ param.b } : #${ param.b }#<br>

<%-- paramValues: 같은 이름의 파라미터가 여러 개인 경우(예: ?a=1&a=2) 배열로 접근 가능. 
     인덱스가 범위를 벗어나면 null(빈 출력). --%>
\${ paramValues.a } : ${ paramValues.a }<br>
\${ paramValues.a[0] } : ${ paramValues.a[0] }<br>
\${ paramValues.a[1] } : ${ paramValues.a[1] }<br>
\${ paramValues.a[100] } : ${ paramValues.a[100] }<br>

<hr>

<%-- request attribute 와 EL 기본 객체 접근
     - 자바: request.setAttribute("a", 값)
     - JSP: ${a} 로 접근
     - 존재하지 않으면 빈 문자열처럼 보임. --%>
request.getAttribute("a") : <%= request.getAttribute("a") %> 
\${ a } : ${ a } <br> 
\${ b } : *${ b }* <br> 

<%-- 리스트/배열 접근: [index] 표기 --%>
\${ list } : ${ list } <br> 
\${ list[0] } : *${ list[0] }* <br> 

\${ arr } : ${ arr } <br> 
\${ arr[0] } : *${ arr[0] }* <br> 

<%-- 맵 접근: ["key"] 또는 .key (키가 식별자 규칙에 맞는 경우)
     아래 줄의 ${ amp } 는 오타(map 아님)로 보이며 빈 출력이 나올 것. --%>
\${ map } : ${ amp } <br> 
\${ map["k1"] } : *${ map["k1"] }* <br> 
\${ map.k1] } : *${ map.k1 }* <br> 

<%-- 자바빈/DTO 프로퍼티 접근: ${dto.ename} -> dto.getEname() 호출 규칙
     getter가 없으면 접근 불가. --%>
\${ dto } : *${ dto }* <br> 
\${ dto.ename } : ${ dto.ename }<br>

<%-- =========================================
     전체 정리
     1) 산술/비교/논리: (+, -, *, /, div, %, mod, >,<,>=,<=, ==/eq, !=/ne, &&/and, ||/or, !/not)
     2) 타입 강제변환: "15"+1 처럼 문자열 숫자는 숫자로 변환되어 연산. 불리언/숫자/문자열 간 비교 시 자동 변환됨.
     3) empty: null, "", 길이 0, 비어있는 컬렉션/맵/배열이면 true.
     4) 스코프 탐색: page → request → session → application 순서로 이름을 찾음.
     5) 파라미터: ${param.x} 첫 값, ${paramValues.x[i]} 다중 값.
     6) 컬렉션/배열/맵: [index], ["key"], .key 로 접근.
     7) 자바빈: ${bean.prop} 는 getProp() 호출 규칙.
     8) 문자열 결합 주의: EL의 + 는 산술, 문자열 결합 용도 아님. 문자열 덧셈 기대하면 예외 가능.
     9) 대입/복합대입(=, += 등): EL에서 지원하지 않음.
    10) 리터럴 ${...} 출력: \${...} 는 컨테이너에 따라 평가될 수 있음. 안전하게는 <c:out value="${'${'} ... }"/> 또는 스크립틀릿 출력 권장.

     실무에서 자주 쓰는 부분
     - ${param...}, ${paramValues...} 로 요청 파라미터 확인
     - ${empty ...} 로 널/빈값 체크
     - ${modelAttr.field} 로 DTO 바인딩 값 확인
     - 맵/리스트/배열 인덱싱으로 뷰 템플릿 렌더링

     디버깅 팁
     - 구분자(#, *, [ ])를 함께 출력해 공백/빈값 구분
     - 오타(map ↔ amp)로 값이 비는 경우가 많으니 이름 통일
     - getter 누락 시 프로퍼티 접근 불가
   ========================================= --%>

</body>
</html>
