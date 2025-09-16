package Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import DAO.InventoryDAO;
import DAO.ItemMasterDAO;
import DAO.LocationRuleDAO;

public class InventoryService {
    private final InventoryDAO inventoryDAO = new InventoryDAO();
    private final LocationRuleDAO locationRuleDAO = new LocationRuleDAO();
    private final ItemMasterDAO itemMasterDAO = new ItemMasterDAO();

    /* ========================= 공용 Result 타입 ========================= */
    public static class Result {
        public final boolean ok;
        public final String message;
        private Result(boolean ok, String message){ this.ok = ok; this.message = message; }
        public static Result ok(){ return new Result(true, "OK"); }
        public static Result ok(String msg){ return new Result(true, msg == null ? "OK" : msg); }
        public static Result fail(String msg){ return new Result(false, msg == null ? "FAIL" : msg); }
        @Override public String toString(){ return (ok ? "OK: " : "FAIL: ") + message; }
    }

    /* ========================= 유틸 ========================= */
    /** 문자열 대문자 정규화(Null-safe) */
    private String upper(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? "" : t.toUpperCase(Locale.ROOT);
    }

    /** 수량 > 0 */
    private boolean isPositive(BigDecimal n) {
        return n != null && n.signum() > 0;
    }

    /** ★ 수량 스케일 정규화(소수 3자리 가정) */
    private BigDecimal normalizeQty(BigDecimal n) {
        if (n == null) return null;
        return n.setScale(3, RoundingMode.HALF_UP);
    }

    /** LOCATION 허용값 검증 (대문자 정규화하여 비교) */
    private boolean isAllowedLocation(String locUpper) {
        Set<String> allowed = locationRuleDAO.getAllowedLocations();
        if (allowed == null || allowed.isEmpty()) return false;
        if (allowed.contains(locUpper)) return true;
        for (String a : allowed) {
            if (Objects.equals(upper(a), locUpper)) return true;
        }
        return false;
    }

    /* ========================= [입고(IN)] ========================= */
    /** 기존 컨트롤러 호환 (boolean) */
    public boolean insertIn(int itemId, BigDecimal qty, String location, int createdBy) {
        return insertInSafe(itemId, qty, location, createdBy).ok;
    }

    /** 메시지까지 주는 안전 버전 */
    public Result insertInSafe(int itemId, BigDecimal qty, String location, int createdBy) {
        try {
            if (itemId <= 0) return Result.fail("품목(ID)이 올바르지 않습니다.");
            if (!isPositive(qty)) return Result.fail("수량은 0보다 커야 합니다.");

            // ★ 수량 스케일 정리
            qty = normalizeQty(qty);

            String loc = upper(location);
            if (loc == null || loc.isEmpty()) return Result.fail("LOCATION이 비어있습니다.");
            if (!isAllowedLocation(loc)) return Result.fail("허용되지 않은 LOCATION: " + loc);

            // DB 트리거가 LOT_NO/TX_AT 자동 처리 (LOT/TX는 NULL로)
            int r = inventoryDAO.insertIn(itemId, qty, loc, createdBy);
            return (r == 1) ? Result.ok("입고 완료") : Result.fail("입고 실패(영향행 0)");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("서버 오류: " + e.getMessage());
        }
    }

    /* ========================= [출고(OUT)] ========================= */
    /**
     * 출고 등록 (FG만, LOT 필수)
     * - ★ 서비스단 사전 검증: LOT 존재/정합성 + 가용수량 체크
     * - DAO의 insertOutCheck(...)는 트랜잭션 내 동시성까지 최종 보장
     */
    public Result insertOutSafe(int itemId, BigDecimal qty, String lotNo, String location, int createdBy) {
        try {
            if (itemId <= 0) return Result.fail("품목(ID)이 올바르지 않습니다.");
            if (!isPositive(qty)) return Result.fail("수량은 0보다 커야 합니다.");

            // ★ 수량 스케일 정리
            qty = normalizeQty(qty);

            String lot = upper(lotNo);
            String loc = upper(location);
            if (lot == null || lot.isEmpty()) return Result.fail("LOT가 비어있습니다.");
            if (loc == null || loc.isEmpty()) return Result.fail("LOCATION이 비어있습니다.");
            if (!isAllowedLocation(loc)) return Result.fail("허용되지 않은 LOCATION: " + loc);

            String kind = itemMasterDAO.findKindById(itemId);
            if (kind == null || !kind.equalsIgnoreCase("FG")) {
                return Result.fail("FG(완제품) 품목만 출고 가능합니다.");
            }

            // ★ (1) LOT이 해당 품목/로케이션에 실제 존재하는지
            if (!inventoryDAO.existsLotForItemAtLocation(itemId, lot, loc)) {
                return Result.fail("해당 LOT이 품목/로케이션과 일치하지 않습니다.");
            }

            // ★ (2) 가용 수량 조회 후 초과 출고 방지
            BigDecimal available = inventoryDAO.getAvailableQty(itemId, lot, loc); // IN-OUT 합계
            if (available == null) available = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
            if (available.compareTo(qty) < 0) {
                return Result.fail("재고 부족: 가용 " + available + " <= 요청 " + qty);
            }

            // 트랜잭션 내 잔량 확인 + 출고 처리 (동시성 최종 보장)
            boolean ok = inventoryDAO.insertOutCheck(itemId, qty, lot, loc, createdBy);
            return ok ? Result.ok("출고 완료") : Result.fail("재고 부족 또는 동시성 충돌로 출고 실패");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("서버 오류: " + e.getMessage());
        }
    }

    /** ★ 기존 컨트롤러 호환용 boolean 버전 */
    public boolean insertOut(int itemId, BigDecimal qty, String lotNo, String location, int createdBy) {
        return insertOutSafe(itemId, qty, lotNo, location, createdBy).ok;
    }
}
