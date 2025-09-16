package Service;

import DAO.UserDAO;
import yaDTO.UserDTO;
import util.PasswordUtil;
import util.SecurityUtils;

public class LoginService {
    private final UserDAO userDAO = new UserDAO();

    public static class LoginResult {
        private final boolean success, viaReset;
        private final UserDTO user;
        public LoginResult(boolean s, boolean v, UserDTO u){ this.success=s; this.viaReset=v; this.user=u; }
        public boolean isSuccess(){ return success; }
        public boolean isViaReset(){ return viaReset; }
        public UserDTO getUser(){ return user; }
    }

    public LoginResult login(String loginId, String inputPassword) {
        try {
            // 공백 입력 방지
            loginId = (loginId == null ? null : loginId.trim());

            UserDTO u = userDAO.findByLoginId(loginId);
            if (u == null) return new LoginResult(false, false, null);

            String stored = u.getPassword();    // BCrypt

            // ------------ 1) 일반 비밀번호 검증(BCrypt) ------------
            if (PasswordUtil.verify(inputPassword, stored)) {
                return new LoginResult(true, false, u);
            }

            // ------------ 2) 리셋코드 로그인 (DB 기준 유효성) ------------
            String raw = inputPassword == null ? "" : inputPassword.trim();
            String digitsOnly = raw.replaceAll("\\D", ""); // 숫자만 추출

            boolean tokenOk = false;
            if (!raw.isEmpty()) {
                String shaRaw = SecurityUtils.sha256(raw);
                tokenOk = userDAO.isResetTokenValidForLogin(loginId, shaRaw);

                if (!tokenOk && !digitsOnly.isEmpty() && !digitsOnly.equals(raw)) {
                    String shaDigits = SecurityUtils.sha256(digitsOnly);
                    tokenOk = userDAO.isResetTokenValidForLogin(loginId, shaDigits);
                }
            }

            System.out.println("[LOGIN][RESET][DB] tokenOk=" + tokenOk + " (loginId=" + loginId + ")");

            if (tokenOk) {
                return new LoginResult(true, true, u); // viaReset = true → /change-password로 이동
            }

            return new LoginResult(false, false, null);

        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResult(false, false, null);
        }
    }
}
