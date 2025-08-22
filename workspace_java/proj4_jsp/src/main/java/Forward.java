

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Forward")
public class Forward extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int a = 10;
		List list = new ArrayList();
		list.add(7);
		list.add("abc");
		System.out.println(list);
		
		Map map = new HashMap();
		map.put("k1", 9);
		map.put("k2", "word");
		System.out.println(map);
		
		String[] arr = {"z", "y", "x"};
		
		EmpDTO dto = new EmpDTO();
		dto.setEname("abab");
		
		request.setAttribute("a", a);
		request.setAttribute("list", list);
		request.setAttribute("map", map);
		request.setAttribute("arr", arr);
		request.setAttribute("dto", dto);
		
		request.getRequestDispatcher("el_3.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
