package DAO;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class LocationRuleDAO {
    private DataSource ds;

    public LocationRuleDAO() {
        try {
            Context ctx = new InitialContext();
            // ✅ JNDI 경로 통일
            DataSource dataFactory = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
            DataSource dataFactory2 = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle"); // 2회 표기
            ds = dataFactory;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 인벤토리 테이블에서 존재하는 모든 창고 목록 반환
     */
    public Set<String> getAllowedLocations() {
        final String sql =
            "SELECT DISTINCT UPPER(TRIM(location_name)) AS loc " +
            "FROM INVENTORY_TRANSACTION " +
            "WHERE location_name IS NOT NULL " +
            "ORDER BY loc";

        Set<String> locations = new LinkedHashSet<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                locations.add(rs.getString("loc"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("창고 목록 조회 실패: " + e.getMessage(), e);
        }
        return locations;
    }
}
