<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>사용자 등록</title>
  <style>
    body{font-family:system-ui,-apple-system,'Noto Sans KR',Roboto,sans-serif;margin:20px}
    .wrap{max-width:540px}
    .field{margin:12px 0}
    label{display:block;font-weight:600;margin-bottom:6px}
    input,select{width:100%;height:40px;padding:0 10px;border:1px solid #e5e7eb;border-radius:8px}
    .btns{display:flex;gap:8px;margin-top:16px}
    .btn{padding:10px 14px;border:1px solid #e5e7eb;border-radius:8px;background:#111;color:#fff;text-decoration:none;cursor:pointer}
    .btn.sec{background:#fff;color:#111}
    .error{background:#fff1f1;color:#b91c1c;border:1px solid #fecaca;border-radius:8px;padding:10px 12px;margin-bottom:12px}
  </style>
</head>
<body>
<div class="wrap">
  <h2>사용자 등록</h2>

  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>

  <form method="post" action="${pageContext.request.contextPath}/users/new" accept-charset="UTF-8" autocomplete="off">
    <div class="field">
      <label for="loginId">로그인 ID</label>
      <input id="loginId" name="loginId" type="text"
             value="${loginId}" required
             pattern="[A-Za-z0-9_-]{4,20}" maxlength="20"
             placeholder="영문/숫자/언더바/하이픈 4~20자">
    </div>

    <div class="field">
      <label for="name">이름</label>
      <input id="name" name="name" type="text"
             value="${name}" required maxlength="50" placeholder="이름 입력">
    </div>

    <div class="field">
      <label for="role">권한</label>
      <c:set var="r" value="${role}"/>
      <select id="role" name="role" required>
        <option value="">선택</option>
        <option value="ADMIN"   ${r=='ADMIN'?'selected':''}>ADMIN</option>
        <option value="MANAGER" ${r=='MANAGER'?'selected':''}>MANAGER</option>
        <option value="WORKER"  ${r=='WORKER'?'selected':''}>WORKER</option>
      </select>
    </div>

    <div class="field">
      <label for="password">비밀번호</label>
      <input id="password" name="password" type="password"
             required minlength="8" maxlength="72"
             placeholder="최소 8자">
    </div>

    <div class="btns">
      <button class="btn" type="submit">저장</button>
      <a class="btn sec" href="${pageContext.request.contextPath}/users">취소</a>
    </div>
  </form>
</div>
</body>
</html>
