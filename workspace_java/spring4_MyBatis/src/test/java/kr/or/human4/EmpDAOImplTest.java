package kr.or.human4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class) // 컨테이너 생성
@WebAppConfiguration // 다이나믹 웹 설정
@ContextConfiguration( // 사용할 bean들과 scan할 패키지 로딩
  locations = {
   "file:src/main/webapp/WEB-INF/spring/mybatis.xml"
   ,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
  })

public class EmpDAOImplTest {
	
	@Autowired
	SqlSession sqlSession;

	@Test
	public void testSelectEmpno() {
//		fail("Not yet implemented");
		int empno = 1111;
		List list = sqlSession.selectList("mapper.emp.selectEmpno", empno);
		System.out.println("list.size : "+ list.size());
		System.out.println("list : "+ list);
		
		assertEquals(list.size(),1);
		assertNotNull(list);
//		assertTrue(boolean 변수);
	}

}
