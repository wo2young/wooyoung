package Controller;

import DAO.UserDAO;
import yaDTO.UserDTO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {
    "/users", "/users/new", "/users/reset-pw",
    "/users/edit", "/password/reset"
})
public class UserController extends HttpServlet {

  private final UserDAO dao = new UserDAO();

  // ---------- GET ----------
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String path = req.getServletPath();
    boolean adminRequired = path.startsWith("/users"); // /users* 는 ADMIN만

    if (adminRequired && !isAdmin(req)) {
      // 로그인 안했으면 로그인으로, 했지만 권한 없으면 403
      if (getLoginUser(req) == null) {
        resp.sendRedirect(req.getContextPath() + "/login");
      } else {
        resp.sendError(403);
      }
      return;
    }

    switch (path) {
      case "/users": {
        String qRaw = req.getParameter("q");
        String q = (qRaw == null ? null : qRaw.trim());

        int page = parseInt(req.getParameter("p"), 1);
        int size = parseInt(req.getParameter("size"), 10);

        // ✅ total 추가
        int total = dao.count(q);

        int totalPages = (int) Math.ceil(total / (double) size);
        if (page < 1) page = 1;
        if (totalPages > 0 && page > totalPages) page = totalPages;

        List<UserDTO> list = dao.list(q, page, size);

        req.setAttribute("q", q);
        req.setAttribute("list", list);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("total", total);
        req.setAttribute("totalPages", totalPages);

        // ★ 실제 파일 위치로 변경
        req.getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(req, resp);
        return;
      }

      case "/users/new": {
        // 빈 폼 표시 (★ 실제 파일 위치로 변경)
        req.getRequestDispatcher("/WEB-INF/views/users/new.jsp").forward(req, resp);
        return;
      }

      case "/users/edit": {
        int id = parseInt(req.getParameter("id"), -1);
        UserDTO target = dao.find(id); // UserDAO에 find(int) 구현 필요
        if (target == null) { resp.sendError(404); return; }
        req.setAttribute("u", target);
        // ★ 실제 파일 위치로 변경
        req.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(req, resp);
        return;
      }

      case "/password/reset": {
        // 사용자 공개 폼 (★ 실제 파일 위치로 변경)
        req.getRequestDispatcher("/WEB-INF/views/users/reset.jsp").forward(req, resp);
        return;
      }

      default:
        resp.sendError(404);
    }
  }

  // ---------- POST ----------
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");
    String path = req.getServletPath();
    boolean adminRequired = path.startsWith("/users"); // /users* 는 ADMIN만

    if (adminRequired && !isAdmin(req)) {
      if (getLoginUser(req) == null) {
        resp.sendRedirect(req.getContextPath() + "/login");
      } else {
        resp.sendError(403);
      }
      return;
    }

    switch (path) {
      case "/users/new": { // 생성
        String loginId = trim(req.getParameter("loginId"));
        String name    = trim(req.getParameter("name"));
        String role    = trim(req.getParameter("role"));
        String pw      = req.getParameter("password");

        if (isBlank(loginId) || isBlank(name) || isBlank(role) || isBlank(pw)) {
          req.setAttribute("error", "모든 항목을 입력하세요.");
          req.setAttribute("loginId", loginId);
          req.setAttribute("name", name);
          req.setAttribute("role", role);
          // ★ 실제 파일 위치로 변경
          req.getRequestDispatcher("/WEB-INF/views/users/new.jsp").forward(req, resp);
          return;
        }
        if (!role.matches("(?i)ADMIN|MANAGER|WORKER")) {
          req.setAttribute("error", "권한 값이 올바르지 않습니다.");
          req.setAttribute("loginId", loginId);
          req.setAttribute("name", name);
          req.setAttribute("role", role);
          // ★ 실제 파일 위치로 변경
          req.getRequestDispatcher("/WEB-INF/views/users/new.jsp").forward(req, resp);
          return;
        }
        if (pw.length() < 8) {
          req.setAttribute("error", "비밀번호는 8자 이상이어야 합니다.");
          req.setAttribute("loginId", loginId);
          req.setAttribute("name", name);
          req.setAttribute("role", role);
          // ★ 실제 파일 위치로 변경
          req.getRequestDispatcher("/WEB-INF/views/users/new.jsp").forward(req, resp);
          return;
        }
        if (dao.existsByLoginId(loginId)) {
          req.setAttribute("error", "이미 사용 중인 로그인ID입니다.");
          req.setAttribute("loginId", loginId);
          req.setAttribute("name", name);
          req.setAttribute("role", role);
          // ★ 실제 파일 위치로 변경
          req.getRequestDispatcher("/WEB-INF/views/users/new.jsp").forward(req, resp);
          return;
        }

        dao.insert(loginId, name, role.toUpperCase(), pw); // DAO에서 해시 저장
        resp.sendRedirect(req.getContextPath() + "/users?created=1");
        return;
      }

      case "/users/reset-pw": { // 관리자: 리셋 코드 발급
        int id = parseInt(req.getParameter("id"), -1);
        String token = dao.issueResetToken(id);
        if (token == null) {
          resp.sendRedirect(req.getContextPath() + "/users?reset=fail");
          return;
        }
        UserDTO target = dao.find(id);
        req.setAttribute("target", target);
        req.setAttribute("token", token);
        // ★ 실제 파일 위치로 변경
        req.getRequestDispatcher("/WEB-INF/views/users/reset_result.jsp").forward(req, resp);
        return;
      }

      case "/users/edit": { // 사용자 정보 수정
        int id = parseInt(req.getParameter("id"), -1);
        String name = trim(req.getParameter("name"));
        String role = trim(req.getParameter("role"));

        if (id <= 0 || isBlank(name) || isBlank(role) || !role.matches("(?i)ADMIN|MANAGER|WORKER")) {
          req.setAttribute("error", "입력값을 확인하세요.");
          UserDTO back = dao.find(id);
          req.setAttribute("u", back);
          // ★ 실제 파일 위치로 변경
          req.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(req, resp);
          return;
        }
        dao.updateBasic(id, name, role.toUpperCase());
        resp.sendRedirect(req.getContextPath()+"/users?updated=1");
        return;
      }

      case "/password/reset": { // 사용자: 코드로 비번 재설정 (공개)
        String loginId = trim(req.getParameter("loginId"));
        String token   = trim(req.getParameter("token"));
        String pw1     = req.getParameter("password");
        String pw2     = req.getParameter("password2");

        if (isBlank(loginId) || isBlank(token) || isBlank(pw1) || isBlank(pw2) ||
            !pw1.equals(pw2) || pw1.length() < 8) {
          req.setAttribute("error", "입력값을 확인하세요. (비밀번호 8자 이상, 두 값 일치)");
          // ★ 실제 파일 위치로 변경
          req.getRequestDispatcher("/WEB-INF/views/users/reset.jsp").forward(req, resp);
          return;
        }

        boolean ok = dao.resetWithToken(loginId, token, pw1);
        if (ok) {
          resp.sendRedirect(req.getContextPath() + "/login?reset=ok");
        } else {
          req.setAttribute("error", "코드가 올바르지 않거나 만료되었습니다.");
          // ★ 실제 파일 위치로 변경
          req.getRequestDispatcher("/WEB-INF/views/users/reset.jsp").forward(req, resp);
        }
        return;
      }

      default:
        resp.sendError(404);
    }
  }

  // ---------- helpers ----------
  private static int parseInt(String s, int def){ try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
  private static String trim(String s){ return s == null ? null : s.trim(); }
  private static boolean isBlank(String s){ return s == null || s.trim().isEmpty(); }

  private static UserDTO getLoginUser(HttpServletRequest req) {
    HttpSession s = req.getSession(false);
    return (s == null) ? null : (UserDTO) s.getAttribute("loginUser");
  }
  private static boolean isAdmin(HttpServletRequest req) {
    UserDTO u = getLoginUser(req);
    return u != null && "ADMIN".equalsIgnoreCase(u.getUser_role());
  }
}
