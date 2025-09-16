package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.math.BigDecimal;
import java.util.*;

/** 실적 조회/등록 DAO (Oracle 11g 기준, 스키마 안전화 + 지시일 포함) */
public class ProductionResultDAO {

    private final DataSource ds;

    public ProductionResultDAO() {
        try {
            Context ctx = new InitialContext();
            // 선호하신 2회 lookup 패턴 유지
            DataSource dataFactory  = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            DataSource dataFactory2 = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            this.ds = dataFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* ============================ 공통 유틸 ============================ */

    private int bindFilters(PreparedStatement ps, java.sql.Date from, java.sql.Date to,
                            Integer orderId, int startIdx) throws SQLException {
        int idx = startIdx;
        if (from != null) ps.setDate(idx++, from);
        if (to   != null) ps.setDate(idx++, to);
        if (orderId != null && orderId > 0) ps.setInt(idx++, orderId);
        return idx;
    }

    private static String likeOrNull(String s){
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : "%" + t + "%";
    }

    /* ============================ 상세 실적 조회 (클라 페이징 전제) ============================ */
    // 지시일 포함: PRODUCTION_ORDER(o) 조인, o.CREATED_AT(또는 ORDER_DATE) -> order_date
    public List<Map<String,Object>> selectResults(java.sql.Date from, java.sql.Date to,
                                                  Integer orderId,
                                                  int page, int size) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ( \n")
          .append("  SELECT inner_q.*, ROW_NUMBER() OVER(ORDER BY inner_q.work_time DESC, inner_q.result_id DESC) rn \n")
          .append("  FROM ( \n")
          .append("    SELECT r.RESULT_ID, r.ORDER_ID, r.WORK_DATE AS work_time, \n")
          .append("           r.WORKER_ID, \n")
          .append("           CAST(NULL AS VARCHAR2(100)) AS worker_name, \n") // 사용자명 조인은 스키마 확정 후
          .append("           CAST(NULL AS VARCHAR2(100)) AS process_name, \n") // PROCESS_* 미보유 대비
          .append("           r.GOOD_QTY, r.FAIL_QTY, \n")
          .append("           it.LOT_NO, \n")
          .append("           o.CREATED_AT AS order_date \n") // ← 지시일(스키마에 따라 ORDER_DATE 사용)
          .append("    FROM PRODUCTION_RESULT r \n")
          .append("    JOIN PRODUCTION_ORDER o ON o.ORDER_ID = r.ORDER_ID \n")
          .append("    LEFT JOIN ( \n")
          .append("      SELECT ORDER_ID, MAX(LOT_NO) AS LOT_NO \n")
          .append("      FROM INVENTORY_TRANSACTION \n")
          .append("      WHERE LOT_NO IS NOT NULL \n")
          .append("      GROUP BY ORDER_ID \n")
          .append("    ) it ON it.ORDER_ID = r.ORDER_ID \n")
          .append("    WHERE 1=1 \n");

        // ★★★ 이 부분을 지시일(CREATED_AT)을 기준으로 변경 ★★★
        if (from != null) sb.append("      AND TRUNC(o.CREATED_AT) >= ? \n");
        if (to   != null) sb.append("      AND TRUNC(o.CREATED_AT) <= ? \n"); // 당일 포함
        if (orderId != null && orderId > 0) sb.append("      AND r.ORDER_ID = ? \n");
        
        sb.append("  ) inner_q \n")
          .append(") \n")
          .append("WHERE rn BETWEEN ? AND ?");

        int p = (page <= 0 ? 1 : page);
        int s = (size <= 0 ? 10000 : size); // 클라 페이징이므로 넉넉히
        int start = (p - 1) * s + 1;
        int end   = start + s - 1;

        List<Map<String,Object>> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            int idx = bindFilters(ps, from, to, orderId, 1);
            ps.setInt(idx++, start);
            ps.setInt(idx,   end);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("result_id",   rs.getInt("RESULT_ID"));
                    m.put("order_id",    rs.getInt("ORDER_ID"));
                    m.put("work_time",   rs.getTimestamp("work_time"));
                    m.put("worker_id",   rs.getInt("WORKER_ID"));
                    m.put("worker_name", rs.getString("worker_name"));
                    m.put("process_name",rs.getString("process_name"));
                    m.put("good_qty",    rs.getBigDecimal("GOOD_QTY"));
                    m.put("fail_qty",    rs.getBigDecimal("FAIL_QTY"));
                    m.put("lot_no",      rs.getString("LOT_NO"));
                    m.put("order_date",  rs.getTimestamp("order_date"));
                    list.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int countResults(java.sql.Date from, java.sql.Date to,
                            Integer orderId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(1) \n")
          .append("FROM PRODUCTION_RESULT r \n")
          .append("JOIN PRODUCTION_ORDER o ON o.ORDER_ID = r.ORDER_ID \n") // 조인 추가
          .append("WHERE 1=1 \n");

        // ★★★ 이 부분을 지시일(CREATED_AT)을 기준으로 변경 ★★★
        if (from != null) sb.append("  AND TRUNC(o.CREATED_AT) >= ? \n");
        if (to   != null) sb.append("  AND TRUNC(o.CREATED_AT) <= ? \n");
        if (orderId != null && orderId > 0) sb.append("  AND r.ORDER_ID = ? \n");

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            bindFilters(ps, from, to, orderId, 1);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /* ============================ 달성률 요약 (지시일 포함) ============================ */
    public List<Map<String,Object>> selectSummary(java.sql.Date from, java.sql.Date to,
                                                  Integer orderId) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT r.ORDER_ID, \n")
          .append("       MAX(o.CREATED_AT) AS order_date, \n")
          .append("       SUM(NVL(r.GOOD_QTY,0)) AS total_good_qty, \n")
          .append("       MAX(NVL(o.QUANTITY,0)) AS order_qty \n")
          .append("FROM PRODUCTION_RESULT r \n")
          .append("JOIN PRODUCTION_ORDER o ON o.ORDER_ID = r.ORDER_ID \n")
          .append("WHERE 1=1 \n");

        // ★★★ 이 부분을 지시일(CREATED_AT)을 기준으로 변경 ★★★
        if (from != null) sb.append("  AND TRUNC(o.CREATED_AT) >= ? \n");
        if (to   != null) sb.append("  AND TRUNC(o.CREATED_AT) <= ? \n");
        if (orderId != null && orderId > 0) sb.append("  AND r.ORDER_ID = ? \n");

        sb.append("GROUP BY r.ORDER_ID \n")
          .append("ORDER BY r.ORDER_ID DESC");

        List<Map<String,Object>> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            bindFilters(ps, from, to, orderId, 1);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("order_id", rs.getInt("ORDER_ID"));
                    m.put("order_date", rs.getTimestamp("order_date"));
                    m.put("total_good_qty", rs.getBigDecimal("total_good_qty"));
                    m.put("order_qty", rs.getBigDecimal("order_qty"));
                    list.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /* ============================ 열린 생산지시 조회 ============================ */
    public List<Map<String,Object>> getOpenOrders(String siteCode) {
        String statusClause =
            "UPPER(TRIM(po.STATUS)) IN ('PLANNED','IN_PROGRESS','RUNNING','진행중','계획')";

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT po.ORDER_ID, po.TARGET_ID, po.DUE_DATE, po.STATUS, po.SITE_CODE, \n")
          .append("       im.ITEM_NAME, \n")
          .append("       NVL(po.QUANTITY,0) AS order_qty, \n")
          .append("       NVL((SELECT SUM(NVL(r.GOOD_QTY,0)) FROM PRODUCTION_RESULT r WHERE r.ORDER_ID = po.ORDER_ID),0) AS done_qty, \n")
          .append("       NVL(po.QUANTITY,0) - NVL((SELECT SUM(NVL(r.GOOD_QTY,0)) FROM PRODUCTION_RESULT r WHERE r.ORDER_ID = po.ORDER_ID),0) AS remain_qty \n")
          .append("  FROM PRODUCTION_ORDER po \n")
          .append("  LEFT JOIN ITEM_MASTER im ON im.ITEM_ID = po.TARGET_ID \n")
          .append(" WHERE ").append(statusClause).append("\n")
          .append("   AND (NVL(po.QUANTITY,0) - NVL((SELECT SUM(NVL(r.GOOD_QTY,0)) FROM PRODUCTION_RESULT r WHERE r.ORDER_ID = po.ORDER_ID),0)) > 0 \n");

        if (siteCode != null && !siteCode.trim().isEmpty()) {
            sb.append("   AND UPPER(TRIM(po.SITE_CODE)) = UPPER(TRIM(?)) \n");
        }

        sb.append(" ORDER BY CASE WHEN po.DUE_DATE IS NULL THEN 1 ELSE 0 END, po.DUE_DATE, po.ORDER_ID DESC");

        List<Map<String,Object>> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            if (siteCode != null && !siteCode.trim().isEmpty()) {
                ps.setString(1, siteCode.trim());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("order_id",   rs.getInt("ORDER_ID"));
                    m.put("target_id",  rs.getInt("TARGET_ID"));
                    m.put("due_date",   rs.getDate("DUE_DATE"));
                    m.put("status",     rs.getString("STATUS"));
                    m.put("site_code",  rs.getString("SITE_CODE"));
                    m.put("item_name",  rs.getString("ITEM_NAME"));
                    m.put("order_qty",  rs.getBigDecimal("order_qty"));
                    m.put("done_qty",   rs.getBigDecimal("done_qty"));
                    m.put("remain_qty", rs.getBigDecimal("remain_qty"));
                    list.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /* ============================ 실적 등록 ============================ */
    public int insertResult(int orderId,
                            int workerId,
                            BigDecimal goodQty,
                            BigDecimal failQty,
                            String processName,
                            String processCode,
                            String lotNo,
                            Timestamp workDate) throws SQLException {

        final String sql =
            "INSERT INTO PRODUCTION_RESULT \n" +
            " (RESULT_ID, ORDER_ID, WORKER_ID, \n" +
            "  GOOD_QTY, FAIL_QTY, WORK_DATE, CREATED_AT) \n" +
            "VALUES \n" +
            " (SEQ_PRODUCTION_RESULT.NEXTVAL, ?, ?, \n" +
            "  ?, ?, NVL(?, SYSDATE), SYSTIMESTAMP)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int i=1;
            ps.setInt(i++, orderId);
            ps.setInt(i++, workerId);
            ps.setBigDecimal(i++, goodQty);
            ps.setBigDecimal(i++, failQty);
            if (workDate != null) ps.setTimestamp(i++, workDate); else ps.setNull(i++, Types.TIMESTAMP);

            return ps.executeUpdate();
        }
    }
}