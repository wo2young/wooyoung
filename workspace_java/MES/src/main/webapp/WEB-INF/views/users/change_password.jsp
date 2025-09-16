<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>비밀번호 변경</h2>
<form method="post" action="${pageContext.request.contextPath}/mypage/change-password">
  <div>
    <label>리셋코드</label>
    <input type="password" name="currentPassword" required />
  </div>
  <div>
    <label>새 비밀번호</label>
    <input type="password" name="newPassword" required />
  </div>
  <div>
    <label>새 비밀번호 확인</label>
    <input type="password" name="confirmPassword" required />
  </div>
  <button type="submit">변경</button>
  <c:if test="${not empty error}">
    <p style="color:red">${error}</p>
  </c:if>
</form>
