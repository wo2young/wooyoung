import { useState } from 'react';
import Header from '../components/Header';
import '../Styles/SignupPage.css';

function SignupPage() {
  const [form, setForm] = useState({
    name: '',
    rrn1: '',
    rrn2: '',
    addr: '',
    addrDetail: '',
    id: '',
    pw: '',
    pw2: '',
    phone: ''
  });
  const [error, setError] = useState('');

  // input 공통 핸들러
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  // 회원가입 로직
  const handleSignup = (e) => {
    e.preventDefault();
    setError('');

    // 필수값 검사
    const requiredFields = ['name', 'rrn1', 'rrn2', 'addr', 'id', 'pw', 'pw2', 'phone'];
    for (const field of requiredFields) {
      if (!form[field]) {
        setError('모든 항목을 입력해주세요.');
        return;
      }
    }
    if (form.pw !== form.pw2) {
      setError('비밀번호가 일치하지 않습니다.');
      return;
    }

    // 아이디 중복 체크 (localStorage 배열 기준)
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    if (users.find(u => u.id === form.id)) {
      setError('이미 존재하는 아이디입니다.');
      return;
    }

    // 회원 정보 저장
  const newUser = {
  name: form.name,
  rrn: `${form.rrn1}-${form.rrn2}`,
  addr: form.addr,
  addrDetail: form.addrDetail,
  id: form.id,
  pw: form.pw,
  phone: form.phone,
  mileage: 0   // 기본 마일리지 추가
};
    users.push(newUser);
    localStorage.setItem('users', JSON.stringify(users));

    alert('회원가입이 완료되었습니다!');
    window.location.href = '/login';
  };

  return (
    <div className="signup-root">
      <Header />
      <div className="signup-center-wrap">
        <div className="signup-card">
          <h2 className="signup-title">회원가입</h2>
          <form className="signup-form" onSubmit={handleSignup} autoComplete="off">
            <div className="signup-field">
              <label>이름</label>
              <input
                name="name"
                value={form.name}
                onChange={handleChange}
                maxLength={10}
                autoFocus
                required
              />
            </div>

            <div className="signup-field rrn-row">
              <label>주민번호</label>
              <input
                name="rrn1"
                value={form.rrn1}
                onChange={handleChange}
                maxLength={6}
                placeholder="앞 6자리"
                required
              />
              <span className="rrn-dash">-</span>
              <input
                name="rrn2"
                value={form.rrn2}
                onChange={handleChange}
                maxLength={7}
                placeholder="뒤 7자리"
                required
              />
            </div>

            <div className="signup-field addr-row">
              <label>주소</label>
              <input
                className="addr"
                name="addr"
                value={form.addr}
                onChange={handleChange}
                placeholder="주소"
                required
              />
              <input
                className="addrDetail"
                name="addrDetail"
                value={form.addrDetail}
                onChange={handleChange}
                placeholder="상세검색"
              />
            </div>

            <div className="signup-field">
              <label>아이디</label>
              <input
                name="id"
                value={form.id}
                onChange={handleChange}
                maxLength={15}
                required
              />
            </div>

            <div className="signup-field">
              <label>비밀번호</label>
              <input
                name="pw"
                type="password"
                value={form.pw}
                onChange={handleChange}
                maxLength={30}
                required
              />
            </div>
            <div className="signup-field">
              <label>비밀번호 확인</label>
              <input
                name="pw2"
                type="password"
                value={form.pw2}
                onChange={handleChange}
                maxLength={30}
                required
              />
            </div>
            <div className="signup-field">
              <label>휴대폰 번호</label>
              <input
                name="phone"
                value={form.phone}
                onChange={handleChange}
                maxLength={13}
                required
              />
            </div>
            {error && <div className="signup-error">{error}</div>}

            <button className="signup-btn" type="submit">가입 하기</button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default SignupPage;
