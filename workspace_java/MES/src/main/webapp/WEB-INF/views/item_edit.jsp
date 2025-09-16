<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="yaDTO.ItemMasterDTO"%>

<%
ItemMasterDTO item = (ItemMasterDTO) request.getAttribute("item");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>제품 수정</title>
<style>
:root {
  --bg: #0a0f1a;
  --card: #111827;
  --line: #1f2b45;
  --line2: #4b5563;
  --text: #e6ebff;
  --muted: #a7b0c5;
  --input-bg: #1a202c;
  --primary: #f59e0b;
  --primary-hover: #d97706;
}

html, body {
	margin: 0;
	padding: 0;
	min-height: 100dvh;
	background: var(--bg);
	color: var(--text);
	font-family: Segoe UI, Pretendard, 'Noto Sans KR', Arial, sans-serif;
	font-size: 15px;
}

.wrap {
	max-width: 800px;
	margin: 40px auto;
	padding: 20px;
}

h2 {
	margin-bottom: 16px;
}

.card {
	background: var(--card);
	border: 1px solid var(--line);
	border-radius: 12px;
	padding: 20px;
	box-shadow: 0 6px 18px rgba(0, 0, 0, .25);
}

label {
	display: flex;
	flex-direction: column;
	margin-top: 12px;
	font-weight: 600;
	font-size: 14px;
	color: var(--text);
	gap: 6px;
}

input, select {
	background: var(--input-bg);
	border: 1px solid var(--line2);
	border-radius: 6px;
	color: var(--text);
	padding: 8px 10px;
	font-size: 14px;
}

input::placeholder {
	color: #6b7280;
}

input:focus, select:focus {
	outline: none;
	border-color: var(--primary);
	box-shadow: 0 0 0 2px rgba(245, 158, 11, .25);
}

/* 버튼 */
.btns {
	margin-top: 20px;
	display: flex;
	gap: 10px;
}

button {
	min-width: 100px;
	height: 36px;
	border-radius: 8px;
	border: none;
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	transition: background .2s;
}

button.primary {
	background: var(--primary);
	color: #111827;
}

button.primary:hover {
	background: var(--primary-hover);
}

button.secondary {
	background: #374151;
	color: #fff;
}

button.secondary:hover {
	background: #4b5563;
}
</style>

<script>
function toggleDetailCodes() {
  var kind = document.getElementById("kindInput").value;
  var opts = document.querySelectorAll("#detailSelect option");

  opts.forEach(o => {
    o.style.display = (o.dataset.kind === kind) ? "block" : "none";
  });

  var sel = document.getElementById("detailSelect");
  if (sel.selectedOptions.length > 0 && sel.selectedOptions[0].dataset.kind !== kind) {
    var first = document.querySelector("#detailSelect option[data-kind='" + kind + "']");
    if (first) first.selected = true;
  }
}
window.onload = toggleDetailCodes;
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/includes/2header.jsp">
		<jsp:param name="activeNav" value="products" />
	</jsp:include>

	<c:if test="${param.msg == 'upd_ok'}">
		<script>alert("수정이 완료되었습니다.");</script>
	</c:if>
	<c:if test="${param.msg == 'upd_fail'}">
		<script>alert("수정에 실패했습니다.");</script>
	</c:if>

	<div class="wrap">
		<h2>제품 수정</h2>
		<div class="card">
			<form method="post" action="master">
				<input type="hidden" name="act" value="item.update" /> 
				
				<label>
					제품 ID 
					<input type="number" name="item_id" value="<%=item.getItem_id()%>" readonly />
				</label> 
				
				<label>
					제품명 
					<input type="text" name="item_name" value="<%=item.getItem_name()%>" required />
				</label> 
				
				<label>
					LOT 코드 
					<input type="text" name="lot_code" value="<%=item.getLot_code()%>" />
				</label> 
				
				<label>
					유통기한(일) 
					<input type="number" name="self_life_day" value="<%=item.getSelf_life_day()%>" min="0" />
				</label> 
				
				<label>
					종류 
					<select name="kind" id="kindInput" onchange="toggleDetailCodes()">
						<option value="FG" ${item.kind eq 'FG' ? 'selected' : ''}>완제품(FG)</option>
						<option value="RM" ${item.kind eq 'RM' ? 'selected' : ''}>원재료(RM)</option>
					</select>
				</label> 
				
				<label>
					상세코드 
					<select name="detail_code" id="detailSelect">
						<c:forEach var="d" items="${pcdDetails}">
							<option value="${d.detail_code}" data-kind="FG"
								${item.detail_code eq d.detail_code ? 'selected' : ''}>
								${d.codeDname}</option>
						</c:forEach>
						<c:forEach var="d" items="${fcdDetails}">
							<option value="${d.detail_code}" data-kind="RM"
								${item.detail_code eq d.detail_code ? 'selected' : ''}>
								${d.codeDname}</option>
						</c:forEach>
					</select>
				</label> 
				
				<label>
					규격 
					<input type="text" name="item_spec" value="<%=item.getItem_spec()%>" />
				</label> 
				
				<label>
					단위 
					<input type="text" name="unit" value="<%=item.getUnit()%>" />
				</label>

				<div class="btns">
					<button type="submit" class="primary">수정 완료</button>
					<button type="button" class="secondary" onclick="location.href='master?act=item.list'">목록으로</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
