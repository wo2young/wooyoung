import { useState } from 'react'; // React 상태 관리 훅
import reactLogo from './assets/react.svg'; // (사용 안 함) 리액트 로고
import viteLogo from '/vite.svg';           // (사용 안 함) Vite 로고
import './App.css';                         // CSS 스타일 import
import Todo from './components/Todo';       // Todo 컴포넌트 import
// import { Paper, List, Container } from '@mui/material'; // MUI 종이 UI 컴포넌트 + 리스트
import AddTodo from './components/AddTodo';
import { Paper, List, Container, Button } from '@mui/material';

function App() {
    // 상태로 할 일 목록을 저장
    const [items, setItems] = useState([]);

    const addItem = (item) => {
    const newItem = {
      ...item,
      id: new Date().getTime().toString(), // 고유 ID 부여
      done: false,                         // 처음엔 미완료
    };
     setItems([...items, newItem]); // 리스트에 추가
  };

  const deleteItem=(item)=>{
    const newItems=items.filter((e)=> e.id !== item.id);
    
    const confirmDelete = window.confirm('정말로 삭제하시겠습니까?')


    const updateItems = [...newItems]
    setItems(updateItems);
    alert('삭제되었습니다')
    console.log("updateItems : ", updateItems);
  }
  const alldelete = () => {
  const ok = window.confirm('정말로 모든 할 일을 삭제하시겠습니까?');
  if (ok) {
    setItems([]);
    alert('전체 삭제되었습니다.');
    }
    };
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
                    <Todo item={item} key={item.id} deleteItem={deleteItem} />
                ))}
            </List>
        </Paper>
    );

    // App 컴포넌트 전체 UI
    return (
        <div className="App">
            <Container maxWidth="md">
                <Button variant="contained" color="error" onClick={alldelete}>
                    전체 삭제
                </Button>
                <AddTodo addItem={addItem}/> 
                <div>{todoItems}</div>
            </Container>
        </div>
    )
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