<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 
  이 파일은 숫자 2개를 입력받아 /calc 서블릿으로 GET 요청을 보내는 간단한 폼 페이지다.
  - method="get": 파라미터가 쿼리스트링(?NUM1=..&NUM2=..)으로 붙는다.
  - action="calc": 현재 페이지 경로 기준의 '상대경로'다. 
                   컨텍스트 경로를 포함한 절대경로를 쓰려면 아래 대안 참고.
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%-- 
      상대경로 action="calc"는 현재 URL이 /proj2_calc/input.jsp 라면 
      최종 요청은 /proj2_calc/calc 가 된다.
      만약 폴더 안에 있다면(예: /proj2_calc/forms/input.jsp) 
      action="calc"는 /proj2_calc/forms/calc 로 향하므로 주의.
      컨텍스트 경로를 안전히 포함하려면 다음처럼 쓰는 걸 권장:
      <form method="get" action="${pageContext.request.contextPath}/calc">
    --%>
	<form method="get" action="../calc">
		<%-- name 속성 값이 서블릿에서 request.getParameter("NUM1")로 읽는 키가 된다. --%>
		<input type="number" name="NUM1">
		<input type="number" name="NUM2">
        <%-- 기본 버튼 타입은 submit 이지만, 명시적으로 적어두는 편이 안전하다. --%>
		<button type="submit">SEND</button>
	</form>

    <%-- 
      전체 정리
      1) name 과 id의 차이: name은 서버 전송 키, id는 JS/CSS 선택용. 
      2) GET과 POST: GET은 주소창에 노출, POST는 본문으로 전송. 길이/보안 관점에서 POST가 일반적으로 안전.
      3) action 경로: 상대경로는 현재 위치에 의존하므로 실무에서는 보통 절대경로(${pageContext.request.contextPath} 포함) 사용을 권장. [중요][실무 자주]
      4) input type="number": 공백 전송 시 빈 문자열("")이 넘어오므로 서버에서 NumberFormatException 처리 필요. [중요]
    --%>
</body>
</html>
