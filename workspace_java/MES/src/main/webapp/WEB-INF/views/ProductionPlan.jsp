<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="uri" value="${pageContext.request.requestURI}" />
<c:set var="cxt" value="${pageContext.request.contextPath}" />
<c:set var="pathNoCtx" value="${fn:substring(uri, fn:length(cxt), fn:length(uri))}" />

<c:set var="isPlan"
       value="${pathNoCtx == '/productionPlan' or
               fn:startsWith(pathNoCtx, '/productionPlan/') or
               fn:startsWith(pathNoCtx, '/productionPlan?') or
               fn:startsWith(pathNoCtx, '/productionPlan.do')}" />
<c:set var="isList" value="${fn:startsWith(pathNoCtx, '/productionPlan/list')}" />

<!-- 상단 탭 -->
<!-- 상단 탭 -->
<div class="ppage-tabs">
  <a href="${cxt}/productionPlan?tab=plan"
     class="ppage-tab ${param.tab eq 'plan' ? 'active' : ''}">
    생산 목표 등록
  </a>
  <a href="${cxt}/productionPlan/list?tab=list"
     class="ppage-tab ${param.tab eq 'list' ? 'active' : ''}">
    생산 목표 조회
  </a>
</div>

<div class="ppage">
  <c:if test="${not empty errorMsg}">
    <script>alert("${errorMsg}");</script>
  </c:if>
  <c:if test="${not empty sessionScope.msg}">
    <script>alert("${sessionScope.msg}");</script>
    <c:remove var="msg" scope="session"/>
  </c:if>

  <!-- 중앙 카드 -->
  <div class="ppage-card">
    <form method="post" action="${cxt}/productionPlan" autocomplete="off">
      <!-- 품목 선택 -->
      <div class="pp-row">
        <label class="pp-label">품목 선택</label>
        <div class="pp-field">
          <select name="item_id" required class="pp-input">
            <option value="">-- 품목을 선택하세요 --</option>
            <c:forEach var="item" items="${drinkItems}">
              <option value="${item.item_id}" <c:if test="${item.item_id == param.item_id}">selected</c:if>>
                ${item.item_name}
              </option>
            </c:forEach>
          </select>
          <span class="pp-caret">▾</span>
        </div>
      </div>

      <!-- 목표 수량 -->
      <div class="pp-row">
        <label class="pp-label">목표 수량</label>
        <div class="pp-field">
          <input type="number" name="target_quantity" min="1" value="${param.target_quantity}"
                 required class="pp-input" placeholder="목표 수량을 입력하세요" />
        </div>
      </div>

      <!-- 기간 -->
      <div class="pp-row">
        <label class="pp-label">기간</label>
        <div class="pp-field pp-range">
          <input id="startDate" type="date" name="period_start" value="${param.period_start}"
                 required class="pp-input" placeholder="시작일" />
          <span class="pp-tilde"></span>
          <input id="endDate" type="date" name="period_end" value="${param.period_end}"
                 required class="pp-input" placeholder="종료일" />
        </div>
      </div>

      <!-- 버튼 -->
      <div class="pp-actions">
        <button type="submit" class="btn-primary">입력 등록</button>
        <button type="reset" class="btn-ghost">초기화</button>
      </div>
    </form>
  </div>
</div>

<style>
  /* 🎨 대시보드 색 팔레트 */
  :root{
    --bg:#0b111c;
    --surface:#0f1626;
    --surface-2:#0d1423;
    --line:#253150;
    --line-2:#2b385c;
    --text:#e6ebff;
    --muted:#9fb0d6;
    --accent:#f59e0b;
    --accent-hover:#d48a07;
    --danger:#ef4444;
    --header-h:56px;
  }

  html, body{
    margin:0; padding:0;
    background:var(--bg) !important;
    color:var(--text) !important;
    font-family:system-ui,-apple-system,Segoe UI,Roboto,Pretendard,sans-serif;
    overflow-x:hidden;
  }

  .ppage{ max-width:1200px; margin:0 auto; padding:0 16px 40px; }

  /* 상단 탭 */
.ppage-tabs {
  display: flex;
  gap: 16px;
  padding: 0 16px;
  border-bottom: 1px solid var(--line);

  position: relative;   /* sticky 대신 relative */
  top: 0;               /* header 바로 아래 */
  z-index: 5;
}
.ppage-tab {
  display: inline-block;
  padding: 12px 2px;
  color: var(--muted);
  font-weight: 600;
  border-bottom: 3px solid transparent;
  transition: color .15s, border-color .15s;
  text-decoration: none;
}

.ppage-tab:hover {
  color: #e7ecff;
  border-bottom-color: #3b4e78;
}

.ppage-tab.active {
  color: #fff;
  border-bottom-color: var(--accent);
  font-weight: 700;
}
  /* 카드 */
  .ppage-card{
    max-width:640px; margin:36px auto 0;
    padding:22px 24px;
    background:var(--surface);
    border:1px solid var(--line);
    border-radius:12px;
    box-shadow:0 10px 28px rgba(3,7,18,.35);
  }

  /* 행 */
  .pp-row{ display:grid; grid-template-columns:140px 1fr; align-items:center; gap:16px; margin:14px 0; }
  .pp-label{ color:#c5d0ff; font-weight:700; }
  .pp-field{ position:relative; }

  /* input */
  .pp-input{
    width:100%; height:44px; padding:0 14px;
    border:1px solid var(--line-2);
    background:var(--surface-2); color:var(--text);
    border-radius:10px; outline:none;
    transition:border-color .15s, box-shadow .15s;
  }
  .pp-input:focus{ border-color:#5aa0ff; box-shadow:0 0 0 4px rgba(62,126,255,.2); }

  select.pp-input{ appearance:none; padding-right:36px; }
  .pp-caret{ position:absolute; right:12px; top:50%; transform:translateY(-50%); color:#8ea2c9; }

  .pp-range{ display:flex; flex-direction:column; gap:6px; }
  .pp-tilde{ text-align:center; color:#8ea2c9; font-weight:bold; }

  /* 버튼 */
  .pp-actions{ display:flex; gap:10px; justify-content:center; margin-top:18px; }
  .btn-primary{
    background:var(--accent); color:#111;
    border:none; border-radius:10px;
    padding:11px 18px; font-weight:800;
    cursor:pointer; transition:.2s;
  }
  .btn-primary:hover{ background:var(--accent-hover); }

  .btn-ghost{
    padding:10px 16px;
    background:transparent;
    border:1px solid var(--line-2);
    color:var(--text);
    border-radius:10px;
    cursor:pointer;
    transition:.2s;
  }
  /* ✅ 초기화 버튼 호버 시 흰색 */
  .btn-ghost:hover{
    background:#ffffff;
    color:#111827;
    border-color:#ffffff;
  }

  /* 반응형 */
  @media (max-width:720px){
    .pp-row{ grid-template-columns:1fr; }
    .pp-label{ margin-bottom:6px; }
    .ppage-card{ margin:20px 8px; }
  }

  ::-webkit-scrollbar{ width:0; height:0; background:transparent; }
</style>


<script>
  (function(){
    const s=document.getElementById('startDate');
    const e=document.getElementById('endDate');
    if(!s||!e) return;
    function sync(){
      if(s.value){
        e.min=s.value;
        if(e.value && e.value< s.value) e.value=s.value;
      } else e.removeAttribute('min');
    }
    s.addEventListener('change',sync);
    document.addEventListener('DOMContentLoaded',sync);
  })();
</script>
