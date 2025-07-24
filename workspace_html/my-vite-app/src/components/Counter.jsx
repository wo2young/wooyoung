import { useState } from 'react' 
function Counter() {
    
    const [count, setCount]=useState(0); // 카운트는 현재 상태값, setCount는 변경하는 함수
    // 증가 버튼을 클릭했을때 실행될 함수
    const handleIncrease=()=>{
        setCount(count+1);
    }
    const handleDecrease=()=>{
        setCount(count-1);
    }

    return(
        <div>
            <h2>카운터</h2>
            <h1>{count}</h1>
            <button onClick ={handleIncrease}>증가</button>
            <button onClick ={handleDecrease}>감소</button>
        </div>
    )
}
export default Counter