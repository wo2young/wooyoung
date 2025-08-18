package proj3_dbcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/dbcp")
public class OracleDBCPServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			// JNDI 방식으로
			// context.xml에 있는 DB 정보를 가져온다
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource)ctx.lookup("java:/comp/env/jdbc/oracle");
			
			// DB 접속
			Connection conn = dataFactory.getConnection();
			
			// (수정) conn.close(); 를 여기서 호출하면 커넥션이 닫혀서 이후 PreparedStatement를 사용할 수 없음
			// conn.close(); // 커넥션 풀로 반환 (제일 마지막에 실행해야 함)
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			// SQL 준비
			String query = "select * from emp";
			ps = conn.prepareStatement(query); // (주의) conn은 열려 있어야 함
			
			// SQL 실행 및 결과 확보
			rs = ps.executeQuery();
			
			// (보충) 응답 인코딩 설정 - 한글 데이터가 있을 경우 깨짐 방지
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			// 결과 활용		
			while( rs.next()) {
				// 전달인자로 컬럼명(대소문자 구분없음)
				int empno = rs.getInt("empno");
				System.out.print(empno +",");
				out.println(empno +",");
				
				Date hiredate = rs.getDate("hiredate");
				System.out.print(hiredate.toLocalDate());
				out.println(hiredate.toLocalDate() + ",");
				
				String ename = rs.getString("ename");
				System.out.print(ename);
				out.println(ename +"<br>");
			}
			
			// 자원 해제 순서: ResultSet → PreparedStatement → Connection
			rs.close();
			ps.close();
			conn.close(); // (수정) 제일 마지막에 닫기
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
