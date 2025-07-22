// BusinessCard.jsx
import { useState } from 'react';
function BusinessCard ({ name, email }){
    const [position, setPosition] = useState('개발자') // 기본값은 사원

    // 버튼 클릭 시 직책 변경 함수
    const togglePosition = () => {
        setPosition(prev => (prev === '개발자' ? '백앤드' : '개발자'));
    }; 

    return(
        <div className='business-card'>
            <h1>이름: {name}</h1>
            <p>직책: {position}</p>
            <p>email: {email}</p>
            <button onClick={togglePosition}>직책 변경</button>
        </div>
    );

    //     [핵심 개념]
    // - useState: React에서 상태(변하는 값)를 관리하는 훅
    // - JSX에서는 변수 대신 상태 값으로 화면 갱신
    // - onClick 속성으로 이벤트 함수 연결

    // [실무 중요도 ★★★]
    // - 버튼 클릭, 입력값 변경, 토글 등은 거의 전부 useState로 처리
    // - 상태가 바뀌면 자동으로 화면이 다시 렌더링됨
}

export default BusinessCard; // 이 명함을 수출합니다