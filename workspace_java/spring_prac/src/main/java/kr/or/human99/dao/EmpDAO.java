package kr.or.human99.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.human99.dto.EmpDTO;

@Repository
public class EmpDAO {

	@Autowired
	SqlSession sqlSession;

	public List<EmpDTO> selectEmpList() {
		List<EmpDTO> resultList = null;

		resultList = sqlSession.selectList("mapper.emp.selectEmp");
		System.out.println("resultList : " + resultList);

		return resultList;
	}
}
