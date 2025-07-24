import { useState } from "react";
import { Grid, TextField, Button } from '@mui/material';

function AddTodo(props) {
    // 사용자 입력을 저장할 객체
    const [item, setItem] = useState({ title: '' });
    const addItem = props.addItem;

    // 입력창에서 값이 변경될 때 호출되는 함수
    const onInputChange = (e) => {
        setItem({ title: e.target.value });
        console.log('현재 입력:', e.target.value);
    };

    // 추가 버튼을 눌렀을 때 실행
    const onButtonClick = () => {
        if (item.title.trim() !== '') {
            addItem(item); // 상위 컴포넌트로 전달
            setItem({ title: '' }); // 입력창 초기화
        } else { // 아이템이 비어있다면
            alert('할 일을 입력하세요!');
        }
    };

    const enterKeyEventHandler = (e) => {
        if (e.key==='Enter') {
            onButtonClick();
        }
    }

    return (
        <Grid container style={{ justifyContent: 'center', marginTop: 20 }}>
            <Grid item style={{ paddingRight: '16px' }}>
                <TextField
                    placeholder="Add Todo here"
                    style={{ width: '700px' }}
                    value={item.title}
                    onChange={onInputChange}
                    onKeyPress={enterKeyEventHandler}
                />
            </Grid>
            <Grid item>
                <Button
                    style={{ height: '100%' }}
                    color="secondary"
                    variant="outlined"
                    onClick={onButtonClick}
                >
                    추가
                </Button>
            </Grid>
        </Grid>
    );
}

export default AddTodo;
