package DAO;

import yaDTO.BoardPostDTO;
import yaDTO.BoardCommentDTO;
import yaDTO.CategoryDTO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class BoardDAO {
    private final DataSource ds;

    public BoardDAO() {
        try {
            Context ctx = new InitialContext();
            this.ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
        } catch (Exception e) {
            throw new RuntimeException("JNDI lookup failed: " + e.getMessage(), e);
        }
    }

    private Connection c() throws SQLException { return ds.getConnection(); }

    // -------------------- 목록 / 카운트 --------------------
    public List<BoardPostDTO> list(Integer categoryId, String keyword, int startRow, int endRow) throws SQLException {
        String sql =
            "SELECT * FROM ( " +
            "  SELECT p.*, u.login_id AS writer_login_id, " +
            "         ROW_NUMBER() OVER( " +
            "           ORDER BY CASE WHEN p.category_id = 1 THEN 0 ELSE 1 END, p.created_at DESC) rn " +
            "  FROM BoardPost p " +
            "  JOIN USER_T u ON u.user_id = p.created_by " +
            "  WHERE p.is_deleted='N' " +
            "    AND ( (? IS NULL) OR (p.category_id = ? OR p.category_id=1) ) " + // ✅ 공지(category_id=1)는 항상 포함
            "    AND (? IS NULL OR (p.title LIKE '%'||?||'%' OR DBMS_LOB.INSTR(p.content, ?) > 0)) " +
            ") t WHERE t.rn BETWEEN ? AND ?";

        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (categoryId == null) { 
                ps.setNull(1, Types.INTEGER); 
                ps.setNull(2, Types.INTEGER); 
            } else { 
                ps.setInt(1, categoryId); 
                ps.setInt(2, categoryId); 
            }

            if (keyword == null || keyword.isEmpty()) {
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(3, keyword);
                ps.setString(4, keyword);
                ps.setString(5, keyword);
            }
            ps.setInt(6, startRow);
            ps.setInt(7, endRow);

            List<BoardPostDTO> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapPost(rs));
            }
            return list;
        }
    }

    public int count(Integer categoryId, String keyword) throws SQLException {
        String sql =
            "SELECT COUNT(*) FROM BoardPost p " +
            " WHERE p.is_deleted='N' " +
            "   AND ( (? IS NULL) OR (p.category_id = ? OR p.category_id=1) ) " + // ✅ 공지글 항상 카운트 포함
            "   AND (? IS NULL OR (p.title LIKE '%'||?||'%' OR DBMS_LOB.INSTR(p.content, ?) > 0))";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (categoryId == null) { 
                ps.setNull(1, Types.INTEGER); 
                ps.setNull(2, Types.INTEGER); 
            } else { 
                ps.setInt(1, categoryId); 
                ps.setInt(2, categoryId); 
            }

            if (keyword == null || keyword.isEmpty()) {
                ps.setNull(3, Types.VARCHAR);
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
            } else {
                ps.setString(3, keyword);
                ps.setString(4, keyword);
                ps.setString(5, keyword);
            }
            try (ResultSet rs = ps.executeQuery()) {
                rs.next(); return rs.getInt(1);
            }
        }
    }

    // -------------------- 게시글 --------------------
    public BoardPostDTO findById(long postId) throws SQLException {
        String sql =
            "SELECT p.*, u.login_id AS writer_login_id " +
            "FROM BoardPost p " +
            "JOIN USER_T u ON u.user_id = p.created_by " +
            "WHERE p.post_id = ?";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapPost(rs) : null; }
        }
    }

    public int increaseView(long postId) throws SQLException {
        try (Connection con = c();
             PreparedStatement ps = con.prepareStatement("UPDATE BoardPost SET view_cnt = view_cnt + 1 WHERE post_id=?")) {
            ps.setLong(1, postId); return ps.executeUpdate();
        }
    }

    public long insert(BoardPostDTO d) throws SQLException {
        String sql = "INSERT INTO BoardPost (category_id, title, content, is_notice, created_by) VALUES (?,?,?,?,?)";
        try (Connection con = c();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"post_id"})) {
            ps.setInt(1, d.getCategoryId());
            ps.setString(2, d.getTitle());
            ps.setString(3, d.getContent());
            ps.setString(4, d.getIsNotice() == null ? "N" : d.getIsNotice());
            ps.setLong(5, d.getCreatedBy());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getLong(1); }
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery("SELECT seq_board_post.CURRVAL FROM dual")) {
                rs.next(); return rs.getLong(1);
            }
        }
    }

    public int update(BoardPostDTO d, long userId, boolean isAdmin) throws SQLException {
        String sql = "UPDATE BoardPost SET title=?, content=?, category_id=?, is_notice=?, updated_at=SYSTIMESTAMP " +
                     "WHERE post_id=? AND (created_by=? OR ?=1)";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getTitle());
            ps.setString(2, d.getContent());
            ps.setInt(3, d.getCategoryId());
            ps.setString(4, d.getIsNotice());
            ps.setLong(5, d.getPostId());
            ps.setLong(6, userId);
            ps.setInt(7, isAdmin ? 1 : 0);
            return ps.executeUpdate();
        }
    }

    public int softDelete(long postId, long userId, boolean isAdmin) throws SQLException {
        String sql = "UPDATE BoardPost SET is_deleted='Y', updated_at=SYSTIMESTAMP WHERE post_id=? AND (created_by=? OR ?=1)";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId);
            ps.setLong(2, userId);
            ps.setInt(3, isAdmin ? 1 : 0);
            return ps.executeUpdate();
        }
    }

    // -------------------- 댓글 --------------------
    public List<BoardCommentDTO> listCommentsTree(long postId) throws SQLException {
        String sql =
            "SELECT cm.*, u.login_id AS writer_name, " +
            "       CASE WHEN cm.parent_id IS NULL THEN 1 ELSE 2 END AS lvl " +
            "  FROM BoardComment cm " +
            "  JOIN USER_T u ON u.user_id = cm.created_by " +
            " WHERE cm.post_id = ? AND cm.is_deleted = 'N' " +
            " ORDER BY cm.root_id, cm.depth, cm.created_at";

        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                List<BoardCommentDTO> list = new ArrayList<>();
                while (rs.next()) {
                    BoardCommentDTO d = new BoardCommentDTO();
                    d.setCommentId(rs.getLong("comment_id"));
                    d.setPostId(rs.getLong("post_id"));
                    d.setContent(rs.getString("content"));
                    d.setIsDeleted(rs.getString("is_deleted"));
                    d.setCreatedBy(rs.getLong("created_by"));
                    d.setCreatedAt(rs.getTimestamp("created_at"));
                    d.setUpdatedAt(rs.getTimestamp("updated_at"));
                    d.setWriterName(rs.getString("writer_name"));

                    d.setParentId(getNullableLong(rs, "parent_id"));
                    d.setRootId(getNullableLong(rs, "root_id"));
                    d.setDepth(rs.getInt("depth"));
                    d.setLevel(rs.getInt("lvl"));

                    list.add(d);
                }
                return list;
            }
        }
    }

    public int addComment(long postId, String content, long userId, Long parentId) throws SQLException {
        final String sql =
            "INSERT INTO BoardComment (post_id, parent_id, content, created_by) VALUES (?,?,?,?)";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId);
            if (parentId == null) ps.setNull(2, Types.NUMERIC); else ps.setLong(2, parentId);
            ps.setString(3, content);
            ps.setLong(4, userId);
            return ps.executeUpdate();
        }
    }

    public int deleteComment(long commentId, long userId, boolean isAdmin) throws SQLException {
        String sql = "UPDATE BoardComment SET is_deleted='Y', updated_at=SYSTIMESTAMP WHERE comment_id=? AND (created_by=? OR ?=1)";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, commentId);
            ps.setLong(2, userId);
            ps.setInt(3, isAdmin ? 1 : 0);
            return ps.executeUpdate();
        }
    }

    /** ✅ 댓글 수정 기능 추가 */
    public int updateComment(long commentId, String newContent, long userId, boolean isAdmin) throws SQLException {
        String sql = "UPDATE BoardComment " +
                     "SET content=?, updated_at=SYSTIMESTAMP " +
                     "WHERE comment_id=? AND is_deleted='N' AND (created_by=? OR ?=1)";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newContent);
            ps.setLong(2, commentId);
            ps.setLong(3, userId);
            ps.setInt(4, isAdmin ? 1 : 0);
            return ps.executeUpdate();
        }
    }

    // -------------------- 좋아요 --------------------
    public boolean hasLike(long postId, long userId) throws SQLException {
        String sql = "SELECT 1 FROM BoardLike WHERE post_id=? AND user_id=?";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId); ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    public int addLike(long postId, long userId) throws SQLException {
        String sql = "INSERT INTO BoardLike (post_id, user_id, created_at) VALUES (?,?,SYSTIMESTAMP)";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId); ps.setLong(2, userId);
            return ps.executeUpdate();
        }
    }

    public int removeLike(long postId, long userId) throws SQLException {
        String sql = "DELETE FROM BoardLike WHERE post_id=? AND user_id=?";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId); ps.setLong(2, userId);
            return ps.executeUpdate();
        }
    }

    public int syncLikeCount(long postId) throws SQLException {
        String sql = "UPDATE BoardPost SET like_cnt = (SELECT COUNT(*) FROM BoardLike WHERE post_id=?) WHERE post_id=?";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, postId); ps.setLong(2, postId);
            return ps.executeUpdate();
        }
    }

    // -------------------- 카테고리 --------------------
    public List<CategoryDTO> findCategories() throws SQLException {
        String sql = "SELECT category_id, category_name FROM BoardCategory WHERE is_active='Y' ORDER BY ord NULLS LAST, category_id";
        try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<CategoryDTO> list = new ArrayList<>();
            while (rs.next()) list.add(new CategoryDTO(rs.getInt(1), rs.getString(2)));
            return list;
        }
    }

    public boolean existsCategory(int categoryId) throws SQLException {
        try (Connection con = c();
             PreparedStatement ps = con.prepareStatement("SELECT 1 FROM BoardCategory WHERE category_id=? AND is_active='Y'")) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    // -------------------- mapper --------------------
    private BoardPostDTO mapPost(ResultSet rs) throws SQLException {
        BoardPostDTO d = new BoardPostDTO();
        d.setPostId(rs.getLong("post_id"));
        d.setCategoryId(rs.getInt("category_id"));
        d.setTitle(rs.getString("title"));
        d.setContent(rs.getString("content"));
        d.setIsNotice(rs.getString("is_notice"));
        d.setViewCnt(rs.getLong("view_cnt"));
        d.setLikeCnt(rs.getLong("like_cnt"));
        d.setIsDeleted(rs.getString("is_deleted"));
        d.setCreatedBy(rs.getLong("created_by"));
        d.setCreatedAt(rs.getTimestamp("created_at"));
        d.setUpdatedAt(rs.getTimestamp("updated_at"));

        String loginId = rs.getString("writer_login_id");
        d.setWriterLoginId(loginId);
        d.setWriterName(loginId);

        return d;
    }

    // -------------------- 헬퍼 --------------------
    private Long getNullableLong(ResultSet rs, String col) throws SQLException {
        long v = rs.getLong(col);
        return rs.wasNull() ? null : v;
    }
}
