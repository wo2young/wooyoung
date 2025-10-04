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

    // 🔧 정적/공개 경로(로그인 불필요) — 여기엔 /board 넣지 않음 (읽기 공개는 아래에서 처리)
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

    // 🔧 게시판 읽기만 공개 (GET /board, /board/list, /board/view 만 허용)
    private boolean isBoardRead(HttpServletRequest req) {
        String ctx = req.getContextPath();
        String uri = req.getRequestURI();
        String m   = req.getMethod();
        if (!"GET".equalsIgnoreCase(m)) return false;
        if (!uri.startsWith(ctx + "/board")) return false;

        // 쓰기/수정/삭제/댓글 등은 읽기 공개에서 제외
        if (uri.startsWith(ctx + "/board/write")) return false;
        if (uri.startsWith(ctx + "/board/edit")) return false;
        if (uri.startsWith(ctx + "/board/delete")) return false;
        if (uri.startsWith(ctx + "/board/comment")) return false;
        if (uri.startsWith(ctx + "/board/attach")) return false;

        // 그 외 GET /board*, 예: /board, /board/, /board/list, /board/view
        return true;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String ctx = req.getContextPath();
        String uri = req.getRequestURI();

        try {
            boolean publicPath  = isPublic(uri, ctx) || isBoardRead(req); // ✅ 읽기 공개 반영

            HttpSession session = req.getSession(false);
            Object loginUser    = (session != null) ? session.getAttribute("loginUser")   : null;
            Object loginUserId  = (session != null) ? session.getAttribute("loginUserId") : null;
            boolean loggedIn    = (loginUser != null) || (loginUserId != null); // ✅ 둘 중 하나만 있어도 로그인으로 인정

            // 디버그 로그(문제 재발 시 확인)
            System.out.println("[AuthFilter] method=" + req.getMethod()
                    + ", uri=" + uri
                    + ", public=" + publicPath
                    + ", loggedIn=" + loggedIn);

            // 1) 로그인 안 한 경우 차단
            if (!publicPath && !loggedIn) {
                resp.sendRedirect(ctx + "/login");
                return;
            }

            // 2) 로그인은 했지만 비밀번호 변경 강제 플래그가 있을 때
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

        } catch (Exception e) {
            System.err.println("[AuthFilter ERROR] uri=" + uri + ", cause=" + e);
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
