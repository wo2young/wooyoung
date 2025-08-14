package sec01.exam01;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/quiz/Login.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");

        if (isBlank(id) || isBlank(pw)) {
            response.sendRedirect(request.getContextPath() + "/quiz/Login.html");
            return;
        }

        if ("admin".equals(id) && "1234".equals(pw)) {
            response.sendRedirect(request.getContextPath() + "/quiz/admin.html");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/quiz/Login.html");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
