package DAO;

import java.sql.*;
import java.util.*;
import yaDTO.BomDTO;
import yaDTO.BomViewDTO;

public class MasterDataBomDAO extends BaseDAO {

    // TODO 목록 이름 LOT 단위
    public List<BomViewDTO> listView() throws SQLException {
        String sql =
            "SELECT b.BOM_ID, " +
            "       b.PARENT_ITEM_ID, p.ITEM_NAME AS PARENT_NAME, p.LOT_CODE AS PARENT_LOT, " +
            "       b.CHILD_ITEM_ID,  c.ITEM_NAME AS CHILD_NAME,  c.LOT_CODE AS CHILD_LOT, " +
            "       b.QUANTITY, c.UNIT AS CHILD_UNIT " +
            "  FROM BOM b " +
            "  JOIN ITEM_MASTER p ON p.ITEM_ID = b.PARENT_ITEM_ID " +
            "  JOIN ITEM_MASTER c ON c.ITEM_ID = b.CHILD_ITEM_ID " +
            " ORDER BY b.BOM_ID";
        List<BomViewDTO> out = new ArrayList<>();
        try (Connection con = getConn();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BomViewDTO v = new BomViewDTO();
                v.setBomId(rs.getInt("BOM_ID"));
                v.setParentItemId(rs.getInt("PARENT_ITEM_ID"));
                v.setParentName(rs.getString("PARENT_NAME"));
                v.setParentLot(rs.getString("PARENT_LOT"));
                v.setChildItemId(rs.getInt("CHILD_ITEM_ID"));
                v.setChildName(rs.getString("CHILD_NAME"));
                v.setChildLot(rs.getString("CHILD_LOT"));
                v.setQuantity(rs.getDouble("QUANTITY"));
                v.setChildUnit(rs.getString("CHILD_UNIT"));
                out.add(v);
            }
        }
        return out;
    }

    // TODO 단건 조회(수정용)
    public BomDTO findById(int bomId) throws SQLException {
        String sql = "SELECT BOM_ID, PARENT_ITEM_ID, CHILD_ITEM_ID, QUANTITY FROM BOM WHERE BOM_ID = ?";
        try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bomId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    BomDTO d = new BomDTO();
                    d.setBom_id(rs.getInt("BOM_ID"));
                    d.setParent_item_id(rs.getInt("PARENT_ITEM_ID"));
                    d.setChild_item_id(rs.getInt("CHILD_ITEM_ID"));
                    d.setQuantity(rs.getDouble("QUANTITY"));
                    return d;
                }
            }
        }
        return null;
    }
    //TODO insert
    public boolean insert(BomDTO d) throws SQLException {
        String sql = "INSERT INTO BOM (BOM_ID, PARENT_ITEM_ID, CHILD_ITEM_ID, QUANTITY) VALUES (?, ?, ?, ?)";
        try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getBom_id());
            ps.setInt(2, d.getParent_item_id());
            ps.setInt(3, d.getChild_item_id());
            ps.setDouble(4, d.getQuantity());
            return ps.executeUpdate() == 1;
        }
    }
    //TODO 업데이트
    public boolean update(BomDTO d) throws SQLException {
        String sql = "UPDATE BOM SET PARENT_ITEM_ID = ?, CHILD_ITEM_ID = ?, QUANTITY = ? WHERE BOM_ID = ?";
        try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getParent_item_id());
            ps.setInt(2, d.getChild_item_id());
            ps.setDouble(3, d.getQuantity());
            ps.setInt(4, d.getBom_id());
            return ps.executeUpdate() == 1;
        }
    }
    //TODO 삭제
    public boolean delete(int bomId) throws SQLException {
        String sql = "DELETE FROM BOM WHERE BOM_ID = ?";
        try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bomId);
            return ps.executeUpdate() == 1;
        }
    }
    
    //TODO 모달창 구현
    public List<BomViewDTO> listViewByParent(int parentItemId) throws SQLException {
        String sql =
            "SELECT b.BOM_ID, " +
            "       b.PARENT_ITEM_ID, p.ITEM_NAME AS PARENT_NAME, p.LOT_CODE AS PARENT_LOT, " +
            "       b.CHILD_ITEM_ID,  c.ITEM_NAME AS CHILD_NAME,  c.LOT_CODE AS CHILD_LOT, " +
            "       b.QUANTITY, c.UNIT AS CHILD_UNIT " +
            "  FROM BOM b " +
            "  JOIN ITEM_MASTER p ON p.ITEM_ID = b.PARENT_ITEM_ID " +
            "  JOIN ITEM_MASTER c ON c.ITEM_ID = b.CHILD_ITEM_ID " +
            " WHERE b.PARENT_ITEM_ID=? " +
            " ORDER BY b.BOM_ID";

        List<BomViewDTO> out = new ArrayList<>();
        try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, parentItemId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BomViewDTO v = new BomViewDTO();
                    v.setBomId(rs.getInt("BOM_ID"));
                    v.setParentItemId(rs.getInt("PARENT_ITEM_ID"));
                    v.setParentName(rs.getString("PARENT_NAME"));
                    v.setParentLot(rs.getString("PARENT_LOT"));
                    v.setChildItemId(rs.getInt("CHILD_ITEM_ID"));
                    v.setChildName(rs.getString("CHILD_NAME"));
                    v.setChildLot(rs.getString("CHILD_LOT"));
                    v.setQuantity(rs.getDouble("QUANTITY"));
                    v.setChildUnit(rs.getString("CHILD_UNIT"));
                    out.add(v);
                }
            }
        }
        return out;
    }
    
}
