package emp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import emp.dto.EmpDTO;

public class EmpDAO {

	// DB 접속
	private Connection getConn() {
		Connection conn = null;
		try {
			// JNDI 방식 : 글씨로 뭔가를 가져오는 방식
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List<EmpDTO> selectAllEmp() {
		List<EmpDTO> list = new ArrayList<EmpDTO>();
		try {
			// DB 접속
			Connection conn = getConn();

			// SQL 준비
			String query = "select * from emp2";
			PreparedStatement ps = conn.prepareStatement(query);

			// SQL 실행
			ResultSet rs = ps.executeQuery();
			
			// 결과 활용
			while(rs.next()) {
				EmpDTO dto = new EmpDTO();
				
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
//				String job = rs.getString("job");
//				Integer mgr = rs.getInt("mgr");
//				Date hiredate = rs.getDate("hiredate");
				int sal = rs.getInt("sal");
//				Integer comm = rs.getInt("comm");
//				int deptno = rs.getInt("deptno");
				
				
				dto.setEmpno(empno);
				dto.setEname(ename);
//				empDTO.setJob(job);
//				empDTO.setMgr(mgr);
//				empDTO.setHiredate(hiredate);
				dto.setSal(sal);
//				empDTO.setComm(comm);
//				empDTO.setDeptno(deptno);
        		list.add(dto);
			}
			
		    rs.close();
		    ps.close();
		    conn.close(); // (수정) 제일 마지막에 닫기
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return list;
	}
	public EmpDTO selectOneEmp(EmpDTO empDTO) {
		EmpDTO resultDTO = null;
		try {
			// DB 접속
			Connection conn = getConn();

			// SQL 준비
			String query = " select * from emp2";
				   query += " where empno = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, empDTO.getEmpno());
			
			// SQL 실행
			ResultSet rs = ps.executeQuery();
			
			// 결과 활용
			if(rs.next()) {
//				EmpDTO dto = new EmpDTO();
				resultDTO = new EmpDTO();
				int empno = rs.getInt("empno");
				resultDTO.setEmpno(empno);				
				resultDTO.setEname(   rs.getString("ename"));
				resultDTO.setJob(     rs.getString("job"));
				resultDTO.setMgr(     rs.getInt("mgr"));
				resultDTO.setHiredate(rs.getDate("hiredate"));
				resultDTO.setSal(     rs.getInt("sal"));
				resultDTO.setComm(    rs.getInt("comm"));
				resultDTO.setDeptno(  rs.getInt("deptno"));

				
			}
			
		    rs.close();
		    ps.close();
		    conn.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		return resultDTO;
	}
	
	public int deleteEmp(EmpDTO empDTO) {
		int result = -1;
		try {
			// DB 접속
			Connection conn = getConn();
			
			String query = " delete emp2";
				  query += " where empno = ?";
					
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, empDTO.getEmpno());
			
			// select가 아님
			result = ps.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
