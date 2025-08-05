import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import '../Styles/HelpPage.css';
import Layout from '../components/Layout';

function HelpPage() {
  const [address, setAddress] = useState('');
  const [detailAddress, setDetailAddress] = useState('');
  const [time, setTime] = useState('');
  const [request, setRequest] = useState('');
  const [customRequest, setCustomRequest] = useState('');
  const [detail, setDetail] = useState('');
  const [problem, setProblem] = useState('');
  const navigate = useNavigate();

  const handleAddressClick = () => {
    const savedAddress = localStorage.getItem("myAddress") || "";
    const savedDetail = localStorage.getItem("myDetailAddress") || "";
    setAddress(savedAddress);
    setDetailAddress(savedDetail);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const selectedRequest = request === 'etc' ? customRequest : request;
    if (!address.trim() || !time.trim() || !selectedRequest.trim() || !detail.trim()) {
      alert('필수 입력란을 모두 채워주세요.');
      return;
    }
    const apiKey = import.meta.env.VITE_GEOCODING_API_KEY;
    const fullAddress = `${address} ${detailAddress || ""}`;
    let lat = null, lng = null;
    try {
      const res = await fetch(
        `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(fullAddress)}&key=${apiKey}&language=ko`
      );
      const data = await res.json();
      if (data.status === "OK" && data.results.length > 0) {
        lat = data.results[0].geometry.location.lat;
        lng = data.results[0].geometry.location.lng;
      }
    } catch (e) { console.error("지오코딩 fetch 에러:", e); }
    if (lat === null || lng === null) {
      alert("주소를 좌표로 변환하지 못했습니다. 주소를 확인해주세요!");
      return;
    }
    const helpRequest = {
      id: Date.now().toString(), // 고유id
      address,
      detailAddress,
      time,
      request: selectedRequest,
      detail,
      problem,
      lat,
      lng,
      date: new Date().toISOString(),
    };
    const prev = JSON.parse(localStorage.getItem("helpRequests") || "[]");
    prev.push(helpRequest);
    localStorage.setItem("helpRequests", JSON.stringify(prev));
    localStorage.setItem("matchStatus", "waiting");
    setAddress('');
    setDetailAddress('');
    setTime('');
    setRequest('');
    setCustomRequest('');
    setDetail('');
    setProblem('');
    // 요청 데이터와 역할을 함께 state로 넘김
    navigate('/wait', {
      state: {
        id: helpRequest.id,
        request: helpRequest.request,   // <-- 도움종류(카테고리)
        detail: helpRequest.detail,     // <-- 사용자가 쓴 세부내용!
        problem: helpRequest.problem,
        time: helpRequest.time,
        place: helpRequest.detailAddress,
        role: 'requester'
      }
    });
  };

  const handleReset = () => {
    setAddress('');
    setDetailAddress('');
    setTime('');
    setRequest('');
    setCustomRequest('');
    setDetail('');
    setProblem('');
  };

  const requestOptions = [
    { value: '', label: '도움 요청 종류를 선택하세요' },
    { value: 'meal', label: '식사 동행/식사 챙김' },
    { value: 'talk', label: '말벗/정서적 교감' },
    { value: 'errand', label: '동네 심부름(장보기, 약 타오기 등)' },
    { value: 'housework', label: '집안일 도움(청소, 정리 등)' },
    { value: 'it', label: 'IT/스마트폰/가전 도움' },
    { value: 'outing', label: '외출 동행/산책' },
    { value: 'agency', label: '관공서/은행 업무 동행' },
    { value: 'health', label: '건강 체크/병원 동행' },
    { value: 'etc', label: '기타(직접 입력)' },
  ];

  return (
    <Layout>
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
          <input
            type="text"
            id="detail-address"
            name="detail-address"
            placeholder="상세주소를 입력해주세요 (예: 101동 101호)"
            value={detailAddress}
            onChange={(e) => setDetailAddress(e.target.value)}
            style={{ marginTop: '7px', marginBottom: '17px' }}
          />

          <label htmlFor="time">
            예상 소요 시간 <span style={{ color: 'red', fontSize: '14px' }}>*</span>
          </label>
          <input
            type="text"
            id="time"
            name="time"
            placeholder="얼마나 걸릴지 써주세요! 예시) 1시간 or 30분"
            value={time}
            onChange={(e) => setTime(e.target.value)}
            required
          />

          <label htmlFor="request">
            요청사항 <span style={{ color: 'red', fontSize: '14px' }}>*</span>
          </label>
          <select
            id="request"
            name="request"
            value={request}
            onChange={(e) => setRequest(e.target.value)}
            required
          >
            {requestOptions.map((opt) => (
              <option key={opt.value} value={opt.value}>
                {opt.label}
              </option>
            ))}
          </select>
          {request === 'etc' && (
            <input
              type="text"
              id="custom-request"
              name="custom-request"
              placeholder="도움 요청 내용을 직접 입력해주세요"
              value={customRequest}
              onChange={(e) => setCustomRequest(e.target.value)}
              style={{ marginTop: '8px' }}
              required
            />
          )}

          <label htmlFor="detail">
            세부 내용 <span style={{ color: 'red', fontSize: '14px' }}>*</span>
          </label>
          <input
            type="text"
            id="detail"
            name="detail"
            placeholder="예시) 천안정형외과 병원, 롯데마트 심부름 등 구체적으로 입력"
            value={detail}
            onChange={(e) => setDetail(e.target.value)}
            required
          />

          <label htmlFor="problem">
            불편 사항 (선택)
          </label>
          <textarea
            id="problem"
            name="problem"
            placeholder="아픈 곳이 있거나 조심해야 할 부분을 써주세요"
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
    </Layout>
  );
}

export default HelpPage;
