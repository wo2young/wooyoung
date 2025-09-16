package DAO;

import yaDTO.UserDTO;
import util.SecurityUtils;
import util.PasswordUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static final String TBL = "user_t"; // 실제 테이블명

    // KST(UTC+9) 기준으로 '지금'을 TIMESTAMP(타임존 없음)로 만드는 SQL 조각
    // (SESSION/DB 타임존이 무엇이든 일관되게 동작)
    private static final String KST_NOW_TS = "CAST(SYSTIMESTAMP AT TIME ZONE '+09:00' AS TIMESTAMP)";

    private final DataSource ds;

    public UserDAO() {
        try {
            Context ctx = new InitialContext();
            this.ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
        } catch (Exception e) {
            throw new RuntimeException("DataSource lookup 실패", e);
        }
    }

    private Connection getConnection() throws SQLException { return ds.getConnection(); }

    /* -------------------- 공통 매핑 -------------------- */
    private UserDTO mapRow(ResultSet rs) throws SQLException {
        UserDTO u = new UserDTO();
        u.setUser_id(rs.getInt("user_id"));
        u.setLogin_id(rs.getString("login_id"));
        u.setPassword(rs.getString("password"));
        u.setName(rs.getString("name"));
        u.setUser_role(rs.getString("user_role"));
        u.setCreated_at(rs.getTimestamp("created_at"));
        u.setUpdated_at(rs.getTimestamp("updated_at"));
        u.setReset_expires(rs.getTimestamp("reset_expires"));
        u.setReset_token(rs.getString("reset_token"));
        return u;
    }

    /* ---------- 단건 조회 ---------- */
    public UserDTO findByLoginId(String loginId) throws SQLException {
        // 진단 로그(배포 시 주석 권장)
        try (Connection con0 = getConnection();
             Statement st0 = con0.createStatement()) {
            try (ResultSet r0 = st0.executeQuery("SELECT SYS_CONTEXT('USERENV','CURRENT_SCHEMA') FROM dual")) {
                if (r0.next()) System.out.println("[DAO] CURRENT_SCHEMA=" + r0.getString(1));
            }
            try (ResultSet r1 = st0.executeQuery("SELECT COUNT(*) FROM " + TBL)) {
                if (r1.next()) System.out.println("[DAO] " + TBL + " total count=" + r1.getInt(1));
            }
        }

        final String sql =
            "SELECT user_id, login_id, name, user_role, password, reset_token, reset_expires " +
            "  FROM " + TBL + " WHERE login_id = ?";

        System.out.println("[DAO] SQL=" + sql);
        System.out.println("[DAO] param loginId=>" + loginId + "< len=" + (loginId==null? "null": loginId.length()));

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, loginId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("[DAO][MISS] no row for login_id=>" + loginId + "<");
                    return null;
                }

                int    userId    = rs.getInt("user_id");
                String dbLoginId = rs.getString("login_id");
                String name      = rs.getString("name");
                String role      = rs.getString("user_role");
                String stored    = rs.getString("password");
                String rtoken    = rs.getString("reset_token");
                Timestamp exp    = rs.getTimestamp("reset_expires");

                String prefix = (stored == null ? "null" : stored.substring(0, Math.min(stored.length(), 4)));
                System.out.println("[DAO] found userId=" + userId + ", login_id=" + dbLoginId
                        + ", passwordPrefix=" + prefix + ", pwLen=" + (stored==null? "null": stored.length()));

                UserDTO dto = new UserDTO();
                dto.setUser_id(userId);
                dto.setLogin_id(dbLoginId);
                dto.setName(name);
                dto.setUser_role(role);
                dto.setPassword(stored);
                dto.setReset_token(rtoken);
                dto.setReset_expires(exp);
                return dto;
            }
        }
    }

    // PK로 단건 조회
    public UserDTO find(int userId) {
        final String sql =
            "SELECT user_id, login_id, password, name, user_role, " +
            "       created_at, updated_at, reset_expires, reset_token " +
            "  FROM " + TBL + " WHERE user_id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /* ---------- 목록/카운트 ---------- */
    public int count(String q) {
        boolean hasQ = q != null && !q.trim().isEmpty();
        String sqlNoQ = "SELECT COUNT(*) FROM " + TBL;
        String sqlQ =
            "SELECT COUNT(*) FROM " + TBL +
            " WHERE UPPER(login_id) LIKE ? OR UPPER(name) LIKE ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(hasQ ? sqlQ : sqlNoQ)) {
            if (hasQ) {
                String like = "%" + q.trim().toUpperCase() + "%";
                ps.setString(1, like);
                ps.setString(2, like);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // ORACLE 호환 ROWNUM 페이징
    public List<UserDTO> list(String q, int page, int size) {
        boolean hasQ = q != null && !q.trim().isEmpty();
        int safePage = Math.max(page, 1);
        int safeSize = Math.max(size, 1);
        int offset   = (safePage - 1) * safeSize;
        int endRow   = offset + safeSize;

        String inner =
            "SELECT user_id, login_id, password, name, user_role, " +
            "       created_at, updated_at, reset_expires, reset_token " +
            "  FROM " + TBL;

        String where = " WHERE UPPER(login_id) LIKE ? OR UPPER(name) LIKE ? ";
        String order = " ORDER BY user_id DESC ";

        String sql = "SELECT * FROM ( " +
                     "  SELECT t.*, ROWNUM rnum FROM ( " +
                     "    %s " +
                     "  ) t WHERE ROWNUM <= ? " +
                     ") WHERE rnum > ?";

        String innerFinal = hasQ ? (inner + where + order) : (inner + order);
        sql = String.format(sql, innerFinal);

        List<UserDTO> list = new ArrayList<>();
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            int idx = 1;
            if (hasQ) {
                String like = "%" + q.trim().toUpperCase() + "%";
                ps.setString(idx++, like);
                ps.setString(idx++, like);
            }
            ps.setInt(idx++, endRow);
            ps.setInt(idx  , offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    /* ---------- 유틸 ---------- */
    public boolean existsByLoginId(String loginId) {
        String sql = "SELECT COUNT(*) FROM " + TBL + " WHERE login_id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, loginId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    /* ---------- 생성/수정(BCrypt 통일) ---------- */

    // 생성: 평문 → BCrypt 저장
    public void insert(String loginId, String name, String role, String plainPassword) {
        String hash = PasswordUtil.hash(plainPassword);
        String sql =
            "INSERT INTO " + TBL + " (login_id, password, name, user_role, created_at) " +
            "VALUES (?, ?, ?, ?, SYSTIMESTAMP)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, hash);
            ps.setString(3, name);
            ps.setString(4, role);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 기본정보 수정
    public void updateBasic(int userId, String name, String role) {
        String sql =
            "UPDATE " + TBL + " SET name = ?, user_role = ?, updated_at = SYSTIMESTAMP " +
            " WHERE user_id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, role);
            ps.setInt(3, userId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 비번만 변경: 평문 → BCrypt 저장
    public void updatePassword(int userId, String newPlain) {
        String newHash = PasswordUtil.hash(newPlain);
        String sql = "UPDATE " + TBL + " SET password = ?, updated_at = SYSTIMESTAMP WHERE user_id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // 비번 변경 + 리셋 토큰 클리어
    public void updatePasswordAndClearReset(int userId, String newPlain) {
        String newHash = PasswordUtil.hash(newPlain);
        String sql =
            "UPDATE " + TBL + " SET password = ?, reset_token = NULL, reset_expires = NULL, " +
            " updated_at = SYSTIMESTAMP WHERE user_id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    /* ---------- 리셋 토큰 ---------- */
    public String issueResetToken(int userId) {
        String code = generateNumericCode(8);
        String hash = SecurityUtils.sha256(code); // 토큰은 SHA-256(평문코드 전달)
        String sql =
            "UPDATE " + TBL + " " +
            "   SET reset_token = ?, " +
            "       reset_expires = " + KST_NOW_TS + " + NUMTODSINTERVAL(30, 'MINUTE'), " + // ★ KST 기준 저장
            "       updated_at = SYSTIMESTAMP " +
            " WHERE user_id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, hash);
            ps.setInt(2, userId);
            int updated = ps.executeUpdate();
            if (updated > 0) return code;
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // 대소문자/공백 무시로 코드 재설정 (KST 기준 비교)
    public boolean resetWithToken(String loginId, String tokenPlain, String newPlainPassword) {
        if (loginId == null || tokenPlain == null || newPlainPassword == null) return false;

        String tokenHash = SecurityUtils.sha256(tokenPlain);    // 토큰 비교는 SHA-256
        String newHash   = PasswordUtil.hash(newPlainPassword); // 새 비번은 BCrypt

        String sql =
            "UPDATE " + TBL + " " +
            "   SET password = ?, reset_token = NULL, reset_expires = NULL, updated_at = SYSTIMESTAMP " +
            " WHERE TRIM(UPPER(login_id)) = TRIM(UPPER(?)) " +
            "   AND TRIM(UPPER(reset_token)) = TRIM(UPPER(?)) " +
            "   AND reset_expires IS NOT NULL " +
            "   AND reset_expires > " + KST_NOW_TS; // ★ KST 기준 비교
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setString(2, loginId);
            ps.setString(3, tokenHash);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // ★ 로그인용: KST 기준 + 대소문자/공백 무시로 리셋토큰 유효성 검사
    public boolean isResetTokenValidForLogin(String loginId, String tokenHash) {
        String sql =
            "SELECT 1 FROM " + TBL +
            " WHERE TRIM(UPPER(login_id)) = TRIM(UPPER(?)) " +
            "   AND TRIM(UPPER(reset_token)) = TRIM(UPPER(?)) " +
            "   AND reset_expires IS NOT NULL " +
            "   AND reset_expires > " + KST_NOW_TS; // ★ KST 기준 비교
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, tokenHash);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ---------- util ---------- */
    private String generateNumericCode(int len) {
        StringBuilder sb = new StringBuilder(len);
        java.util.concurrent.ThreadLocalRandom r = java.util.concurrent.ThreadLocalRandom.current();
        for (int i = 0; i < len; i++) sb.append(r.nextInt(10));
        return sb.toString();
    }
}
