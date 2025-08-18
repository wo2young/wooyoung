package todo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
}
