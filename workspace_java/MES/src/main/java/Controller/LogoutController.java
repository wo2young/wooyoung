package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // 기존 세션만 가져오기
        if (session != null) {
            session.invalidate(); // 세션 전체 무효화
        }
        // 로그인 페이지로 이동
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
