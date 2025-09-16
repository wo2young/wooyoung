package util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
    // 해시 강도(cost): 10~12 권장. 서버 성능에 따라 조절
    private static final int COST = 12;

    private PasswordUtil() { /* 인스턴스화 방지 */ }

    /* =========================
       생성(추천 알고리즘: BCrypt)
       ========================= */
    // 평문 → BCrypt 해시
    public static String hash(String plain) {
        if (plain == null) throw new IllegalArgumentException("plain password is null");
        return BCrypt.hashpw(plain, BCrypt.gensalt(COST));
    }

    // (옵션) 평문 → SHA-256(hex) (레거시/호환용)
    public static String hashSha256(String plain) {
        if (plain == null) throw new IllegalArgumentException("plain password is null");
        return SecurityUtils.sha256(plain);
    }

    /* =========================
       검증(저장값 형태 자동 감지)
       ========================= */
    // 평문 vs 저장된 값(BCrypt / SHA-256(hex) / 평문) 자동 검증
    public static boolean verify(String plain, String stored) {
        if (plain == null || stored == null) return false;
        stored = stored.trim();

        // 1) BCrypt: $2a$ / $2b$ / $2y$
        if (isBCrypt(stored)) {
            try { return BCrypt.checkpw(plain, stored); }
            catch (Exception e) { return false; }
        }

        // 2) SHA-256(hex) 64자
        if (isSha256Hex(stored)) {
            String sha = SecurityUtils.sha256(plain);
            return stored.equalsIgnoreCase(sha);
        }

        // 3) 마지막 폴백: 평문 비교(아주 오래된 데이터 호환)
        return stored.equals(plain);
    }

    /* =========================
       형태 판별 유틸
       ========================= */
    // 값이 BCrypt 형식인지 여부($2a/$2b/$2y...로 시작)
    public static boolean isBCrypt(String s) {
        return s != null && (s.startsWith("$2a$") || s.startsWith("$2b$") || s.startsWith("$2y$"));
    }

    // 값이 SHA-256(hex) 형식(64자 16진)인지 여부
    public static boolean isSha256Hex(String s) {
        return s != null && s.matches("^[0-9a-fA-F]{64}$");
    }
}

/*
전체 정리
- verify()가 저장값 접두/형태에 따라 자동으로 BCrypt 또는 SHA-256로 검증합니다.
- 가입/비번변경: hash() (BCrypt 권장). 레거시 호환이 필요하면 hashSha256() 사용.
- DB 컬럼 길이는 60자 이상 권장(BCrypt 길이 60). VARCHAR2(100 CHAR) 추천.
- LoginService에서는 반드시 PasswordUtil.verify(input, stored)를 사용하세요.
*/
