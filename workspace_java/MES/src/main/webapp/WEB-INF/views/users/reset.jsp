<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html lang="ko"><head><meta charset="UTF-8"><title>비밀번호 재설정</title>
<style>body{font-family:system-ui,'Noto Sans KR',sans-serif;margin:20px}
.wrap{max-width:480px}.field{margin:12px 0}label{display:block;font-weight:600;margin-bottom:6px}
input{width:100%;height:40px;padding:0 10px;border:1px solid #e5e7eb;border-radius:8px}
.btn{margin-top:12px;padding:10px 14px;border:1px solid #e5e7eb;border-radius:8px;background:#111;color:#fff}
.error{background:#fff1f1;color:#b91c1c;border:1px solid #fecaca;border-radius:8px;padding:10px 12px;margin-bottom:12px}
</style></head><body>
<div class="wrap">
  <h2>비밀번호 재설정</h2>
  <c:if test="${not empty error}"><div class="error">${error}</div></c:if>
  <form method="post" action="${pageContext.request.contextPath}/password/reset" autocomplete="off">
    <div class="field">
      <label for="loginId">로그인 ID</label>
      <input id="loginId" name="loginId" type="text" required>
    </div>
    <div class="field">
      <label for="token">리셋 코드</label>
      <input id="token" name="token" type="text" required maxlength="64" placeholder="관리자가 알려준 코드">
    </div>
    <div class="field">
      <label for="password">새 비밀번호</label>
      <input id="password" name="password" type="password" required minlength="8" maxlength="72">
    </div>
    <div class="field">
      <label for="password2">새 비밀번호 확인</label>
      <input id="password2" name="password2" type="password" required minlength="8" maxlength="72">
    </div>
    <button class="btn" type="submit">변경</button>
  </form>
</div>
</body></html>
