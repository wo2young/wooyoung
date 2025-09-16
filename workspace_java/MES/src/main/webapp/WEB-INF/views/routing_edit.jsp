<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp" %>

<jsp:include page="/WEB-INF/includes/2header.jsp">s
	<jsp:param name="activeNav" value="routing" />
</jsp:include>

<h2>공정 수정</h2>

<c:if test="${param.msg == 'upd_ok'}">
	<script>
		alert("수정 완료");
	</script>
</c:if>
<c:if test="${param.msg == 'upd_fail'}">
	<script>
		alert("수정 실패");
	</script>
</c:if>
<c:if test="${param.msg == 'del_ok'}">
	<script>
		alert("삭제 완료");
	</script>
</c:if>
<c:if test="${param.msg == 'del_fail'}">
	<script>
		alert("삭제 실패");
	</script>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/master"
	enctype="multipart/form-data">
	<input type="hidden" name="act" value="routing.update" /> <input
		type="hidden" name="routing_id" value="${routing.routing_id}" />

	<p>
		<label>품명 (FG): <select name="item_id" required>
				<c:forEach var="it" items="${itemsFG}">
					<option value="${it.item_id}"
						<c:if test="${it.item_id == routing.item_id}">selected</c:if>>
						${it.item_name} [${it.item_id}]</option>
				</c:forEach>
		</select>
		</label>
	</p>

	<p>
		<label>공정 순서: <input type="number" name="process_step"
			value="${routing.process_step}" min="1" required />
		</label>
	</p>

	<p>
		<label>이미지: <input type="file" name="img_file"
			accept="image/*" /> <input type="hidden" name="img_path"
			value="${routing.img_path}" /> <c:if
				test="${not empty routing.img_path}">
				<a
					href="${pageContext.request.contextPath}/file?path=${routing.img_path}"
					target="_blank">보기</a>
				<a
					href="${pageContext.request.contextPath}/file?path=${routing.img_path}"
					download>다운</a>
			</c:if>
		</label>
	</p>

	<p>
		<label>작업 상세: <input type="text" name="workstation"
			value="${routing.workstation}" />
		</label>
	</p>

	<div style="margin-top: 15px; display: flex; gap: 10px;">
		<!-- 수정 -->
		<button type="submit">수정 완료</button>


	</div>
</form>
<!-- 삭제 -->
<form method="post" action="${pageContext.request.contextPath}/master"
	onsubmit="return confirm('정말 삭제하시겠습니까?');" style="display: inline">
	<input type="hidden" name="act" value="routing.delete" /> <input
		type="hidden" name="routing_id" value="${routing.routing_id}" />
	<button type="submit" style="background: #d9534f; color: white;">삭제</button>
</form>

<!-- 목록 -->
<form method="get" action="${pageContext.request.contextPath}/master"
	style="display: inline">
	<input type="hidden" name="act" value="routing.list" />
	<button type="submit">목록으로</button>
</form>
