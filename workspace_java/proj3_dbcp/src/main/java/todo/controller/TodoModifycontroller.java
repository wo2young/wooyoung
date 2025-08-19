package todo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.DTO.TodoDTO;
import todo.service.TodoService;

/**
 * Servlet implementation class TodoModifycontroller
 */
@WebServlet("/modify")
public class TodoModifycontroller extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
		
		TodoDTO todoDTO = new TodoDTO();
		
		String str_tno = request.getParameter("tno");
		int tno = Integer.parseInt(str_tno);
		todoDTO.setTno(tno);
		
		TodoService todoService = new TodoService();
		TodoDTO todoDTO2 = todoService.getTodo(tno);
		
		PrintWriter out = response.getWriter();
		out.println("<form method='post' action='modify'>");
		out.println("tno: " +todoDTO2.getTno()+"<br>");
		out.println(" title: <input type='text' name='title' value='" +todoDTO2.getTitle()+"'><br>");
		out.println(" duedate: <input type='date' name='duedate' value='" +todoDTO2.getDuedate()+"'><br>");
		out.println(" finished: <input type='number' name='finished' value='" +todoDTO2.getFinished()+"'><br>");
		out.println(" <input type='hidden' name='tno' value='"+ todoDTO2.getTno() +"'>");
		out.println(" <input type='submit' value='수정하기'>");
		out.println(" <a herf='todo'><button>돌아가기</button></a>");
		out.println("</form>");
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        String title = request.getParameter("title");
        String duedate = request.getParameter("duedate");
        String finished = request.getParameter("finished");
        String tno = request.getParameter("tno");
		
        TodoDTO todoDTO = new TodoDTO();
        todoDTO.setTno(Integer.parseInt(tno));
        todoDTO.setDuedate(Date.valueOf(duedate));
        todoDTO.setFinished(Integer.parseInt(finished));
        todoDTO.setTitle((title));
        
        TodoService todoService = new TodoService();
        int result = todoService.modifyTodo(todoDTO);
        System.out.println("업데이트 결과 : "+ result);
        
        response.sendRedirect("todo?command=detail&tno="+ tno);
	}

}
