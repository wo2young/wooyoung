package Service;

import DAO.MasterDataItemDAO;
import yaDTO.ItemMasterDTO;
import java.util.List;

public class MasterDataItemService {
	private final MasterDataItemDAO dao = new MasterDataItemDAO();

	public boolean delete(int itemId) {
		try {
			return dao.delete(itemId);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ItemMasterDTO> listByKind(String kind) {
		try {
			return dao.listByKind(kind);
		} catch (Exception e) {
			e.printStackTrace();
			return java.util.List.of();
		}
	}

	public boolean create(ItemMasterDTO d) {
		try {
			return dao.insert(d);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(ItemMasterDTO d) {
		try {
			return dao.update(d);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ItemMasterDTO> list(String keyword, int offset, int limit) {
		try {
			return dao.list(keyword, offset, limit);
		} catch (Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public ItemMasterDTO find(int itemId) {
		try {
			return dao.find(itemId).orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}