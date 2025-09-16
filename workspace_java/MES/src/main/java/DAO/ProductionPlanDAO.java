package DAO;

import yaDTO.ProductionTargetDTO;
import yaDTO.ItemMasterDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProductionPlanDAO {

    private DataSource ds;

    public ProductionPlanDAO() {
        try {
            Context init = new InitialContext();
            ds = (DataSource) init.lookup("java:comp/env/jdbc/oracle");
        } catch (Exception e) {
            throw new RuntimeException("❌ DataSource lookup 실패", e);
        }
    }

    // ✅ 등록
    public boolean insertTarget(ProductionTargetDTO dto) {
        String sql = "INSERT INTO production_target "
                   + "(target_id, item_id, period_start, period_end, target_quantity, created_at, created_by) "
                   + "VALUES (seq_target.NEXTVAL, ?, ?, ?, ?, SYSDATE, ?)";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dto.getItem_id());
            ps.setDate(2, dto.getPeriod_start());
            ps.setDate(3, dto.getPeriod_end());
            ps.setInt(4, dto.getTarget_quantity());
            ps.setInt(5, dto.getCreated_by());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ 특정 음료 조회
    public List<ItemMasterDTO> getDrinkItems() {
        List<ItemMasterDTO> list = new ArrayList<>();
        String sql = "SELECT item_id, item_name FROM item_master "
                   + "WHERE item_id IN (1001,1002,1003) ORDER BY item_id";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ItemMasterDTO dto = new ItemMasterDTO();
                dto.setItem_id(rs.getInt("item_id"));
                dto.setItem_name(rs.getString("item_name"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ 전체 조회
    public List<ProductionTargetDTO> getTargetList() {
        List<ProductionTargetDTO> list = new ArrayList<>();
        String sql = "SELECT t.target_id, t.item_id, i.item_name, t.period_start, t.period_end, "
                   + "t.target_quantity, u.login_id AS created_by_name, u.user_role AS created_by_role "
                   + "FROM production_target t "
                   + "JOIN user_t u ON t.created_by = u.user_id "
                   + "JOIN item_master i ON t.item_id = i.item_id "
                   + "ORDER BY t.target_id DESC";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapTargetDTO(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }



    // ✅ 날짜 검색
    public List<ProductionTargetDTO> getTargetList(Date startDate, Date endDate) {
        List<ProductionTargetDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t.target_id, t.item_id, t.period_start, t.period_end, ");
        sql.append("t.target_quantity, u.login_id AS created_by_name, u.user_role AS created_by_role ");
        sql.append("FROM production_target t ");
        sql.append("JOIN user_t u ON t.created_by = u.user_id ");
        sql.append("WHERE 1=1 ");
        if (startDate != null) sql.append("AND t.period_start >= ? ");
        if (endDate != null) sql.append("AND t.period_end <= ? ");
        sql.append("ORDER BY t.target_id DESC");

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (startDate != null) ps.setDate(idx++, startDate);
            if (endDate != null) ps.setDate(idx++, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapTargetDTO(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ 페이징 전체 조회
    public List<ProductionTargetDTO> getTargetListPaged(int page, int pageSize) {
        List<ProductionTargetDTO> list = new ArrayList<>();
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        String sql = "SELECT * FROM ( " +
                     " SELECT a.*, ROWNUM rnum FROM ( " +
                     "   SELECT t.target_id, t.item_id, i.item_name, t.period_start, t.period_end, " +
                     "          t.target_quantity, u.login_id AS created_by_name, u.user_role AS created_by_role " +
                     "   FROM production_target t " +
                     "   JOIN user_t u ON t.created_by = u.user_id " +
                     "   JOIN item_master i ON t.item_id = i.item_id " +
                     "   ORDER BY t.target_id DESC " +
                     " ) a WHERE ROWNUM <= ? ) WHERE rnum >= ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, endRow);
            ps.setInt(2, startRow);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapTargetDTO(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ 페이징 + 날짜검색
    public List<ProductionTargetDTO> getTargetListPaged(Date startDate, Date endDate, int page, int pageSize) {
        List<ProductionTargetDTO> list = new ArrayList<>();
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ( ");
        sql.append(" SELECT a.*, ROWNUM rnum FROM ( ");
        sql.append("   SELECT t.target_id, t.item_id, i.item_name, t.period_start, t.period_end, t.target_quantity, ");
        sql.append("          u.login_id AS created_by_name, u.user_role AS created_by_role ");
        sql.append("   FROM production_target t ");
        sql.append("   JOIN user_t u ON t.created_by = u.user_id ");
        sql.append("   JOIN item_master i ON t.item_id = i.item_id ");
        sql.append("   WHERE 1=1 ");
        if (startDate != null) sql.append(" AND t.period_start >= ? ");
        if (endDate != null) sql.append(" AND t.period_end <= ? ");
        sql.append("   ORDER BY t.target_id DESC ");
        sql.append(" ) a WHERE ROWNUM <= ? ) WHERE rnum >= ?");

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (startDate != null) ps.setDate(idx++, startDate);
            if (endDate != null) ps.setDate(idx++, endDate);
            ps.setInt(idx++, endRow);
            ps.setInt(idx, startRow);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapTargetDTO(rs));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // ✅ 전체 개수
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM production_target";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ✅ 날짜검색 개수
    public int getTotalCount(Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM production_target t WHERE 1=1 ");
        if (startDate != null) sql.append(" AND t.period_start >= ? ");
        if (endDate != null) sql.append(" AND t.period_end <= ? ");
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (startDate != null) ps.setDate(idx++, startDate);
            if (endDate != null) ps.setDate(idx++, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ✅ item_id → item_name 매핑
    public Map<Integer, String> getItemMap() {
        Map<Integer, String> map = new HashMap<>();
        String sql = "SELECT item_id, item_name FROM item_master";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) map.put(rs.getInt("item_id"), rs.getString("item_name"));
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }

    // ✅ 단건 조회
    public ProductionTargetDTO getTargetById(int targetId) {
        String sql = "SELECT t.target_id, t.item_id, i.item_name, t.period_start, t.period_end, t.target_quantity, "
                   + "u.login_id AS created_by_name, u.user_role AS created_by_role "
                   + "FROM production_target t "
                   + "JOIN user_t u ON t.created_by = u.user_id "
                   + "JOIN item_master i ON t.item_id = i.item_id "
                   + "WHERE t.target_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, targetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapTargetDTO(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }



    // ✅ target_id 기준 생산지시 합계
    public int getOrdersSumByTarget(int targetId) {
        String sql = "SELECT NVL(SUM(quantity),0) FROM production_order WHERE target_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, targetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ✅ target_id 기준 양품 합계
    public int getGoodQtySumByTarget(int targetId) {
        String sql = "SELECT NVL(SUM(r.good_qty),0) "
                   + "FROM production_result r "
                   + "JOIN production_order o ON r.order_id = o.order_id "
                   + "WHERE o.target_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, targetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ✅ 수정
    public boolean updateTarget(ProductionTargetDTO dto) {
        String sql = "UPDATE production_target "
                   + "SET item_id=?, period_start=?, period_end=?, target_quantity=? "
                   + "WHERE target_id=?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dto.getItem_id());
            ps.setDate(2, dto.getPeriod_start());
            ps.setDate(3, dto.getPeriod_end());
            ps.setInt(4, dto.getTarget_quantity());
            ps.setInt(5, dto.getTarget_id());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // ✅ 삭제 (production_order에 target_id 있으면 삭제 불가)
    public boolean deleteTarget(int targetId) {
        String checkSql = "SELECT COUNT(*) FROM production_order WHERE target_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setInt(1, targetId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // 지시가 존재하면 삭제 불가
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String deleteSql = "DELETE FROM production_target WHERE target_id = ?";
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteSql)) {
            ps.setInt(1, targetId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ DTO 매핑
    private ProductionTargetDTO mapTargetDTO(ResultSet rs) throws SQLException {
        ProductionTargetDTO dto = new ProductionTargetDTO();
        dto.setTarget_id(rs.getInt("target_id"));
        dto.setItem_id(rs.getInt("item_id"));
        dto.setItem_name(rs.getString("item_name"));   // ✅ 여기서 안전하게 가져옴
        dto.setPeriod_start(rs.getDate("period_start"));
        dto.setPeriod_end(rs.getDate("period_end"));
        dto.setTarget_quantity(rs.getInt("target_quantity"));
        dto.setCreated_by_name(rs.getString("created_by_name"));
        dto.setCreated_by_role(rs.getString("created_by_role"));

        // 상태 계산
        int ordersSum = getOrdersSumByTarget(dto.getTarget_id());
        dto.setOrders_sum(ordersSum);

        int goodSum = getGoodQtySumByTarget(dto.getTarget_id());
        dto.setResults_sum(goodSum);

        if (goodSum >= dto.getTarget_quantity()) {
            dto.setStatus("완료");
        } else if (ordersSum > 0) {
            dto.setStatus("진행중");
        } else {
            dto.setStatus("대기");
        }

        return dto;
    }


}
