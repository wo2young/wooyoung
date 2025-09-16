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
        if (res == null || !res.isSuccess() || res.getUser() == null) {
            req.setAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        UserDTO user = res.getUser();

        // 🔐 세션 세팅 (필수 키 통일)
        HttpSession session = req.getSession(true);
        session.setAttribute("loginUser",   user);                    // 객체(필터/뷰에서 사용)
        session.setAttribute("loginUserId", user.getUser_id());       // 숫자 ID (컨트롤러/DAO에서 사용)

        // 권한 플래그 (헤더/권한 체크용)
        String role = user.getUser_role();                            // 예: "ADMIN", "Manager" 등
        boolean isAdmin = role != null && role.equalsIgnoreCase("ADMIN");
        session.setAttribute("isAdmin", isAdmin);

        // 비밀번호 변경 강제 플래그 — 두 키 모두 세팅(필터/헤더에서 이름 다를 수 있어 통일)
        boolean mustChange = res.isViaReset();
        session.setAttribute("mustChangePw",        mustChange);      // 헤더에서 사용 (top-banner)
        session.setAttribute("FORCE_CHANGE_PASSWORD", mustChange);    // 필터에서 사용

        // (선택) 세션 타임아웃
        // session.setMaxInactiveInterval(60 * 30); // 30분

        // 리다이렉트
        if (mustChange) {
            // 리셋코드로 로그인 시 마이페이지로 유도
            resp.sendRedirect(req.getContextPath() + "/mypage");
        } else {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        }
    }
}
