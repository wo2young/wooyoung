package DAO;

import yaDTO.ItemMasterDTO;
import yaDTO.ProductionOrderDTO;

import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.sql.DataSource;

public class ProductionOrderDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    private Connection getConnection() throws Exception {
        Context init = new InitialContext();
        DataSource ds = (DataSource) init.lookup("java:/comp/env/jdbc/oracle");
        return ds.getConnection();
    }

    private void close() {
        try { if(rs!=null) rs.close(); } catch(Exception e) {}
        try { if(ps!=null) ps.close(); } catch(Exception e) {}
        try { if(conn!=null) conn.close(); } catch(Exception e) {}
    }

    public List<ProductionOrderDTO> listOrders(int itemId, String status) {
        List<ProductionOrderDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.order_id, o.target_id, o.quantity, o.due_date, ");
        sql.append("o.status, o.created_at, u.name AS created_by_name, ");
        sql.append("t.target_quantity, i.item_name AS target_name ");
        sql.append("FROM production_order o ");
        sql.append("JOIN production_target t ON o.target_id = t.target_id ");
        sql.append("JOIN item_master i ON t.item_id = i.item_id ");
        sql.append("JOIN user_t u ON o.created_by = u.user_id ");
        sql.append("WHERE 1=1 ");
        if (itemId > 0) sql.append("AND i.item_id = ? ");
        if (status != null && !"ALL".equalsIgnoreCase(status)) {
            sql.append("AND o.status = ? ");
        }
        sql.append("ORDER BY o.order_id DESC");

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (itemId > 0) ps.setInt(idx++, itemId);
            if (status != null && !"ALL".equalsIgnoreCase(status)) {
                ps.setString(idx++, status);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductionOrderDTO dto = new ProductionOrderDTO();
                dto.setOrder_id(rs.getInt("order_id"));
                dto.setTarget_id(rs.getInt("target_id"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setDue_date(rs.getDate("due_date"));
                dto.setStatus(rs.getString("status"));
                dto.setCreated_at(rs.getTimestamp("created_at"));
                dto.setCreated_by_name(rs.getString("created_by_name"));
                dto.setTarget_quantity(rs.getInt("target_quantity"));
                dto.setTarget_name(rs.getString("target_name"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(); }
        return list;
    }

    public int insertOrder(ProductionOrderDTO dto) {
        int r = 0;
        String sql =
            "INSERT INTO production_order " +
            " (order_id, target_id, quantity, due_date, created_at, created_by, status) " +
            "VALUES (SEQ_PROD_ORDER.NEXTVAL, ?, ?, ?, SYSDATE, ?, 'PLANNED')";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getTarget_id());
            ps.setInt(2, dto.getQuantity());
            ps.setDate(3, dto.getDue_date());
            ps.setInt(4, dto.getCreated_by());
            r = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(); }
        return r;
    }

    public int updateOrder(ProductionOrderDTO dto) {
        int r = 0;
        String sql = "UPDATE production_order SET quantity=?, due_date=? WHERE order_id=?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getQuantity());
            ps.setDate(2, dto.getDue_date());
            ps.setInt(3, dto.getOrder_id());
            r = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(); }
        return r;
    }

    public int deleteOrder(int orderId) {
        int r = 0;
        String sql = "DELETE FROM production_order WHERE order_id=?";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            r = ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(); }
        return r;
    }

    public List<ItemMasterDTO> getDistinctTargetItems() {
        List<ItemMasterDTO> list = new ArrayList<>();
        String sql = "SELECT DISTINCT i.item_id, i.item_name " +
                     "FROM production_target t " +
                     "JOIN item_master i ON t.item_id = i.item_id " +
                     "WHERE i.item_kind = 'FG' " +
                     "ORDER BY i.item_name";
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItemMasterDTO dto = new ItemMasterDTO();
                dto.setItem_id(rs.getInt("item_id"));
                dto.setItem_name(rs.getString("item_name"));
                list.add(dto);
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally { close(); }
        return list;
    }
}
