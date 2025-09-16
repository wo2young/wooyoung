package DAO;

import java.sql.*;
import java.util.*;
import yaDTO.CodeDetailDTO;

public class MasterDataCodeDAO extends BaseDAO {
	public int deleteDetail(String detailCode) throws SQLException {
		String sql = "DELETE FROM CODE_DETAIL WHERE DETAIL_CODE = ?";
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, detailCode);
			return ps.executeUpdate();
		}
	}

	public List<CodeDetailDTO> listDetailsByCodeId(String codeId) throws SQLException {
		String sql = "SELECT DETAIL_CODE, CODE_ID, CODE_DNAME, IS_ACTIVE "
				+ "FROM CODE_DETAIL WHERE CODE_ID = ? ORDER BY DETAIL_CODE";
		List<CodeDetailDTO> list = new ArrayList<>();
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, codeId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					CodeDetailDTO d = new CodeDetailDTO();
					d.setDetail_code(rs.getString("DETAIL_CODE"));
					d.setCode_id(rs.getString("CODE_ID"));
					d.setCode_Dname(rs.getString("CODE_DNAME"));
					d.setIs_active(rs.getString("IS_ACTIVE")); // ← 추가
					list.add(d);
				}
			}
		}
		return list;
	}

	// 자동생성: DETAIL_CODE 없이 INSERT
	public boolean insertDetail(String codeId, String codeDname) throws SQLException {
		String sql = "INSERT INTO CODE_DETAIL (CODE_ID, CODE_DNAME) VALUES (?, ?)";
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, codeId); // 'PCD' or 'FCD'
			ps.setString(2, codeDname);
			return ps.executeUpdate() == 1;
		}
	}

	public boolean updateDetailName(String detailCode, String newName) throws SQLException {
		String sql = "UPDATE CODE_DETAIL SET CODE_DNAME=? WHERE DETAIL_CODE=?";
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, newName);
			ps.setString(2, detailCode);
			return ps.executeUpdate() == 1;
		}
	}

	public boolean setDetailActive(String detailCode, boolean active) throws SQLException {
		String sql = "UPDATE CODE_DETAIL SET IS_ACTIVE=? WHERE DETAIL_CODE=?";
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, active ? "Y" : "N");
			ps.setString(2, detailCode);
			return ps.executeUpdate() == 1;
		}
	}
}