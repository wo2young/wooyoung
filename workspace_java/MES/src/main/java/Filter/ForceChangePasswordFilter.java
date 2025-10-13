package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/*")
public class ForceChangePasswordFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String ctx = req.getContextPath();  // 예: /mes2
    String path = req.getServletPath(); // 예: /mypage/mypage

    HttpSession s = req.getSession(false);
    boolean loggedIn = (s != null && s.getAttribute("loginUser") != null);

    // mustChangePw 플래그 안전하게 파싱
    boolean must = false;
    if (loggedIn && s != null) {
      Object v = s.getAttribute("mustChangePw");
      if (v instanceof Boolean) must = (Boolean) v;
      else if (v != null) {
        String sv = String.valueOf(v);
        must = "true".equalsIgnoreCase(sv) || "Y".equalsIgnoreCase(sv) || "1".equals(sv);
      }
    }

    // ===== 허용 경로들 (리다이렉트 루프 방지) =====
    boolean allowed =
        path.startsWith("/mypage/change-password") ||
        path.startsWith("/password") ||
        path.equals("/logout") ||
        path.equals("/login") ||
        path.startsWith("/resources/") ||
        path.startsWith("/static/") ||
        path.endsWith(".css") || path.endsWith(".js") ||
        path.endsWith(".png") || path.endsWith(".jpg") ||
        path.endsWith(".jpeg") || path.endsWith(".gif") ||
        path.endsWith(".svg") || path.endsWith(".ico") ||
        path.endsWith(".woff") || path.endsWith(".woff2");

    // ===== ① mustChangePw 상태인 경우 강제 리다이렉트 =====
    if (loggedIn && must) {
      // (1) 비밀번호 변경 페이지가 아니면 /mypage/change-password 로 강제 이동
      if (!allowed && !path.startsWith("/mypage/change-password")) {
        resp.sendRedirect(ctx + "/mypage/change-password");
        return;
      }
    }

    // ===== ② 일반 사용자면 그냥 통과 =====
    chain.doFilter(request, response);
  }
}
