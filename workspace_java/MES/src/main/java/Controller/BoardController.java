package Controller;

import yaDTO.BoardPostDTO;
import Service.BoardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
    private final BoardService svc = new BoardService();

    // -------------------- helpers --------------------
    private Long requireLoginUserIdOrRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null) { resp.sendRedirect(req.getContextPath()+"/login"); return null; }
        Object o = s.getAttribute("loginUserId");
        if (o == null) { resp.sendRedirect(req.getContextPath()+"/login"); return null; }
        return (o instanceof Number) ? ((Number)o).longValue() : Long.parseLong(o.toString());
    }
    private static Integer parseIntOrNull(String s) { try { return (s==null||s.isBlank())?null:Integer.parseInt(s);} catch(Exception e){return null;} }
    private static int parseIntOrDefault(String s, int def){ try { return (s==null||s.isBlank())?def:Integer.parseInt(s);}catch(Exception e){return def;} }
    private static String nullIfBlank(String s){ return (s==null||s.isBlank())?null:s.trim(); }

    // -------------------- routing --------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || "/".equals(path) || "/list".equals(path)) {
            list(req, resp);
        } else if ("/view".equals(path)) {
            view(req, resp);
        } else if ("/write".equals(path)) {
            if (requireLoginUserIdOrRedirect(req, resp) == null) return;
            try {
                req.setAttribute("categories", svc.categories());
            } catch (SQLException e) {
                throw new ServletException("카테고리 로딩 실패", e);
            }
            req.getRequestDispatcher("/WEB-INF/views/board/write.jsp").forward(req, resp);
        } else if ("/edit".equals(path)) {
            editForm(req, resp);
        } else {
            resp.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if ("/write".equals(path)) {
            write(req, resp);
        } else if ("/edit".equals(path)) {
            edit(req, resp);
        } else if ("/delete".equals(path)) {
            delete(req, resp);
        } else if ("/comment/add".equals(path)) {
            addComment(req, resp);
        } else if ("/comment/delete".equals(path)) {
            deleteComment(req, resp);
        } else if ("/like/toggle".equals(path)) {
            toggleLike(req, resp);
        } else {
            resp.sendError(404);
        }
    }

    // -------------------- handlers --------------------
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer categoryId = parseIntOrNull(req.getParameter("categoryId"));
            String keyword = nullIfBlank(req.getParameter("keyword"));
            int page = parseIntOrDefault(req.getParameter("page"), 1);
            int size = parseIntOrDefault(req.getParameter("size"), 10);

            var posts = svc.list(categoryId, keyword, page, size);
            int total = svc.count(categoryId, keyword);

            req.setAttribute("categories", svc.categories());
            req.setAttribute("selectedCategoryId", categoryId);
            req.setAttribute("keyword", keyword);

            req.setAttribute("posts", posts);
            req.setAttribute("page", page);
            req.setAttribute("size", size);
            req.setAttribute("total", total);
            req.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long postId = Long.parseLong(req.getParameter("id"));
            var post = svc.view(postId, true);
            var comments = svc.comments(postId);

            req.setAttribute("post", post);
            req.setAttribute("comments", comments);
            req.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void write(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("utf-8");
            Long uid = requireLoginUserIdOrRedirect(req, resp); if (uid == null) return;

            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String title   = req.getParameter("title");
            String content = req.getParameter("content");
            String isNotice= "Y".equals(req.getParameter("isNotice")) ? "Y" : "N";

            BoardPostDTO d = new BoardPostDTO();
            d.setCategoryId(categoryId);
            d.setTitle(title);
            d.setContent(content);
            d.setIsNotice(isNotice);
            d.setCreatedBy(uid);

            long newId = svc.write(d);
            resp.sendRedirect(req.getContextPath() + "/board/view?id=" + newId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void editForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long uid = requireLoginUserIdOrRedirect(req, resp); if (uid == null) return;
            long postId = Long.parseLong(req.getParameter("id"));
            var post = svc.view(postId, false);
            if (post == null) { resp.sendError(404); return; }

            boolean isAdmin = Boolean.TRUE.equals(req.getSession().getAttribute("isAdmin"));
            if (!(isAdmin || uid == post.getCreatedBy())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            req.setAttribute("categories", svc.categories());
            req.setAttribute("post", post);
            req.getRequestDispatcher("/WEB-INF/views/board/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("utf-8");
            Long uid = requireLoginUserIdOrRedirect(req, resp); if (uid == null) return;
            boolean isAdmin = Boolean.TRUE.equals(req.getSession().getAttribute("isAdmin"));

            BoardPostDTO d = new BoardPostDTO();
            d.setPostId(Long.parseLong(req.getParameter("postId")));
            d.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
            d.setTitle(req.getParameter("title"));
            d.setContent(req.getParameter("content"));
            d.setIsNotice("Y".equals(req.getParameter("isNotice")) ? "Y" : "N");

            svc.edit(d, uid, isAdmin);
            resp.sendRedirect(req.getContextPath()+"/board/view?id="+d.getPostId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    // 게시글 삭제: 작성자 본인만 (관리자 우회 금지)
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long uid = requireLoginUserIdOrRedirect(req, resp); if (uid == null) return;
            long postId = Long.parseLong(req.getParameter("postId"));
            int affected = svc.delete(postId, uid);
            if (affected == 0) { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }
            resp.sendRedirect(req.getContextPath()+"/board");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // 댓글 작성: 대댓글 금지 로직은 Service에서 depth 체크
    private void addComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("utf-8");
            Long uid = requireLoginUserIdOrRedirect(req, resp); if (uid == null) return;

            long postId = Long.parseLong(req.getParameter("postId"));
            String content = req.getParameter("content");
            Long parentId = null;
            String p = req.getParameter("parentId");
            if (p != null && !p.isBlank()) parentId = Long.parseLong(p);

            svc.addComment(postId, content, uid, parentId);
            resp.sendRedirect(req.getContextPath()+"/board/view?id="+postId+"#comments");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    // 댓글 삭제: 작성자 본인만 (관리자 우회 금지)
    private void deleteComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long uid = requireLoginUserIdOrRedirect(req, resp); if (uid == null) return;
            long commentId = Long.parseLong(req.getParameter("commentId"));
            long postId = Long.parseLong(req.getParameter("postId"));
            int affected = svc.deleteComment(commentId, uid);
            if (affected == 0) { resp.sendError(HttpServletResponse.SC_FORBIDDEN); return; }
            resp.sendRedirect(req.getContextPath()+"/board/view?id="+postId+"#comments");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void toggleLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long uid = requireLoginUserIdOrRedirect(req, resp); if (uid == null) return;
            long postId = Long.parseLong(req.getParameter("postId"));
            svc.toggleLike(postId, uid);
            resp.sendRedirect(req.getContextPath()+"/board/view?id="+postId);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // =========================
    // 전체 정리
    // - 라우팅/정책은 기존과 동일
    // - 댓글 작성은 Service에서 depth 검증 → DAO 단순 INSERT
    // - 화면 들여쓰기는 DTO.level(1/2)로 처리 가능
    // =========================
}
