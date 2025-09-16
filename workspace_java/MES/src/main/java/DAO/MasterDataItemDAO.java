package DAO;

import java.sql.*;
import java.util.*;
import yaDTO.ItemMasterDTO;

public class MasterDataItemDAO extends BaseDAO {

	public List<ItemMasterDTO> listByKind(String kind) throws SQLException {
		// 컬럼을 전부 명시해서 가져옴 (DTO에 맞춤)
		final String SQL_ALL = "SELECT ITEM_ID, ITEM_NAME, LOT_CODE, ITEM_KIND, "
				+ "       UNIT, DETAIL_CODE, SHELF_LIFE_DAYS, SPECIFICATION " + "  FROM ITEM_MASTER ORDER BY ITEM_NAME";

		final String SQL_BY_KIND = "SELECT ITEM_ID, ITEM_NAME, LOT_CODE, ITEM_KIND, "
				+ "       UNIT, DETAIL_CODE, SHELF_LIFE_DAYS, SPECIFICATION "
				+ "  FROM ITEM_MASTER WHERE UPPER(ITEM_KIND)=UPPER(?) ORDER BY ITEM_NAME";

		List<ItemMasterDTO> out = new ArrayList<>();

		try (Connection con = getConn();
				PreparedStatement ps = con.prepareStatement("ALL".equalsIgnoreCase(kind) ? SQL_ALL : SQL_BY_KIND)) {

			if (!"ALL".equalsIgnoreCase(kind)) {
				ps.setString(1, kind); // "FG" 또는 "RM"
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ItemMasterDTO d = new ItemMasterDTO();
					d.setItem_id(rs.getInt("ITEM_ID"));
					d.setItem_name(rs.getString("ITEM_NAME"));
					d.setLot_code(rs.getString("LOT_CODE"));
					d.setKind(rs.getString("ITEM_KIND"));
					d.setUnit(rs.getString("UNIT"));
					d.setDetail_code(rs.getString("DETAIL_CODE"));
					d.setSelf_life_day(rs.getInt("SHELF_LIFE_DAYS"));
					d.setItem_spec(rs.getString("SPECIFICATION")); // ← DB가 ITEM_SPEC이면 "ITEM_SPEC"로 변경

					// 화면 라벨(예: 콜라 500ml [1001])
					d.setUi_label(d.getItem_name() + " [" + d.getItem_id() + "]");

					out.add(d);
				}
			}
		}
		return out;
	}

	// TODO insert
	public boolean insert(ItemMasterDTO d) throws SQLException {
	    final String SQL =
	        "INSERT INTO ITEM_MASTER ("
	      + " ITEM_ID, ITEM_NAME, LOT_CODE, SHELF_LIFE_DAYS, "
	      + " ITEM_KIND, DETAIL_CODE, SPECIFICATION, UNIT "
	      + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection con = getConn();
	         PreparedStatement ps = con.prepareStatement(SQL)) {

	        ps.setInt(1, d.getItem_id());
	        ps.setString(2, d.getItem_name());
	        ps.setString(3, d.getLot_code());
	        ps.setInt(4, d.getSelf_life_day());
	        ps.setString(5, d.getKind());

	        if (d.getDetail_code() == null || d.getDetail_code().isBlank())
	            ps.setNull(6, Types.VARCHAR);
	        else
	            ps.setString(6, d.getDetail_code());

	        if (d.getItem_spec() == null || d.getItem_spec().isBlank())
	            ps.setNull(7, Types.VARCHAR);
	        else
	            ps.setString(7, d.getItem_spec());

	        if (d.getUnit() == null || d.getUnit().isBlank())
	            ps.setNull(8, Types.VARCHAR);
	        else
	            ps.setString(8, d.getUnit());

	        return ps.executeUpdate() == 1;
	    }
	}


	// TODO update
	public boolean update(ItemMasterDTO d) throws SQLException {
		String sql = "UPDATE ITEM_MASTER SET " + " ITEM_NAME=?, " + " LOT_CODE=?, " + " SHELF_LIFE_DAYS=?, "
				+ " ITEM_KIND=?, " + " DETAIL_CODE=?, " + " SPECIFICATION=?, " + " UNIT=? " + "WHERE ITEM_ID=?";

		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, d.getItem_name());
			ps.setString(2, d.getLot_code());
			ps.setInt(3, d.getSelf_life_day());
			ps.setString(4, d.getKind());

			if (d.getDetail_code() == null || d.getDetail_code().isBlank())
				ps.setNull(5, Types.VARCHAR);
			else
				ps.setString(5, d.getDetail_code());

			if (d.getItem_spec() == null || d.getItem_spec().isBlank())
				ps.setNull(6, Types.VARCHAR);
			else
				ps.setString(6, d.getItem_spec());

			if (d.getUnit() == null || d.getUnit().isBlank())
				ps.setNull(7, Types.VARCHAR);
			else
				ps.setString(7, d.getUnit());

			ps.setInt(8, d.getItem_id());

			return ps.executeUpdate() == 1;
		}
	}// TODO delete

	public boolean delete(int itemId) {
	    String sql = "DELETE FROM ITEM_MASTER WHERE ITEM_ID=?";
	    try (Connection con = getConn();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, itemId);
	        return ps.executeUpdate() == 1;
	    } catch (SQLIntegrityConstraintViolationException e) {
	        // FK 제약(ORA-02292) 걸렸을 때
	        System.out.println(">>> 삭제 불가: BOM 참조 존재");
	        return false;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	// TODO 단건 조회
	public Optional<ItemMasterDTO> find(int itemId) throws SQLException {
		String sql = "SELECT ITEM_ID, ITEM_NAME, LOT_CODE, SHELF_LIFE_DAYS, "
				+ "ITEM_KIND, DETAIL_CODE, SPECIFICATION, UNIT " + "FROM ITEM_MASTER WHERE ITEM_ID=?";

		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, itemId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					ItemMasterDTO d = new ItemMasterDTO();
					d.setItem_id(rs.getInt("ITEM_ID"));
					d.setItem_name(rs.getString("ITEM_NAME"));
					d.setLot_code(rs.getString("LOT_CODE"));
					d.setSelf_life_day(rs.getInt("SHELF_LIFE_DAYS"));
					d.setKind(rs.getString("ITEM_KIND"));
					d.setDetail_code(rs.getString("DETAIL_CODE"));
					d.setItem_spec(rs.getString("SPECIFICATION"));
					d.setUnit(rs.getString("UNIT"));

					// 화면 표시용 라벨
					d.setUi_label(d.getItem_name() + " [" + d.getItem_id() + "]");

					return Optional.of(d);
				}
			}
		}
		return Optional.empty();
	}

	// TODO 목록
	public List<ItemMasterDTO> list(String keyword, int offset, int limit) throws SQLException {
		String base = "SELECT ITEM_ID, ITEM_NAME, LOT_CODE, SHELF_LIFE_DAYS, "
				+ "ITEM_KIND, DETAIL_CODE, SPECIFICATION, UNIT " + "FROM ITEM_MASTER ";
		String where = (keyword == null || keyword.isBlank())
			    ? ""
			    : " WHERE UPPER(ITEM_NAME) LIKE UPPER(?) OR UPPER(LOT_CODE) LIKE UPPER(?) ";
		String ordered = base + where + " ORDER BY ITEM_ID DESC";

		String paged = "SELECT * FROM (" + "  SELECT t.*, ROWNUM rnum FROM (" + ordered + ") t " + "  WHERE ROWNUM <= ?"
				+ ") WHERE rnum > ?";

		List<ItemMasterDTO> out = new ArrayList<>();
		try (Connection con = getConn(); PreparedStatement ps = con.prepareStatement(paged)) {

			int idx = 1;
			if (!where.isEmpty()) {
				String kw = "%" + keyword + "%";
				ps.setString(idx++, kw);
				ps.setString(idx++, kw);
			}
			ps.setInt(idx++, offset + limit);
			ps.setInt(idx, offset);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					ItemMasterDTO d = new ItemMasterDTO();
					d.setItem_id(rs.getInt("ITEM_ID"));
					d.setItem_name(rs.getString("ITEM_NAME"));
					d.setLot_code(rs.getString("LOT_CODE"));
					d.setSelf_life_day(rs.getInt("SHELF_LIFE_DAYS"));
					d.setKind(rs.getString("ITEM_KIND"));
					d.setDetail_code(rs.getString("DETAIL_CODE"));
					d.setItem_spec(rs.getString("SPECIFICATION"));
					d.setUnit(rs.getString("UNIT"));

					StringBuilder label = new StringBuilder();
					label.append(d.getItem_id()).append(" - ").append(d.getItem_name());
					if (d.getLot_code() != null && !d.getLot_code().isBlank()) {
						label.append(" (").append(d.getLot_code());
						if ("RM".equalsIgnoreCase(d.getKind()) && d.getUnit() != null && !d.getUnit().isBlank()) {
							label.append(", ").append(d.getUnit());
						}
						label.append(")");
					}
					d.setUi_label(label.toString());

					out.add(d);
				}
			}
		}
		return out;
	}

	
}
