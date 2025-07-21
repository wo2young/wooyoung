import logo from './logo.svg';
import './App.css';

function App() {
  const name = "김우영"
  const year=2025;

  return(
    // 1. 하나의 부모 요소로 감싸자
    <div>
      <h1>안녕하세여, {name}!</h1>
      <h2>react와 함께하는 {year}년!</h2>
      <p>이것은 첫번쨰 react 컴포넌트입니다.</p>
      </div>
  )
}

export default App;
