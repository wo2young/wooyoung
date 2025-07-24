import { useState } from 'react'; // React 상태 관리 훅
import reactLogo from './assets/react.svg'; // (사용 안 함) 리액트 로고
import viteLogo from '/vite.svg';           // (사용 안 함) Vite 로고
import './App.css';                         // CSS 스타일 import
import Todo from './components/Todo';       // Todo 컴포넌트 import
import { Paper, List } from '@mui/material'; // MUI 종이 UI 컴포넌트 + 리스트

function App() {
    // 상태로 할 일 목록을 저장
    const [items, setItems] = useState([
        {
            id: '0',
            title: 'Sample Todo1',
            done: true, // 완료 상태 표시
        },
        {
            id: '1',
            title: 'Sample Todo2',
            done: false,
        },
    ]);

    // 리스트에 항목이 있을 경우만 렌더링
    let todoItems = items.length > 0 && (
        <Paper
            style={{
                margin: 16,
                boxShadow: '0 5px 8px rgba(0,0,0,0.5)', // 그림자 효과
            }}
        >
            <List>
                {items.map((item) => (
                    <Todo item={item} key={item.id} />
                ))}
            </List>
        </Paper>
    );

    // App 컴포넌트 전체 UI
    return <div className="App">{todoItems}</div>;
}

export default App;
        // // 컴포넌트 구성
        // - App 컴포넌트는 items라는 상태 배열을 가지고 있음
        // - 각 할 일은 <Todo /> 컴포넌트로 렌더링됨
        // - Paper → List → Todo 항목들 구조

        // // 렌더링 조건
        // - items 배열이 비어 있지 않을 경우에만 Paper + List가 렌더링됨

        // // 외부 컴포넌트
        // - Todo 컴포넌트는 할 일 1개를 담당