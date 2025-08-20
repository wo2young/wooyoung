package practice.service;

import java.util.List;

import practice.dao.TodoDAO;
import practice.dto.TodoDTO;

public class TodoServiceImpl {
	
	public List<TodoDTO> getList() {
		TodoDAO dao = new TodoDAO();
		return dao.selectAll();
	}
	
	public TodoDTO getOne(int tno) {
	    TodoDAO dao = new TodoDAO();
	    return dao.selectOne(tno); // 객체를 돌려주기 때문에 TodoDTO로 타입을 해야함.
	}
	
	public int add(TodoDTO dto) {
		TodoDAO dao = new TodoDAO();
		return dao.insert(dto);
	}
	
}
