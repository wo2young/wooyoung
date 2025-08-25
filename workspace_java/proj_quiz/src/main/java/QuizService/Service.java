package QuizService;

import java.util.List;

import QuizDAO.DAO;
import QuizDTO.DTO;

public class Service {
	DAO dao = new DAO();
	
	public List<DTO> getAll(){
		List<DTO> list = dao.selectAll();
		return list;
	}
	
}
