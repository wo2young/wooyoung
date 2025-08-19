package todo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import todo.DTO.TodoDTO;

// DAO : Data Access Object
public class TodoDAO {
	
	// tbl_todo의 모든 항목을 돌려주는 메소드
	// 메소트명 : selectAll
	// 전달인자 : 없음
	// 리턴타입 : List<todoDTO>
	public List<TodoDTO> selectAll(){
		
		List<TodoDTO> list = new ArrayList();
		try {
		    // JNDI 방식으로
		    // context.xml에 있는 DB 정보를 가져온다
		    Context ctx = new InitialContext();
		    DataSource dataFactory = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");

		    // DB 접속
		    Connection conn = dataFactory.getConnection();

		    // (수정) conn.close(); 를 여기서 호출하면 커넥션이 닫혀서 이후 PreparedStatement를 사용할 수 없음
		    // conn.close(); // 커넥션 풀로 반환 (제일 마지막에 실행해야 함)

		    // SQL 준비
		    String query = "select * from tbl_todo";
		    PreparedStatement ps = conn.prepareStatement(query); // (주의) conn은 열려 있어야 함

		    // SQL 실행 및 결과 확보
		    ResultSet rs = ps.executeQuery();

		    // 결과 활용		
		    while( rs.next()) {
		        // 전달인자로 컬럼명(대소문자 구분없음)
		        int tno = rs.getInt("tno");
		        System.out.print("tno : "+ tno);
		        String title = rs.getString("title");
        		Date duedate = rs.getDate("duedate");
        		int finished = rs.getInt("finished");
		     
//        		Map map = new HashMap();
//			    map.put("tno", tno);
//			    map.put("title", title);
//			    map.put("duedate", duedate);
//			    map.put("finished", finished);
//			    list.add(map);
		  		
        		TodoDTO todoDTO = new TodoDTO();
        		todoDTO.setTno(tno);
        		todoDTO.setTitle(title);
        		todoDTO.setDuedate(duedate);
        		todoDTO.setFinished(finished);
        		list.add(todoDTO);
		    }

		    // 자원 해제 순서: ResultSet → PreparedStatement → Connection
		    rs.close();
		    ps.close();
		    conn.close(); // (수정) 제일 마지막에 닫기

		}catch(Exception e){
		    e.printStackTrace();
		}

		return list;
	}
	Connection getConnection() {
		Connection conn = null;
		try {
			// JNDI 방식으로
		    // context.xml에 있는 DB 정보를 가져온다
		    Context ctx = new InitialContext();
		    // DataSource : 커넥션 풀 관리자
		    DataSource dataFactory = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
		
		    // DB 접속
		    conn = dataFactory.getConnection();	
		}catch(Exception e) {
			e.printStackTrace();
		}
	    return conn;
	}
	// 자료 삽입
	// 메소드명: insert
	// 전달인자: TodoDTO
	// 리턴타입: int; insert된 행의 수
	public int insert(TodoDTO dto) {
		int result = -1;
		try {
			// 접속
			Connection conn = getConnection();
			
			// SQL 준비
			String query = "insert into";
			query += " tbl_todo(tno, title, duedate, finished)";
//			query += " values (seq_tbl_todo.nextval, '"+ dto.getTitle()+"','"+ dto.getDuedate()+"' , "+ dto.getFinished() +")";
			query += " values (seq_tbl_todo.nextval, ?, ?, ?)";	// 변수 방식	
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, dto.getTitle());
			ps.setDate(2, dto.getDuedate());
			ps.setInt(3, dto.getFinished());
			
			// SQL 실행 및 결과 확보
			// select 실행 : executeQuery();
			// 그 외 실행 : executeUpdate();
			
			result = ps.executeUpdate();
			// 결과 활용
			System.out.println(result + "행 이(가) 삽입되었습니다.");	
			
			ps.close();
			conn.close();
			
//			 result.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int delete(TodoDTO dto) {
		int result = -1;
		
		try {
			// 접속
			Connection conn = getConnection();
			
			String query = " DELETE FROM tbl_todo";
			query       += " WHERE tno = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, dto.getTno());
			
			result = ps.executeUpdate();
	
			ps.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	// tbl_todo의 모든 항목을 돌려주는 메소드
	// 메소트명 : select_todo
	// 전달인자 : 없음
	// 리턴타입 : List<todoDTO>
	public TodoDTO select_todo(int tno) {
	    TodoDTO todoDTO = null;
	    try {
	        Context ctx = new InitialContext();
	        DataSource dataFactory = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
	        Connection conn = dataFactory.getConnection();

	        String query = "SELECT * FROM tbl_todo WHERE tno = ?";
	        PreparedStatement ps = conn.prepareStatement(query);
	        ps.setInt(1, tno); // ? 자리에 값 바인딩

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {  // 한 건만 조회하니까 while 대신 if
	            todoDTO = new TodoDTO();
	            todoDTO.setTno(rs.getInt("tno"));
	            todoDTO.setTitle(rs.getString("title"));
	            todoDTO.setDuedate(rs.getDate("duedate"));
	            todoDTO.setFinished(rs.getInt("finished"));
	        }

	        rs.close();
	        ps.close();
	        conn.close();

	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return todoDTO;
	}
	public int updateTodo(TodoDTO dto) {
		int result = -1;
		try {
			// DB 접속
			Connection conn = getConnection();
			String query = "";
			query += " Update tbl_todo";
			query += " set title = ?,";
			query += "     duedate = ?,";
			query += "     finished = ?";
			query += " where tno = ?";
			
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, dto.getTitle());
			ps.setDate(2, dto.getDuedate());
			ps.setInt(3, dto.getFinished());
			ps.setInt(4, dto.getTno());
			
			result = ps.executeUpdate();
			System.out.println(result +" 행 이(가) 업데이트되었습니다.");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
