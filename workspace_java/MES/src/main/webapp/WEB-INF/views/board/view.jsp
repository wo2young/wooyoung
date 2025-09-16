<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="/WEB-INF/includes/header.jsp" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="loginUserId" value="${sessionScope.loginUserId}" />
<c:set var="isAdmin" value="${sessionScope.isAdmin}" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8"/>
<title><c:out value="${post.title}"/> - 게시글</title>
<style>
  /* ===== 남색 다크톤 팔레트 ===== */
  :root{
    --bg:#0a0f1a;
    --panel:#0f1626;
    --line:#1f2b45;
    --text:#e6ebff;
    --muted:#a7b0c5;
    --input:#1a202c;
    --accent:#f59e0b;          
    --accent-hover:#d97706;
    --blue:#1e3a8a;            
    --blue-hover:#2563eb;
    --danger:#c2410c;          
    --danger-bg:#7f1d1d;
    --danger-border:#b91c1c;
    --hover:#111a2b;
  }

  *{box-sizing:border-box}
  html,body{
    margin:0; padding:0;
    background:var(--bg);
    color:var(--text);
    font-family:system-ui, Segoe UI, Arial, 'Noto Sans KR', 'Apple SD Gothic Neo', sans-serif;
  }

  /* 레이아웃 */
  .wrap{max-width:900px;margin:24px auto;padding:0 16px}

  /* 카드 */
  .card{
    background:var(--panel);
    border:1px solid var(--line);
    border-radius:12px;
    overflow:hidden;
  }
  .card-body{padding:16px}

  /* 제목/메타 */
  .title{
    margin:0 0 6px;
    font-size:22px;
    line-height:1.35;
    word-break:break-word;
    color:var(--text);
  }
  .sub{
    display:flex;gap:10px;flex-wrap:wrap;align-items:center;
  }
  .meta{color:var(--muted);font-size:13px}

  .pill{
    display:inline-block;
    padding:2px 8px;border-radius:999px;
    font-size:12px;font-weight:600;
    color:#111827;background:var(--accent);
    border:1px solid var(--accent);
  }

  /* 본문 */
  .content{
    white-space:pre-wrap;
    border-top:1px solid var(--line);
    padding-top:12px;margin-top:12px;
    line-height:1.7;
    color:var(--text);
  }

  /* 파일 리스트 */
  .divider{height:1px;background:var(--line);margin:16px 0}
  .files{margin-top:8px}
  .file-item{
    display:flex;align-items:center;gap:8px;
    padding:6px 0;border-top:1px dashed var(--line);color:var(--text);
  }
  .file-item:first-child{border-top:none}

  /* 버튼 공통 */
  .btn{
    display:inline-block;
    min-width:84px;
    text-align:center;
    padding:8px 12px;
    border-radius:10px;
    cursor:pointer;
    text-decoration:none;
    font-weight:600;
    border:1px solid var(--line);
    background:var(--panel);
    color:var(--text);
    transition:background .15s, transform .15s, border-color .15s, color .15s, box-shadow .15s;
  }
  .btn:hover{background:var(--hover);transform:translateY(-1px)}

  .btn-primary{background:var(--accent);border-color:var(--accent);color:#111827}
  .btn-primary:hover{background:var(--accent-hover);border-color:var(--accent-hover);color:#111827}

  .btn-blue{background:var(--blue);border-color:var(--blue);color:#e6ebff}
  .btn-blue:hover{background:var(--blue-hover);border-color:var(--blue-hover);color:#fff}

  .btn-danger{
    background:transparent;
    border-color:var(--danger-border);
    color:#fecaca;
  }
  .btn-danger:hover{
    background:var(--danger-bg);
    border-color:var(--danger-border);
    color:#fff;
    box-shadow:0 0 0 3px rgba(220,38,38,.15);
  }

  /* ===== toolbar 버튼 줄정렬 & 크기 통일 ===== */
  .toolbar{
    display:flex;
    align-items:center;
    gap:12px;
    flex-wrap:nowrap;          /* 줄바꿈 방지 */
    margin:12px 0;
    white-space:nowrap;        /* 이모지/문자 줄바꿈 방지 */
  }

  /* form 기본 여백 제거 + 한 줄 정렬 */
  .toolbar > form{
    display:flex;              
    align-items:center;
    margin:0;
  }

  /* a 링크도 버튼처럼 정렬 */
  .toolbar > a{
    display:flex;
    align-items:center;
    justify-content:center;
  }

  /* 버튼/링크 공통 크기 완전 통일 */
  .toolbar .btn,
  .toolbar button{
    display:flex;
    align-items:center;
    justify-content:center;
    height:44px;               
    min-width:110px;           
    padding:0 16px;            
    line-height:1;             
    border-radius:10px;        
  }

  .toolbar .btn + .btn,
  .toolbar form + form,
  .toolbar form + a,
  .toolbar a + form,
  .toolbar a + a{
    margin:0 !important;
  }

  /* 댓글 */
  .cm-row{margin-top:12px}
  .cm-row.reply{margin-left:20px;border-left:2px solid var(--line);border-radius:8px}
  .cm-meta{font-weight:700;color:var(--text)}
  .cm-text{white-space:pre-wrap;margin:4px 0 8px;line-height:1.6;color:var(--text)}

  textarea{
    width:100%;padding:10px;border:1px solid var(--line);border-radius:10px;
    background:var(--input);color:var(--text);
  }
  textarea:focus{
    outline:none;border-color:var(--accent);box-shadow:0 0 0 2px rgba(245,158,11,.2);
  }

  details summary{
    cursor:pointer;color:var(--blue-hover);
  }
  details[open] summary{color:var(--accent)}
</style>
</head>
<body>


  <main class="wrap">

    <!-- 게시글 카드 -->
    <div class="card">
      <div class="card-body">
        <h2 class="title"><c:out value="${post.title}"/></h2>
        <div class="sub">
          <span class="meta">글번호 #${post.postId}</span>
          <span class="meta">조회수 ${post.viewCnt}</span>
          <span class="meta">작성일 <fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm"/></span>
          <c:if test="${post.isNotice == 'Y'}"><span class="pill">공지</span></c:if>
        </div>

        <!-- 본문 -->
        <div class="content"><c:out value="${post.content}"/></div>

        <!-- 첨부파일 -->
        <c:if test="${not empty attachments}">
          <div class="divider"></div>
          <div class="files">
            <strong>첨부</strong>
            <c:forEach var="f" items="${attachments}">
              <div class="file-item">
                📎 <span><c:out value="${f.origName}"/></span>
                <span class="meta">(${f.fileSize} bytes)</span>
              </div>
            </c:forEach>
          </div>
        </c:if>

        <!-- 동작 버튼 -->
        <div class="toolbar">
          <form method="post" action="${ctx}/board/like/toggle">
            <input type="hidden" name="postId" value="${post.postId}" />
            <button class="btn btn-blue" type="submit">❤️ 좋아요 (${post.likeCnt})</button>
          </form>

          <!-- 수정 -->
          <c:if test="${isAdmin or loginUserId == post.createdBy}">
            <a class="btn" href="${ctx}/board/edit?id=${post.postId}">수정</a>
          </c:if>

          <!-- 삭제 -->
          <c:if test="${loginUserId == post.createdBy}">
            <form method="post" action="${ctx}/board/delete"
                  onsubmit="return confirm('정말 글을 삭제하시겠습니까?');">
              <input type="hidden" name="postId" value="${post.postId}" />
              <button class="btn btn-danger" type="submit">삭제</button>
            </form>
          </c:if>

          <a class="btn btn-primary" href="${ctx}/board">목록</a>
        </div>
      </div>
    </div>

    <!-- 댓글 -->
    <h3 id="comments" style="margin:18px 0 8px;color:var(--text)">댓글</h3>

    <c:forEach var="cm" items="${comments}">
      <div class="card cm-row ${cm.level == 2 ? 'reply' : ''}">
        <div class="card-body">
          <div>
            <span class="cm-meta"><c:out value="${cm.writerName}"/></span>
            <span class="meta"> · <fmt:formatDate value="${cm.createdAt}" pattern="yyyy-MM-dd HH:mm"/></span>
          </div>
          <div class="cm-text"><c:out value="${cm.content}"/></div>

          <!-- 답글 폼 -->
          <c:if test="${cm.level == 1}">
            <details>
              <summary>답글</summary>
              <form method="post" action="${ctx}/board/comment/add" style="margin-top:6px">
                <input type="hidden" name="postId" value="${post.postId}"/>
                <input type="hidden" name="parentId" value="${cm.commentId}"/>
                <textarea name="content" rows="3" placeholder="답글을 입력하세요" required></textarea>
                <div style="margin-top:6px">
                  <button class="btn btn-primary" type="submit">등록</button>
                </div>
              </form>
            </details>
          </c:if>

          <!-- 댓글 삭제 -->
          <c:if test="${loginUserId == cm.createdBy}">
            <form method="post" action="${ctx}/board/comment/delete" style="display:inline"
                  onsubmit="return confirm('댓글을 삭제할까요?');">
              <input type="hidden" name="commentId" value="${cm.commentId}"/>
              <input type="hidden" name="postId" value="${post.postId}"/>
              <button class="btn btn-danger" type="submit">삭제</button>
            </form>
          </c:if>
        </div>
      </div>
    </c:forEach>

    <!-- 최상위 댓글 작성 -->
    <div class="card" style="margin-top:14px">
      <div class="card-body">
        <form method="post" action="${ctx}/board/comment/add">
          <input type="hidden" name="postId" value="${post.postId}"/>
          <textarea name="content" rows="4" placeholder="댓글을 입력하세요" required></textarea>
          <div style="margin-top:8px">
            <button class="btn btn-primary" type="submit">댓글 등록</button>
          </div>
        </form>
      </div>
    </div>

  </main>
</body>
</html>
