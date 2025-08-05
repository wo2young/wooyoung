import React, { useState, useEffect } from 'react';
import Layout from '../components/Layout';
import '../Styles/MileageUsagePage.css';
import { useAuth } from '../contexts/AuthContext';

const STORES = [
  { name: '재홍이의 인생 족발', img: '이미지URL1.png' },
  { name: '재민이의 여행 치킨', img: '이미지URL2.png' },
  { name: '재준이의 참 횟집', img: '이미지URL3.png' },
  { name: '동현 짜장', img: '' },
  { name: '동현 짬뽕', img: '' },
  { name: '동현 쌀국수', img: '' },
  { name: '동현 칼국수', img: '' },
  { name: '동현 피자', img: '' },
  { name: '동현 탕수육', img: '' },
  // ...더 채우기...
];

const PAGE_SIZE = 8;

function MileageUsagePage() {
  const { user } = useAuth();
  const [myUser, setMyUser] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = Math.ceil(STORES.length / PAGE_SIZE);

  useEffect(() => {
    if (user) {
      const users = JSON.parse(localStorage.getItem('users') || '[]');
      setMyUser(users.find(u => u.id === user.id));
    }
  }, [user]);

  const startIdx = (currentPage - 1) * PAGE_SIZE;
  const pagedStores = STORES.slice(startIdx, startIdx + PAGE_SIZE);
  const myMileage = myUser?.mileage ?? 0;

  return (
    <Layout>
      <div className="container">
        <div className="main-content">
          <h2 className="page-title">마일리지 사용처</h2>
          <div className="point-box">
            <span>사용가능 마일리지 : {myMileage.toLocaleString()}p</span>
          </div>
          <div className="main-layout">
            <section className="store-section">
              <h3>가게 사진 첨부</h3>
              <div className="store-list">
                {pagedStores.map((store, idx) => (
                  <div className={`store-card${idx > 2 ? " small" : ""}`} key={startIdx + idx}>
                    <div className="store-image-box">
                      {store.img && <img src={store.img} alt="" />}
                    </div>
                    <div className="store-name">{store.name}</div>
                  </div>
                ))}
              </div>
            </section>
            <aside className="ad-section">
              <div className="ad-box">광고</div>
            </aside>
          </div>
          <div className="bottom-info">
            <div className="notice">
              안내 사항<br />
              1년 동안 사용하지 않으실 시 마일리지는 자동 소멸 됩니다!
            </div>
            <div className="pagination">
              <button
                disabled={currentPage === 1}
                onClick={() => setCurrentPage(currentPage - 1)}
              >&lt;</button>
              {Array.from({ length: totalPages }, (_, i) => i + 1).map(num => (
                <button
                  key={num}
                  className={num === currentPage ? 'active' : ''}
                  onClick={() => setCurrentPage(num)}
                >
                  {num}
                </button>
              ))}
              <button
                disabled={currentPage === totalPages}
                onClick={() => setCurrentPage(currentPage + 1)}
              >&gt;</button>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
}

export default MileageUsagePage;