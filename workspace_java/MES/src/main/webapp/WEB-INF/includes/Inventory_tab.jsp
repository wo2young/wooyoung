<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<c:set var="cxt" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Inventory Tabs</title>
<style>
  :root{ --blue:#2563EB; --muted:#6B7280; --line:#E5E7EB; }

  /* 탭 메뉴 기본 스타일 */
  .inv-tabs{
    display:flex;
    gap:8px;
    border-bottom:2px solid var(--line);
    margin:0 0 16px 0;
    flex-wrap: nowrap; /* 기본값은 줄바꿈 안 함 */
  }
  
  .inv-tab{
    position:relative;
    display:inline-flex;
    align-items:center;
    padding:10px 14px;
    border-radius:10px 10px 0 0;
    color:var(--muted);
    text-decoration:none;
    font-weight:600;
    white-space: nowrap; /* 탭 이름이 잘리지 않도록 */
  }
  .inv-tab:hover{
    background:#F3F4F6;
    color:#374151;
  }
  .inv-tab.active{
    color:var(--blue);
    background:#EEF2FF;
  }
  .inv-tab.active::after{
    content:"";
    position:absolute;
    left:0;
    right:0;
    bottom:-2px;
    height:3px;
    background:var(--blue);
    border-radius:3px 3px 0 0;
  }

  /* ✅ 반응형 CSS 추가 */
  @media (max-width: 768px) {
    .inv-tabs {
      flex-wrap: wrap; /* 작은 화면에서 줄바꿈 허용 */
      gap: 4px; /* 간격 줄임 */
    }
    .inv-tab {
      padding: 8px 10px; /* 패딩 줄임 */
      font-size: 14px; /* 폰트 크기 줄임 */
    }
  }
</style>
</head>
<body>

<div class="inv-tabs" role="tablist" aria-label="재고 탭">
  <a class="inv-tab ${requestScope.currentPage eq 'in' ? 'active' : ''}" href="${cxt}/inventory/in/new"
      aria-current="${requestScope.currentPage eq 'in' ? 'page' : 'false'}">입고 등록</a>
  <a class="inv-tab ${requestScope.currentPage eq 'out' ? 'active' : ''}" href="${cxt}/inventory/out/new"
      aria-current="${requestScope.currentPage eq 'out' ? 'page' : 'false'}">출고 등록</a>
  <a class="inv-tab ${requestScope.currentPage eq 'status' ? 'active' : ''}" href="${cxt}/inventory/status?tab=status"
      aria-current="${requestScope.currentPage eq 'status' ? 'page' : 'false'}">재고 현황</a>
  <a class="inv-tab ${requestScope.currentPage eq 'history' ? 'active' : ''}" href="${cxt}/inventory/history?tab=history"
      aria-current="${requestScope.currentPage eq 'history' ? 'page' : 'false'}">입출고 이력</a>
</div>

</body>
</html>