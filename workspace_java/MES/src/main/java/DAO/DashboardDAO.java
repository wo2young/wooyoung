package DAO;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class DashboardDAO {
  private final DataSource ds;

  public DashboardDAO() {
    try {
      Context ctx = new InitialContext();
      ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  private Connection c() throws SQLException { return ds.getConnection(); }

  // 테이블 이름 상수
  private static final String T_RES   = "PRODUCTION_RESULT";
  private static final String T_TGT   = "PRODUCTION_TARGET";
  private static final String T_DEF   = "QUALITY_DEFECT";
  private static final String T_CD    = "CODE_DETAIL";
  private static final String T_ITEM  = "ITEM_MASTER";              // 제품 마스터
  private static final String T_INV   = "INVENTORY_TRANSACTION";    // 재고 트랜잭션

  /** 1) 오늘 요약 */
  public Map<String,Object> selectTodaySummary() {
    String sql =
      "SELECT NVL(SUM(GOOD_QTY),0) good, NVL(SUM(FAIL_QTY),0) defect " +
      "FROM " + T_RES + " WHERE TRUNC(WORK_DATE)=TRUNC(SYSDATE)";
    Map<String,Object> out = new HashMap<>();
    try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      if (rs.next()) {
        out.put("good", rs.getLong("good"));
        out.put("defect", rs.getLong("defect"));
      }
    } catch (Exception e) { throw new RuntimeException(e); }
    return out;
  }

  /** 2) 최근 7일 생산량 */
  public Map<String,Object> selectProdLast7() {
    String sql =
      "SELECT TO_CHAR(d,'MM-DD') label, NVL(SUM(r.GOOD_QTY),0) qty " +
      "FROM (SELECT TRUNC(SYSDATE) - LEVEL + 1 d FROM dual CONNECT BY LEVEL <= 7) d " +
      "LEFT JOIN " + T_RES + " r ON TRUNC(r.WORK_DATE)=d.d " +
      "GROUP BY d " +
      "ORDER BY d";
    List<String> labels = new ArrayList<>();
    List<Integer> data  = new ArrayList<>();
    try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        labels.add(rs.getString("label"));
        data.add(rs.getInt("qty"));
      }
    } catch (Exception e) { throw new RuntimeException(e); }
    Map<String,Object> out = new HashMap<>();
    out.put("labels", labels);
    out.put("data", data);
    return out;
  }

  /** 3) 이번달 목표 vs 실적 */
  public Map<String,Object> selectTargetVsActualMonth() {
    String sql =
      "WITH cal AS ( " +
      "  SELECT TRUNC(SYSDATE,'MM') + LEVEL - 1 AS d " +
      "  FROM dual CONNECT BY LEVEL <= (LAST_DAY(SYSDATE) - TRUNC(SYSDATE,'MM') + 1) " +
      "), " +
      "actual_d AS ( " +
      "  SELECT TRUNC(WORK_DATE) d, SUM(GOOD_QTY) qty " +
      "  FROM " + T_RES + " " +
      "  WHERE TRUNC(WORK_DATE,'MM') = TRUNC(SYSDATE,'MM') " +
      "  GROUP BY TRUNC(WORK_DATE) " +
      "), " +
      "tgt_d AS ( " +
      "  SELECT d.d, " +
      "         SUM( t.TARGET_QUANTITY / NULLIF( (TRUNC(t.PERIOD_END) - TRUNC(t.PERIOD_START) + 1), 0) ) tgt_per_day " +
      "  FROM cal d " +
      "  JOIN " + T_TGT + " t " +
      "    ON d.d BETWEEN TRUNC(t.PERIOD_START) AND TRUNC(t.PERIOD_END) " +
      "  GROUP BY d.d " +
      "), " +
      "wk AS ( SELECT d, TO_CHAR(d,'W') AS w FROM cal ) " +
      "SELECT 'W'||w.w AS label, " +
      "       NVL(SUM(td.tgt_per_day),0) AS target_qty, " +
      "       NVL(SUM(ad.qty),0)        AS actual_qty " +
      "FROM cal c " +
      "JOIN wk w ON w.d = c.d " +
      "LEFT JOIN tgt_d   td ON td.d = c.d " +
      "LEFT JOIN actual_d ad ON ad.d = c.d " +
      "GROUP BY w.w " +
      "ORDER BY MIN(c.d)";
    List<String> labels = new ArrayList<>();
    List<Integer> target = new ArrayList<>(), actual = new ArrayList<>();
    try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        labels.add(rs.getString("label"));
        target.add(rs.getBigDecimal("target_qty").setScale(0, java.math.RoundingMode.HALF_UP).intValue());
        actual.add(rs.getInt("actual_qty"));
      }
    } catch (Exception e) { throw new RuntimeException(e); }
    Map<String,Object> out = new HashMap<>();
    out.put("labels", labels);
    out.put("target", target);
    out.put("actual", actual);
    return out;
  }

  /** 4) 월간 불량 분포 */
  public Map<String,Object> selectDefectPieMonth() {
    String sql =
      "WITH M AS ( " +
      "  SELECT TRUNC(SYSDATE,'MM') S, ADD_MONTHS(TRUNC(SYSDATE,'MM'),1) E FROM dual " +
      "), Q AS ( " +
      "  SELECT TRIM(UPPER(DETAIL_CODE)) DETAIL_CODE, SUM(NVL(QUANTITY,0)) CNT " +
      "  FROM " + T_DEF + " q, M " +
      "  WHERE q.CREATED_AT >= M.S AND q.CREATED_AT < M.E " +
      "    AND q.DETAIL_CODE IS NOT NULL " +
      "    AND TRIM(UPPER(q.DETAIL_CODE)) LIKE 'DEF-%' " +
      "  GROUP BY TRIM(UPPER(DETAIL_CODE)) " +
      ") " +
      "SELECT NVL(cd.CODE_DNAME, q.DETAIL_CODE) label, NVL(q.CNT,0) cnt " +
      "FROM " + T_CD + " cd " +
      "LEFT JOIN Q q ON TRIM(UPPER(cd.DETAIL_CODE)) = q.DETAIL_CODE " +
      "WHERE TRIM(UPPER(cd.DETAIL_CODE)) LIKE 'DEF-%' " +
      "ORDER BY cnt DESC, label ASC";
    List<String> labels = new ArrayList<>();
    List<Integer> counts = new ArrayList<>();
    try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) { labels.add(rs.getString("label")); counts.add(rs.getInt("cnt")); }
    } catch (Exception e) { throw new RuntimeException(e); }
    Map<String,Object> out = new HashMap<>();
    out.put("labels", labels);
    out.put("counts", counts);
    return out;
  }

  /** 5) 재고 현황 (ITEM별 합계) */
  public Map<String,Object> selectInventoryStatus() {
    String sql =
      "SELECT i.ITEM_NAME label, NVL(SUM(t.QUANTITY),0) cnt " +
      "FROM " + T_ITEM + " i " +
      "LEFT JOIN " + T_INV + " t ON i.ITEM_ID = t.ITEM_ID " +
      "GROUP BY i.ITEM_NAME " +
      "ORDER BY i.ITEM_NAME";
    List<String> labels = new ArrayList<>();
    List<Integer> counts = new ArrayList<>();
    try (Connection con = c(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
      while (rs.next()) { labels.add(rs.getString("label")); counts.add(rs.getInt("cnt")); }
    } catch (Exception e) { throw new RuntimeException(e); }
    Map<String,Object> out = new HashMap<>();
    out.put("labels", labels);
    out.put("counts", counts);
    return out;
  }
}
