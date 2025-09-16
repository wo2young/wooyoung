package DAO;

import java.sql.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import yaDTO.UserDTO;
import util.PasswordUtil;   // BCrypt 유틸 (hash/isBCrypt/verify)
import util.SecurityUtils; // SHA-256 유틸 (sha256)

import org.apache.commons.codec.digest.Crypt;    // DES-crypt (JCRYPT)
import org.apache.commons.codec.digest.Md5Crypt; // $1$
import org.apache.commons.codec.digest.Sha2Crypt; // $5$/$6$

public class LoginDAO {
    private final DataSource ds;

    private static final String T_USER = "USER_T";

    public static class LoginResult {
        public final UserDTO user;
        public final boolean usedResetToken;
        public LoginResult(UserDTO user, boolean usedResetToken) {
            this.user = user; this.usedResetToken = usedResetToken;
        }
    }

    public LoginDAO() {
        try {
            ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/oracle");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Connection c() throws SQLException { return ds.getConnection(); }

    /** 구버전 호환: 성공 시 UserDTO만 반환(리셋 여부 구분 불가) */
    public UserDTO login(String loginId, String inputPassword) {
        LoginResult r = loginEx(loginId, inputPassword);
        return (r == null) ? null : r.user;
    }

    /** 신규: 리셋코드 로그인 여부까지 반환 */
    public LoginResult loginEx(String loginId, String inputPassword) {
        if (loginId == null || inputPassword == null) return null;
        loginId = loginId.trim();
        if (loginId.isEmpty()) return null;

        final String sql =
            "SELECT user_id, login_id, name, user_role, password, " +
            "       reset_token, reset_expires " +
            "  FROM " + T_USER + " WHERE login_id = ?";

        try (Connection con = c();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, loginId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                int    userId    = rs.getInt("user_id");
                String dbLoginId = rs.getString("login_id");
                String name      = rs.getString("name");
                String role      = rs.getString("user_role");
                String stored    = rs.getString("password");      // 해시/평문
                String rtoken    = rs.getString("reset_token");   // SHA-256 해시 가정
                Timestamp exp    = rs.getTimestamp("reset_expires");

                boolean ok = false;
                boolean viaReset = false;

                // --- 1) 일반 비밀번호 우선 (형식별 분기) ---
                if (stored != null) {
                    if (PasswordUtil.isBCrypt(stored)) {
                        // BCrypt
                        ok = PasswordUtil.verify(inputPassword, stored);

                    } else if (stored.length() == 64 && stored.matches("[0-9a-fA-F]{64}")) {
                        // SHA-256 (hex 64)
                        ok = SecurityUtils.sha256(inputPassword).equalsIgnoreCase(stored);

                    } else if (stored.startsWith("$1$")) {
                        // MD5-crypt
                        String salt = stored.substring(0, stored.lastIndexOf('$')); // $1$<salt>
                        ok = Md5Crypt.md5Crypt(inputPassword.getBytes(), salt).equals(stored);

                    } else if (stored.startsWith("$5$")) {
                        // SHA-256-crypt
                        // salt는 $5$<salt>$ 형태 (마지막 $까지 포함)
                        int idx = stored.indexOf('$', 3);
                        String salt = (idx > 0) ? stored.substring(0, stored.indexOf('$', idx + 1) + 1) : "$5$";
                        ok = Sha2Crypt.sha256Crypt(inputPassword.getBytes(), salt).equals(stored);

                    } else if (stored.startsWith("$6$")) {
                        // SHA-512-crypt
                        int idx = stored.indexOf('$', 3);
                        String salt = (idx > 0) ? stored.substring(0, stored.indexOf('$', idx + 1) + 1) : "$6$";
                        ok = Sha2Crypt.sha512Crypt(inputPassword.getBytes(), salt).equals(stored);

                    } else if (stored.length() == 13) {
                        // JCRYPT / legacy DES-crypt (2글자 salt 포함)
                        ok = Crypt.crypt(inputPassword, stored).equals(stored);

                    } else {
                        // 과거 평문 저장
                        ok = stored.equals(inputPassword);
                    }

                    // ✅ 성공했는데 BCrypt가 아니면 → 즉시 BCrypt로 마이그레이션
                    if (ok && !PasswordUtil.isBCrypt(stored)) {
                        String newHash = PasswordUtil.hash(inputPassword);
                        try (PreparedStatement ups = con.prepareStatement(
                                "UPDATE " + T_USER + " SET password = ?, updated_at = SYSTIMESTAMP WHERE user_id = ?")) {
                            ups.setString(1, newHash);
                            ups.setInt(2, userId);
                            ups.executeUpdate();
                        }
                    }
                }

                // --- 2) 일반 비번 실패 → 리셋코드 경로 ---
                if (!ok && rtoken != null && exp != null) {
                    boolean notExpired = exp.after(new Timestamp(System.currentTimeMillis()));
                    String inputHash = SecurityUtils.sha256(inputPassword); // 입력을 리셋코드로 간주
                    if (notExpired && inputHash.equalsIgnoreCase(rtoken)) {
                        ok = true;
                        viaReset = true;
                    }
                }

                if (!ok) return null;

                UserDTO u = new UserDTO();
                u.setUser_id(userId);
                u.setLogin_id(dbLoginId);
                u.setName(name);
                u.setUser_role(role);

                return new LoginResult(u, viaReset);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** (선택) 평문/비BCrypt 비밀번호를 일괄 재해시 */
    public int rehashAllPlainPasswords() {
        final String SEL = "SELECT user_id, password FROM " + T_USER +
                           " WHERE password IS NOT NULL AND password NOT LIKE '$2%'";
        final String UPD = "UPDATE " + T_USER +
                           " SET password = ?, updated_at = SYSTIMESTAMP WHERE user_id = ?";

        int updated = 0;
        try (Connection con = c();
             PreparedStatement ps = con.prepareStatement(SEL);
             ResultSet rs = ps.executeQuery();
             PreparedStatement ups = con.prepareStatement(UPD)) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String cur = rs.getString("password");
                if (cur == null || cur.isEmpty()) continue;

                // 기존 문자열이 평문/타해시여도 일단 BCrypt로 통일
                String hash = PasswordUtil.hash(cur);
                ups.setString(1, hash);
                ups.setInt(2, userId);
                ups.addBatch();
                updated++;
            }
            ups.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return updated;
    }
}
