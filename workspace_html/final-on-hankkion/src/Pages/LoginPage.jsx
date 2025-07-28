import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../Styles/LoginPage.css';
import logoImg from '../assets/로고.png';

function LoginPage() {
  const [tab, setTab] = useState('id');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [remember, setRemember] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    if (!username || !password) {
      setError('아이디/비밀번호를 입력해주세요.');
    } else {
      setError('');
      navigate('/');
    }
  };

  return (
    <div className="login-wrapper">
      <div className="login-container">
        <div className="login-logo-wrap" onClick={() => navigate('/')}>
          <img src={logoImg} alt="한끼온 로고" className="login-main-logo" />
        </div>

        <div className="login-tabs">
          <button
            className={`login-tab-button ${tab === 'id' ? 'active' : ''}`}
            type="button"
            onClick={() => setTab('id')}
          >
            로그인
          </button>
          <button
            className={`login-tab-button ${tab === 'email' ? 'active' : ''}`}
            type="button"
            onClick={() => setTab('email')}
          >
            이메일 로그인
          </button>
        </div>

        <form className="login-form" onSubmit={handleLogin}>
          <div className="login-input-group">
            <input
              id="username"
              className="login-input"
              type={tab === 'email' ? 'email' : 'text'}
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder=" "   // 반드시 공백 한 칸만!
              required
              autoComplete="username"
            />
            <label htmlFor="username" className={`login-label ${username ? 'active' : ''}`}>
              {tab === 'email' ? '이메일 주소' : '아이디 또는 전화번호'}
            </label>
          </div>

          <div className="login-input-group">
            <input
              id="password"
              className="login-input"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder=" "
              required
              autoComplete="current-password"
            />
            <label htmlFor="password" className={`login-label ${password ? 'active' : ''}`}>
              비밀번호
            </label>
          </div>

          <div className="login-options">
            <input
              type="checkbox"
              id="remember"
              checked={remember}
              onChange={() => setRemember(!remember)}
            />
            <label htmlFor="remember" className="keep-login-label">로그인 상태 유지</label>
          </div>

          {error && <div className="login-error">{error}</div>}

          <button type="submit" className="login-btn">로그인</button>
          <button
            type="button"
            className="passkey-btn"
            onClick={() => alert('패스키 로그인이 아직 지원되지 않습니다.')}
          >
            패스키 로그인
          </button>
        </form>

        <div className="login-links">
          <a href="/find-id">아이디 찾기</a>
          <a href="/find-pw">비밀번호 찾기</a>
          <a href="#" onClick={(e) => { e.preventDefault(); navigate('/signup'); }}>회원가입</a>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
