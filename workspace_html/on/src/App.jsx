import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import { LoadScript } from "@react-google-maps/api";

import WarmthMapPage from './Pages/WarmthMap';
import Home from './Pages/Home';
import LoginPage from './Pages/LoginPage';
import SignupPage from './Pages/SignupPage';
import SignupSelect from './Pages/SignupSelect';
import MatchingPage from './Pages/MatchingPage';
import MatchingWaitPage from './Pages/MatchingWaitPage';
import Support from './Pages/Support';
import MyPage from './Pages/MyPage';
import WaitingPage from './Pages/WaitingPage';
import HelpPage from './Pages/HelpPage';
import MileageUsagePage from './Pages/MileageUsagePage';
import FindIdPage from './Pages/FindIdPage';
import FindPwPage from './Pages/FindPwPage';
import MeetingWaitPage from './Pages/MeetingWaitPage';
import CommunityPage from './Pages/CommunityPage';
import MeetingPage from './Pages/Meeting';
import MeetingDone from './Pages/MeetingDone';

// 관리자페이지 컴포넌트 import
import AdminUser from './Pages/AdminUser';
import AdminInquiryPage from "./Pages/AdminInquiryPage";
import AdminPostManagePage from './Pages/AdminPostManage';
import AdminSystemSetting from './Pages/AdminSystemSetting'; // 추가!
import AdminStatistics from './Pages/AdminStatistics';

// 관리자 포함 users를 최초 1회만 세팅 (이미 있으면 무시)
if (!localStorage.getItem('users')) {
  localStorage.setItem('users', JSON.stringify([
    {
      id: 'kimwooyoung',
      pw: 'admin1234',
      name: '김우영',
      role: 'admin'
    },
    {
      id: 'kimdonghyun',
      pw: 'admin1234',
      name: '김동현',
      role: 'admin'
    },
    {
      id: 'shimjaewon',
      pw: 'admin1234',
      name: '심재원',
      role: 'admin'
    },
    {
      id: 'hayonggoon',
      pw: 'admin1234',
      name: '하용군',
      role: 'admin'
    }
  ]));
}

const GOOGLE_MAPS_API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

function App() {
  if (!GOOGLE_MAPS_API_KEY) {
    return <div>Google Maps API 키가 .env에 없습니다.</div>;
  }

  return (
    <AuthProvider>
      <LoadScript googleMapsApiKey={GOOGLE_MAPS_API_KEY} language="ko">
        <Router>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />
            <Route path="/signup-select" element={<SignupSelect />} />
            <Route path="/matching" element={<MatchingPage />} />
            <Route path="/matching-wait" element={<MatchingWaitPage />} />
            <Route path="/support" element={<Support />} />
            <Route path="/mypage" element={<MyPage />} />
            <Route path="/wait" element={<WaitingPage />} />
            <Route path="/help" element={<HelpPage />} />
            <Route path="/mileage" element={<MileageUsagePage />} />
            <Route path="/warmthmap" element={<WarmthMapPage />} />
            <Route path="/find-id" element={<FindIdPage />} />
            <Route path="/find-pw" element={<FindPwPage />} />
            <Route path="/meeting-wait" element={<MeetingWaitPage />} />
            <Route path="/meeting" element={<MeetingPage />} />
            <Route path="/meeting-done" element={<MeetingDone />} />
            <Route path="/community" element={<CommunityPage />} />
            {/* 관리자 페이지 라우트 추가 */}
            <Route path="/admin" element={<AdminUser />} />
            <Route path="/admin/inquiry" element={<AdminInquiryPage />} />
            <Route path="/admin/post" element={<AdminPostManagePage />} />
            <Route path="/admin/setting" element={<AdminSystemSetting />} /> {/* 시스템 설정 페이지 */}
            <Route path="/admin/stats" element={<AdminStatistics />} />
            <Route path="*" element={<div>404 페이지를 찾을 수 없습니다.</div>} />
          </Routes>
        </Router>
      </LoadScript>
    </AuthProvider>
  );
}

export default App;
