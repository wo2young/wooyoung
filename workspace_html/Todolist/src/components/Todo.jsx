import { useState } from 'react'; // 상태 관리를 위한 useState 훅 import
import { ListItem, Checkbox, InputBase, ListItemText } from '@mui/material'; // MUI에서 필요한 UI 컴포넌트 import

// props로 받은 item 객체 구조: { id, title, done }
function Todo(props) {
    const [item, setItem] = useState(props.item); // props로 전달된 item을 상태로 저장

    return (
        <ListItem>
            {/* 체크박스: 완료 여부 표시 (체크 여부는 item.done으로 결정됨) */}
            <Checkbox checked={item.done} />

            {/* ListItemText는 텍스트 영역을 감싸는 MUI 컴포넌트 */}
            <ListItemText>
                {/* 입력창: 할 일 제목 수정 가능하게 보이지만, 아직 변경 기능은 구현되지 않음 */}
                <InputBase
                    type="text"            // 입력 타입: 일반 텍스트
                    id={item.id}           // input 요소의 고유 ID
                    name={item.id}         // input 요소의 name (일반적으로 서버 통신 시 유용)
                    value={item.title}     // 현재 item의 제목을 input에 표시
                    multiline={true}       // 여러 줄 입력 허용
                    fullWidth={true}       // input 너비를 전체로 설정
                />
            </ListItemText>
        </ListItem>
    );
}

export default Todo;
        // 전체 구조
        // - props로 전달된 todo item을 내부 상태로 저장 (id, title, done 포함)
        // - ListItem 컴포넌트로 하나의 할 일을 렌더링
        // - Checkbox는 완료 여부 표시만 가능 (현재는 클릭 이벤트 없음)
        // - InputBase는 제목을 보여주지만 수정 로직 없음

        // 실무 중요 포인트 ★★★
        // ★ (중요) Input 입력값을 수정하려면 onChange 핸들러와 setItem 필요
        //    예: onChange={(e) => setItem({ ...item, title: e.target.value })}
        // ★ (중요) Checkbox 토글도 마찬가지로 클릭 시 done 값을 변경하도록 핸들링해야 함
        //    예: onClick={() => setItem({ ...item, done: !item.done })}
        // ★ (실무에서 자주 사용) ListItem 구조 + MUI 컴포넌트 조합은 리스트 UI를 만들 때 기본 패턴임