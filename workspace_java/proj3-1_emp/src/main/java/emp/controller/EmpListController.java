package emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import emp.dto.EmpDTO;
import emp.service.EmpService;

@WebServlet("/list")
public class EmpListController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
		
		System.out.println("/list doGet 실행");
		
		EmpService empService = new EmpService();
		List<EmpDTO> list = empService.getAllemp();
		
		PrintWriter out = response.getWriter();
		
		out.println("<h1>회원목록<h1>");
		out.println("<table border=1cellspacing=0 cellpadding=5>");
		out.println("   <tr>");		
		out.println("      <th>empno</th>");		
		out.println("      <th>ename</th>");		
		out.println("      <th>sal</th>");		
		out.println("   </tr>");		
		for(EmpDTO dto : list) {
			System.out.println(dto);
		
			out.println("<tr>");
			out.println("	<td>"+ dto.getEmpno() +"</td>");
			out.println(" <td><a href=\"detail?empno=" + dto.getEmpno() + "\">" + dto.getEname() + "</a></td>");
			out.println("	<td>"+ dto.getSal() +"</td>");
			out.println("</tr>");
		}
		out.println("</table>");
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
