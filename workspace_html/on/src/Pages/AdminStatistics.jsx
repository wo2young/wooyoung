import React, { useEffect, useRef, useState } from "react";
import Chart from "chart.js/auto";
import "../Styles/AdminStatistics.css";
import AdminHeader from "../components/AdminHeader";
import AdminSidebar from "../components/AdminSidebar";

// ----------- 데이터/라벨 정의 -----------
const LABELS = {
  day: ["8월 1일", "8월 2일", "8월 3일", "8월 4일", "8월 5일", "8월 6일", "8월 7일"],
  week: ["7월 1주", "7월 2주", "7월 3주", "7월 4주", "8월 1주", "8월 2주", "8월 3주", "8월 4주"],
  month: ["3월", "4월", "5월", "6월", "7월", "8월"],
  year: ["2022년", "2023년", "2024년", "2025년"]
};
const viewText = {
  totalMatches: "전체 매칭 횟수",
  avgConnectTime: "평균 접속 시간",
  matchCount: "매칭 성공/실패 횟수",
  avgMatchSuccessTime: "평균 매칭 성공 시간",
  userRatio: "도움요청자/제공자 비율"
};
const periodText = {
  day: "일", week: "주", month: "월", year: "년"
};
const pieCategoryText = {
  reason: "매칭 실패 사유",
  age: "연 령",
  gender: "성 별",
  region: "지역 별 분포",
  mileage: "마일리지 사용처 순위"
};

// ----------- 파이차트용(카테고리+기간) 함수 -----------
const getPieChartData = (category, period = "week") => {
  const base = {
    reason: {
      labels: ["시간 착각", "불친절", "위치 실수", "요구 변경"],
      colors: ["#FF9800", "#F44336", "#9C27B0", "#3F51B5"],
      day:   [12, 8, 6, 4],
      week:  [74, 67, 44, 34],
      month: [280, 210, 145, 95],
      year:  [900, 720, 370, 250]
    },
    age: {
      labels: ["10대", "20대", "30대", "40대 이상"],
      colors: ["#4CAF50", "#00BCD4", "#FFC107", "#FF5722"],
      day:   [1, 3, 4, 2],
      week:  [10, 23, 28, 12],
      month: [50, 68, 72, 34],
      year:  [170, 320, 430, 152]
    },
    gender: {
      labels: ["남성", "여성", "기타"],
      colors: ["#1976D2", "#E91E63", "#BDBDBD"],
      day:   [4, 5, 1],
      week:  [51, 53, 2],
      month: [19, 20, 1],
      year:  [320, 338, 11]
    },
    region: {
      labels: ["천안", "서울", "부산", "기타"],
      colors: ["#673AB7", "#009688", "#FF9800", "#9E9E9E"],
      day:   [3, 2, 1, 2],
      week:  [32, 21, 18, 13],
      month: [24, 18, 14, 13],
      year:  [79, 58, 43, 22]
    },
    mileage: {
      labels: ["식비", "교통", "편의점", "기타"],
      colors: ["#C2185B", "#7B1FA2", "#0288D1", "#8BC34A"],
      day:   [3, 2, 1, 1],
      week:  [42, 19, 15, 8],
      month: [34, 19, 11, 6],
      year:  [133, 56, 38, 13]
    }
  };
  if (!base[category]) return { labels: [], data: [], colors: [] };
  return {
    labels: base[category].labels,
    data: base[category][period] || [],
    colors: base[category].colors
  };
};

const getPieRank = (category, period = "week") => {
  // 값 예시, 직접 원하는 퍼센트·수치로 바꿔도 됨(단, getPieChartData와 비율 맞출 것!)
  if (category === "reason") {
    switch (period) {
      case "day": return [
        { label: "시간 착각", percent: "37%", count: "12회", bar: "37%" },
        { label: "불친절", percent: "25%", count: "8회", bar: "25%" },
        { label: "위치 실수", percent: "18%", count: "6회", bar: "18%" },
        { label: "요구 변경", percent: "12%", count: "4회", bar: "12%" }
      ];
      case "week": return [
        { label: "시간 착각", percent: "34.0%", count: "74회", bar: "34%" },
        { label: "불친절", percent: "32.0%", count: "67회", bar: "32%" },
        { label: "위치 실수", percent: "18.0%", count: "44회", bar: "18%" },
        { label: "요구 변경", percent: "16.0%", count: "34회", bar: "16%" }
      ];
      case "month": return [
        { label: "시간 착각", percent: "37%", count: "280회", bar: "37%" },
        { label: "불친절", percent: "28%", count: "210회", bar: "28%" },
        { label: "위치 실수", percent: "19%", count: "145회", bar: "19%" },
        { label: "요구 변경", percent: "13%", count: "95회", bar: "13%" }
      ];
      case "year": return [
        { label: "시간 착각", percent: "38%", count: "900회", bar: "38%" },
        { label: "불친절", percent: "30%", count: "720회", bar: "30%" },
        { label: "위치 실수", percent: "16%", count: "370회", bar: "16%" },
        { label: "요구 변경", percent: "11%", count: "250회", bar: "11%" }
      ];
      default: return [];
    }
  }
  if (category === "age") {
    switch (period) {
      case "day": return [
        { label: "30대", percent: "40%", count: "4명", bar: "40%" },
        { label: "20대", percent: "30%", count: "3명", bar: "30%" },
        { label: "40대 이상", percent: "20%", count: "2명", bar: "20%" },
        { label: "10대", percent: "10%", count: "1명", bar: "10%" }
      ];
      case "week": return [
        { label: "30대", percent: "35.4%", count: "28명", bar: "35%" },
        { label: "20대", percent: "29.1%", count: "23명", bar: "29%" },
        { label: "40대 이상", percent: "15.2%", count: "12명", bar: "15%" },
        { label: "10대", percent: "12.7%", count: "10명", bar: "13%" }
      ];
      case "month": return [
        { label: "30대", percent: "38%", count: "27명", bar: "38%" },
        { label: "20대", percent: "33%", count: "23명", bar: "33%" },
        { label: "40대 이상", percent: "18%", count: "12명", bar: "18%" },
        { label: "10대", percent: "11%", count: "8명", bar: "11%" }
      ];
      case "year": return [
        { label: "30대", percent: "42%", count: "64명", bar: "42%" },
        { label: "20대", percent: "30%", count: "48명", bar: "30%" },
        { label: "40대 이상", percent: "16%", count: "26명", bar: "16%" },
        { label: "10대", percent: "12%", count: "18명", bar: "12%" }
      ];
      default: return [];
    }
  }
  if (category === "gender") {
    switch (period) {
      case "day": return [
        { label: "여성", percent: "50%", count: "5명", bar: "50%" },
        { label: "남성", percent: "40%", count: "4명", bar: "40%" },
        { label: "기타", percent: "10%", count: "1명", bar: "10%" }
      ];
      case "week": return [
        { label: "여성", percent: "48.2%", count: "53명", bar: "48%" },
        { label: "남성", percent: "46.4%", count: "51명", bar: "46%" },
        { label: "기타", percent: "1.8%", count: "2명", bar: "2%" }
      ];
      case "month": return [
        { label: "여성", percent: "51%", count: "20명", bar: "51%" },
        { label: "남성", percent: "47%", count: "19명", bar: "47%" },
        { label: "기타", percent: "2%", count: "1명", bar: "2%" }
      ];
      case "year": return [
        { label: "여성", percent: "52%", count: "338명", bar: "52%" },
        { label: "남성", percent: "46%", count: "300명", bar: "46%" },
        { label: "기타", percent: "2%", count: "11명", bar: "2%" }
      ];
      default: return [];
    }
  }
  if (category === "region") {
    switch (period) {
      case "day": return [
        { label: "천안", percent: "30%", count: "3명", bar: "30%" },
        { label: "서울", percent: "20%", count: "2명", bar: "20%" },
        { label: "부산", percent: "10%", count: "1명", bar: "10%" },
        { label: "기타", percent: "40%", count: "4명", bar: "40%" }
      ];
      case "week": return [
        { label: "천안", percent: "39.5%", count: "32명", bar: "40%" },
        { label: "서울", percent: "25.9%", count: "21명", bar: "26%" },
        { label: "부산", percent: "22.2%", count: "18명", bar: "22%" },
        { label: "기타", percent: "12.4%", count: "13명", bar: "12%" }
      ];
      case "month": return [
        { label: "천안", percent: "35%", count: "24명", bar: "35%" },
        { label: "서울", percent: "26%", count: "18명", bar: "26%" },
        { label: "부산", percent: "20%", count: "14명", bar: "20%" },
        { label: "기타", percent: "19%", count: "13명", bar: "19%" }
      ];
      case "year": return [
        { label: "천안", percent: "39%", count: "79명", bar: "39%" },
        { label: "서울", percent: "29%", count: "58명", bar: "29%" },
        { label: "부산", percent: "21%", count: "43명", bar: "21%" },
        { label: "기타", percent: "11%", count: "22명", bar: "11%" }
      ];
      default: return [];
    }
  }
  if (category === "mileage") {
    switch (period) {
      case "day": return [
        { label: "식비", percent: "42%", count: "3건", bar: "42%" },
        { label: "교통", percent: "29%", count: "2건", bar: "29%" },
        { label: "편의점", percent: "14%", count: "1건", bar: "14%" },
        { label: "기타", percent: "14%", count: "1건", bar: "14%" }
      ];
      case "week": return [
        { label: "식비", percent: "52.5%", count: "42건", bar: "53%" },
        { label: "교통", percent: "23.8%", count: "19건", bar: "24%" },
        { label: "편의점", percent: "18.8%", count: "15건", bar: "19%" },
        { label: "기타", percent: "10.0%", count: "8건", bar: "10%" }
      ];
      case "month": return [
        { label: "식비", percent: "49%", count: "34건", bar: "49%" },
        { label: "교통", percent: "27%", count: "19건", bar: "27%" },
        { label: "편의점", percent: "16%", count: "11건", bar: "16%" },
        { label: "기타", percent: "8%", count: "6건", bar: "8%" }
      ];
      case "year": return [
        { label: "식비", percent: "55%", count: "133건", bar: "55%" },
        { label: "교통", percent: "23%", count: "56건", bar: "23%" },
        { label: "편의점", percent: "16%", count: "38건", bar: "16%" },
        { label: "기타", percent: "6%", count: "13건", bar: "6%" }
      ];
      default: return [];
    }
  }
  return [];
};

export default function AdminStatistics() {
  // --------- 상태(드롭다운) ---------
  const [view, setView] = useState("totalMatches");
  const [period, setPeriod] = useState("week");
  const [viewDrop, setViewDrop] = useState(false);
  const [periodDrop, setPeriodDrop] = useState(false);
  const [pieCategory, setPieCategory] = useState("reason");
  const [pieCategoryDrop, setPieCategoryDrop] = useState(false);
  const [periodPie, setPeriodPie] = useState("week");
  const [periodPieDrop, setPeriodPieDrop] = useState(false);

  // --------- 차트 Ref ---------
  const barChartRef = useRef(null);
  const barChartInstance = useRef(null);
  const pieChartRef = useRef(null);
  const pieChartInstance = useRef(null);

  // --------- 종합(상단) 차트 데이터 ---------
  const chartConfigs = {
    totalMatches: {
      day: {
        type: "bar",
        data: {
          labels: LABELS.day,
          datasets: [
            { type: "line", label: "추세선", data: [38, 48, 42, 55, 52, 61, 65], borderColor: "red", tension: 0.4, order: 3, fill: false },
            { label: "매칭", data: [36, 47, 40, 54, 51, 59, 63], backgroundColor: "red", order: 1 },
            { label: "매칭실패", data: [31, 41, 36, 48, 44, 55, 57], backgroundColor: "black", order: 2 }
          ]
        },
        options: { plugins: { legend: { display: false } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      week: {
        type: "bar",
        data: {
          labels: LABELS.week,
          datasets: [
            { type: "line", label: "추세선", data: [45, 55, 50, 65, 60, 70, 68, 75], borderColor: "red", tension: 0.4, order: 3, fill: false },
            { type: "line", label: "추세선2", data: [40, 50, 45, 60, 55, 65, 63, 70], borderColor: "teal", tension: 0.4, order: 4, fill: false },
            { label: "매칭", data: [40, 50, 45, 60, 55, 65, 63, 70], backgroundColor: "red", order: 1 },
            { label: "매칭실패", data: [42, 48, 42, 55, 50, 60, 58, 62], backgroundColor: "black", order: 2 }
          ]
        },
        options: { plugins: { legend: { display: false } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      month: {
        type: "bar",
        data: {
          labels: LABELS.month,
          datasets: [
            { label: "매칭", data: [230, 250, 270, 290, 310, 340], backgroundColor: "red" },
            { label: "매칭실패", data: [150, 160, 180, 200, 220, 230], backgroundColor: "black" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      year: {
        type: "bar",
        data: {
          labels: LABELS.year,
          datasets: [
            { label: "매칭", data: [1800, 2200, 2500, 2700], backgroundColor: "red" },
            { label: "매칭실패", data: [950, 1200, 1150, 1350], backgroundColor: "black" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      }
    },
    avgConnectTime: {
      day: {
        type: "line",
        data: {
          labels: LABELS.day,
          datasets: [{ label: "평균 접속 시간(분)", data: [21, 20, 22, 23, 22, 25, 27], borderColor: "green", backgroundColor: "green", borderWidth: 3, tension: 0.25 }]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: false } } }
      },
      week: {
        type: "line",
        data: {
          labels: LABELS.week,
          datasets: [{ label: "평균 접속 시간(분)", data: [25, 28, 26, 30, 32, 35, 33, 38], borderColor: "green", backgroundColor: "green", borderWidth: 3, tension: 0.25 }]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: false } } }
      },
      month: {
        type: "line",
        data: {
          labels: LABELS.month,
          datasets: [{ label: "평균 접속 시간(분)", data: [27, 29, 30, 32, 34, 37], borderColor: "green", backgroundColor: "green", borderWidth: 3, tension: 0.25 }]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: false } } }
      },
      year: {
        type: "line",
        data: {
          labels: LABELS.year,
          datasets: [{ label: "평균 접속 시간(분)", data: [32, 33, 36, 38], borderColor: "green", backgroundColor: "green", borderWidth: 3, tension: 0.25 }]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: false } } }
      }
    },
    matchCount: {
      day: {
        type: "bar",
        data: {
          labels: LABELS.day,
          datasets: [
            { label: "성공", data: [15, 20, 21, 18, 25, 28, 31], backgroundColor: "blue" },
            { label: "실패", data: [2, 3, 2, 4, 5, 6, 4], backgroundColor: "red" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      week: {
        type: "bar",
        data: {
          labels: LABELS.week,
          datasets: [
            { label: "성공", data: [150, 160, 155, 170, 165, 175, 180, 185], backgroundColor: "blue" },
            { label: "실패", data: [20, 25, 22, 30, 28, 35, 32, 40], backgroundColor: "red" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      month: {
        type: "bar",
        data: {
          labels: LABELS.month,
          datasets: [
            { label: "성공", data: [350, 380, 400, 420, 440, 470], backgroundColor: "blue" },
            { label: "실패", data: [40, 38, 42, 39, 41, 45], backgroundColor: "red" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      year: {
        type: "bar",
        data: {
          labels: LABELS.year,
          datasets: [
            { label: "성공", data: [1600, 1900, 2100, 2450], backgroundColor: "blue" },
            { label: "실패", data: [230, 290, 310, 400], backgroundColor: "red" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      }
    },
    avgMatchSuccessTime: {
      day: {
        type: "bar",
        data: {
          labels: LABELS.day,
          datasets: [
            { label: "평균 매칭 성공 시간(분)", data: [6.2, 7.0, 6.6, 7.2, 7.5, 7.7, 8.1], backgroundColor: "#880080" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      week: {
        type: "bar",
        data: {
          labels: LABELS.week,
          datasets: [
            { label: "평균 매칭 성공 시간(분)", data: [7, 8, 7.5, 9, 8.5, 10, 9.5, 11], backgroundColor: "#880080" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      month: {
        type: "bar",
        data: {
          labels: LABELS.month,
          datasets: [
            { label: "평균 매칭 성공 시간(분)", data: [8, 8.8, 9.5, 10.2, 9.7, 10.5], backgroundColor: "#880080" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      },
      year: {
        type: "bar",
        data: {
          labels: LABELS.year,
          datasets: [
            { label: "평균 매칭 성공 시간(분)", data: [11, 12, 11.5, 12.8], backgroundColor: "#880080" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { }, y: { beginAtZero: true } } }
      }
    },
    userRatio: {
      day: {
        type: "bar",
        data: {
          labels: LABELS.day,
          datasets: [
            { label: "요청자", data: [50, 55, 52, 60, 58, 65, 62], backgroundColor: "orange", stack: "user" },
            { label: "제공자", data: [40, 45, 42, 50, 48, 55, 52], backgroundColor: "teal", stack: "user" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { stacked: true }, y: { stacked: true, beginAtZero: true } } }
      },
      week: {
        type: "bar",
        data: {
          labels: LABELS.week,
          datasets: [
            { label: "요청자", data: [330, 345, 340, 355, 360, 368, 372, 388], backgroundColor: "orange", stack: "user" },
            { label: "제공자", data: [230, 242, 240, 250, 258, 270, 272, 281], backgroundColor: "teal", stack: "user" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { stacked: true }, y: { stacked: true, beginAtZero: true } } }
      },
      month: {
        type: "bar",
        data: {
          labels: LABELS.month,
          datasets: [
            { label: "요청자", data: [1420, 1490, 1510, 1570, 1600, 1700], backgroundColor: "orange", stack: "user" },
            { label: "제공자", data: [1110, 1130, 1170, 1200, 1220, 1290], backgroundColor: "teal", stack: "user" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { stacked: true }, y: { stacked: true, beginAtZero: true } } }
      },
      year: {
        type: "bar",
        data: {
          labels: LABELS.year,
          datasets: [
            { label: "요청자", data: [6500, 7000, 7200, 7900], backgroundColor: "orange", stack: "user" },
            { label: "제공자", data: [4200, 4800, 5200, 5900], backgroundColor: "teal", stack: "user" }
          ]
        },
        options: { plugins: { legend: { display: true } }, scales: { x: { stacked: true }, y: { stacked: true, beginAtZero: true } } }
      }
    }
  };

  const availablePeriods = Object.keys(chartConfigs[view]);
  useEffect(() => {
    if (!availablePeriods.includes(period)) setPeriod(availablePeriods[0]);
    // eslint-disable-next-line
  }, [view]);

  useEffect(() => {
    const config = (chartConfigs[view] && chartConfigs[view][period]) ?
      chartConfigs[view][period] : null;
    if (barChartInstance.current) barChartInstance.current.destroy();
    if (barChartRef.current && config) barChartInstance.current = new Chart(barChartRef.current, config);
    return () => { if (barChartInstance.current) barChartInstance.current.destroy(); };
  }, [view, period, availablePeriods]);

  // ----------- 파이차트(카테고리+기간) -----------
  useEffect(() => {
    if (pieChartInstance.current) pieChartInstance.current.destroy();
    if (pieChartRef.current) {
      const d = getPieChartData(pieCategory, periodPie);
      pieChartInstance.current = new Chart(pieChartRef.current, {
        type: "pie",
        data: {
          labels: d.labels,
          datasets: [{
            label: pieCategoryText[pieCategory],
            data: d.data,
            backgroundColor: d.colors,
            hoverOffset: 4
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: { position: "bottom", labels: { padding: 20, font: { size: 14 } } }
          }
        }
      });
    }
    return () => { if (pieChartInstance.current) pieChartInstance.current.destroy(); };
  }, [pieCategory, periodPie]);

  // --- 외부 클릭시 드롭다운 닫기
  useEffect(() => {
    function close(e) {
      if (!e.target.className?.includes("admin-stats-dropdown-btn")) {
        setViewDrop(false);
        setPeriodDrop(false);
        setPieCategoryDrop(false);
        setPeriodPieDrop(false);
      }
    }
    window.addEventListener("click", close);
    return () => window.removeEventListener("click", close);
  }, []);

  // ------------- 렌더링 -------------
  return (
    <div>
      <AdminHeader />
      <div className="admin-stats-main-container">
        <AdminSidebar />
        <main className="admin-stats-content">
          {/* ----------- 위(종합) 차트 ----------- */}
          <div className="admin-stats-content-box">
            <div className="admin-stats-chart-header">
              {/* 왼쪽 드롭다운 */}
              <div className="admin-stats-dropdown" style={{ position: "relative" }}>
                <button
                  className="admin-stats-dropdown-btn"
                  onClick={e => { e.stopPropagation(); setViewDrop(v => !v); }}
                >
                  {viewText[view]} ▼
                </button>
                {viewDrop && (
                  <div className="admin-stats-dropdown-menu show">
                    {Object.entries(viewText).map(([k, v]) => (
                      <a
                        href="#"
                        key={k}
                        onClick={e => { e.preventDefault(); setView(k); setViewDrop(false); }}
                        style={{ color: view === k ? "#222" : "#888", fontWeight: view === k ? 700 : 400 }}
                      >{v}</a>
                    ))}
                  </div>
                )}
              </div>
              {/* 오른쪽 드롭다운 */}
              <div className="admin-stats-dropdown" id="admin-stats-date-dropdown" style={{ position: "relative" }}>
                <button
                  className="admin-stats-dropdown-btn"
                  style={{ width: 70, textAlign: "center", padding: "6px 0" }}
                  onClick={e => { e.stopPropagation(); setPeriodDrop(v => !v); }}
                >
                  {periodText[period]} ▼
                </button>
                {periodDrop && (
                  <div className="admin-stats-dropdown-menu show">
                    {Object.keys(periodText).map(k =>
                      availablePeriods.includes(k) ? (
                        <a
                          href="#"
                          key={k}
                          onClick={e => { e.preventDefault(); setPeriod(k); setPeriodDrop(false); }}
                          style={{
                            color: period === k ? "#222" : "#888",
                            fontWeight: period === k ? 700 : 400
                          }}
                        >{periodText[k]}</a>
                      ) : (
                        <a key={k} href="#" style={{ color: "#ddd", cursor: "not-allowed", pointerEvents: "none" }}>{periodText[k]}</a>
                      )
                    )}
                  </div>
                )}
              </div>
            </div>
            <div className="admin-stats-chart-placeholder" style={{ height: 350 }}>
              <canvas ref={barChartRef} />
            </div>
          </div>
          {/* ----------- 파이차트 + 카테고리/기간 드롭다운 ----------- */}
          <div className="admin-stats-content-box">
            <div className="admin-stats-chart-header">
              {/* 파이차트 왼쪽 카테고리 드롭다운 */}
              <div className="admin-stats-dropdown" style={{ position: "relative" }}>
                <button
                  className="admin-stats-dropdown-btn"
                  style={{ minWidth: 180 }}
                  onClick={e => { e.stopPropagation(); setPieCategoryDrop(v => !v); }}
                >
                  {pieCategoryText[pieCategory]} ▼
                </button>
                {pieCategoryDrop && (
                  <div className="admin-stats-dropdown-menu show">
                    {Object.entries(pieCategoryText).map(([k, v]) => (
                      <a
                        href="#"
                        key={k}
                        onClick={e => { e.preventDefault(); setPieCategory(k); setPieCategoryDrop(false); }}
                        style={{
                          color: pieCategory === k ? "#222" : "#fff",
                          fontWeight: pieCategory === k ? 700 : 400,
                          background: pieCategory === k ? "#333" : "none"
                        }}
                      >{v}</a>
                    ))}
                  </div>
                )}
              </div>
              {/* 오른쪽(기간) 드롭다운 (※파이차트용) */}
              <div className="admin-stats-dropdown" id="admin-stats-date-dropdown" style={{ position: "relative" }}>
                <button
                  className="admin-stats-dropdown-btn"
                  style={{ width: 70, textAlign: "center", padding: "6px 0" }}
                  onClick={e => { e.stopPropagation(); setPeriodPieDrop(v => !v); }}
                >
                  {periodText[periodPie]} ▼
                </button>
                {periodPieDrop && (
                  <div className="admin-stats-dropdown-menu show">
                    {Object.keys(periodText).map(k => (
                      <a
                        href="#"
                        key={k}
                        onClick={e => { e.preventDefault(); setPeriodPie(k); setPeriodPieDrop(false); }}
                        style={{
                          color: periodPie === k ? "#222" : "#888",
                          fontWeight: periodPie === k ? 700 : 400
                        }}
                      >{periodText[k]}</a>
                    ))}
                  </div>
                )}
              </div>
            </div>
            {/* 파이 + 랭킹 */}
            <div className="admin-stats-pie-chart-layout" style={{ gap: "36px" }}>
              <div
                className="admin-stats-chart-placeholder"
                style={{
                  height: 380,
                  maxWidth: "600px",
                  minWidth: "300px",
                  flex: 2
                }}
              >
                <canvas ref={pieChartRef} style={{ width: "100%", height: "100%" }} />
              </div>
              <div
                className="admin-stats-ranked-list"
                style={{
                  flex: 1,
                  maxWidth: "220px",
                  minWidth: "120px"
                }}
              >
                {getPieRank(pieCategory, periodPie).map((item, idx, arr) => (
                  <React.Fragment key={item.label}>
                    <div className="admin-stats-rank-item">
                      <span className="admin-stats-rank-num">{idx + 1}</span>
                      <span className="admin-stats-rank-label">{item.label}</span>
                      <div className="admin-stats-rank-bar-bg">
                        <div className="admin-stats-rank-bar" style={{
                          width: item.bar, background: "#FF9800"
                        }}></div>
                      </div>
                      <span className="admin-stats-rank-percent">{item.percent}</span>
                      <span className="admin-stats-rank-count">{item.count}</span>
                    </div>
                    {idx < arr.length - 1 && <div style={{ borderTop: "1px solid #eee", margin: "10px 0" }} />}
                  </React.Fragment>
                ))}
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}
