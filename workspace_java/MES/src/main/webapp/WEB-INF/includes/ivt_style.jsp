<%-- /WEB-INF/includes/ivt_style.jsp : Out/In 공용 스타일 (초기화 + 변수 + 폼 + 버튼 + 알럿 + 반응형) --%>
<style>
/* ========== CSS Reset(간략) ========== */
* { box-sizing: border-box; }
html, body { margin:0; padding:0; }
img { max-width:100%; display:block; }
button, input, select { font: inherit; }

/* ========== 디자인 토큰 ========== */
:root{
  --color-bg: #ffffff;
  --color-text: #111827;        /* gray-900 */
  --color-muted: #6B7280;       /* gray-500 */
  --color-border: #E5E7EB;      /* gray-200 */

  --color-primary: #2563EB;     /* indigo-600 */
  --color-primary-600:#1D4ED8;  /* hover */
  --ring-primary: rgba(37,99,235,0.15);

  --success-bg: #ECFDF5;
  --success-bd: #10B981;
  --success-tx: #065F46;

  --error-bg: #FEF2F2;
  --error-bd: #EF4444;
  --error-tx: #991B1B;

  --radius: 10px;
  --shadow: 0 4px 14px rgba(16,24,40,0.08);
  --shadow-strong: 0 8px 24px rgba(16,24,40,0.12);

  --space-1: 6px;
  --space-2: 10px;
  --space-3: 14px;
  --space-4: 20px;
  --space-5: 24px;

  --font-base: 14px;
  --font-small: 13px;
  --font-label: 14px;
  --font-title: 18px;

  --container-w: 880px;
}

/* ========== 기본 레이아웃 ========== */
body{
  background: var(--color-bg);
  color: var(--color-text);
  font-family: system-ui, -apple-system, "Noto Sans KR", Segoe UI, Roboto, Helvetica, Arial, "Apple SD Gothic Neo", "Malgun Gothic", sans-serif;
  font-size: var(--font-base);
  line-height: 1.5;
}

.wrap{
  max-width: var(--container-w);
  margin: var(--space-5) auto;
  padding: var(--space-4);
}

/* ========== 제목(필요시) ========== */
.page-title{
  font-size: var(--font-title);
  font-weight: 700;
  margin-bottom: var(--space-4);
}

/* ========== 폼 ========== */
form{
  background: #fff;
  border: 1px solid var(--color-border);
  border-radius: 12px;
  box-shadow: var(--shadow);
  padding: var(--space-5);
}

.row{
  display: grid;
  grid-template-columns: 140px 1fr;
  align-items: center;
  gap: 10px 16px;
  margin-bottom: var(--space-3);
}

.row > label{
  font-size: var(--font-label);
  font-weight: 600;
  color: #374151; /* gray-700 */
}

/* 입력 공통 */
.row select,
.row input[type="number"],
.row input[type="text"],
.row input[type="datetime-local"]{
  width: 100%;
  padding: 10px 12px;
  border: 1px solid var(--color-border);
  border-radius: var(--radius);
  background: #fff;
  color: var(--color-text);
  transition: border-color .15s ease, box-shadow .15s ease, background .2s;
}

/* 포커스 링 */
.row select:focus,
.row input:focus{
  outline: none;
  border-color: var(--color-primary);
  box-shadow: 0 0 0 3px var(--ring-primary);
}

/* 비활성 */
.row select:disabled,
.row input:disabled{
  background: #F3F4F6;   /* gray-100 */
  color: #9CA3AF;        /* gray-400 */
  border-color: var(--color-border);
  cursor: not-allowed;
}

/* number 스핀 버튼 정리 */
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button{ margin:0; }
input[type="number"]{ -moz-appearance:textfield; }

/* ========== 버튼 ========== */
.actions{
  display:flex;
  gap: 10px;
  margin-top: var(--space-4);
}

.btn{
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 120px;
  padding: 10px 14px;
  border-radius: var(--radius);
  border: 1px solid transparent;
  background: #fff;
  color: var(--color-text);
  font-weight: 700;
  cursor: pointer;
  transition: transform .05s ease, box-shadow .15s ease, background .15s ease, border-color .15s ease;
  user-select: none;
}

.btn:active{ transform: translateY(1px); }

/* 주요 버튼 */
.btn.primary{
  background: var(--color-primary);
  color: #fff;
  box-shadow: 0 4px 14px rgba(37,99,235,0.25);
}
.btn.primary:hover{ background: var(--color-primary-600); }

/* 보조 버튼 */
.btn.secondary{
  background: #fff;
  color: #374151;
  border-color: var(--color-border);
}
.btn.secondary:hover{ background: #F9FAFB; }

/* ========== 알럿 ========== */
.alert{
  display: none;
  margin-top: var(--space-3);
  padding: 12px 14px;
  border-radius: var(--radius);
  border: 1px solid;
  font-size: var(--font-base);
  line-height: 1.4;
}
.alert.show{ display:block; }

.alert.success{
  background: var(--success-bg);
  border-color: var(--success-bd);
  color: var(--success-tx);
}
.alert.error{
  background: var(--error-bg);
  border-color: var(--error-bd);
  color: var(--error-tx);
}

/* ========== 탭 스타일(선택: include된 탭에 클래스 맞춰 사용) ========== */
.tabbar{
  display:flex;
  gap: 6px;
  margin: 0 0 var(--space-4);
}
.tabbar a,
.tabbar button{
  padding: 8px 12px;
  border-radius: 999px;
  border: 1px solid var(--color-border);
  background: #fff;
  color: var(--color-muted);
  text-decoration: none;
  font-weight: 600;
}
.tabbar a.active,
.tabbar button.active{
  background: var(--color-primary);
  border-color: var(--color-primary);
  color: #fff;
}

/* ========== 유틸리티 클래스 ========== */
.hidden{ display:none !important; }
.m-0{ margin:0 !important; }
.mt-1{ margin-top: var(--space-1) !important; }
.mt-2{ margin-top: var(--space-2) !important; }
.mt-3{ margin-top: var(--space-3) !important; }
.mt-4{ margin-top: var(--space-4) !important; }
.w-100{ width:100% !important; }

/* ========== 반응형 ========== */
@media (max-width: 800px){
  .wrap{ padding: var(--space-4); }
}

@media (max-width: 640px){
  .row{
    grid-template-columns: 1fr;
  }
  .row > label{
    margin-bottom: 4px;
  }
  .actions{
    flex-direction: column;
  }
  .btn{ width:100%; }
}
</style>
