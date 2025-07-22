// import { useState } from 'react'     // 지금은 사용되지 않으므로 생략 가능
// import reactLogo from './assets/react.svg'  // 사용 안 함 → 생략 가능
// import viteLogo from '/vite.svg'            // 사용 안 함 → 생략 가능
import './App.css'
import BusinessCard from './components/BusinessCard';  // 명함 컴포넌트 import
import Book from './components/Book';
function App() {
   // ↓ 아래는 분리했으니 더 이상 필요 없음
   // const name = "김우영"
   // const position = "사원"
   // const email = "ywk5974@gmail.com"

   // BusinessCard에 props로 값 전달
   // return <BusinessCard name="김우영" position="사원" email="ywk5974@gamil.com" />;
   // const people = [
   //    { name : "김우영" , email : "ywk5974@gmail.com"},
   //    { name : "김동현" , email : "aa@gmail.com"},
   //    { name : "하용군" , email : "bbb@gmail.com"},
   //    { name : "심재원" , email : "ccc@gmail.com"},
   //    { name : "김현민" , email : "ddd@gmail.com"}
   // ]
   const book = [
      { title : "잠자고 싶은 토끼", 
         author : "칼 요한", 
         price : "15,120원",
           // imgSrc: src = "https://..." 는 문법 오류!
         // 'src =' 는 변수에 값을 할당하는 문법이고, JSX 객체 내부에서는 단순 문자열로만 사용해야 함 
         imgSrc : "https://contents.kyobobook.co.kr/sih/fit-in/400x0/pdt/9791199247819.jpg"
      },
      { title : "소설 쓰기 싫은 날", 
         author : "오한기", 
         price : "14,400원",
           // imgSrc: src = "https://..." 는 문법 오류!
         // 'src =' 는 변수에 값을 할당하는 문법이고, JSX 객체 내부에서는 단순 문자열로만 사용해야 함 
         imgSrc : "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788937419638.jpg"
      },
      { title : "아이네이스3", 
         author : "베르길리우스", 
         price : "11,520원",
           // imgSrc: src = "https://..." 는 문법 오류!
         // 'src =' 는 변수에 값을 할당하는 문법이고, JSX 객체 내부에서는 단순 문자열로만 사용해야 함 
         imgSrc : "https://contents.kyobobook.co.kr/sih/fit-in/200x0/pdt/9788932925189.jpg"
      }
   ]
   return (
      <div className='book-list'>
         {/* { people.map((person, index) => (
            <BusinessCard
            key={index} // 실무에서는 고유ID
            name={person.name}
            email={person.email}
            />
         ))} */}
         { book.map((num, index) => (
            <Book
            key={index}
            title={num.title}
            author={num.author}
            price={num.price}
            imgSrc={num.imgSrc}
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
