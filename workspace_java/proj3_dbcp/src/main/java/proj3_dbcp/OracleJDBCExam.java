package proj3_dbcp;

import java.sql.Connection;

public class OracleJDBCExam {

	public static void main(String[] args) {
		
		String url="jdbc:oracle:thin:@125.181.132.133:51521:xe";
		String user="scott4_11";
		String password = "tiger";
		
		Connection con = null;
		try {
			// JDBC 드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
