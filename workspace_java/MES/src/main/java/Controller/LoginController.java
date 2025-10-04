package Controller;

import Service.LoginService;
import yaDTO.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String loginId  = req.getParameter("loginId");
        String password = req.getParameter("password");

        LoginService.LoginResult res = loginService.login(loginId, password);

        // ✅ 로그인 실패 처리
        if (res == null || !res.isSuccess() || res.getUser() == null) {
            req.setAttribute("errorMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
            // forward로 다시 login.jsp 호출
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        UserDTO user = res.getUser();

        // 🔐 세션 세팅
        HttpSession session = req.getSession(true);
        session.setAttribute("loginUser",   user);
        session.setAttribute("loginUserId", user.getUser_id());

        String role = user.getUser_role();
        boolean isAdmin = role != null && role.equalsIgnoreCase("ADMIN");
        session.setAttribute("isAdmin", isAdmin);

        boolean mustChange = res.isViaReset();
        session.setAttribute("mustChangePw", mustChange);
        session.setAttribute("FORCE_CHANGE_PASSWORD", mustChange);

        // 리다이렉트
        if (mustChange) {
            resp.sendRedirect(req.getContextPath() + "/mypage");
        } else {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        }
    }
}
