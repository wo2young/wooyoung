<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.List"%>
<%@ page import="yaDTO.ProductionResultDTO"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>작업실적 입력</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
/* =========================
   ✅ 전역( body ) 테마 — 실적조회와 동일
   ========================= */
:root{
  --bg:#0a0f1a; --panel:#0f1626; --line:#1f2b45; --text:#e6ebff; --muted:#a7b0c5;
  --card:#111827; --line2:#4b5563; --input-bg:#1a202c;

  /* 헤더/버튼/탭 강조색(주황) */
  --primary:#f59e0b;               /* 강조 */
  --primary-hover:#d97706;         /* 강조 hover */
  --accent: var(--primary);        /* 버튼 기본색 */
  --accent-light: var(--primary-hover);

  /* 공통 사이즈 */
  --ctl-h:34px; --ctl-r:10px; --ctl-fs:14px;
  --tab-h:44px; --tab-r:12px; --tab-fs:15px;
}

html, body{
  margin:0; padding:0; min-height:100dvh;
  background:var(--bg); color:var(--text);
  font-family:Segoe UI,Pretendard,'Noto Sans KR','Apple SD Gothic Neo',Arial,sans-serif;
}

/* 레이아웃 */
.wrap{ max-width:1200px; margin:28px auto; padding:0 20px; }
h3{ margin:16px 0 12px; }

/* panel(폼 카드) */
.p-result-memo{
  border:1px solid var(--line); background:var(--card);
  border-radius:12px; padding:20px; width:100%;
  box-shadow:0 6px 16px rgba(0,0,0,.25);
}

/* 폼 그리드 */
.form-grid{ display:grid; grid-template-columns:1fr; gap:16px; }
@media (min-width:768px){ .form-grid{ grid-template-columns:1fr 1fr; } }

.field label{
  display:block; margin-bottom:6px; font-weight:600; font-size:14px; color:var(--muted);
}
.field input[type="text"],
.field input[type="number"],
.field input[type="datetime-local"],
.field select,
.field textarea{
  width:100%; padding:12px 10px; font-size:14px;
  border:1px solid var(--line2); border-radius:10px;
  background:var(--input-bg); color:#fff; height:auto;
}

/* 셀렉트 화살표 */
.field select{
  appearance:none;
  background-image:url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20' fill='%239ca3af'%3E%3Cpath fill-rule='evenodd' d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z' clip-rule='evenodd'/%3E%3C/svg%3E");
  background-repeat:no-repeat; background-position:right 10px center; padding-right:30px;
}
.field option{ background:var(--input-bg); }

/* 버튼 */
.button-group{ display:flex; justify-content:flex-end; gap:10px; margin-top:20px; }
.btn{
  background:var(--accent); color:#111827; border:none;
  border-radius:10px; padding:12px 18px; cursor:pointer;
  font-size:14px; font-weight:700; transition:background .15s, box-shadow .15s, color .15s;
}
.btn:hover{ background:var(--accent-light); box-shadow:0 0 0 3px rgba(245,158,11,.18); color:#111827; }
.btn1{
  border:1px solid var(--line2); border-radius:10px; background:transparent;
  color:var(--text); padding:12px 18px; cursor:pointer; font-size:14px; font-weight:700;
  transition:background .15s;
}
.btn1:hover{ background:rgba(255,255,255,.08); }
@media (max-width:480px){ .button-group{ flex-direction:column } .btn,.btn1{ width:100% } }

/* =========================
   ✅ 탭: 실적조회와 동일 메트릭/색
   ========================= */
.p-result-tab{
  display:flex; gap:12px; padding:6px; margin:10px 0 18px;
  background:var(--card); border:1px solid var(--line); border-radius:var(--tab-r);

  /* 폰트/렌더링 고정 */
  font-family:system-ui,-apple-system,'Segoe UI',Roboto,'Noto Sans KR','Apple SD Gothic Neo',Arial,sans-serif;
  -webkit-font-smoothing:antialiased; -moz-osx-font-smoothing:grayscale;
  text-rendering:optimizeLegibility; font-kerning:normal; letter-spacing:0;
  font-size:0; /* 상속 차단 */
}
.p-result-tab > div{ flex:1 1 0%; display:flex; }
.p-result-tab a{
  width:100%; height:var(--tab-h); padding:0 14px;
  display:flex; align-items:center; justify-content:center;
  border-radius:calc(var(--tab-r) - 2px);
  font-family:inherit; font-weight:600; font-size:var(--tab-fs); line-height:normal;
  text-decoration:none; color:var(--muted); background:transparent; border:1px solid transparent;
  transition:background-color .15s, color .15s, border-color .15s, box-shadow .15s;
}
.p-result-tab a.active{
  background:var(--bg); color:var(--primary); border-color:var(--line2);
  box-shadow:0 1px 3px rgba(0,0,0,.15);
}
.p-result-tab a:hover:not(.active){
  background:var(--input-bg); color:#fff; border-color:var(--line2);
}

/* ====== [스코프 공통 안정화] ====================================== */
.pr-new, .pr-new * { box-sizing: border-box; }
.pr-new input, .pr-new select, .pr-new textarea, .pr-new button { color-scheme: dark; }

/* iOS/안드로이드에서 입력 시 줌 확대 방지(14px일 때 확대됨) */
.pr-new .field input,
.pr-new .field select,
.pr-new .field textarea { font-size: 16px; }

/* 달력/시간 피커 아이콘이 다크에서 안 보이는 문제 */
.pr-new .field input[type="date"]::-webkit-calendar-picker-indicator,
.pr-new .field input[type="datetime-local"]::-webkit-calendar-picker-indicator {
  filter: invert(1);
  opacity: .85;
  cursor: pointer;
}

/* datetime-local placeholder 대비 ↑ */
.pr-new .field input[type="date"]::placeholder,
.pr-new .field input[type="datetime-local"]::placeholder { color: #9ca3af; }

/* 탭: font-size:0 상속 이슈 방지 (글자 사라짐/줄높이 깨짐) */
.p-result-tab a { font-size: var(--tab-fs) !important; line-height: normal; }

/* 사파리 기본 화살표 겹침 방지 */
.pr-new .field select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
}

/* 버튼들 높이·간격 균일화 */
.pr-new .btn, .pr-new .btn1 {
  min-height: 44px;
  padding: 12px 18px;
}

/* 폼 카드 안쪽 요소가 넘침으로 그림자 잘리는 문제 방지 */
.p-result-memo { overflow: hidden; }

/* (옵션) 취소 버튼을 흰색 보조 버튼으로 */
.pr-new .btn1 {
  background: #fff;
  color: #111827;
  border: 1px solid #d1d5db;
}
.pr-new .btn1:hover {
  background: #f9fafb;
  border-color: #9ca3af;
}
</style>
</head>

<body>
<%-- 컨텍스트/유저 --%>
<c:set var="cxt"  value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.loginUser}" />

<%-- ⭐ 이 페이지는 실적등록 탭 활성화(헤더/탭 둘 다 안전) --%>
<c:set var="currentPage" value="create" scope="request" />

<jsp:include page="/WEB-INF/includes/header.jsp" />

<main class="wrap pr-new"><%-- ← 스코프 추가 --%>
  <!-- 탭 (URL로도 자동판단하지만 명시적으로 create 전달) -->
  <jsp:include page="/WEB-INF/includes/p-result-tab.jsp" />

  <h3>작업실적 입력</h3>

  <form method="post" action="${cxt}/result/new">
    <div class="p-result-memo">
      <div class="form-grid">
        <div class="field">
          <label for="worker-name">작업자</label>
          <input id="worker-name" type="text" value="${empty user.name ? '작업자' : user.name}" readonly />
        </div>

        <div class="field">
          <label for="good-ea">양품 수량</label>
          <input type="number" id="good-ea" name="good_qty" placeholder="양품 수량" min="0" inputmode="numeric">
        </div>

        <div class="field">
          <label for="order-id">생산지시번호</label>
          <select id="order-id" name="order_id" required>
            <option value="">-- 생산지시를 선택하세요 --</option>
            <c:forEach var="o" items="${orders}">
              <option value="${o.order_id}" data-target="${o.remain_qty}">
  					지시번호 : <c:out value="${o.order_id}"/>
  					<c:if test="${not empty o.item_name}">&nbsp;|&nbsp;<c:out value="${o.item_name}"/></c:if>
 					 &nbsp;- 목표량 <fmt:formatNumber value="${o.remain_qty}"/>
					  <c:if test="${not empty o.due_date}">
					    &nbsp;(마감 <fmt:formatDate value="${o.due_date}" pattern="yyyy-MM-dd"/>)
					  </c:if>
					</option>
            </c:forEach>
          </select>
        </div>

        <div class="field">
          <label for="fail-ea">불량 수량</label>
          <input type="number" id="fail-ea" name="fail_qty" placeholder="불량 수량" min="0" inputmode="numeric">
        </div>

        <div class="field">
          <label for="work-date">작업일시</label>
          <input type="datetime-local" id="work-date" name="work_date" placeholder="YYYY-MM-DDThh:mm">
        </div>
      </div>

      <div class="button-group">
        <button type="button" class="btn1" onclick="history.back()">취소</button>
        <input class="btn" type="submit" value="실적 등록">
      </div>
    </div>
  </form>
</main>

<script>
document.addEventListener('DOMContentLoaded', function () {
	  const orderSel = document.getElementById('order-id');
	  const goodInp  = document.getElementById('good-ea');
	  const failInp  = document.getElementById('fail-ea');
	  let targetQty  = 0;

	  // 지시 선택 시 목표수량 저장
	  orderSel.addEventListener('change', function() {
	    const opt = orderSel.options[orderSel.selectedIndex];
	    targetQty = parseInt(opt.getAttribute('data-target')) || 0;
	    goodInp.value = '';
	    failInp.value = '';
	  });

	  // 자동 계산 및 최대값 제한
	  function recalc(e) {
	    if (!targetQty) return;

	    let g = parseInt(goodInp.value) || 0;
	    let f = parseInt(failInp.value) || 0;

	    if (e.target === goodInp) {
	      if (g > targetQty) g = targetQty; // 최대값 제한
	      goodInp.value = g;
	      failInp.value = targetQty - g;
	    } else if (e.target === failInp) {
	      if (f > targetQty) f = targetQty; // 최대값 제한
	      failInp.value = f;
	      goodInp.value = targetQty - f;
	    }
	  }

	  goodInp.addEventListener('input', recalc);
	  failInp.addEventListener('input', recalc);
	});


document.addEventListener('DOMContentLoaded', function () {
  const form = document.querySelector('form[action*="/result/new"]');
  if (!form) return;

  const els = {
    good:  document.getElementById('good-ea'),
    order: document.getElementById('order-id'),
    fail:  document.getElementById('fail-ea'),
    work:  document.getElementById('work-date')
  };

  form.addEventListener('submit', function (e) {
    const missing = [];
    const good  = (els.good.value  || '').trim();
    const order = (els.order.value || '').trim();
    const fail  = (els.fail.value  || '').trim();
    const work  = (els.work.value  || '').trim();

    if (good === ''  || isNaN(good)  || Number(good)  < 0) missing.push('양품 수량 입력 해주세요');
    if (order === '' || isNaN(order) || !Number.isInteger(Number(order)) || Number(order) < 1) missing.push('생산지시번호 입력 해주세요');
    if (fail === ''  || isNaN(fail)  || Number(fail)  < 0) missing.push('불량 수량 입력 해주세요');
    if (work === '') missing.push('작업일시 입력 해주세요');

    if (missing.length > 0) {
      e.preventDefault();
      alert('다음 항목을 입력/확인하세요:\n- ' + missing.join('\n- '));
      if (good === ''  || isNaN(good)  || Number(good)  < 0) { els.good.focus(); return; }
      if (order === '' || isNaN(order) || !Number.isInteger(Number(order)) || Number(order) < 1) { els.order.focus(); return; }
      if (fail === ''  || isNaN(fail)  || Number(fail)  < 0) { els.fail.focus(); return; }
      if (work === '') { els.work.focus(); return; }
      return;
    }

    e.preventDefault();
    const submitBtn = form.querySelector('input[type="submit"], button[type="submit"]');
    if (submitBtn) submitBtn.disabled = true;
    alert('작업실적이 등록되었습니다.');
    setTimeout(() => form.submit(), 0); // alert 이후 안전 제출
  });
});
</script>
</body>
</html>
