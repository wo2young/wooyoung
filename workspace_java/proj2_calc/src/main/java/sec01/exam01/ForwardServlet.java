package sec01.exam01;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/forward")
public class ForwardServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/forward doGet 실행");
		
		String text = request.getParameter("text");
		System.out.println("text: " + text);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("another");
		dispatcher.forward(request, response);
		
		response.getWriter().println("forward doGet 실행");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/forward doPost 실행");
		
		String text = request.getParameter("text");
		System.out.println("text: " + text);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("another");
		dispatcher.forward(request, response);
		
		response.getWriter().println("forward post 실행");
		
	}

}
