<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html><html lang="ko"><head><meta charset="UTF-8"><title>비밀번호 초기화 코드</title>
<style>body{font-family:system-ui, 'Noto Sans KR', sans-serif; margin:20px}
.box{border:1px solid #e5e7eb; padding:16px; border-radius:10px; max-width:520px}
.code{font-size:20px; font-weight:700; letter-spacing:1px; background:#f5f5f5; padding:8px 10px; border-radius:8px; display:inline-block}
.tip{color:#6b7280; font-size:13px; margin-top:8px}
</style></head><body>
  <div class="box">
    <h3>비밀번호 초기화 코드 발급 완료</h3>
    <p>대상: <strong>${target.login_id}</strong> / ${target.name}</p>
    <p>아래 <strong>리셋 코드</strong>를 사용자에게 전달하세요. (유효기간: 30분)</p>
    <p class="code">${token}</p>
    <p class="tip">사용자는 <code>/password/reset</code> 페이지에서 로그인ID와 이 코드를 입력해 새 비밀번호를 설정합니다.</p>
    <p><a href="${pageContext.request.contextPath}/users">사용자 목록으로 돌아가기</a></p>
  </div>
</body></html>
