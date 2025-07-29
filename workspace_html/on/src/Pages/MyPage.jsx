import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import '../Styles/MyPage.css';
import Header from '../components/Header';
import { useAuth } from '../contexts/AuthContext';
import userDefaultImg from '../assets/user.png';

function MyPage() {
  const { user, login } = useAuth();
  const navigate = useNavigate();

  const [myUser, setMyUser] = useState(null);
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    image: '',
    address: '',
    notes: '',
    interests: '',
    nickname: ''
  });
  const fileInputRef = useRef(null);

  useEffect(() => {
    if (!user) navigate('/login', { replace: true });
  }, [user, navigate]);
  if (!user) return null;

  useEffect(() => {
    if (user) {
      const users = JSON.parse(localStorage.getItem('users') || '[]');
      const found = users.find(u => u.id === user.id);
      setMyUser(found);
      if (found) {
        setFormData({
          image: found.image || '',
          address: found.addr || found.address || '',
          notes: found.notes || '',
          interests: found.interests || '',
          nickname: found.nickname || ''
        });
      }
    }
  }, [user, editMode]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleEdit = () => setEditMode(true);

  const handleCancel = () => {
    setEditMode(false);
    if (myUser) {
      setFormData({
        image: myUser.image || '',
        address: myUser.addr || myUser.address || '',
        notes: myUser.notes || '',
        interests: myUser.interests || '',
        nickname: myUser.nickname || ''
      });
    }
  };

  const handleSave = () => {
    let users = JSON.parse(localStorage.getItem('users') || '[]');
    const idx = users.findIndex(u => u.id === user.id);
    if (idx !== -1) {
      users[idx] = { ...users[idx], ...formData };
      localStorage.setItem('users', JSON.stringify(users));
      localStorage.setItem('loggedUser', JSON.stringify(users[idx]));
      if (typeof login === 'function') login(users[idx].id, users[idx].pw);
      setEditMode(false);
      setMyUser(users[idx]);
    }
  };

  const handleImageButton = () => {
    if (fileInputRef.current) fileInputRef.current.click();
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => {
      setFormData(prev => ({ ...prev, image: reader.result }));
    };
    reader.readAsDataURL(file);
  };

  const myMileage = myUser?.mileage ?? 0;

  return (
    <>
      <Header />
      <div className="mypage-root">
        <div className="profile-multi-row">
          <div className="profile-card mini">
            {/* 프로필이미지 + 업로드버튼을 세로로 묶기 */}
            <div className="profile-img-col">
              <div className="profile-img-wrap">
                {formData.image ? (
                  <img src={formData.image} alt="프로필" className="profile-img" />
                ) : (
                  <img src={userDefaultImg} alt="기본 프로필" className="profile-img" />
                )}
              </div>
              {editMode && (
                <div className="profile-upload-row">
                  <button
                    type="button"
                    className="edit-btn profile-upload-btn"
                    onClick={handleImageButton}
                  >
                    사진업로드
                  </button>
                  <input
                    type="file"
                    ref={fileInputRef}
                    style={{ display: "none" }}
                    accept="image/*"
                    onChange={handleImageChange}
                  />
                </div>
              )}
            </div>
            <div className="profile-mini-info">
              {/* 1줄: 이름-관심사 */}
              <div className="profile-row double">
                <div className="info-field">
                  <span className="info-label">이름 : </span>
                  <span className="info-value">{myUser?.name}</span>
                </div>
                <div className="info-field">
                  <span className="info-label">관심사 : </span>
                  {editMode ? (
                    <input name="interests" value={formData.interests} onChange={handleChange} />
                  ) : (
                    <span className="info-value">{formData.interests}</span>
                  )}
                </div>
              </div>
              {/* 2줄: 별명-특이사항 */}
              <div className="profile-row double">
                <div className="info-field">
                  <span className="info-label">별명 : </span>
                  {editMode ? (
                    <input name="nickname" value={formData.nickname} onChange={handleChange} />
                  ) : (
                    <span className="info-value">{formData.nickname}</span>
                  )}
                </div>
                <div className="info-field">
                  <span className="info-label">특이사항 : </span>
                  {editMode ? (
                    <input name="notes" value={formData.notes} onChange={handleChange} />
                  ) : (
                    <span className="info-value">{formData.notes}</span>
                  )}
                </div>
              </div>
              {/* 3줄: 주소 */}
              <div className="profile-row single">
                <span className="info-label">주소 : </span>
                {editMode ? (
                  <input
                    name="address"
                    value={formData.address}
                    onChange={handleChange}
                    className="wide"
                  />
                ) : (
                  <span className="info-value">{formData.address}</span>
                )}
              </div>
              <div className="profile-btn-row">
                {editMode ? (
                  <>
                    <button className="edit-btn" onClick={handleSave}>저장</button>
                    <button className="edit-btn" onClick={handleCancel}>취소</button>
                  </>
                ) : (
                  <button className="edit-btn" onClick={handleEdit}>수정</button>
                )}
              </div>
            </div>
          </div>
          <div className="profile-mileage-summary out">
            <span className="profile-mileage-label">나의 마일리지</span>
            <span className="profile-mileage-amount">{myMileage.toLocaleString()}p</span>
          </div>
        </div>
        <div className="mypage-bottom">
          <div className="mypage-bottom-left">
            <div className="mileage-box">
              <div className="mileage-title">마일리지 적립 현황</div>
              <div className="mileage-content"></div>
            </div>
            <div className="mileage-box">
              <div className="mileage-title">마일리지 사용 내역</div>
              <div className="mileage-content"></div>
            </div>
          </div>
          <div className="mypage-bottom-right">
            <div className="activity-box">
              <div className="activity-title">활동 내역</div>
              <div className="activity-content"></div>
            </div>
            <div className="notice-box">
              <div className="notice-title">공지사항</div>
              <div className="notice-content">
                <ul>
                  <li>8월 1일: 마일리지 정책이 개편됩니다.</li>
                  <li>7월 28일: 새로운 기능이 추가되었습니다!</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default MyPage;
