// yaService/PasswordResetService.java
package Service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import util.SecurityUtils;

public class PasswordResetService {
    private final DataSource ds;
    public PasswordResetService() {
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    // 로그인ID 기준 리셋코드 발행 (코드 반환: 데모용)
    public String issueCode(String loginId, int ttlMinutes) throws SQLException {
        String code = SecurityUtils.generateNumericCode(8);
        String codeHash = SecurityUtils.sha256(code);

        String sql = "UPDATE users " +
                     "SET reset_token=?, reset_expires=SYSTIMESTAMP + NUMTODSINTERVAL(?, 'MINUTE') " +
                     "WHERE login_id=?";
        try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, codeHash);
            ps.setInt(2, ttlMinutes);
            ps.setString(3, loginId);
            if (ps.executeUpdate() == 0) throw new SQLException("존재하지 않는 사용자");
        }
        return code;
    }

    public boolean isResetValid(long userId) throws SQLException {
        String sql = "SELECT CASE WHEN reset_token IS NOT NULL " +
                     "AND reset_expires IS NOT NULL " +
                     "AND reset_expires > SYSTIMESTAMP THEN 1 ELSE 0 END AS ok " +
                     "FROM users WHERE user_id=?";
        try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("ok") == 1;
            }
        }
    }

    public void changePassword(long userId, String newHash) throws SQLException {
        String sql = "UPDATE users SET password=?, reset_token=NULL, reset_expires=NULL WHERE user_id=?";
        try (Connection c = ds.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newHash);
            ps.setLong(2, userId);
            ps.executeUpdate();
        }
    }
}
