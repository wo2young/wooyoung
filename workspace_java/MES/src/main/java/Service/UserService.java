package Service;

import java.sql.SQLException;
import java.sql.Timestamp;

import DAO.UserDAO;
import util.SecurityUtils;
import yaDTO.UserDTO;

public class UserService {

    private final UserDAO userDao = new UserDAO();

    // 로그인ID로 사용자 조회(필요 시)
    public UserDTO getUserByLoginId(String loginId) throws SQLException {
        return userDao.findByLoginId(loginId);
    }

    // 리셋코드 로그인 세션에서 비밀번호 변경
    public void changePasswordAfterResetLogin(UserDTO sessionUser,
                                              String currentResetCode,
                                              String newPlainPassword) throws SQLException {
        if (sessionUser == null) {
            throw new SecurityException("세션이 유효하지 않습니다.");
        }

        // 세션값이 오래됐을 수 있으니 DB에서 최신 조회
        UserDTO fresh = userDao.findByLoginId(sessionUser.getLogin_id());
        if (fresh == null) {
            throw new SecurityException("사용자 정보를 찾을 수 없습니다.");
        }

        // 1) 리셋코드 유효성 검사(존재/만료/일치)
        String tokenHash = fresh.getReset_token();
        Timestamp expires = fresh.getReset_expires();

        if (tokenHash == null || expires == null) {
            throw new SecurityException("리셋코드가 설정되어 있지 않습니다.");
        }
        if (expires.before(new Timestamp(System.currentTimeMillis()))) {
            throw new SecurityException("리셋코드가 만료되었습니다. 다시 발급받으세요.");
        }
        String inputHash = SecurityUtils.sha256(currentResetCode);
        if (!inputHash.equals(tokenHash)) {
            throw new SecurityException("리셋코드가 올바르지 않습니다.");
        }

        // 2) 새 비밀번호 정책(간단 검증)
        if (newPlainPassword == null || newPlainPassword.length() < 8) {
            throw new SecurityException("새 비밀번호는 8자 이상이어야 합니다.");
        }

        // 3) DB 업데이트: PASSWORD 저장 + RESET_* 초기화 → DAO에 위임
        String newHash = SecurityUtils.sha256(newPlainPassword);
        userDao.updatePasswordAndClearReset(fresh.getUser_id(), newHash);

        // 컨트롤러에서는 세션 최신화:
        // sessionUser.setPassword(newHash);
        // sessionUser.setReset_token(null);
        // sessionUser.setReset_expires(null);
        // session.setAttribute("FORCE_CHANGE_PASSWORD", false);
    }

    // 일반 비밀번호 변경(관리자/본인 인증 완료 상황 등)
    public void changePasswordDirect(int userId, String newPlainPassword) throws SQLException {
        if (newPlainPassword == null || newPlainPassword.length() < 8) {
            throw new SecurityException("새 비밀번호는 8자 이상이어야 합니다.");
        }
        String newHash = SecurityUtils.sha256(newPlainPassword);
        userDao.updatePassword(userId, newHash);
    }
}

/*
[전체 정리]
- 서비스에서 DB 커넥션을 열지 않음(DAO 책임).
- changePasswordAfterResetLogin:
  1) DB 재조회 → 리셋코드 유효성(존재/만료/일치) 확인
  2) 새 비번 길이 체크
  3) DAO.updatePasswordAndClearReset 호출로 비번 저장 + 리셋필드 NULL
- 컨트롤러에서 세션 값도 갱신해야 화면/필터가 즉시 정상화됨.
*/
