package kr.or.human4.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.or.human4.dto.EmpDTO;

@Service
public interface EmpService {
	
	public List<EmpDTO> getEmpList();
	public EmpDTO getEmp();
	public Map getMap();
	public List getEmpno(int empno);
	public List getEname(String ename);
	
}
