package Filter;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public class AuthFilter implements Filter {

    // 어떤 값이 와도 boolean으로 안전하게 변환
    private boolean safeBoolean(Object v) {
        if (v == null) return false;
        if (v instanceof Boolean) return (Boolean) v;
        return Boolean.parseBoolean(v.toString());
    }

    // 🔧 정적/공개 경로(로그인 불필요)
    //  - /master, /file, /board 는 "공개 아님" → 여기 절대 넣지 말 것
    private boolean isPublic(String uri, String ctx) {
        return uri.equals(ctx + "/login")
            || uri.equals(ctx + "/logout")
            || uri.equals(ctx + "/mypage/change-password")
            || uri.startsWith(ctx + "/resources/")
            || uri.startsWith(ctx + "/css/")
            || uri.startsWith(ctx + "/js/")
            || uri.startsWith(ctx + "/images/")
            || uri.startsWith(ctx + "/assets/")
            || uri.startsWith(ctx + "/static/"); 
    }

    // root cause 추출(로그 가독성)
    private Throwable root(Throwable t) {
        Throwable c = t;
        while (c.getCause() != null && c.getCause() != c) c = c.getCause();
        return c;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String ctx = req.getContextPath(); 
        String uri = req.getRequestURI();  

        try {
            boolean publicPath  = isPublic(uri, ctx);

            HttpSession session = req.getSession(false);
            Object loginUser    = (session != null) ? session.getAttribute("loginUser")   : null;
            Object loginUserId  = (session != null) ? session.getAttribute("loginUserId") : null;
            boolean loggedIn    = (loginUser != null) || (loginUserId != null);

            System.out.println("[AuthFilter] method=" + req.getMethod()
                    + ", uri=" + uri
                    + ", public=" + publicPath
                    + ", loggedIn=" + loggedIn);

            // 1) 로그인 안 한 경우 차단
            if (!publicPath && !loggedIn) {
                resp.sendRedirect(ctx + "/login");
                return;
            }

            // 2) 비밀번호 변경 강제 플래그 처리
            boolean mustChange = (session != null) && safeBoolean(session.getAttribute("FORCE_CHANGE_PASSWORD"));
            if (loggedIn && mustChange) {
                boolean onChangePage = uri.equals(ctx + "/mypage/change-password");
                boolean allow = onChangePage || isPublic(uri, ctx);
                if (!allow) {
                    resp.sendRedirect(ctx + "/mypage/change-password");
                    return;
                }
            }

            chain.doFilter(request, response);

        } catch (Throwable t) {
            Throwable rc = root(t);
            System.err.println("[AuthFilter ERROR] uri=" + uri + ", cause=" + rc.getClass().getName() + ": " + rc.getMessage());
            rc.printStackTrace();
            throw new ServletException(rc);
        }
    }
}
