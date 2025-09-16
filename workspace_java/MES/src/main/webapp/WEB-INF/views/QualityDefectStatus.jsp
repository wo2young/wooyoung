<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/includes/header.jsp"%>

<div style="max-width:800px; margin:24px auto; font-family:Arial, sans-serif;">

  <!-- ✅ 탭 네비게이션 -->
  <div class="tab-container">
    <a href="${pageContext.request.contextPath}/quality/new"
       class="tab ${empty param.action ? 'active' : ''}">
      불량 등록
    </a>
    <a href="${pageContext.request.contextPath}/quality/new?action=status"
       class="tab ${param.action eq 'status' ? 'active' : ''}">
      불량 현황
    </a>
  </div>

  <h2>불량 현황</h2>

  <!-- ✅ 날짜 검색 -->
  <form method="get" action="${pageContext.request.contextPath}/quality/new" 
        style="margin-bottom:20px; display:flex; align-items:center; gap:10px;">
    <input type="hidden" name="action" value="status"/>
    <label>시작일:</label>
    <input type="date" name="searchStart" value="${param.searchStart}">
    
    <label>종료일:</label>
    <input type="date" name="searchEnd" value="${param.searchEnd}">
    
    <button type="submit" class="btn-search">조회</button>
  </form>

  <!-- ✅ 요약 박스 -->
  <div style="display:flex; gap:16px; margin-bottom:20px;">
    <!-- 총 불량 수량 -->
    <div style="flex:1; background:#fef2f2; padding:20px; border-radius:8px; text-align:center; border:1px solid #fca5a5;">
      <h3 style="margin:0; font-size:16px; color:#b91c1c;">총 불량 수량</h3>
      <p style="margin:8px 0 0; font-size:20px; font-weight:bold; color:#dc2626;">
        ${summary.totalDefects} 개
      </p>
    </div>
    <!-- 평균 불량률 -->
    <div style="flex:1; background:#eff6ff; padding:20px; border-radius:8px; text-align:center; border:1px solid #93c5fd;">
      <h3 style="margin:0; font-size:16px; color:#1d4ed8;">평균 불량률</h3>
      <p style="margin:8px 0 0; font-size:20px; font-weight:bold; color:#2563eb;">
        ${summary.defectRate} %
      </p>
    </div>
  </div>

  <!-- ✅ 상세 테이블 -->
  <c:if test="${empty defectStats}">
    <p style="color:#666; text-align:center;">등록된 불량 데이터가 없습니다.</p>
  </c:if>

  <c:if test="${not empty defectStats}">
    <table border="1" cellpadding="8" cellspacing="0" width="100%" 
           style="border-collapse:collapse; background:#fff; text-align:center;">
      <thead style="background:#f5f5f5;">
        <tr>
          <th>불량코드</th>
          <th>불량명</th>
          <th>발생건수</th>
          <th>총 불량수량</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="row" items="${defectStats}">
          <tr>
            <td>${row.defect_code}</td>
            <td>${row.defect_name}</td>
            <td>${row.occurrence_count}건</td>
            <td style="color:red; font-weight:bold;">${row.total_quantity}개</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </c:if>
</div>

<!-- ✅ CSS -->
<style>
  .tab-container {
    display: flex;
    gap: 8px;
    margin-bottom: 20px;
  }
  .tab {
    flex: 1;
    text-align: center;
    padding: 10px;
    border-radius: 6px;
    text-decoration: none;
    font-weight: bold;
    background: #f3f3f3;
    color: black;
    transition: all 0.2s ease-in-out;
  }
  .tab:hover {
    background: #e5e7eb;
  }
  .tab.active {
    background: #1f2937;
    color: white;
  }
  .btn-search {
    padding: 6px 12px;
    background: #2563eb;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  .btn-search:hover {
    background: #1e40af;
  }
</style>
