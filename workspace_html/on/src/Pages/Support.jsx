import { useState, useRef, useEffect } from 'react';
import '../Styles/Support.css';
import Layout from '../components/Layout';
import SupportPopup from '../components/SupportPopup';
import chatbotImg from '../assets/chatbot.png';

function Support() {
  const [input, setInput] = useState('');
  const [messages, setMessages] = useState([
    {
      type: 'bot',
      text: '안녕하세요. 한끼온 AI입니다.\n궁금하신 내용을 간단한 문장으로 입력해 주세요.'
    }
  ]);
  const [popupCollapsed, setPopupCollapsed] = useState(false);

  // 👇 스크롤 내릴 ref
  const messagesEndRef = useRef(null);

  // 메시지 바뀌면 자동 스크롤
  useEffect(() => {
    if (messagesEndRef.current) {
      messagesEndRef.current.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages]);

  const today = new Date();
  const dateStr = `${today.getFullYear()}.${String(today.getMonth() + 1).padStart(2, '0')}.${String(today.getDate()).padStart(2, '0')}`;

  const API_KEY = 'AIzaSyBlUUYUxrGyzMqvI1i7O_IgAUWr80iC9l4'; // 실제 발급키로 교체!

  // 챗봇 메시지 전송
  const handleSend = async (q = null) => {
    const userText = q !== null ? q : input;
    if (!userText.trim()) return;
    setInput('');
    const updatedMessages = [...messages, { type: 'user', text: userText }];
    setMessages(updatedMessages);

    const conversation = [
      {
        role: 'user',
        parts: [{ text: '당신은 한끼온+ 고객지원 AI입니다. 고령층이 쉽게 이해할 수 있도록 설명해야 합니다.' }]
      },
      {
        role: 'model',
        parts: [{ text: '안녕하세요. 한끼온+ 고객지원 챗봇입니다. 무엇을 도와드릴까요?' }]
      },
      ...updatedMessages.map(msg =>
        msg.type === 'user'
          ? { role: 'user', parts: [{ text: msg.text }] }
          : { role: 'model', parts: [{ text: msg.text }] }
      )
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
      setMessages(prev => [
        ...prev,
        { type: 'bot', text: '오류가 발생했어요. 다시 시도해 주세요.' }
      ]);
    }
  };

  // 인풋 Enter 전송
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') handleSend();
  };

  // 팝업 내리기/올리기 토글
  const handleTogglePopup = () => setPopupCollapsed(prev => !prev);

  // 팝업 FAQ/카드 클릭 시 질문+전송, 팝업내리기
  const handlePopupQuestion = (questionText) => {
    setPopupCollapsed(true); // 팝업을 내려서 챗봇만 보이게
    setTimeout(() => handleSend(questionText), 0);
  };

  return (
    <Layout>
      <div className="support-page">
        <main className="support-main">
          <h2>고객 센터</h2>
          <p className="sub-title">무엇을 도와드릴까요?</p>
          <section className="chat-box">
            <div className="chat-title">한끼온 Chat AI</div>
            <div className="chat-date">{dateStr}</div>
            {/* 스크롤 자동 이동 가능하게 overflow-y: auto와 ref */}
            <div className="chat-content" style={{ overflowY: 'auto', maxHeight: 440 }}>
              {messages.map((msg, i) =>
                msg.type === 'bot' ? (
                  <div key={i} className="chat-bot">
                    <div className="chat-profile">
                      <img src={chatbotImg} alt="AI 챗봇" />
                      <span></span>
                    </div>
                    <div className="chat-bubble">{msg.text}</div>
                  </div>
                ) : (
                  <div key={i} className="chat-user">
                    <div className="chat-bubble user">{msg.text}</div>
                    <div className="chat-profile user">사용자</div>
                  </div>
                )
              )}
              {/* 이 div가 맨 아래로 스크롤 이동을 트리거 */}
              <div ref={messagesEndRef} />
            </div>
            <div className="chat-input-area">
              <input
                type="text"
                placeholder="예: 제휴 사이트를 알고싶어요"
                value={input}
                onChange={e => setInput(e.target.value)}
                onKeyDown={handleKeyPress}
                autoFocus
              />
              <button onClick={() => handleSend()}>전송</button>
            </div>
          </section>
        </main>
        {/* SupportPopup 항상 화면 아래 */}
        <SupportPopup
          collapsed={popupCollapsed}
          onToggle={handleTogglePopup}
          onQuestion={handlePopupQuestion}
        />
      </div>
    </Layout>
  );
}

export default Support;
