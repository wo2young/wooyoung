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

    HttpServletRequest req  = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    String ctx  = req.getContextPath();
    String path = req.getServletPath();

    HttpSession s = req.getSession(false);
    boolean loggedIn = (s != null && s.getAttribute("loginUser") != null);

    // mustChangePw 값을 다양한 타입(Boolean, String 등)으로 안전 해석
    boolean must = false;
    if (loggedIn && s != null) {
      Object v = s.getAttribute("mustChangePw");
      if (v instanceof Boolean) must = (Boolean) v;
      else if (v != null) {
        String sv = String.valueOf(v);
        must = "true".equalsIgnoreCase(sv) || "Y".equalsIgnoreCase(sv) || "1".equals(sv);
      }
    }

    // 강제 변경 중에도 허용할 경로 (무한리다이렉트 방지)
    boolean allowed =
        path.startsWith("/mypage")           // 마이페이지 & 비번변경 처리
     || path.equals("/logout")
     || path.equals("/login")
     || path.startsWith("/resources/")      // 정적 리소스 경로들
     || path.startsWith("/static/")
     || path.endsWith(".css") || path.endsWith(".js")
     || path.endsWith(".png") || path.endsWith(".jpg")
     || path.endsWith(".jpeg") || path.endsWith(".gif")
     || path.endsWith(".svg") || path.endsWith(".ico")
     || path.endsWith(".woff") || path.endsWith(".woff2");

    if (loggedIn && must && !allowed) {
      // 비번 변경을 강제한다
      resp.sendRedirect(ctx + "/mypage");
      return;
    }

    chain.doFilter(request, response);
  }
}
