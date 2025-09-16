<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>글쓰기</title>
  <style>
    /* ===== 다크톤 팔레트 ===== */
    :root{
      --bg-color:#0a0f1a;
      --panel-bg:#0f1626;
      --line:#1f2b45;
      --text-color:#e6ebff;
      --muted:#a7b0c5;
      --input-bg:#1a202c;
      --accent:#f59e0b;
      --accent-light:#d97706;
      --hover:#1a2333;

      /* 헤더가 고정이면 높이 맞춰주세요 (예: 64, 72 등) */
      --header-h: 64px;
    }

    *{box-sizing:border-box}
    html, body{
      margin:0; padding:0;
      background:var(--bg-color);
      color:var(--text-color);
      font-family:system-ui,Segoe UI,Arial,'Noto Sans KR',sans-serif;
      min-height:100dvh;
    }

    /* 본문 래퍼: 가운데 정렬 + 헤더 공간 확보 */
    main.wrap{
      max-width: 760px;
      margin: 0 auto;
      padding: calc(var(--header-h) + 16px) 16px 24px; /* 헤더 고정 대비 상단 여백 */
    }

    h2{
      font-size:22px;
      margin:0 0 20px;
      font-weight:800;
      text-align:center;
    }

    /* 글쓰기 카드(form) */
    .editor{
      background:var(--panel-bg);
      border:1px solid var(--line);
      border-radius:12px;
      padding:20px;
      width:100%;
      margin:0 auto;           /* 중앙 */
    }

    /* 라벨 */
    label{
      display:block;
      margin:12px 0 6px;
      font-weight:600;
      color:var(--muted);
    }

    /* 입력 요소 */
    input[type=text],
    textarea,
    select{
      width:100%;
      padding:10px;
      border:1px solid var(--line);
      border-radius:8px;
      background:var(--input-bg);
      color:var(--text-color);
      font-size:14px;
    }
    textarea{resize:vertical}

    input[type=text]:focus,
    textarea:focus,
    select:focus{
      outline:none;
      border-color:var(--accent);
      box-shadow:0 0 0 2px rgba(245,158,11,.2);
    }

    /* 체크박스 */
    input[type=checkbox]{ margin-right:6px; transform:scale(1.1); }
    label.inline{
      display:flex; align-items:center; gap:6px;
      margin-top:12px; color:var(--text-color);
    }

    /* 버튼 공통 */
    .btn{
      display:inline-block;
      padding:8px 14px;
      border:1px solid var(--line);
      border-radius:10px;
      text-decoration:none;
      color:var(--text-color);
      background:var(--panel-bg);
      transition:background .15s,transform .15s,border-color .15s;
      min-width:90px;
      text-align:center;
      font-weight:600;
      cursor:pointer;
    }
    .btn:hover{ background:var(--hover); transform:translateY(-1px); }

    /* 등록 버튼 (주황) */
    .btn-primary{
      background:var(--accent);
      border-color:var(--accent);
      color:#111827;
    }
    .btn-primary:hover{
      background:var(--accent-light);
      border-color:var(--accent-light);
      color:#111827;
    }

    /* 취소 버튼 (파란색) */
    .btn-cancel{
      background:#1e3a8a;
      border-color:#1e3a8a;
      color:#e6ebff;
    }
    .btn-cancel:hover{
      background:#2563eb;
      border-color:#2563eb;
      color:#fff;
    }

    /* 버튼 그룹: 오른쪽 정렬 */
    .form-actions{
      display:flex; gap:10px; margin-top:20px; justify-content:flex-end;
    }
  </style>
</head>
<body>

  <%-- 헤더 (고정 헤더라면 --header-h 값에 맞춰 main 상단 패딩 조정) --%>
  <jsp:include page="/WEB-INF/includes/header.jsp"></jsp:include>

  <main class="wrap">
    <h2>게시글 작성</h2>

    <form class="editor" method="post" action="${pageContext.request.contextPath}/board/write" onsubmit="return v();">
      <!-- 카테고리 -->
      <label>카테고리</label>
      <select name="categoryId" required>
        <c:forEach var="c" items="${categories}">
          <option value="${c.categoryId}"><c:out value="${c.categoryName}"/></option>
        </c:forEach>
      </select>

      <!-- 제목 -->
      <label>제목</label>
      <input type="text" name="title" placeholder="제목을 입력하세요" required />

      <!-- 내용 -->
      <label>내용</label>
      <textarea name="content" rows="8" placeholder="내용을 입력하세요" required></textarea>

      <!-- 공지 체크박스 -->
      <label class="inline">
        <input type="checkbox" name="isNotice" value="Y" /> 공지글로 등록
      </label>

      <!-- 버튼 그룹 -->
      <div class="form-actions">
        <button class="btn btn-primary" type="submit">등록</button>
        <a class="btn btn-cancel" href="${pageContext.request.contextPath}/board">취소</a>
      </div>
    </form>
  </main>

  <script>
    function v(){
      const f=document.forms[0];
      if(!f.title.value.trim()){
        alert('제목을 입력하세요');
        f.title.focus();
        return false;
      }
      if(!f.content.value.trim()){
        alert('내용을 입력하세요');
        f.content.focus();
        return false;
      }
      if(!f.categoryId.value){
        alert('카테고리를 선택하세요');
        return false;
      }
      return true;
    }
  </script>

</body>
</html>
