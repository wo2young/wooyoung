<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>사용자 수정</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/users.css">
  <style>		
    .form-card{background:#fff;border:1px solid #e5e7eb;border-radius:14px;
      padding:20px;max-width:720px;box-shadow:0 8px 24px rgba(17,24,39,.06)}
    .field{margin:16px 0}
    label{display:block;font-weight:700;margin-bottom:8px}
    input,select{width:100%;height:42px;border:1px solid #e5e7eb;border-radius:10px;padding:0 12px}
    .note{font-size:12px;color:#6b7280;margin-top:6px}
    .row{display:grid;grid-template-columns:1fr 1fr;gap:20px}  /* 간격 넉넉히 */
    #name{
  width: 320px;      /* 원하는 픽셀로 */
  max-width: 100%;   /* 모바일에서 넘치지 않게 */
}    
    @media (max-width: 720px){ .row{grid-template-columns:1fr} }
    .actions{display:flex;gap:8px;align-items:center;margin-top:18px}
    .actions .spacer{flex:1}
    .btn{height:38px;padding:0 14px;border:1px solid #e5e7eb;border-radius:10px;background:#111;color:#fff;cursor:pointer;text-decoration:none;display:inline-flex;align-items:center}
    .btn.sec{background:#fff;color:#111}
    .btn.danger{background:#fff;color:#b91c1c;border-color:#fecaca}
    
  </style>
</head>
<body>

<%@ include file="/WEB-INF/includes/header.jsp" %>

<div class="wrap">
  <h1 class="page-title">사용자 수정</h1>

  <c:if test="${not empty error}">
    <div class="error" style="background:#fff1f1;color:#b91c1c;border:1px solid #fecaca;border-radius:10px;padding:10px 12px;margin-bottom:12px">
      ${error}
    </div>
  </c:if>

  <div class="form-card">
    <!-- ✅ 폼은 하나만 사용 -->
    <form method="post" action="${pageContext.request.contextPath}/users/edit" accept-charset="UTF-8" autocomplete="off">
      <input type="hidden" name="id" value="${u.user_id}"/>

      <div class="field">
        <label for="loginId">로그인 ID</label>
        <input id="loginId" name="loginId" type="text" value="${u.login_id}" readonly/>
        <div class="note">로그인 ID는 수정하지 않습니다.</div>
      </div>

      <div class="row">
        <div class="field">
          <label for="name">이름</label>
          <input id="name" name="name" type="text" value="${u.name}" required maxlength="50"/>
        </div>
        <div class="field">
          <label for="role">권한</label>
          <select id="role" name="role" required>
            <option value="ADMIN"   ${u.user_role=='ADMIN'?'selected':''}>ADMIN</option>
            <option value="MANAGER" ${u.user_role=='MANAGER'?'selected':''}>MANAGER</option>
            <option value="WORKER"  ${u.user_role=='WORKER'?'selected':''}>WORKER</option>
          </select>
        </div>
      </div>

      <!-- ✅ 한 줄 액션바: 저장/목록은 기본 제출, 비번초기화는 다른 URL로 전송 -->
      <div class="actions">
        <button class="btn" type="submit">저장</button>
        <a class="btn sec" href="${pageContext.request.contextPath}/users">목록</a>
        <span class="spacer"></span>
        <button
          class="btn danger"
          type="submit"
          formaction="${pageContext.request.contextPath}/users/reset-pw"
          formmethod="post"
          title="비밀번호 리셋 토큰 발급">
          비번초기화
        </button>
      </div>
    </form>
  </div>
</div>

</body>
</html>
