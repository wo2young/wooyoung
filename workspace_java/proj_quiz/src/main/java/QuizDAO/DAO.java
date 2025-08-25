package QuizDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import QuizDTO.DTO;

public class DAO {
	
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
	public List<DTO> selectAll() {
		List<DTO> list = new ArrayList();
		try {
		// DB 접속
		Connection conn = getConn();
		
		// SQL 준비
		String query = "SELECT * FROM list";
		PreparedStatement ps = conn.prepareStatement(query);
		
		// SQL 실행
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			DTO dto = new DTO();
			
			int num = rs.getInt("num");
			String ename = rs.getString("ename");
			String empno = rs.getString("empno");
			int level_type = rs.getInt("level_type");			
	
			dto.setNum(num);
			dto.setEname(ename);
			dto.setEmpno(empno);
			dto.setLevel_type(level_type);
			
			list.add(dto);
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}
}