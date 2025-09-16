<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>대시보드</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
html, body {
	margin: 0;
	padding: 0;
}

body {
	background-color: #0a0f1a; /* 다크 네이비 (통일) */
	color: #fff;
	font-family: Arial, sans-serif;
}

.wrap {
	max-width: 1200px;
	margin: 24px auto;
	padding: 0 12px;
}

.cards {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	gap: 12px;
	margin-bottom: 16px;
}

.card {
	border: 1px solid #1f2937;
	border-radius: 10px;
	padding: 16px;
	background-color: #111827;
	color: #fff;
}

.big {
	font-size: 28px;
	font-weight: 700;
}

.grid {
	display: grid;
	grid-template-columns: repeat(2, 1fr);
	gap: 16px;
}

.panel {
	border: 1px solid #1f2937;
	border-radius: 10px;
	padding: 12px;
	background: #111827;
	height: 320px;
	min-height: 320px;
	position: relative;
	overflow: hidden;
	color: #fff;
}

.panel h3 {
	margin: 0 0 8px 0;
	font-size: 16px;
}

.panel canvas {
	display: block !important;
	width: 100% !important;
	height: calc(100% - 28px) !important;
}

@media ( max-width : 900px) {
	.grid {
		grid-template-columns: 1fr;
	}
	.cards {
		grid-template-columns: 1fr;
	}
}
</style>
</head>
<body>

	<!-- 상단 헤더 -->
	<%@ include file="/WEB-INF/includes/header.jsp"%>

	<main class="wrap">
		<h2>대시보드</h2>

		<div class="cards">
			<div class="card">
				<div>오늘 생산량</div>
				<div class="big">${todayGood}</div>
			</div>
			<div class="card">
				<div>오늘 불량</div>
				<div class="big">${todayDef}</div>
			</div>
			<div class="card">
				<div>오늘 불량률</div>
				<div class="big">${todayRate}%</div>
			</div>
		</div>

		<div class="grid">
			<div class="panel">
				<h3>최근 7일 생산량</h3>
				<canvas id="chart7"></canvas>
			</div>
			<div class="panel">
				<h3>이번달 목표 & 실적</h3>
				<canvas id="chartMonth"></canvas>
			</div>
			<div class="panel">
				<h3>불량별 현황</h3>
				<canvas id="chartDefectPie"></canvas>
			</div>
			<div class="panel">
				<h3>재고 현황</h3>
				<canvas id="chartInventory"></canvas>
			</div>
		</div>
	</main>

	<script>
    // 문자열 리스트
    const labels7 = [
      <c:forEach var="x" items="${labels7}" varStatus="s">
        '<c:out value="${x}"/>'<c:if test="${!s.last}">,</c:if>
      </c:forEach>
    ];
    const labelsMonth = [
      <c:forEach var="x" items="${labelsMonth}" varStatus="s">
        '<c:out value="${x}"/>'<c:if test="${!s.last}">,</c:if>
      </c:forEach>
    ];
    const defectLabels = [
      <c:forEach var="x" items="${defectLabels}" varStatus="s">
        '<c:out value="${x}"/>'<c:if test="${!s.last}">,</c:if>
      </c:forEach>
    ];
    const inventoryLabels = [
    	  <c:forEach var="x" items="${inventoryLabels}" varStatus="s">
    	    '<c:out value="${x}"/>'<c:if test="${!s.last}">,</c:if>
    	  </c:forEach>
    	];

    // 숫자 리스트
    const prod7 = [<c:forEach var="n" items="${prod7}" varStatus="s"><c:out value="${n}"/><c:if test="${!s.last}">,</c:if></c:forEach>];
    const target = [<c:forEach var="n" items="${target}" varStatus="s"><c:out value="${n}"/><c:if test="${!s.last}">,</c:if></c:forEach>];
    const actual = [<c:forEach var="n" items="${actual}" varStatus="s"><c:out value="${n}"/><c:if test="${!s.last}">,</c:if></c:forEach>];
    const defectCounts = [<c:forEach var="n" items="${defectCounts}" varStatus="s"><c:out value="${n}"/><c:if test="${!s.last}">,</c:if></c:forEach>];
    const inventoryCounts = [
    	  <c:forEach var="n" items="${inventoryCounts}" varStatus="s">
    	    <c:out value="${n}"/><c:if test="${!s.last}">,</c:if>
    	  </c:forEach>
    	];

    // Chart.js 전역 기본
    Chart.defaults.color = '#e0e0e0';

    const commonOpts = {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { position: 'top', labels: { color: '#e0e0e0' } },
        tooltip: { mode: 'index', intersect: false }
      },
      scales: {
        y: { beginAtZero: true, ticks: { precision: 0, color: '#b0b0b0' }, grid: { color: 'rgba(255, 255, 255, 0.1)' } },
        x: { ticks: { color: '#b0b0b0' }, grid: { color: 'rgba(255, 255, 255, 0.1)' } }
      }
    };

    // 최근 7일 생산량
    new Chart(document.getElementById('chart7'), {
      type: 'line',
      data: {
        labels: labels7,
        datasets: [{
          label: '생산량',
          data: prod7,
          borderColor: '#5eead4',
          backgroundColor: 'rgba(94, 234, 212, 0.2)',
          tension: 0.4,
          fill: true
        }]
      },
      options: commonOpts
    });

    // 이번달 목표 & 실적
    new Chart(document.getElementById('chartMonth'), {
      type: 'bar',
      data: {
        labels: labelsMonth,
        datasets: [
          {
            label: '실적',
            data: actual,
            type: 'line',
            borderColor: '#f87171',
            tension: 0.4,
            order: 1
          },
          {
            label: '목표',
            data: target,
            backgroundColor: '#60a5fa',
            order: 2
          }
        ]
      },
      options: commonOpts
    });

    // 불량 코드 분포 (색상 다양화)
    new Chart(document.getElementById('chartDefectPie'), {
      type: 'doughnut',
      data: {
        labels: defectLabels,
        datasets: [{
          label: '건수',
          data: defectCounts,
          backgroundColor: [
            '#60a5fa', // 파랑
            '#34d399', // 초록
            '#fbbf24', // 노랑
            '#f87171', // 빨강
            '#a78bfa'  // 보라
          ],
          borderColor: '#0a0f1a',
          borderWidth: 2
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { position: 'top', labels: { color: '#e0e0e0' } } }
      }
    });

    new Chart(document.getElementById('chartInventory'), {
    	  type: 'line',
    	  data: {
    	    labels: inventoryLabels,
    	    datasets: [{
    	      label: '재고 수량',
    	      data: inventoryCounts,
    	      borderColor: '#60a5fa',
    	      backgroundColor: 'rgba(96,165,250,0.3)',
    	      fill: true,
    	      tension: 0.3
    	    }]
    	  },
    	  options: commonOpts
    	});
  </script>
</body>
</html>
