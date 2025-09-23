package kr.or.human4.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.human4.dao.EmpDAO;
import kr.or.human4.dto.EmpDTO;

@Service
public class EmpServiceImpl implements EmpService{

	@Autowired
	EmpDAO empDAO;
	
	@Override
	public List<EmpDTO> getEmpList(){
		List<EmpDTO> result = empDAO.selectEmpList();
		
		return result;
	}
	
	@Override
	public EmpDTO getEmp(){
		EmpDTO result = empDAO.selectOneEmp();
		
		return result;
	}
	
	@Override
	public Map getEmpMap(){
		Map result = empDAO.selectOneEmpMap();
		
		return result;
	}

	@Override
	public List getEmpno(int empno) {
		return empDAO.selectEmpno(empno);
	}
	
	@Override
	public List getEname(String ename) {
		return empDAO.selectEname(ename);
	}

	@Override
	public List getEmpnoEname(EmpDTO dto) {
		return empDAO.selectEmpnoEname(dto);
	}

	@Override
	public int joinEmp2(EmpDTO dto) {
		return empDAO.insertEmp2(dto);
	}

	@Override
	public EmpDTO getOneEmpno(int empno) {
		return empDAO.selectOneEmpno(empno);
	}

	@Override
	public int modifyEmp2(EmpDTO dto) {
		return empDAO.updateEmp2(dto);
	}

	@Override
	public int removeEmp2(EmpDTO dto) {
		return empDAO.deleteEmp2(dto);
	}

	@Override
	public List<EmpDTO> selectEmp(EmpDTO dto) {
		
		if(dto.getType().equals("1")) {
			dto.setEname(dto.getKeyword());
		} else if(dto.getType().equals("2")) {
			dto.setJob(dto.getKeyword());
		} else if(dto.getType().equals("3")) {
			dto.setEname(dto.getKeyword());
			dto.setJob(dto.getKeyword());
		} else if(dto.getType().equals("4")) {
			int sal = Integer.parseInt(dto.getKeyword());
			dto.setSal(sal);
		}
		
		return empDAO.selectEmp(dto);
	}
}