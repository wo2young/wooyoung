package DAO;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class ItemMasterDAO {
    private DataSource ds;

    public ItemMasterDAO() {
        try {
            Context ctx = new InitialContext();
            // ✅ 표준 JNDI로 교정 (2회 lookup 유지)
            DataSource dataFactory  = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            DataSource dataFactory2 = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            ds = dataFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* ===== 공통 유틸 ===== */
    private Map<String, Object> rowToItem(ResultSet rs) throws SQLException {
        Map<String, Object> m = new HashMap<>();
        m.put("item_id",   rs.getInt("ITEM_ID"));
        m.put("item_name", rs.getString("ITEM_NAME"));
        return m;
    }

    /* ===== 조회 메서드 ===== */
    /** ✅ 전체 품목 (키: item_id, item_name) — RM/FG 전부 */
    public List<Map<String, Object>> getAllItems() {
        List<Map<String, Object>> list = new ArrayList<>();
        final String sql = "SELECT ITEM_ID, ITEM_NAME FROM ITEM_MASTER ORDER BY ITEM_NAME";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rowToItem(rs));
        } catch (SQLException e) {
            throw new RuntimeException("품목 조회 실패: " + e.getMessage(), e);
        }
        return list;
    }

    /** 🔁 (선택) JSP에서 id/name 키를 기대할 때 바로 쓰는 버전 */
    public List<Map<String, Object>> getAllItemsIdName() {
        List<Map<String, Object>> list = new ArrayList<>();
        final String sql = "SELECT ITEM_ID, ITEM_NAME FROM ITEM_MASTER ORDER BY ITEM_NAME";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> m = new HashMap<>();
                m.put("id",   rs.getInt("ITEM_ID"));
                m.put("name", rs.getString("ITEM_NAME"));
                list.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException("품목 조회 실패: " + e.getMessage(), e);
        }
        return list;
    }

    /** 특정 item_id의 KIND(FG/RM 등) 조회 */
    public String findKindById(int itemId) throws SQLException {
        final String sql = "SELECT ITEM_KIND FROM ITEM_MASTER WHERE ITEM_ID = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        }
    }

    /** 입고 화면: FG 제외 (데이터 오염 대비 TRIM/UPPER/COALESCE 처리) */
    public List<Map<String, Object>> getItemsExcludingFG() {
        List<Map<String, Object>> list = new ArrayList<>();
        final String sql =
            "SELECT ITEM_ID, ITEM_NAME " +
            "  FROM ITEM_MASTER " +
            " WHERE UPPER(TRIM(COALESCE(ITEM_KIND, ''))) <> 'FG' " +
            " ORDER BY ITEM_NAME";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rowToItem(rs));
        } catch (SQLException e) {
            throw new RuntimeException("FG 제외 품목 조회 실패: " + e.getMessage(), e);
        }
        return list;
    }

    /** 출고 화면: FG만 */
    public List<Map<String, Object>> getItemsOnlyFG() {
        List<Map<String, Object>> list = new ArrayList<>();
        final String sql =
            "SELECT ITEM_ID, ITEM_NAME " +
            "  FROM ITEM_MASTER " +
            " WHERE UPPER(TRIM(COALESCE(ITEM_KIND, ''))) = 'FG' " +
            " ORDER BY ITEM_NAME";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rowToItem(rs));
        } catch (SQLException e) {
            throw new RuntimeException("FG 전용 품목 조회 실패: " + e.getMessage(), e);
        }
        return list;
    }

    /** 입고 화면: RM만 */
    public List<Map<String, Object>> getItemsOnlyRM() {
        List<Map<String, Object>> list = new ArrayList<>();
        final String sql =
            "SELECT ITEM_ID, ITEM_NAME " +
            "  FROM ITEM_MASTER " +
            " WHERE UPPER(TRIM(COALESCE(ITEM_KIND, ''))) = 'RM' " +
            " ORDER BY ITEM_NAME";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rowToItem(rs));
        } catch (SQLException e) {
            throw new RuntimeException("RM 전용 품목 조회 실패: " + e.getMessage(), e);
        }
        return list;
    }
}