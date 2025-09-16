package Service;

import DAO.BoardDAO;
import yaDTO.BoardPostDTO;
import yaDTO.BoardCommentDTO;
import yaDTO.CategoryDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class BoardService {
    private final BoardDAO dao = new BoardDAO();

    // -------------------- 내부 유틸 --------------------
    // 부모 댓글 depth 조회 (DAO 수정 없이 직접 질의)
    private Integer getCommentDepth(Long commentId) throws SQLException {
        if (commentId == null) return null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            try (Connection con = ds.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT depth FROM BoardComment WHERE comment_id=?")) {
                ps.setLong(1, commentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getInt(1);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new SQLException("JNDI lookup failed for depth query", e);
        }
    }

    // -------------------- 목록/카운트 --------------------
    public List<BoardPostDTO> list(Integer categoryId, String keyword, int page, int size) throws SQLException {
        int startRow = (page - 1) * size + 1;
        int endRow   = page * size;
        return dao.list(categoryId, keyword, startRow, endRow);
    }

    public int count(Integer categoryId, String keyword) throws SQLException {
        return dao.count(categoryId, keyword);
    }

    // -------------------- 조회 --------------------
    public BoardPostDTO view(long postId, boolean increaseView) throws SQLException {
        if (increaseView) dao.increaseView(postId);
        return dao.findById(postId);
    }

    // -------------------- 작성/수정/삭제 --------------------
    public long write(BoardPostDTO d) throws SQLException {
        if (d.getTitle() == null || d.getTitle().isBlank())      throw new IllegalArgumentException("title empty");
        if (d.getContent() == null || d.getContent().isBlank())  throw new IllegalArgumentException("content empty");
        if (d.getCategoryId() <= 0)                               throw new IllegalArgumentException("categoryId empty");
        if (!dao.existsCategory(d.getCategoryId()))               throw new IllegalArgumentException("invalid categoryId");
        return dao.insert(d);
    }

    public int edit(BoardPostDTO d, long userId, boolean isAdmin) throws SQLException {
        if (d.getTitle() == null || d.getTitle().isBlank())      throw new IllegalArgumentException("title empty");
        if (d.getContent() == null || d.getContent().isBlank())  throw new IllegalArgumentException("content empty");
        if (d.getCategoryId() <= 0)                               throw new IllegalArgumentException("categoryId empty");
        if (!dao.existsCategory(d.getCategoryId()))               throw new IllegalArgumentException("invalid categoryId");
        return dao.update(d, userId, isAdmin);
    }

    // 작성자 본인만 삭제(관리자 우회 금지) — DAO엔 false로 고정 전달
    public int delete(long postId, long userId) throws SQLException {
        return dao.softDelete(postId, userId, /*isAdmin*/ false);
    }

    // -------------------- 댓글 (트리) --------------------
    public List<BoardCommentDTO> comments(long postId) throws SQLException {
        return dao.listCommentsTree(postId);
    }

    // 대댓글 금지: parentId의 depth가 0일 때만 허용
    public int addComment(long postId, String content, long userId, Long parentId) throws SQLException {
        if (content == null || content.isBlank()) throw new IllegalArgumentException("comment content required");
        if (parentId != null) {
            Integer parentDepth = getCommentDepth(parentId);
            if (parentDepth == null) throw new IllegalArgumentException("parent comment not found");
            if (parentDepth >= 1)     throw new IllegalArgumentException("reply-to-reply is not allowed");
        }
        return dao.addComment(postId, content, userId, parentId);
    }

    // 댓글 삭제: 작성자 본인만 (관리자 우회 금지)
    public int deleteComment(long commentId, long userId) throws SQLException {
        return dao.deleteComment(commentId, userId, /*isAdmin*/ false);
    }

    // -------------------- 좋아요 --------------------
    public boolean toggleLike(long postId, long userId) throws SQLException {
        boolean had = dao.hasLike(postId, userId);
        if (had) dao.removeLike(postId, userId);
        else     dao.addLike(postId, userId);
        dao.syncLikeCount(postId);
        return !had;
    }

    // -------------------- 카테고리 --------------------
    public List<CategoryDTO> categories() throws SQLException {
        return dao.findCategories();
    }

    // =========================
    // 전체 정리
    // - DB 제약으로 이미 대댓글 금지이지만, 서비스 레벨 선제 체크로 UX 개선
    // - 목록/조회/수정/삭제는 기존 정책 유지
    // - 좋아요 토글 후 like_cnt 동기화 유지
    // =========================
}
