// import { useState } from 'react'     // 지금은 사용되지 않으므로 생략 가능
// import reactLogo from './assets/react.svg'  // 사용 안 함 → 생략 가능
// import viteLogo from '/vite.svg'            // 사용 안 함 → 생략 가능
import './App.css'
import BusinessCard from './components/BusinessCard';  // 명함 컴포넌트 import

function App() {
   // ↓ 아래는 분리했으니 더 이상 필요 없음
   // const name = "김우영"
   // const position = "사원"
   // const email = "ywk5974@gmail.com"

   // BusinessCard에 props로 값 전달
   // return <BusinessCard name="김우영" position="사원" email="ywk5974@gamil.com" />;
   const people = [
      { name : "김우영" , email : "ywk5974@gmail.com"},
      { name : "임영웅" , email : "aa@gmail.com"},
      { name : "박효신" , email : "bbb@gmail.com"}
   ];

   return (
      <div>
         { people.map((person, index) => (
            <BusinessCard
            key={index} // 실무에서는 고유ID
            name={person.name}
            email={person.email}
            />
         ))}
      {/* [이번 단계 핵심]
      - 배열을 map()으로 반복 출력
      - 각 요소마다 <BusinessCard /> 컴포넌트 사용
      - key={index}는 map 반복문에서 필수 (실무에선 고유한 id 사용 권장)

      [실무 중요도 ★★★]
      - 리스트 출력은 거의 100% map() 사용
      - key 없으면 React가 최적화를 못하고 경고 발생
      - 데이터 → UI 자동 렌더링 흐름에 꼭 익숙해져야 함 */}

      </div>
   );
}

export default App;  
