package practice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import practice.dto.TodoDTO;

public class TodoDAO {
	public List<TodoDTO> selectAll() {
		List<TodoDTO> list = new ArrayList<>();
		try {
			// 1) JNDI로 DataSource 먼저 얻기
			Context init = new InitialContext();
			Context env = (Context) init.lookup("java:comp/env");
			DataSource ds = (DataSource) env.lookup("jdbc/oracle");

			// 2) try-with-resources: conn → ps → rs
			try (Connection conn = ds.getConnection();
					PreparedStatement ps = conn.prepareStatement("SELECT * FROM tb_todo");
					ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					TodoDTO dto = new TodoDTO(); // 루프 안에서 new
					dto.setTno(rs.getInt("tno"));
					dto.setTitle(rs.getString("title"));
					dto.setDuedate(rs.getDate("duedate"));
					dto.setFinished(rs.getInt("finished"));
					list.add(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 3) 항상 반환
		return list;
	}

	public TodoDTO selectOne(int tno) {
		TodoDTO dto = null;

		try {
			Context init = new InitialContext();
			Context env = (Context) init.lookup("java:comp/env");
			DataSource ds = (DataSource) env.lookup("jdbc/oracle");

			try (Connection conn = ds.getConnection();
					PreparedStatement ps = conn.prepareStatement("SELECT * FROM tb_todo WHERE tno = ?")) {

				ps.setInt(1, tno); // 파라미터 바인딩

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						dto = new TodoDTO();
						dto.setTno(rs.getInt("tno"));
						dto.setTitle(rs.getString("title"));
						dto.setDuedate(rs.getDate("duedate"));
						dto.setFinished(rs.getInt("finished"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto; // 없으면 null
	}

	public int insert(TodoDTO dto) {
	    int result = 0;
	    try {
	        Context init = new InitialContext();
	        Context env  = (Context) init.lookup("java:comp/env");
	        DataSource ds = (DataSource) env.lookup("jdbc/oracle");

	        String sql = "INSERT INTO tb_todo (tno, title, duedate, finished) " +
	                     "VALUES (seq_tb_todo.nextval, ?, ?, ?)";

	        try (Connection conn = ds.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	        	

	            ps.setString(1, dto.getTitle());           // 1) title
	            
	            if (dto.getDuedate() == null) {
	                ps.setNull(2, java.sql.Types.DATE);
	            } else {
	                ps.setDate(2, dto.getDuedate()); // java.sql.Date
	            }
	            
	            ps.setInt(3, dto.getFinished());           // 3) finished (0/1)

	            result = ps.executeUpdate();               // 영향받은 행수 (성공시 1)
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}


}
