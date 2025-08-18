package todo.service;

import java.util.List;

import todo.DAO.TodoDAO;
import todo.DTO.TodoDTO;

public class TodoService {
	
	public List <TodoDTO> getList(){
		TodoDAO todoDAO = new TodoDAO();
		return todoDAO.selectAll();
	}
	
	
	
	
}
