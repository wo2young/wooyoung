import { useState } from 'react';
import { ListItem, ListItemText, InputBase, Checkbox, ListItemSecondaryAction, IconButton } from '@mui/material';
import DeleteOutlined from '@mui/icons-material/DeleteOutlined';
function Todo(props) {
    const [item, setItem] = useState(props.item);
    const deleteItem = props.deleteItem; // 부모 컴포넌트에서 전달된 deleteItem 함수

    // deleteEventHandler 함수는 아이템 삭제 버튼 클릭 시 호출
    const deleteEventHandler = () => {
       if(item.done) {
        deleteItem(item); // 아이템 삭제 함수 호출
       } else {
        alert('체크 먼저 해주세요')
       }
    }
    const CheckboxHandler = (e) => {
        item.done=e.target.checked;
        setItem({...item});
    }

    return (
        <ListItem>
            <Checkbox checked={item.done} onChange={CheckboxHandler}/>
            <ListItemText>
                <InputBase
                    type="text"
                    id={item.id}
                    name={item.id}
                    value={item.title}
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton onClick={deleteEventHandler}>
                    <DeleteOutlined />
                </IconButton>
                
            </ListItemSecondaryAction>
        </ListItem>
    );
}
export default Todo;