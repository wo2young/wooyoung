import { useState } from 'react';
// import Header from '../components/Layout';
import '../Styles/SignupPage.css';
import Layout from '../components/Layout';

function SignupPage() {
  const [form, setForm] = useState({
    name: '',
    rrn1: '',
    rrn2: '',
    email: '',
    id: '',
    pw: '',
    pw2: '',
    phone: '',
    addr: '',
    addrDetail: ''
  });
  const [checked, setChecked] = useState({
    all: false,
    terms: false,
    privacy: false,
    marketing: false,
  });
  const [idCheck, setIdCheck] = useState(false);
  const [emailCheck, setEmailCheck] = useState(false);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value, type, checked: checkedValue } = e.target;
    if (type === "checkbox") {
      if (name === "all") {
        setChecked({
          all: checkedValue,
          terms: checkedValue,
          privacy: checkedValue,
          marketing: checkedValue,
        });
      } else {
        setChecked(prev => {
          const next = { ...prev, [name]: checkedValue };
          next.all = next.terms && next.privacy && next.marketing;
          return next;
        });
      }
    } else if (name === "phone") {
      const onlyNum = value.replace(/[^0-9]/g, '').slice(0, 11);
      setForm(prev => ({ ...prev, phone: onlyNum }));
    } else if (name === "rrn2") {
      // 1~4만 허용, 숫자 외 무시
      const val = value.replace(/[^0-9]/g, '').slice(0, 1);
      if (val === "" || (Number(val) >= 1 && Number(val) <= 4)) {
        setForm(prev => ({ ...prev, rrn2: val }));
      }
    } else {
      setForm(prev => ({ ...prev, [name]: value }));
    }
    if (name === "id") setIdCheck(false);
    if (name === "email") setEmailCheck(false);
  };

  const handleIdCheck = () => {
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    if (!form.id) {
      alert('아이디를 입력하세요!');
      return;
    }
    if (users.some(u => u.id === form.id)) {
      alert('이미 사용 중인 아이디입니다.');
      setIdCheck(false);
    } else {
      alert('사용 가능한 아이디입니다.');
      setIdCheck(true);
    }
  };
  const handleEmailCheck = () => {
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    if (!form.email) {
      alert('이메일을 입력하세요!');
      return;
    }
    if (users.some(u => u.email === form.email)) {
      alert('이미 등록된 이메일입니다.');
      setEmailCheck(false);
    } else {
      alert('사용 가능한 이메일입니다.');
      setEmailCheck(true);
    }
  };

  const handleAddrSearch = () => alert("주소 상세검색 기능 준비 중!");

  const handleSignup = (e) => {
    e.preventDefault();
    setError('');
    if (!checked.terms || !checked.privacy) {
      setError('필수 약관을 동의해야 가입할 수 있습니다.');
      return;
    }
    const requiredFields = ['name', 'rrn1', 'rrn2', 'email', 'id', 'pw', 'pw2', 'phone', 'addr'];
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
    if (form.phone.length !== 11) {
      setError('휴대폰 번호는 11자리 숫자로 입력해야 합니다.');
      return;
    }
    if (!idCheck) {
      setError('아이디 중복확인을 해주세요.');
      return;
    }
    if (!emailCheck) {
      setError('이메일 중복확인을 해주세요.');
      return;
    }
    const users = JSON.parse(localStorage.getItem('users') || '[]');
    const newUser = {
      name: form.name,
      rrn: `${form.rrn1}-${form.rrn2}******`,
      email: form.email,
      addr: form.addr,
      addrDetail: form.addrDetail,
      id: form.id,
      pw: form.pw,
      phone: form.phone,
      mileage: 0
    };
    users.push(newUser);
    localStorage.setItem('users', JSON.stringify(users));
    alert('회원가입이 완료되었습니다!');
    window.location.href = '/login';
  };

  return (
    <Layout>
      <div className="signup-root-minimal">
        <form className="signup-form-minimal" onSubmit={handleSignup} autoComplete="off">
          <h2 className="signup-title-minimal">회원가입</h2>
          <p className="signup-guide-minimal">
            <span>*</span> 표시 항목은 필수 입력해야 합니다
          </p>
          <div className="signup-field-minimal">
            <label htmlFor="name">이름<span>*</span></label>
            <input id="name" name="name" value={form.name} onChange={handleChange} maxLength={10} autoFocus required placeholder="이름을 입력해주세요" />
          </div>
          <div className="signup-field-minimal rrn-row">
            <label htmlFor="rrn1">주민번호<span>*</span></label>
            <input id="rrn1" name="rrn1" value={form.rrn1} onChange={handleChange} maxLength={6} required autoComplete="off" />
            <span className="rrn-dash">-</span>
            <input
              id="rrn2"
              name="rrn2"
              type="number"
              min={1}
              max={4}
              value={form.rrn2}
              onChange={handleChange}
              maxLength={1}
              required
              autoComplete="off"
              className="rrn-back-num"
            />
            <span className="rrn-star">******</span>
          </div>
          <div className="signup-field-minimal with-btn">
            <label htmlFor="email">이메일<span>*</span></label>
            <input id="email" name="email" value={form.email} onChange={handleChange} maxLength={40} required placeholder="이메일을 입력해주세요." autoComplete="off" />
            <button type="button" className="id-btn-minimal" onClick={handleEmailCheck}>중복확인</button>
          </div>
          <div className="signup-field-minimal with-btn">
            <label htmlFor="id">아이디<span>*</span></label>
            <input id="id" name="id" value={form.id} onChange={handleChange} maxLength={20} required placeholder="아이디 입력(6~20자)" autoComplete="off" />
            <button type="button" className="id-btn-minimal" onClick={handleIdCheck}>중복확인</button>
          </div>
          <div className="signup-field-minimal">
            <label htmlFor="pw">비밀번호<span>*</span></label>
            <input id="pw" name="pw" type="password" value={form.pw} onChange={handleChange} maxLength={20} required placeholder="비밀번호 입력(문자,숫자,특수문자 포함 8~20자)" autoComplete="new-password" />
          </div>
          <div className="signup-field-minimal">
            <label htmlFor="pw2">비밀번호 확인<span>*</span></label>
            <input id="pw2" name="pw2" type="password" value={form.pw2} onChange={handleChange} maxLength={20} required placeholder="비밀번호 재입력" autoComplete="new-password" />
          </div>
          <div className="signup-field-minimal">
            <label htmlFor="phone">휴대폰 번호<span>*</span></label>
            <input id="phone" name="phone" value={form.phone} onChange={handleChange} maxLength={11} required inputMode="numeric" pattern="[0-9]*" placeholder="휴대폰 번호 입력 ('-' 제외 11자리)" autoComplete="off" />
          </div>
          <div className="signup-field-minimal with-btn">
            <label htmlFor="addr">주소<span>*</span></label>
            <input id="addr" name="addr" value={form.addr} onChange={handleChange} placeholder="주소를 입력해주세요." required autoComplete="off" />
            <button type="button" className="addr-btn-minimal" onClick={handleAddrSearch}>상세검색</button>
          </div>
          <div className="signup-field-minimal">
            <label htmlFor="addrDetail" className="invisible-label">상세주소</label>
            <input id="addrDetail" name="addrDetail" value={form.addrDetail} onChange={handleChange} placeholder="상세주소" autoComplete="off" />
          </div>
          {error && <div className="signup-error-minimal">{error}</div>}
          <div className="signup-check-group-minimal">
            <div className="signup-check-row-minimal">
              <input type="checkbox" id="all" name="all" checked={checked.all} onChange={handleChange} />
              <label htmlFor="all"><b>모든 약관에 동의합니다.</b></label>
            </div>
            <div className="signup-check-row-minimal">
              <input type="checkbox" id="terms" name="terms" checked={checked.terms} onChange={handleChange} />
              <label htmlFor="terms">
                한끼온+ 이용 약관에 동의 합니다. <span className="check-essential">(필수)</span>
                <a href="#" className="check-detail">상세보기</a>
              </label>
            </div>
            <div className="signup-check-row-minimal">
              <input type="checkbox" id="privacy" name="privacy" checked={checked.privacy} onChange={handleChange} />
              <label htmlFor="privacy">
                개인정보 수집 및 이용에 동의합니다. <span className="check-essential">(필수)</span>
                <a href="#" className="check-detail">상세보기</a>
              </label>
            </div>
            <div className="signup-check-row-minimal">
              <input type="checkbox" id="marketing" name="marketing" checked={checked.marketing} onChange={handleChange} />
              <label htmlFor="marketing">
                마케팅 활용 및 광고성 정보 수신에 동의합니다. <span className="check-optional">(선택)</span>
                <a href="#" className="check-detail">상세보기</a>
              </label>
            </div>
          </div>
          <button className="signup-submit-btn-minimal" type="submit">가입완료</button>
        </form>
      </div>
    </Layout>
  );
}

export default SignupPage;
