package emp.service;

import java.util.List;

import emp.dao.EmpDAO;
import emp.dto.EmpDTO;

public class EmpService {
	
	EmpDAO dao = new EmpDAO();
	
	public List<EmpDTO> getAllemp() {	
		List<EmpDTO> list = dao.selectAllEmp();
		return list;
	}
	public EmpDTO getOneemp(EmpDTO dto) {
		dto = dao.selectOneEmp(dto);
		return dto;
	}
	
	public int removeEmp(EmpDTO dto) {
		return dao.deleteEmp(dto);
	}
	public int addEmp(EmpDTO dto) {
		return dao.insertEmp(dto);
	}
	public int updateEmp(EmpDTO dto) {
		return dao.updateEmp(dto);
	}
}
