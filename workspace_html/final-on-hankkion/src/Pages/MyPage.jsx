import React, { useState, useEffect, useRef } from 'react';
import '../Styles/MyPage.css';
import Header from '../components/Header';
import { useAuth } from '../contexts/AuthContext';

function MyPage() {
  const { user } = useAuth();
  const [editMode, setEditMode] = useState(false);
  const [originalData, setOriginalData] = useState({});
  const [formData, setFormData] = useState({
    image: '',
    address: '',
    notes: '',
    interests: '',
    nickname: ''
  });

  const fileInputRef = useRef(null);

  useEffect(() => {
    if (user) {
      const saved = JSON.parse(localStorage.getItem('users') || '[]').find(u => u.id === user.id);
      if (saved) {
        setFormData({
          image: saved.image || '',
          address: saved.addr || saved.address || '',
          notes: saved.notes || '',
          interests: saved.interests || '',
          nickname: saved.nickname || ''
        });
        setOriginalData(saved);
      }
    }
  }, [user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleEdit = () => setEditMode(true);

  const handleCancel = () => {
    setEditMode(false);
    setFormData({
      image: originalData.image || '',
      address: originalData.addr || originalData.address || '',
      notes: originalData.notes || '',
      interests: originalData.interests || '',
      nickname: originalData.nickname || ''
    });
  };

  const handleSave = () => {
    // 저장 로직: localStorage 업데이트
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    const idx = users.findIndex(u => u.id === user.id);
    if (idx !== -1) {
      users[idx] = { ...users[idx], ...formData };
      localStorage.setItem('users', JSON.stringify(users));
      localStorage.setItem('loggedUser', JSON.stringify({ ...users[idx] }));
      setOriginalData(users[idx]);
    }
    setEditMode(false);
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

  return (
    <>
      <Header />
      <div className="mypage-root">
        <h2 className="mypage-title">나의 정보</h2>
        <div className="profile-card">
          <div className="profile-left">
            <div className="profile-img-wrap">
              {formData.image ? (
                <img src={formData.image} alt="프로필" className="profile-img" />
              ) : (
                <div className="profile-img placeholder"></div>
              )}
            </div>
            {editMode && (
              <>
                <button
                  type="button"
                  className="edit-btn"
                  onClick={handleImageButton}
                  style={{ marginTop: '8px', width: '90px' }}
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
              </>
            )}
          </div>
          <div className="profile-right">
            <div className="profile-row">
              <span className="info-label">이름 :</span>
              <span>{user?.name}</span>
            </div>
            <div className="profile-row">
              <span className="info-label">주소 :</span>
              {editMode ? (
                <input
                  type="text"
                  name="address"
                  value={formData.address}
                  onChange={handleChange}
                />
              ) : (
                <span>{formData.address}</span>
              )}
            </div>
            <div className="profile-row">
              <span className="info-label">특이사항 :</span>
              {editMode ? (
                <input
                  type="text"
                  name="notes"
                  value={formData.notes}
                  onChange={handleChange}
                />
              ) : (
                <span>{formData.notes}</span>
              )}
            </div>
            <div className="profile-row">
              <span className="info-label">관심사 :</span>
              {editMode ? (
                <input
                  type="text"
                  name="interests"
                  value={formData.interests}
                  onChange={handleChange}
                />
              ) : (
                <span>{formData.interests}</span>
              )}
            </div>
            <div className="profile-row">
              <span className="info-label">별명 :</span>
              {editMode ? (
                <input
                  type="text"
                  name="nickname"
                  value={formData.nickname}
                  onChange={handleChange}
                />
              ) : (
                <span>{formData.nickname}</span>
              )}
            </div>
            {/* 버튼 영역: 항상 동일한 공간 차지! */}
            <div
              className="profile-btn-row"
            >
              {editMode ? (
                <>
                  <button className="edit-btn" onClick={handleSave}>저장</button>
                  <button className="edit-btn" onClick={handleCancel}>취소</button>
                </>
              ) : (
                <button className="edit-btn" onClick={handleEdit}>수정하기</button>
              )}
            </div>
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
          </div>
        </div>
      </div>
    </>
  );
}

export default MyPage;
