import logoImg from '../assets/logo.png';
import '../Styles/SupportPopup.css';

function SupportPopup({ collapsed, onToggle, onQuestion }) {
  const faqList = [
    "포인트 쓸 수 있는곳을 찾고 싶어요.",
    "제휴 업체로 등록하고 싶어요.",
    "탈퇴 후 아이디 복구를 하고 싶어요."
  ];
  const flowList = [
    "아이디 / 비밀번호\n찾기 관련",
    "포인트 문의",
    "제휴 업체 문의",
    "건의 하기",
    "1:1 문의"
  ];

  return (
    <div
      className={
        `support-popup-root${collapsed ? ' collapsed' : ''}`
      }
      style={{
        position: 'fixed',
        left: 0, right: 0, bottom: 0, zIndex: 99,
        display: 'flex', flexDirection: 'column', alignItems: 'center',
        pointerEvents: 'none'
      }}
    >
      <div
        className={`support-popup-overlay${collapsed ? ' collapsed' : ''}`}
        style={{
          pointerEvents: collapsed ? 'none' : 'all'
        }}
      >
        <div className="support-popup-modal">
          <div className="popup-logo-row">
            <img src={logoImg} alt="한끼온 로고" className="popup-logo-img" />
          </div>
          <div className="popup-title">고객 센터</div>
          <div className="popup-subtitle">무엇을 도와드릴까요?</div>
          <div className="popup-divider"></div>
          <div className="popup-flow-section">
            <div className="popup-flow-label">아래에 필요한 내용이 있으신가요?</div>
            <div className="popup-flow-list">
              {flowList.map((item, idx) => (
                <div
                  key={idx}
                  className="popup-flow-item"
                  onClick={() => onQuestion(item.replace(/\n/g, " "))}
                >
                  {item}
                </div>
              ))}
            </div>
          </div>
          <div className="popup-faq-section">
            <div className="popup-faq-label">자주 묻는 질문</div>
            <div className="popup-faq-list">
              {faqList.map((faq, idx) => (
                <div
                  key={idx}
                  className="popup-faq-item"
                  onClick={() => onQuestion(faq)}
                >
                  {faq}
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* 토글바 - 항상 하단에, 팝업 상태에 따라 텍스트와 동작 변경 */}
      <div
        className="support-popup-togglebar"
        onClick={onToggle}
        style={{
          borderTopLeftRadius: 20, borderTopRightRadius: 20,
          pointerEvents: 'all'
        }}
      >
        {collapsed ? "▲ 고객센터 열기" : "▼ 고객센터 내리기"}
      </div>
    </div>
  );
}
export default SupportPopup;
