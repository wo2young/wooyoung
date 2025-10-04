<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>입고 등록</title>

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
            /* 강조 */
            --primary-hover: #d97706;
            /* 강조 hover */
            --accent: var(--primary);
            /* 버튼 기본색 */
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
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 20 20' fill='%239ca3af'%3E%3Cpath fill-rule='evenodd' d='M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z' clip-rule='evenodd'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 10px center;
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
        
        .btn:not(.primary) {
            background: transparent;
            color: var(--text);
            border: 1px solid var(--line2);
        }

        .btn:not(.primary):hover {
            background: rgba(255, 255, 255, .08);
        }

        /* 알럿 */
        .alert {
            display: none;
            margin: 20px auto;
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
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
input[type="number"] {
    -moz-appearance: textfield;
}
    </style>
</head>
<body>
	<c:set var="cxt" value="${pageContext.request.contextPath}" />
	<c:set var="user" value="${sessionScope.loginUser}" />

	<jsp:include page="/WEB-INF/includes/header.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/includes/Inventory_tab.jsp"></jsp:include>

	<c:if test="${not empty error}">
		<div class="alert error show">${error}</div>
	</c:if>
<%--     <c:if test="${not empty message}"> --%>
<%--         <div class="alert success show">${message}</div> --%>
<%--     </c:if> --%>

	<div class="wrap">
		<form method="post" action="${cxt}/inventory/in/submit">

			<div class="row">
				<label for="itemId">품목 선택</label> <select id="itemId" name="itemId"
					required>
					<option value="">품목 선택</option>
					<c:forEach var="item" items="${itemList}">
						<c:if test="${item.type ne 'FG'}">
							<option value="${item.id}">${item.name}(ID:${item.id})</option>
						</c:if>
					</c:forEach>
				</select>
			</div>

			<div class="row">
				<label for="qty">수량</label> <input type="number" id="qty"
					name="quantity" min="1" step="1" required />
			</div>

			<div class="row">
				<label for="locationName">보관 위치</label> <select id="locationName"
					name="locationName" required>
					<c:forEach var="loc" items="${allowedLocations}">
						<option value="${loc}">${loc}</option>
					</c:forEach>
				</select>
			</div>

			<div class="actions">
				<button type="submit" class="btn primary">입고 등록</button>
				<button type="reset" class="btn">초기화</button>
			</div>
		</form>
	</div>
	<c:if test="${not empty error}">
    <script>
        alert('${error}');
    </script>
</c:if>
<c:if test="${not empty message}">
    <script>
        alert('${message}');
    </script>
</c:if>
</body>
</html>