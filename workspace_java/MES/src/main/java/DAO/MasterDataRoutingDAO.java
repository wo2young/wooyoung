package DAO;

import java.sql.*;
import java.util.*;
import yaDTO.RoutingDTO;
import yaDTO.RoutingViewDTO;

public class MasterDataRoutingDAO extends BaseDAO {
	
	public List<RoutingViewDTO> listAllView() throws SQLException {
	    String sql = "SELECT r.ROUTING_ID, r.ITEM_ID, i.ITEM_NAME, r.PROCESS_STEP, r.IMG_PATH, r.WORKSTATION " +
	                 "FROM ROUTING r JOIN ITEM_MASTER i ON r.ITEM_ID=i.ITEM_ID " +
	                 "ORDER BY r.ITEM_ID, r.PROCESS_STEP";
	    List<RoutingViewDTO> list = new ArrayList<>();
	    try (Connection con = getConn();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            RoutingViewDTO v = new RoutingViewDTO();
	            v.setRoutingId(rs.getInt("ROUTING_ID"));
	            v.setItemId(rs.getInt("ITEM_ID"));
	            v.setItemName(rs.getString("ITEM_NAME"));
	            v.setProcessStep(rs.getInt("PROCESS_STEP"));
	            v.setImgPath(rs.getString("IMG_PATH"));
	            v.setWorkstation(rs.getString("WORKSTATION"));
	            list.add(v);
	        }
	    }
	    return list;
	}


	public List<RoutingViewDTO> listViewByItem(int itemId) throws SQLException {
	    String sql = "SELECT r.ROUTING_ID, r.ITEM_ID, i.ITEM_NAME, r.PROCESS_STEP, r.IMG_PATH, r.WORKSTATION " +
	                 "FROM ROUTING r JOIN ITEM_MASTER i ON r.ITEM_ID=i.ITEM_ID " +
	                 "WHERE r.ITEM_ID=? ORDER BY r.PROCESS_STEP";
	    List<RoutingViewDTO> list = new ArrayList<>();
	    try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, itemId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                RoutingViewDTO v = new RoutingViewDTO();
	                v.setRoutingId(rs.getInt("ROUTING_ID"));
	                v.setItemId(rs.getInt("ITEM_ID"));
	                v.setItemName(rs.getString("ITEM_NAME"));
	                v.setProcessStep(rs.getInt("PROCESS_STEP"));
	                v.setImgPath(rs.getString("IMG_PATH"));
	                v.setWorkstation(rs.getString("WORKSTATION"));
	                list.add(v);
	            }
	        }
	    }
	    return list;
	}

	public boolean insert(RoutingDTO d) throws SQLException {
		String sql = "INSERT INTO ROUTING (ITEM_ID, PROCESS_STEP, IMG_PATH, WORKSTATION) "
				+ "VALUES (?, ?, ?, ?)";
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, d.getItem_id());
			ps.setInt(2, d.getProcess_step());
			ps.setString(3, d.getImg_path());
			ps.setString(4, d.getWorkstation());
			return ps.executeUpdate() == 1;
		}
	}

	public boolean update(RoutingDTO d) throws SQLException {
		String sql = "UPDATE ROUTING SET ITEM_ID=?, PROCESS_STEP=?, IMG_PATH=?, WORKSTATION=? WHERE ROUTING_ID=?";
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, d.getItem_id());
			ps.setInt(2, d.getProcess_step());
			ps.setString(3, d.getImg_path());
			ps.setString(4, d.getWorkstation());
			ps.setInt(5, d.getRouting_id());
			return ps.executeUpdate() == 1;
		}
	}

	public boolean delete(int routingId) {
	    String sql = "DELETE FROM ROUTING WHERE ROUTING_ID=?";
	    try (Connection con = getConn();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, routingId);
	        return ps.executeUpdate() == 1;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public Optional<RoutingDTO> find(int routingId) throws SQLException {
	    String sql = "SELECT ROUTING_ID, ITEM_ID, PROCESS_STEP, IMG_PATH, WORKSTATION " +
	                 "FROM ROUTING WHERE ROUTING_ID=?";
	    try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, routingId);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                RoutingDTO d = new RoutingDTO();
	                d.setRouting_id(rs.getInt("ROUTING_ID"));
	                d.setItem_id(rs.getInt("ITEM_ID"));
	                d.setProcess_step(rs.getInt("PROCESS_STEP"));
	                d.setImg_path(rs.getString("IMG_PATH"));
	                d.setWorkstation(rs.getString("WORKSTATION"));
	                return Optional.of(d);
	            }
	        }
	    }
	    return Optional.empty();
	}
}
