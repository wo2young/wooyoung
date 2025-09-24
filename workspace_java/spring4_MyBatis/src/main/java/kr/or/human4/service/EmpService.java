package kr.or.human4.service;

import java.util.List;
import java.util.Map;

import kr.or.human4.dto.EmpDTO;

public interface EmpService {

	public List<EmpDTO> getEmpList();
	public EmpDTO getEmp();
	public Map getEmpMap();
	public List getEmpno(int empno);
	public List getEname(String ename);
	public List getEmpnoEname(EmpDTO dto);
	public int joinEmp2(EmpDTO dto);
	
	public EmpDTO getOneEmpno(int empno);
	public int modifyEmp2(EmpDTO dto);
	public int removeEmp2(EmpDTO dto);
	
	public List<EmpDTO> selectEmp(EmpDTO dto);
	public List<EmpDTO> foreach(EmpDTO dto);
	
}