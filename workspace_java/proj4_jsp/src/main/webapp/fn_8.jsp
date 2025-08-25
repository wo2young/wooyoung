<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="title" value="영일이삼사오육칠팔구칠팔" />
\${title } : ${title }<br>
\${fn:length(title) } ${fn:length(title) }
<br>
\${fn:substring(title,3,7) } ${fn:substring(title,3,7) }
<br>
\${fn:replace(title,"칠팔","78") } ${fn:replace(title,"칠팔","78") }
<br>
\${fn:indexOf(title,"칠팔") } ${fn:indexOf(title,"칠팔") }
<br>
<!--  indexOf 는 없으면 -1 이 나옴 -->
\${fn:indexOf(title,"abc") } ${fn:indexOf(title,"abc") }

</body>
</html>