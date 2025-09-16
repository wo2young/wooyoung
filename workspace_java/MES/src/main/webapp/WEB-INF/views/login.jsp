<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>MES System - 로그인</title>

  <!-- Google Fonts (스크립트 느낌의 로고 글꼴 + 본문 글꼴) -->
<!--   <link href="https://fonts.googleapis.com/css2?family=Pacifico&family=Pretendard:wght@400;600&display=swap" rel="stylesheet"> -->

  <style>
    :root{
      --card-bg:#ffffff;
      --bg:#f3f6fc;
      --text:#111827;
      --muted:#6b7280;
      --primary:#2f6bff;
      --primary-hover:#2457d6;
      --ring:#c7d2fe;
      --border:#e5e7eb;
      --danger:#ef4444;
      --radius:16px;
      --shadow:0 12px 30px rgba(17, 24, 39, .08);
    }
    *{box-sizing:border-box}
    html,body{height:100%}
    body{
      margin:0; background:var(--bg); color:var(--text);
      font-family:"Pretendard",system-ui,-apple-system,Segoe UI,Roboto,"Noto Sans KR",Apple SD Gothic Neo,sans-serif;
      display:grid; place-items:center;
    }
    .card{
      width:min(420px, 92vw);
      background:var(--card-bg);
      border-radius:var(--radius);
      box-shadow:var(--shadow);
      padding:28px 24px 20px;
      border:1px solid var(--border);
    }
    .brand{
      text-align:center; margin-bottom:6px;
      font-family:"Pacifico", cursive; font-size:34px; letter-spacing:.5px;
    }
    .subtitle{
      text-align:center; color:var(--muted); margin-bottom:22px; font-size:14px;
    }
    .field{
      margin:14px 0;
    }
    .label{
      display:block; font-weight:600; font-size:14px; margin-bottom:8px;
    }
    .input-wrap{
      position:relative;
    }
    .input{
      width:100%; height:46px; border-radius:12px; border:1px solid var(--border);
      background:#fff; padding:0 14px 0 44px; font-size:14px; outline:none;
      transition:border .15s, box-shadow .15s;
    }
    .input:focus{
      border-color:var(--primary);
      box-shadow:0 0 0 4px var(--ring);
    }
    .icon{
      position:absolute; left:12px; top:50%; transform:translateY(-50%);
      width:20px; height:20px; color:#9aa4b2;
    }
    .btn{
      width:100%; height:48px; border:none; border-radius:12px;
      background:var(--primary); color:#fff; font-weight:600; font-size:15px;
      cursor:pointer; margin-top:8px; transition:background .15s;
    }
    .btn:hover{ background:var(--primary-hover); }
    .helper{
      text-align:center; color:var(--muted); font-size:12px; margin-top:14px;
    }
    .error{
      background:#fff1f1; color:#b91c1c; border:1px solid #fecaca;
      border-radius:10px; padding:10px 12px; font-size:13px; margin:6px 0 2px;
    }
  </style>
</head>
<body>

  <main class="card">
    <h1 class="brand">MES System</h1>
    <p class="subtitle">음료수 공장 관리 시스템</p>

    <!-- 에러 메시지 -->
    <c:if test="${not empty errorMsg}">
      <div class="error">${errorMsg}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login" accept-charset="UTF-8" autocomplete="on">
      <!-- 사용자 ID -->
      <div class="field">
        <label class="label" for="id">사용자 ID</label>
        <div class="input-wrap">
          <!-- user icon (svg) -->
          <svg class="icon" viewBox="0 0 24 24" fill="none" aria-hidden="true">
            <path d="M12 12a5 5 0 1 0-5-5 5 5 0 0 0 5 5Zm0 2c-4.42 0-8 2.24-8 5v1h16v-1c0-2.76-3.58-5-8-5Z" stroke="currentColor" stroke-width="1.5"/>
          </svg>
          <input class="input" id="loginId" name="loginId" type="text" autocomplete="username"
                 placeholder="사용자 ID를 입력하세요" required />
        </div>
      </div>

      <!-- 비밀번호(요청하신 대로 name 필드명 사용) -->
      <div class="field">
        <label class="label" for="password">비밀번호</label>
        <div class="input-wrap">
          <!-- lock icon (svg) -->
          <svg class="icon" viewBox="0 0 24 24" fill="none" aria-hidden="true">
            <path d="M7 10V8a5 5 0 0 1 10 0v2" stroke="currentColor" stroke-width="1.5"/>
            <rect x="5" y="10" width="14" height="10" rx="2.5" stroke="currentColor" stroke-width="1.5"/>
            <circle cx="12" cy="15" r="1.6" fill="currentColor"/>
          </svg>
          <!-- ***중요***: 컨트롤러가 비밀번호를 name 파라미터로 받도록 했으므로 name="name" -->
<!--           <input class="input" id="password" name="name" type="password" -->
			   <input class="input" id="password" name="password" type="password"
                 placeholder="비밀번호를 입력하세요" required />
        </div>
      </div>

      <button class="btn" type="submit">로그인</button>
      <p class="helper">환영합니다</p>
    </form>
  </main>

</body>
</html>
