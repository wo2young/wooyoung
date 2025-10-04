<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 컨텍스트 경로/세션 사용자/탭 상태 --%>
<c:set var="cxt" value="${pageContext.request.contextPath}" />
<c:set var="user" value="${sessionScope.loginUser}" />
<c:set var="currentPage" value="out" scope="request" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>출고 등록</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<style>
/* =========================
   ✅ 전역( body ) 테마 - 다크모드
   ========================= */
:root {
    --bg: #0a0f1a;
    --panel: #0f1626;
    --line: #1f2b45;
    --text: #e6ebff;
    --muted: #a7b0c5;
    --card: #111827;
    --line2: #4b5563;
    --input-bg: #1a202c;

    /* 헤더/버튼/탭 강조색(주황) */
    --primary: #f59e0b;
    --primary-hover: #d97706;
    --accent: var(--primary);
    --accent-light: var(--primary-hover);

    /* 공통 사이즈 */
    --ctl-h: 34px;
    --ctl-r: 10px;
    --ctl-fs: 14px;
    --tab-h: 44px;
    --tab-r: 12px;
    --tab-fs: 15px;
}

html, body {
    margin: 0;
    padding: 0;
    min-height: 100dvh;
    background: var(--bg);
    color: var(--text);
    font-family: Segoe UI, Pretendard, 'Noto Sans KR', 'Apple SD Gothic Neo', Arial, sans-serif;
    font-size: 16px;
}

/* 레이아웃 */
.wrap {
    max-width: 600px;
    margin: 28px auto;
    padding: 20px;
}

/* 폼 카드 */
form {
    border: 1px solid var(--line);
    background: var(--card);
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 6px 16px rgba(0, 0, 0, .25);
}

.row {
    display: grid;
    grid-template-columns: 140px 1fr;
    align-items: center;
    gap: 10px 16px;
    margin-bottom: 16px;
}

label {
    display: block;
    font-weight: 600;
    font-size: 14px;
    color: var(--muted);
}

input[type="number"],
select {
    width: 100%;
    padding: 12px 10px;
    font-size: 16px;
    border: 1px solid var(--line2);
    border-radius: 10px;
    background: var(--input-bg);
    color: #fff;
    height: auto;
    box-sizing: border-box;
    transition: border-color .15s ease, box-shadow .15s ease;
}

input[type="number"]:focus,
select:focus {
    outline: none;
    border-color: var(--primary);
    box-shadow: 0 0 0 3px rgba(245, 158, 11, .18);
}

/* 셀렉트 화살표 */
select {
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20' fill='%239ca3af'%3E%3Cpath fill-rule='evenodd' d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z' clip-rule='evenodd'/%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-position: right 0.75rem center;
    padding-right: 30px;
}

option {
    background: var(--input-bg);
}

/* 버튼 */
.actions {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 20px;
}

.btn {
    min-width: 120px;
    padding: 12px 18px;
    border-radius: 10px;
    border: none;
    cursor: pointer;
    font-size: 14px;
    font-weight: 700;
    transition: background .15s, box-shadow .15s, color .15s;
}

.btn:hover {
    box-shadow: 0 0 0 3px rgba(245, 158, 11, .18);
}

.btn.primary {
    background: var(--accent);
    color: #111827;
}

.btn.primary:hover {
    background: var(--accent-light);
    color: #111827;
}

.btn.secondary {
    background: transparent;
    color: var(--text);
    border: 1px solid var(--line2);
}

.btn.secondary:hover {
    background: rgba(255, 255, 255, .08);
}

/* 알럿 */
.alert {
    display: none;
    margin: 20px auto 0;
    max-width: 600px;
    padding: 12px 14px;
    border-radius: 10px;
    border: 1px solid;
    font-size: 14px;
    line-height: 1.4;
    text-align: center;
}

.alert.show {
    display: block;
}

.alert.success {
    background-color: #27ae60;
    color: #fff;
    border-color: #2ecc71;
}

.alert.error {
    background-color: #c0392b;
    color: #fff;
    border-color: #e74c3c;
}

/* input number 스핀 버튼 숨기기 */
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

input[type="number"] {
    -moz-appearance: textfield;
}

/* 반응형 */
@media (max-width: 640px) {
    .row {
        grid-template-columns: 1fr;
    }
    .actions {
        flex-direction: column;
    }
    .btn {
        width: 100%;
    }
}
/* 크롬, 엣지, 사파리 */
::-webkit-scrollbar {
  display: none;
}

/* 파이어폭스 */
html {
  scrollbar-width: none;
}

/* IE, Edge(레거시) */
html, body {
  -ms-overflow-style: none;
}
/* ===== 스크롤 제어: 전역은 숨기고 .wrap만 내부 스크롤 ===== */

/* 1) 전역 스크롤 완전 차단 */
html, body {
  height: 100dvh;      /* 모바일 주소창 대응 */
  overflow: hidden;    /* 페이지 스크롤 막기 */
}

/* 2) 헤더/탭을 제외한 .wrap만 스크롤 영역으로 */
:root{
  /* 헤더/탭 높이가 다르면 여기 숫자만 조절 */
  --header-h: 56px;    /* header.jsp 높이 추정 */
  --tab-h: 44px;       /* Inventory_tab.jsp 높이 추정 */
  --vpad: 28px;        /* .wrap의 위아래 마진 합 보정 */
}

/* .wrap이 화면 안에서만 스크롤되도록 높이 제한 + 스크롤 허용 */
.wrap{
  max-height: calc(100dvh - var(--header-h) - var(--tab-h) - var(--vpad));
  overflow: auto;
  overscroll-behavior: contain; /* 바운스 방지(모바일) */
}

/* 3) .wrap 스크롤바만 숨기기(동작은 유지) */
.wrap::-webkit-scrollbar { display: none; }  /* 크롬/사파리/엣지 */
.wrap { scrollbar-width: none; }             /* 파이어폭스 */

</style>




</head>
<body>
	<jsp:include page="/WEB-INF/includes/header.jsp"></jsp:include>
	
	
	<jsp:include page="/WEB-INF/includes/Inventory_tab.jsp"></jsp:include>
<div class="wrap">

  <form method="post" action="${cxt}/inventory/out/new" autocomplete="off">
    <input type="hidden" name="createdBy" value="<c:out value='${user != null ? user.user_id : 0}'/>" />

    <!-- 품목 -->
<!-- 품목 -->
<div class="row">
  <label for="itemId">품목</label>
  <select id="itemId" name="itemId" required>
    <option value="">제품 선택</option>
    <c:forEach var="it" items="${items}">
      <option value="${it.item_id}">
        <c:out value="${it.item_name}"/>
      </option>
    </c:forEach>
  </select>
</div>


    <!-- LOCATION -->
    <div class="row">
      <label for="locationName">출고 </label>
      <select id="locationName" name="locationName" required>
        <option value="">창고선택</option>
        <c:forEach var="loc" items="${allowedLocations}">
          <option value="${loc}"><c:out value="${loc}"/></option>
        </c:forEach>
      </select>
    </div>

    <!-- LOT -->
    <div class="row">
      <label for="lotNo">LOT</label>
      <select id="lotNo" name="lotNo" required disabled>
        <option value="">먼저 품목과 창고를 선택하세요</option>
      </select>
    </div>

    <!-- 수량 -->
    <div class="row">
      <label for="qty">출고 수량</label>
      <input type="number" id="qty" name="qty" min="0" step="0.001" required />
    </div>


    <div class="actions">
      <button class="btn primary" type="submit">출고 등록</button>
      <button class="btn secondary" type="button" onclick="resetForm()">초기화</button>
    </div>

    <!-- 서버에서 내려준 메시지 표시(선택) -->
    <div id="msgErr" class="alert error <c:out value='${not empty error ? "show" : ""}'/>">
      <c:out value="${error}"/>
    </div>
  </form>
</div>

<script>
document.addEventListener('DOMContentLoaded', function () {
  var CXT  = '<c:out value="${cxt}"/>';
  var $item = document.getElementById('itemId');
  var $loc  = document.getElementById('locationName');
  var $lot  = document.getElementById('lotNo');
  var $qty  = document.getElementById('qty');
  let currentStock = 0;

  $lot.disabled = true;
  $item.addEventListener('change', loadLots);
  $loc.addEventListener('change', loadLots);

  async function loadLots(){
    var itemId = $item.value;
    var loc    = $loc.value;

    if(!itemId || !loc){
      $lot.innerHTML = '<option value="">먼저 품목과 LOCATION을 선택하세요</option>';
      $lot.disabled = true;
      currentStock = 0;
      return;
    }

    var locUpper = loc.trim().toUpperCase();
    var url = CXT + '/inventory/lot/list'
            + '?itemId=' + encodeURIComponent(itemId)
            + '&locationName=' + encodeURIComponent(locUpper);

    try {
      const r = await fetch(url, { headers: { 'Accept': 'application/json' }, cache: 'no-store' });
      if (!r.ok) throw new Error('HTTP ' + r.status);
      const list = await r.json();

      $lot.innerHTML = '';
      if (!Array.isArray(list) || list.length === 0) {
        $lot.innerHTML = '<option value="">해당 조건의 LOT이 없습니다</option>';
        $lot.disabled = true;
        currentStock = 0;
        return;
      }

      const opts = ['<option value="">-- LOT 선택 --</option>'];
      list.forEach(function(row){
        var label = row.expire ? (row.lotNo + ' / 잔량 ' + row.qty + ' / 유통기한 ' + row.expire)
                               : (row.lotNo + ' / 잔량 ' + row.qty);
        // ✅ data-qty 속성에 잔량 저장
        opts.push('<option value="' + row.lotNo + '" data-qty="' + row.qty + '">' + label + '</option>');
      });
      $lot.innerHTML = opts.join('');
      $lot.disabled = false;
    } catch (e) {
      console.error(e);
      $lot.innerHTML = '<option value="">LOT 조회 오류</option>';
      $lot.disabled = true;
      currentStock = 0;
    }
  }

//LOT 선택 시 잔량 저장
  $lot.addEventListener('change', function(){
    const opt = $lot.options[$lot.selectedIndex];
    currentStock = opt ? parseFloat(opt.getAttribute('data-qty')) || 0 : 0;
    $qty.value = '';
    if (currentStock > 0) {
      $qty.setAttribute('max', currentStock);   // 브라우저 기본 제약
    } else {
      $qty.removeAttribute('max');
    }
  });

  // 출고 수량 입력 시 초과 방지 (alert 없음)
  $qty.addEventListener('input', function(){
    let v = parseFloat($qty.value) || 0;
    if (currentStock && v > currentStock) {
      $qty.value = currentStock;  // 최대값으로 자동 보정
    } else if (v < 0) {
      $qty.value = 0;             // 음수 → 0
    }
  });

  // 제출 시 중복 방지 + 알림
  var form = document.querySelector('form[action*="/inventory/out/new"]');
  if (form) {
    var submitBtn = form.querySelector('button[type="submit"]');
    var isSubmitting = false;

    form.addEventListener('submit', function(e){
      if (form.checkValidity && !form.checkValidity()) return;
      if (isSubmitting) { e.preventDefault(); return; }

      e.preventDefault();
      isSubmitting = true;

      alert('출고 등록이 완료되었습니다.');
      if (submitBtn) submitBtn.disabled = true;

      HTMLFormElement.prototype.submit.call(form);
    });
  }

  // reset 버튼
  window.resetForm = function(){
    if (!form) return;
    form.reset();
    $lot.innerHTML = '<option value="">먼저 품목과 창고를 선택하세요</option>';
    $lot.disabled = true;
    currentStock = 0;
    var submitBtn = form.querySelector('button[type="submit"]');
    if (submitBtn) submitBtn.disabled = false;
  };
});
</script>
</body>
</html>