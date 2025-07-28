import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';

import Home from './Pages/Home';
import LoginPage from './Pages/LoginPage';
import SignupPage from './Pages/SignupPage';
import MatchingPage from './Pages/MatchingPage';
import Support from './Pages/Support';
import MyPage from './Pages/MyPage';
import WaitingPage from './Pages/WaitingPage';
import HelpPage from './Pages/HelpPage'; // <- HelpPage 추가

if (
  !localStorage.getItem('users') ||
  JSON.parse(localStorage.getItem('users')).length === 0
) {
  localStorage.setItem(
    'users',
    JSON.stringify([
      {
        name: '김우영',
        rrn: '021030-1111222',
        addr: '경기도 여주시',
        addrDetail: '',
        id: 'wooyoung02',
        pw: 'qwe123',
        phone: '010-1111-2222',
        email: 'ywk02@gmail.com',
      },
    ])
  );
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
          <Route path="/help" element={<HelpPage />} /> {/* HelpPage 라우터 추가 */}
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
