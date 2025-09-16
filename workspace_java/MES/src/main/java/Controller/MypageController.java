package Controller;

import DAO.UserDAO;
import util.PasswordUtil;
import yaDTO.UserDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/mypage", "/mypage/change-password"})
public class MypageController extends HttpServlet {
  private final UserDAO dao = new UserDAO();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (getLoginUser(req) == null) {
      resp.sendRedirect(req.getContextPath() + "/login");
      return;
    }
    req.getRequestDispatcher("/WEB-INF/views/mypage.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (getLoginUser(req) == null) {
      resp.sendRedirect(req.getContextPath() + "/login");
      return;
    }
    // ⚠️ 폼이 /mypage 로 날아와도 처리되도록 안전망
    String path = req.getServletPath();
    if ("/mypage".equals(path) || "/mypage/change-password".equals(path)) {
      handleChangePassword(req, resp);
      return;
    }
    resp.sendError(404);
  }

  private void handleChangePassword(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
    req.setCharacterEncoding("UTF-8");
    HttpSession s = req.getSession(false);
    UserDTO u = (UserDTO) s.getAttribute("loginUser");
    boolean must = resolveMust(s);

    String cur = req.getParameter("currentPassword");
    String npw = req.getParameter("newPassword");
    String cpw = req.getParameter("confirmPassword");

    if (npw == null || cpw == null || npw.length() < 8 || !npw.equals(cpw)) {
      req.setAttribute("msgErr", "비밀번호 입력을 확인하세요. (8자 이상, 동일하게)");
      req.getRequestDispatcher("/WEB-INF/views/mypage.jsp").forward(req, resp);
      return;
    }

    try {
      if (!must) {
        // 일반 변경: 현재 비번 검증
        if (!PasswordUtil.verify(cur, u.getPassword())) {
          req.setAttribute("msgErr", "현재 비밀번호가 올바르지 않습니다.");
          req.getRequestDispatcher("/WEB-INF/views/mypage.jsp").forward(req, resp);
          return;
        }
        dao.updatePassword(u.getUser_id(), npw); // BCrypt
      } else {
        // 리셋 로그인: 현재 비번 없이 변경 + 토큰 클리어
        dao.updatePasswordAndClearReset(u.getUser_id(), npw);
      }

      // ✅ 강제변경 플래그 완전 해제(두 키 모두) + 사용자 갱신 + 리다이렉트(PRG)
      s.removeAttribute("mustChangePw");
      s.removeAttribute("FORCE_CHANGE_PASSWORD");
      s.setAttribute("mustChangePw", Boolean.FALSE);
      s.setAttribute("FORCE_CHANGE_PASSWORD", Boolean.FALSE);

      UserDTO refreshed = dao.findByLoginId(u.getLogin_id());
      if (refreshed != null) s.setAttribute("loginUser", refreshed);

      resp.sendRedirect(req.getContextPath() + "/dashboard?pw=ok");
    } catch (Exception e) {
      e.printStackTrace();
      req.setAttribute("msgErr", "처리 중 오류가 발생했습니다.");
      req.getRequestDispatcher("/WEB-INF/views/mypage.jsp").forward(req, resp);
    }
  }

  private static boolean resolveMust(HttpSession s) {
    if (s == null) return false;
    Object v = s.getAttribute("mustChangePw");
    if (v == null) v = s.getAttribute("FORCE_CHANGE_PASSWORD");
    if (v instanceof Boolean) return (Boolean) v;
    if (v != null) {
      String sv = String.valueOf(v);
      return "true".equalsIgnoreCase(sv) || "Y".equalsIgnoreCase(sv) || "1".equals(sv);
    }
    return false;
  }

  private static UserDTO getLoginUser(HttpServletRequest req) {
    HttpSession s = req.getSession(false);
    return (s == null) ? null : (UserDTO) s.getAttribute("loginUser");
  }
}
