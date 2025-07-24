import { useState } from 'react';
import '../Styles/Support.css';
import logoImg from '../assets/로고.png';
import { Button } from '@mui/material';

function Support() {
  const [input, setInput] = useState('');
  const [messages, setMessages] = useState([
    {
      type: 'bot',
      text: '안녕하세요. 한끼온 AI입니다.\n궁금하신 내용을 간단한 문장으로 입력해 주세요.'
    }
  ]);

  const API_KEY = 'AIzaSyBlUUYUxrGyzMqvI1i7O_IgAUWr80iC9l4';

  const handleSend = async () => {
    if (!input.trim()) return;

    const userText = input;
    setInput('');
    setMessages(prev => [...prev, { type: 'user', text: userText }]);

    const conversation = [
      {
        role: 'user',
        parts: [{ text: '당신은 한끼온+ 고객지원 AI입니다. 고령층이 쉽게 이해할 수 있도록 설명해야 합니다.' }]
      },
      {
        role: 'model',
        parts: [{ text: '안녕하세요. 한끼온+ 고객지원 챗봇입니다. 무엇을 도와드릴까요?' }]
      },
      ...messages.flatMap((msg) =>
        msg.type === 'user'
          ? [{ role: 'user', parts: [{ text: msg.text }] }]
          : [{ role: 'model', parts: [{ text: msg.text }] }]
      ),
      {
        role: 'user',
        parts: [{ text: userText }]
      }
    ];

    try {
      const res = await fetch(
        `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${API_KEY}`,
        {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ contents: conversation })
        }
      );

      const data = await res.json();
      const botText =
        data?.candidates?.[0]?.content?.parts?.[0]?.text ||
        '죄송합니다. 이해하지 못했어요.';

      setMessages(prev => [...prev, { type: 'bot', text: botText }]);
    } catch (error) {
      console.error('Gemini API 오류:', error);
      setMessages(prev => [
        ...prev,
        { type: 'bot', text: '오류가 발생했어요. 다시 시도해 주세요.' }
      ]);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') handleSend();
  };

  return (
    <div className="support-page">
      <header className="support-header">
        <div className="header-left">
          <img src={logoImg} alt="한끼온 로고" className="logo-img" />
        </div>
        <nav className="header-center">
          <a href="/matching">매칭페이지</a>
          <a href="/board">게시판</a>
          <a href="/support">고객지원</a>
          <a href="/mileage">마일리지 사용자</a>
        </nav>
        <div className="header-right">
          <a href="/mypage">마이페이지</a>
          <Button variant="outlined" color="warning" size="small">
            LOG OUT
          </Button>
        </div>
      </header>

      <main className="support-main">
        <h2>고객 센터</h2>
        <p className="sub-title">무엇을 도와드릴까요?</p>

        <section className="chat-box">
          <div className="chat-title">한끼온 Chat AI</div>
          <div className="chat-date">2025.7.31</div>

          <div className="chat-content">
           {messages.map((msg, i) => (
            <div key={i} className={`chat-${msg.type}`}>
              {msg.type === 'bot' ? (
                <>
                  <div className="chat-profile">한끼온+ Chat AI</div>
                  <div className={`chat-bubble ${msg.type}`}>{msg.text}</div>
                </>
              ) : (
                <>
                  <div className={`chat-bubble ${msg.type}`}>{msg.text}</div>
                  <div className="chat-profile">사용자</div>
                </>
              )}
            </div>
          ))}
          </div>

          <div className="chat-input-area">
            <input
              type="text"
              placeholder="예: 제휴 사이트를 알고싶어요"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={handleKeyPress}
            />
            <button onClick={handleSend}>전송</button>
          </div>
        </section>
      </main>
    </div>
  );
}

export default Support;
