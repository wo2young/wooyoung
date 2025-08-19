package todo.service;

import java.util.List;

import todo.DAO.TodoDAO;
import todo.DTO.TodoDTO;

public class TodoService {
	
	public List <TodoDTO> getList(){
		TodoDAO todoDAO = new TodoDAO();
		return todoDAO.selectAll();
	}
	
	public int addTodo(TodoDTO todoDTO) {
		if(todoDTO.getTitle() == null) {
			return -1;
		}
		TodoDAO todoDAO = new TodoDAO();	
		return todoDAO.insert(todoDTO);
	}
	public int removeTodo(TodoDTO todoDTO) {
		TodoDAO todoDAO = new TodoDAO();	
		return todoDAO.delete(todoDTO);
	}
	public TodoDTO getTodo(int tno) {
	    TodoDAO dao = new TodoDAO();
	    return dao.select_todo(tno);
	}
	public int modifyTodo(TodoDTO todoDTO) {
		TodoDAO todoDAO = new TodoDAO();	
		return todoDAO.updateTodo(todoDTO);
	}
	
	
	
	
}
