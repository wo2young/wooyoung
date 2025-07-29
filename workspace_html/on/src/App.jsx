import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';

import WarmthMapPage from './components/WarmthMap';

import Home from './Pages/Home';
import LoginPage from './Pages/LoginPage';
import SignupPage from './Pages/SignupPage';
import MatchingPage from './Pages/MatchingPage';
import Support from './Pages/Support';
import MyPage from './Pages/MyPage';
import WaitingPage from './Pages/WaitingPage';
import HelpPage from './Pages/HelpPage';
import MileageUsagePage from './Pages/MileageUsagePage';

// 기본 계정 초기 설정 (생략 가능하면 별도 파일로 분리해도 좋아요)
const defaultUser = {
  name: '김우영',
  rrn: '021030-1111222',
  addr: '경기도 여주시',
  addrDetail: '',
  id: 'wooyoung02',
  pw: 'qwe123',
  phone: '010-1111-2222',
  email: 'ywk02@gmail.com',
  mileage: 13250,
};

let users = JSON.parse(localStorage.getItem('users') || '[]');
if (users.length === 0) {
  users = [defaultUser];
} else {
  const idx = users.findIndex(u => u.id === defaultUser.id);
  if (idx !== -1) {
    users[idx] = { ...users[idx], ...defaultUser };
  } else {
    users.push(defaultUser);
  }
}
localStorage.setItem('users', JSON.stringify(users));

const found = users.find(u => u.id === defaultUser.id);
if (found) {
  localStorage.setItem('loggedUser', JSON.stringify(found));
  localStorage.setItem('isLoggedIn', 'true');
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignupPage />} />
          <Route path="/matching" element={<MatchingPage />} />
          <Route path="/support" element={<Support />} />
          <Route path="/mypage" element={<MyPage />} />
          <Route path="/wait" element={<WaitingPage />} />
          <Route path="/help" element={<HelpPage />} />
          <Route path="/mileage" element={<MileageUsagePage />} />
          <Route path="/warmthmap" element={<WarmthMapPage />} />
          {/* 404 페이지 임시 처리 */}
          <Route path="*" element={<div>404 페이지를 찾을 수 없습니다.</div>} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
