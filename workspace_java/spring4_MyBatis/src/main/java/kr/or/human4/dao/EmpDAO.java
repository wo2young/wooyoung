package kr.or.human4.dao;

import java.util.List;
import java.util.Map;

import kr.or.human4.dto.EmpDTO;

public interface EmpDAO {
	List<EmpDTO> selectEmpList();
	EmpDTO selectOneEmp();
	Map selectOneEmpMap();
	List selectEmpno(int empno);
	List selectEname(String ename);
}
