package kr.or.human99.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.human99.dao.EmpDAO;
import kr.or.human99.dto.EmpDTO;

@Service
public class EmpService {
	
	@Autowired
	EmpDAO empDAO;
	
	public List<EmpDTO> getEmpList(){
		List<EmpDTO> result = empDAO.selectEmpList();
		
		return result;
	}
}
