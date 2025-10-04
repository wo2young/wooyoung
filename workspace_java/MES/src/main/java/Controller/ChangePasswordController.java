package Controller;

import DAO.UserDAO;
import yaDTO.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/change-password") 
public class ChangePasswordController extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    private static final String JSP = "/WEB-INF/views/dashboard.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        UserDTO me = (UserDTO) session.getAttribute("loginUser");
        String newPw  = req.getParameter("newPassword");
        String newPw2 = req.getParameter("confirmPassword");

        if (newPw == null || !newPw.equals(newPw2) || newPw.length() < 8) {
            req.setAttribute("error", "비밀번호를 다시 확인하세요. (8자 이상, 두 값 일치)");
            req.getRequestDispatcher(JSP).forward(req, resp);
            return;
        }

        try {
            // ★ 평문 전달 → DAO에서 BCrypt로 저장 + 토큰 초기화
            userDAO.updatePasswordAndClearReset(me.getUser_id(), newPw);
            session.setAttribute("mustChangePw", Boolean.FALSE);
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "비밀번호 변경 중 오류가 발생했습니다.");
            req.getRequestDispatcher(JSP).forward(req, resp);
        }
    }
}
