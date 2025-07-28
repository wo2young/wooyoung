import logoImg from '../assets/로고.png';
import '../Styles/SupportPopup.css';

function SupportPopup({ onClose }) {
  return (
    <div className="support-popup-overlay">
      <div className="support-popup-modal">

        {/* 헤더 로고 영역 */}
        <div className="popup-logo-row">
          <img src={logoImg} alt="한끼온 로고" className="popup-logo-img" />
        </div>

        {/* 타이틀 */}
        <div className="popup-title">
          고객 센터
        </div>
        <div className="popup-subtitle">
          무엇을 도와드릴까요?
        </div>

        {/* 구분선 */}
        <div className="popup-divider"></div>

        {/* 주요 문의 유형 플로우 */}
        <div className="popup-flow-section">
          <div className="popup-flow-label">아래에 필요한 내용이 있으신가요?</div>
          <div className="popup-flow-list">
            <div className="popup-flow-item">아이디 / 비밀번호<br/>찾기 관련</div>
            <div className="popup-flow-arrow">→</div>
            <div className="popup-flow-item">포인트 문의</div>
            <div className="popup-flow-arrow">→</div>
            <div className="popup-flow-item">제휴 업체 문의</div>
            <div className="popup-flow-arrow">→</div>
            <div className="popup-flow-item">건의 하기</div>
            <div className="popup-flow-arrow">→</div>
            <div className="popup-flow-item">1:1 문의</div>
          </div>
        </div>

        {/* 자주 묻는 질문 */}
        <div className="popup-faq-section">
          <div className="popup-faq-label">자주 묻는 질문</div>
          <div className="popup-faq-list">
            <div className="popup-faq-item">포인트 쓸 수 있는곳을 찾고 싶어요.</div>
            <div className="popup-faq-item">제휴 업체로 등록하고 싶어요.</div>
            <div className="popup-faq-item">탈퇴 후 아이디 복구를 하고 싶어요.</div>
          </div>
        </div>

        {/* 닫기 버튼 */}
        <div className="popup-footer">
          <button onClick={onClose} className="popup-close-btn">닫기</button>
        </div>

      </div>
    </div>
  );
}

export default SupportPopup;
