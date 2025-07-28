import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header'; // ← 이렇게!
import '../Styles/HelpPage.css';

function HelpPage() {
  const [address, setAddress] = useState('');
  const [time, setTime] = useState('');
  const [request, setRequest] = useState('');
  const [problem, setProblem] = useState('');
  const navigate = useNavigate();

  const handleAddressClick = () => {
    setAddress('서울특별시 강남구 테헤란로 123');
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!address.trim() || !time.trim() || !request.trim()) {
      alert('필수 입력란을 모두 채워주세요.');
      return;
    }
    setAddress('');
    setTime('');
    setRequest('');
    setProblem('');
    navigate('/wait');
  };

  const handleReset = () => {
    setAddress('');
    setTime('');
    setRequest('');
    setProblem('');
  };

  return (
    <>
      <Header />
      <div className="help-page">
        <h2>도움 요청 사항을 입력해주세요</h2>
        <form onSubmit={handleSubmit}>
          <div className="label-row">
            <label htmlFor="address">
              주소 <span style={{ color: 'red', fontSize: '14px' }}>*</span>
            </label>
            <button type="button" id="adressbtn" onClick={handleAddressClick}>
              내 주소 입력하기
            </button>
          </div>
          <input
            type="text"
            id="address"
            name="address"
            placeholder="주소를 입력해주세요"
            value={address}
            onChange={(e) => setAddress(e.target.value)}
            required
          />

          <label htmlFor="time">
            예상 소요 시간 <span style={{ color: 'red', fontSize: '14px' }}>*</span>
          </label>
          <input
            type="text"
            id="time"
            name="time"
            placeholder="얼마나 걸릴지 써주세요!  예시) 1시간 or 30분 "
            value={time}
            onChange={(e) => setTime(e.target.value)}
            required
          />

          <label htmlFor="request">
            요청사항 <span style={{ color: 'red', fontSize: '14px' }}>*</span>
          </label>
          <input
            type="text"
            id="request"
            name="request"
            placeholder="병원, 산책, 등등..."
            value={request}
            onChange={(e) => setRequest(e.target.value)}
            required
          />

          <label htmlFor="problem">
            불편 사항 (선택)
          </label>
          <textarea
            id="problem"
            name="problem"
            placeholder="아픈곳이 있거나 조심해야 할 부분을 써주세요"
            value={problem}
            onChange={(e) => setProblem(e.target.value)}
          ></textarea>

          <div className="button-group">
            <button type="submit" className="submit-btn">
              도움 요청
            </button>
            <button
              type="button"
              className="cancel-btn"
              onClick={handleReset}
            >
              취소 하기
            </button>
          </div>
        </form>
      </div>
    </>
  );
}

export default HelpPage;
