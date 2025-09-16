<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="cxt" value="${pageContext.request.contextPath}" />
<c:set var="uri" value="${pageContext.request.requestURI}" />
<c:set var="isCreate" value="${(currentPage eq 'create') or fn:contains(uri, '/result/new')}" />
<c:set var="isSearch" value="${(currentPage eq 'search') or fn:contains(uri, '/result/search')}" />

<style>
/* ── 공통 탭 레이아웃 ───────────────────────────── */
.p-result-tab { box-sizing:border-box; width:100%; overflow:visible; margin:10px 0 18px; }
.p-result-tab .tabbar{
  display:flex; gap:12px; padding:6px;
  background:var(--card); border:1px solid var(--line); border-radius:12px;
  font-family:system-ui,-apple-system,'Segoe UI',Roboto,'Noto Sans KR','Apple SD Gothic Neo',Arial,sans-serif;
  -webkit-font-smoothing:antialiased; -moz-osx-font-smoothing:grayscale;
  text-rendering:optimizeLegibility; font-kerning:normal; letter-spacing:0; font-size:0;
}
.p-result-tab .slot{ flex:1 1 0%; display:flex; min-width:0; }
.p-result-tab .slot a{
  width:100%; height:var(--tab-h,44px); padding:0 14px;
  display:flex; align-items:center; justify-content:center;
  border-radius:10px; border:1px solid transparent;
  text-decoration:none; background:transparent; color:var(--muted);
  font-family:inherit; font-weight:600; font-size:var(--tab-fs,15px); line-height:normal;
  transition:color .15s ease;
  box-sizing:border-box;
}

/* ── ✨ 박스 없고 글씨만 주황 ───────────────────── */
.p-result-tab .slot a:hover{
  background:transparent !important;
  border-color:transparent !important;
  box-shadow:none !important;
  color:var(--primary, #f59e0b) !important;  /* 호버 = 주황 */
}
.p-result-tab .slot a.active{
  background:transparent !important;
  border-color:transparent !important;
  box-shadow:none !important;
  color:var(--primary, #f59e0b) !important;  /* 활성 = 주황 */
}

/* 혹시 파란색으로 재정의된 경우를 차단하고 싶으면 탭 컨테이너에 지역 변수로 고정 */
.p-result-tab { --primary:#f59e0b; }

</style>

<div id="presult-tab" class="p-result-tab">
  <div class="slot">
    <a href="${cxt}/result/new"    class="${isCreate ? 'active' : ''}">실적등록</a>
  </div>
  <div class="slot">
    <a href="${cxt}/result/search" class="${isSearch ? 'active' : ''}">실적조회</a>
  </div>
</div>

<script>
/* 서버에서 active 못 잡은 경우 대비용(URL로 보정) */
(function(){
  const root = document.getElementById('presult-tab');
  if(!root) return;
  if (!root.querySelector('a.active')) {
    const path = location.pathname;
    root.querySelectorAll('a[data-match]').forEach(a=>{
      if (path.indexOf(a.getAttribute('data-match')) !== -1) a.classList.add('active');
    });
  }
})();
</script>
