package DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.sql.*;
import java.math.BigDecimal;
import java.util.*;

public class InventoryDAO {

    private DataSource ds;

    public InventoryDAO() {
        try {
            Context ctx = new InitialContext();
            // ✅ JNDI 통일 (2회 lookup 유지)
            DataSource dataFactory  = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            DataSource dataFactory2 = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            ds = dataFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /** UTC → KST 보정 (9시간) */
    private static Timestamp toKst(Timestamp raw) {
        if (raw == null) return null;
        long nineHours = 9L * 60 * 60 * 1000; // 9시간(ms)
        return new Timestamp(raw.getTime() + nineHours);
    }

    

    /* ========================= [입고 등록] ========================= */
    /**
     * 입고 등록
     * - PK, LOT_NO, TX_AT 은 DB 트리거에서 자동 처리
     */
    public int insertIn(int itemId, BigDecimal qty, String locationUpper, int createdBy) throws SQLException {
        final String sql =
        		"INSERT INTO INVENTORY_TRANSACTION " +
        				"(ITEM_ID, TXN_TYPE, QUANTITY, LOCATION_NAME, CREATED_BY) " +
        				"VALUES (?, 'IN', ?, ?, ?)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setBigDecimal(2, qty);          // 수량
            ps.setString(3, locationUpper);    // LOCATION
            ps.setInt(4, createdBy);           // 사용자
            return ps.executeUpdate();
        }
    }

    /* ========================= [출고 등록] ========================= */
    /**
     * 출고 처리
     * - 동시성 보장을 위해 트랜잭션 사용
     * - 출고 시 가용 수량 확인 후 INSERT
     */
    public boolean insertOutCheck(int itemId, BigDecimal qty, String lotUpper,
                                  String locationUpper, int createdBy) throws SQLException {

        final String sqlAvail =
            "SELECT NVL(SUM(CASE WHEN TXN_TYPE='IN'  THEN QUANTITY " +
            "                  WHEN TXN_TYPE='OUT' THEN -QUANTITY END), 0) AS AVAIL " +
            "  FROM INVENTORY_TRANSACTION " +
            " WHERE ITEM_ID=? AND LOT_NO=? AND LOCATION_NAME=?";

        final String sqlInsert =
        		"INSERT INTO INVENTORY_TRANSACTION " +
        				"(ITEM_ID, TXN_TYPE, QUANTITY, LOT_NO, LOCATION_NAME, CREATED_BY) " +
        				"VALUES (?, 'OUT', ?, ?, ?, ?)";

        try (Connection conn = ds.getConnection()) {
            boolean oldAuto = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try {
                BigDecimal available = BigDecimal.ZERO;

                // 1) 현재 가용 수량 조회
                try (PreparedStatement ps = conn.prepareStatement(sqlAvail)) {
                    ps.setInt(1, itemId);
                    ps.setString(2, lotUpper);
                    ps.setString(3, locationUpper);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            available = rs.getBigDecimal(1);
                            if (available == null) available = BigDecimal.ZERO;
                        }
                    }
                }

                // 2) 부족하면 롤백 후 false 반환
                if (available.compareTo(qty) < 0) {
                    conn.rollback();
                    return false;
                }

                // 3) 출고 INSERT
                try (PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
                    ps.setInt(1, itemId);
                    ps.setBigDecimal(2, qty);
                    ps.setString(3, lotUpper);
                    ps.setString(4, locationUpper);
                    ps.setInt(5, createdBy);

                    int r = ps.executeUpdate();
                    if (r != 1) {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();
                return true;

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(oldAuto);
            }
        }
    }

    /* ========================= [LOT 확인 / 가용수량 조회] ========================= */

    /** 특정 LOT이 해당 품목/창고에 존재하는지 확인 */
    public boolean existsLotForItemAtLocation(int itemId, String lotUpper, String locationUpper) throws SQLException {
        final String sql =
            "SELECT 1 FROM INVENTORY_TRANSACTION " +
            " WHERE ITEM_ID=? AND LOT_NO=? AND LOCATION_NAME=? AND ROWNUM=1";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setString(2, lotUpper);
            ps.setString(3, locationUpper);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** 현재 가용 수량 조회 (IN - OUT 합계) */
    public BigDecimal getAvailableQty(int itemId, String lotUpper, String locationUpper) throws SQLException {
        final String sql =
            "SELECT NVL(SUM(CASE WHEN TXN_TYPE='IN'  THEN QUANTITY " +
            "                  WHEN TXN_TYPE='OUT' THEN -QUANTITY END), 0) AS AVAIL " +
            "  FROM INVENTORY_TRANSACTION " +
            " WHERE ITEM_ID=? AND LOT_NO=? AND LOCATION_NAME=?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setString(2, lotUpper);
            ps.setString(3, locationUpper);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BigDecimal v = rs.getBigDecimal(1);
                    return v == null ? BigDecimal.ZERO : v;
                }
                return BigDecimal.ZERO;
            }
        }
    }

    /* ========================= [LOT 목록 조회] ========================= */
    /**
     * 특정 품목 + 창고에 대해 가용 LOT 목록 조회
     */
    public List<Map<String, Object>> selectAvailableLotsMap(int itemId, String locationUpper) throws SQLException {
        final String sql =
            "SELECT lot_no, " +
            "       NVL(SUM(CASE WHEN txn_type='IN'  THEN quantity " +
            "                   WHEN txn_type='OUT' THEN -quantity END), 0) AS avail " +
            "  FROM inventory_transaction " +
            " WHERE item_id=? AND location_name=? " +
            " GROUP BY lot_no " +
            " HAVING NVL(SUM(CASE WHEN txn_type='IN' THEN quantity " +
            "                     WHEN txn_type='OUT' THEN -quantity END), 0) > 0 " +
            " ORDER BY lot_no";

        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setString(2, locationUpper);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("lot_no", rs.getString("lot_no"));
                    BigDecimal avail = rs.getBigDecimal("avail");
                    if (avail == null) avail = BigDecimal.ZERO;
                    m.put("avail", avail);
                    list.add(m);
                }
            }
        }
        return list;
    }

    /* ========================= [재고 현황 조회] ========================= */
    /**
     * 재고현황 조회
     * - locationName, itemId, lotLike 필터
     * - 안전재고 컬럼명: safety_stock
     */
    /** 재고현황 조회 (스키마: EXPIRY_AT 사용, 안전재고 컬럼 없음) */
    /** 
     * 재고현황 조회
     * - qty <= 0 → LOW
     * - EXPIRY_AT <= SYSDATE + 30 → WARN
     * - 그 외 → OK
     */
    public List<Map<String, Object>> getInventoryStatus(String locationName, Integer itemId, String lotLike) throws SQLException {

        // 1) WHERE 조건 생성
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (locationName != null && !locationName.trim().isEmpty()) {
            where.append(" AND UPPER(TRIM(t.location_name)) = ? ");
            params.add(locationName.trim().toUpperCase());
        }
        if (itemId != null && itemId > 0) {
            where.append(" AND t.item_id = ? ");
            params.add(itemId);
        }
        if (lotLike != null && !lotLike.trim().isEmpty()) {
            where.append(" AND t.lot_no LIKE ? ");
            params.add("%" + lotLike.trim() + "%");
        }

        // 2) 집계 및 상태 계산
        String sql =
            "WITH agg AS ( " +
            "  SELECT i.item_name, t.lot_no, t.location_name, " +
            "         SUM(CASE WHEN t.txn_type = 'IN' THEN t.quantity " +
            "                  WHEN t.txn_type = 'OUT' THEN -t.quantity END) AS qty, " +
            "         MIN(t.tx_at)     AS in_date, " +
            "         MAX(t.expiry_at) AS expire_date " +
            "    FROM inventory_transaction t " +
            "    JOIN item_master i ON i.item_id = t.item_id " +
                 where.toString() +
            "   GROUP BY i.item_name, t.lot_no, t.location_name " +
            ") " +
            "SELECT item_name, lot_no, location_name, qty, in_date, expire_date, " +
            "       CASE " +
            "         WHEN qty <= 0 THEN 'LOW' " +
            "         WHEN expire_date IS NOT NULL AND expire_date <= SYSDATE + 30 THEN 'WARN' " +
            "         ELSE 'OK' " +
            "       END AS status " +
            "  FROM agg " +
            " ORDER BY item_name, location_name, lot_no";

        List<Map<String, Object>> list = new ArrayList<>();

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int idx = 1;
            for (Object p : params) {
                if (p instanceof String) {
                    ps.setString(idx++, (String) p);
                } else if (p instanceof Integer) {
                    ps.setInt(idx++, (Integer) p);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("item_name",     rs.getString("item_name"));
                    row.put("lot_no",        rs.getString("lot_no"));
                    row.put("location_name", rs.getString("location_name"));
                    row.put("qty",           rs.getBigDecimal("qty"));

                    // 현재는 DB 시간을 그대로 사용하고 있음
                    row.put("in_date",     toKst(rs.getTimestamp("in_date")));     // +9h
                    row.put("expire_date", toKst(rs.getTimestamp("expire_date"))); // +9h


                    row.put("status",        rs.getString("status"));
                    list.add(row);
                }
            }

        }
        return list;
    }
    /** 기간 + 타입(ALL/IN/OUT)으로 이력 조회 */
 // InventoryDAO.java 안에 추가
    public List<Map<String,Object>> selectHistory(Timestamp startTs, Timestamp endTs, String type) {
        final String sql =
            "SELECT t.TX_AT, t.TXN_TYPE, t.LOT_NO, i.ITEM_NAME, t.QUANTITY, t.LOCATION_NAME " +
            "FROM INVENTORY_TRANSACTION t " +
            "JOIN ITEM_MASTER i ON i.ITEM_ID = t.ITEM_ID " +
            "WHERE t.TX_AT >= ? AND t.TX_AT < ? " +
            "  AND (? = 'ALL' OR t.TXN_TYPE = ?) " +
            "ORDER BY t.TX_AT DESC, t.TRANSACTION_ID DESC";

        List<Map<String,Object>> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, startTs);
            ps.setTimestamp(2, endTs);
            ps.setString(3, type);
            ps.setString(4, type);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> m = new HashMap<>();

                    // 1) DB에서 가져온 UTC 시간
                    Timestamp raw = rs.getTimestamp("TX_AT");

                    // 2) 9시간(9*60*60*1000 ms) 더해서 KST로 보정
                    if (raw != null) {
                        long nineHours = 9L * 60 * 60 * 1000;  // 9시간
                        Timestamp kst = new Timestamp(raw.getTime() + nineHours);
                        m.put("tx_at", kst); // JSP로 넘길 값은 KST 시간
                    } else {
                        m.put("tx_at", null);
                    }

                    // 나머지 값 그대로 넣기
                    m.put("txn_type", rs.getString("TXN_TYPE"));
                    m.put("lot_no", rs.getString("LOT_NO"));
                    m.put("item_name", rs.getString("ITEM_NAME"));
                    m.put("quantity", rs.getBigDecimal("QUANTITY"));
                    m.put("location_name", rs.getString("LOCATION_NAME"));

                    list.add(m);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }




}
