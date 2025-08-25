package session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 기존 세션이 없으면 새로 만들어주고
		// 있으면 그걸 가져옴
		HttpSession session = request.getSession();

		// 기존 세션이 없으면 null
		// 있으면 그걸 가져옴
//		HttpSession session = request.getSession(false);
		
		session.setAttribute("K", "V");
		
		String id = session.getId();
		System.out.println("getId(): "+ id);

		boolean isNew = session.isNew();
		System.out.println("isNew(): "+isNew);
		
		String userId = request.getParameter("id");
		if("admin".equals(userId)) {
			session.setAttribute("login", true);
			session.setAttribute("userId", userId);
		} else if("woo".equals(userId)){
			session.setAttribute("login", true);
			session.setAttribute("userId", userId);
			session.setAttribute("level", "1");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

}
