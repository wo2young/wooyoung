package practice.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import practice.dto.MovieDTO;
import practice.service.MovieService;

@WebServlet("/movie")
public class MovieController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
		
        MovieService ms = new MovieService();
        
        // DB에서 영화 목록 조회
        List<MovieDTO> list = ms.selectall();
        
        PrintWriter out = response.getWriter();
		
        out.println("<h1>영화목록</h1>");
        out.println("<table border=1 cellspacing=0 cellpadding=5>");
        out.println("	<tr>");
        out.println("		<th>영화ID</th>");
        out.println("		<th>영화제목</th>");
        out.println("		<th>이미지</th>");
        out.println("		<th>개봉일</th>");
        out.println("	</tr>"); 
        
        for (MovieDTO dto : list) {
            out.println("<tr>");
            out.println("  <td>" + dto.getMovie_id() + "</td>");
            out.println("  <td>" + dto.getTitle() + "</td>");
            out.println("  <td>" + dto.getImg_url() + "</td>");
            out.println("  <td>" + dto.getOpen_date() + "</td>");
            out.println("</tr>");
        }        
        out.println("</table>");
        
        
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
		
		
		
	}

}
