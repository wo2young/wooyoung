<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>게시판</title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <style>
    :root{
      --bg-color:#0a0f1a; --panel-bg:#0f1626; --line:#1f2b45; --text-color:#e6ebff;
      --muted:#a7b0c5; --input-bg:#1a202c; --accent:#f59e0b; --accent-light:#d97706; --hover:#1a2333;
    }
    *{box-sizing:border-box}
    html, body {
      margin:0; padding:0; background:var(--bg-color); color:var(--text-color);
      font-family:system-ui, Segoe UI, Arial, Apple SD Gothic Neo, 'Noto Sans KR', sans-serif; height:100dvh;
    }
    main{ overflow:auto; -webkit-overflow-scrolling:touch; }
    .wrap{max-width:1100px;margin:24px auto;padding:0 16px}

    .page-head{display:flex;align-items:end;justify-content:space-between;gap:12px;margin:12px 0 16px}
    .page-head h2{margin:0;font-size:22px;font-weight:800}
    .muted{color:var(--muted);font-size:13px;margin:6px 0 0}

    .tabs{display:flex;gap:8px;flex-wrap:wrap;margin:8px 0 10px}
    .tab{ padding:8px 14px; border:1px solid var(--line); border-radius:999px; text-decoration:none;
      color:var(--muted); background:var(--panel-bg); transition:.15s; line-height:1; white-space:nowrap; }
    .tab:hover{ background:var(--hover); color:#fff; transform:translateY(-1px); }
    .tab.active{ background:var(--accent); color:#111827; border-color:var(--accent); }

    .toolbar{
      display:flex;gap:8px;align-items:center;flex-wrap:wrap;
      background:var(--panel-bg); border:1px solid var(--line); border-radius:12px; padding:10px;
    }
    .toolbar input[type="text"]{
      padding:8px 10px; border:1px solid var(--line); border-radius:8px;
      background:var(--input-bg); color:var(--text-color); min-height:36px;
    }

    .btn{ display:inline-block; padding:8px 14px; border:1px solid var(--line); border-radius:10px;
      text-decoration:none; color:var(--text-color); background:var(--panel-bg);
      transition:background .15s,transform .15s,border-color .15s; min-width:70px; text-align:center;
    }
    .btn:hover { background:var(--hover); transform:translateY(-1px); }
    .btn-search { background:var(--accent); border-color:var(--accent); color:#111827; font-weight:600; }
    .btn-search:hover { background:var(--accent-light); border-color:var(--accent-light); color:#111827; }

    .card{ background:var(--panel-bg); border:1px solid var(--line); border-radius:12px; overflow:hidden }
    table{ width:100%; border-collapse:collapse; table-layout:fixed }
    th,td{
      border-top:1px solid var(--line); padding:10px 12px; text-align:left; vertical-align:middle;
      background:var(--input-bg); color:var(--text-color);
    }
    thead th{ border-top:none; background:var(--panel-bg); font-weight:700 }
    tbody tr:hover{ background:#1c2538 }
    .right{text-align:right}

    .title-cell{display:flex;align-items:center;gap:8px;min-width:240px}
    .title-link{
      color:var(--text-color); text-decoration:none; display:inline-block; max-width:100%;
      white-space:nowrap; overflow:hidden; text-overflow:ellipsis;
    }
    .badge{ display:inline-block; background:#f87171; color:#fff; padding:2px 8px; border-radius:999px; font-size:12px; flex-shrink:0; }
    .cat-pill{ display:inline-block; padding:4px 8px; border:1px solid var(--accent); border-radius:999px; font-size:12px; color:var(--accent); background:transparent; white-space:nowrap; }

    .pagination{
      display:flex;gap:8px;margin:14px 0; align-items:center;flex-wrap:wrap;justify-content:center;
    }
    .pagination a, .pagination span{
      padding:6px 12px; border:1px solid var(--line); border-radius:8px; background:var(--input-bg);
      color:var(--text-color); min-width:34px; text-align:center; line-height:1; text-decoration:none;
    }
    .pagination a:hover{ background:var(--hover); }
    .pagination .active{ background:var(--accent); border-color:var(--accent); color:#111827; }
    .pagination .disabled{ opacity:.5; cursor:not-allowed; }

    /* ✅ 챗봇 관련 추가 */
    .chatbot-icon {
      position: fixed;
      bottom: 20px;
      right: 20px;
      cursor: pointer;
      font-size: 28px;
      background: var(--accent);
      color: #111827;
      border-radius: 50%;
      width: 55px;
      height: 55px;
      text-align: center;
      line-height: 55px;
      box-shadow: 0 2px 8px rgba(0,0,0,0.3);
      z-index: 999;
    }
    .chatbot-popup {
      display: none;
      position: fixed;
      bottom: 90px;
      right: 20px;
      width: 320px;
      height: 400px;
      background: var(--panel-bg);
      border: 1px solid var(--line);
      border-radius: 12px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.4);
      padding: 10px;
      z-index: 1000;
    }
    .chatbot-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-weight: bold;
      margin-bottom: 5px;
      color: var(--text-color);
    }
    #chatLog {
      width: 100%;
      height: 280px;
      margin-bottom: 8px;
      padding: 5px;
      resize: none;
      background: var(--input-bg);
      color: var(--text-color);
      border: 1px solid var(--line);
      border-radius: 6px;
    }
    #userInput {
      width: 70%;
      padding: 5px;
      background: var(--input-bg);
      color: var(--text-color);
      border: 1px solid var(--line);
      border-radius: 6px;
    }
    #sendBtn {
      padding: 6px 12px;
      background: var(--accent);
      border: none;
      border-radius: 6px;
      font-weight: bold;
      cursor: pointer;
    }
    #sendBtn:hover {
      background: var(--accent-light);
    }
  </style>
</head>
<body>
  <main class="wrap">
    <div class="page-head">
      <h2>게시판</h2>
      <p class="muted">※ <b>공지</b> 카테고리 글은 항상 제목 옆에 표시됩니다.</p>
    </div>

    <!-- 탭 -->
    <div class="tabs">
      <a class="tab ${empty selectedCategoryId ? 'active' : ''}" href="${ctx}/board">전체</a>
      <c:forEach var="c" items="${categories}">
        <c:if test="${c.categoryName ne '공지'}">
          <a class="tab ${selectedCategoryId == c.categoryId ? 'active' : ''}"
             href="${ctx}/board?categoryId=${c.categoryId}">
            <c:out value="${c.categoryName}"/>
          </a>
        </c:if>
      </c:forEach>
    </div>

    <!-- 검색/글쓰기 -->
    <form class="toolbar" method="get" action="${ctx}/board">
      <input type="text" name="keyword" value="${param.keyword}" placeholder="제목 또는 본문 키워드"/>
      <button class="btn btn-search" type="submit">검색</button>
      <a class="btn" href="${ctx}/board/write">글쓰기</a>
    </form>

    <!-- 목록 -->
    <div class="card" style="margin-top:10px">
      <table>
        <thead>
          <tr>
            <th style="width:160px">카테고리</th>
            <th>제목</th>
            <th style="width:120px">작성자</th>
            <th style="width:170px">작성일</th>
            <th style="width:90px" class="right">조회</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="p" items="${posts}">
            <tr>
              <td>
                <c:set var="catName" value="" />
                <c:forEach var="c" items="${categories}">
                  <c:if test="${c.categoryId == p.categoryId}">
                    <c:set var="catName" value="${c.categoryName}" />
                  </c:if>
                </c:forEach>
                <span class="cat-pill">
                  <c:out value="${empty catName ? p.categoryId : catName}" />
                </span>
              </td>
              <td>
                <div class="title-cell">
                  <a class="title-link" href="${ctx}/board/view?id=${p.postId}">
                    <c:out value="${p.title}"/>
                  </a>
                  <!-- ✅ 카테고리가 공지(1)일 때 빨간 라벨 -->
                  <c:if test="${p.categoryId == 1}">
                    <span class="badge">공지</span>
                  </c:if>
                </div>
              </td>
              <td><c:out value="${p.writerLoginId}"/></td>
              <td><fmt:formatDate value="${p.createdAt}" pattern="yyyy-MM-dd HH:mm"/></td>
              <td class="right">${p.viewCnt}</td>
            </tr>
          </c:forEach>
          <c:if test="${empty posts}">
            <tr><td colspan="5" style="text-align:center">게시글이 없습니다.</td></tr>
          </c:if>
        </tbody>
      </table>
    </div>

    <!-- ✅ 페이징 -->
    <div class="pagination">
      <c:if test="${page > 1}">
        <a href="${ctx}/board?page=1&categoryId=${param.categoryId}&keyword=${param.keyword}">≪</a>
        <a href="${ctx}/board?page=${page-1}&categoryId=${param.categoryId}&keyword=${param.keyword}">‹</a>
      </c:if>
      <c:if test="${page <= 1}">
        <span class="disabled">≪</span>
        <span class="disabled">‹</span>
      </c:if>

      <c:forEach var="p" begin="${startPage}" end="${endPage}">
        <a class="${p == page ? 'active' : ''}"
           href="${ctx}/board?page=${p}&categoryId=${param.categoryId}&keyword=${param.keyword}">${p}</a>
      </c:forEach>

      <c:if test="${page < totalPages}">
        <a href="${ctx}/board?page=${page+1}&categoryId=${param.categoryId}&keyword=${param.keyword}">›</a>
        <a href="${ctx}/board?page=${totalPages}&categoryId=${param.categoryId}&keyword=${param.keyword}">≫</a>
      </c:if>
      <c:if test="${page >= totalPages}">
        <span class="disabled">›</span>
        <span class="disabled">≫</span>
      </c:if>
    </div>
  </main>

   <!-- ✅ 챗봇 아이콘 -->
  <div class="chatbot-icon" onclick="toggleChatbot()">🤖</div>

  <!-- ✅ 챗봇 팝업 -->
  <div id="chatbotPopup" class="chatbot-popup">
    <div class="chatbot-header">
      <span>또야또야 MES AI</span>
      <button onclick="toggleChatbot()">X</button>
    </div>
    <!-- ✅ 처음부터 인사말 박혀 있음 -->
    <textarea id="chatLog" readonly>봇: 안녕하세요! MES 고객센터입니다 😊
궁금한 점을 물어봐주세요♡

</textarea><br>
    <input type="text" id="userInput" placeholder="메시지를 입력하세요" />
    <button id="sendBtn" onclick="sendMessage()">전송</button>
  </div>

  <script>
    function toggleChatbot() {
      const popup = document.getElementById("chatbotPopup");
      popup.style.display = (popup.style.display === "none" || popup.style.display === "") 
        ? "block" : "none";
    }

    async function sendMessage() {
      const userText = document.getElementById("userInput").value;
      if (!userText.trim()) return;

      const chatLog = document.getElementById("chatLog");
      chatLog.value += "사용자: " + userText + "\n";

      const res = await fetch("<c:out value='${ctx}'/>/chatbot", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "message=" + encodeURIComponent(userText)
      });

      const data = await res.json();
      const botReply = data.candidates?.[0]?.content?.parts?.[0]?.text || "응답 없음";

      chatLog.value += "봇: " + botReply + "\n";
      document.getElementById("userInput").value = "";
      chatLog.scrollTop = chatLog.scrollHeight; // ✅ 스크롤 자동 하단 이동
    }
  </script>

</body>
</html>
