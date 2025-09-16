package Service;

import DAO.MasterDataCodeDAO;
import yaDTO.CodeDetailDTO;
import java.util.List;

public class MasterDataCodeService {
	private final MasterDataCodeDAO dao = new MasterDataCodeDAO();

	public boolean deleteDetail(String detailCode) {
		try {
			return new MasterDataCodeDAO().deleteDetail(detailCode) > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<CodeDetailDTO> listDetails(String codeId) {
		try {
			return dao.listDetailsByCodeId(codeId);
		} catch (Exception e) {
			e.printStackTrace();
			return List.of();
		}
	}

	public boolean addDetail(String codeId, String codeDname) {
		if (codeId == null || codeDname == null || codeDname.isBlank())
			return false;
		try {
			return dao.insertDetail(codeId, codeDname);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean renameDetail(String detailCode, String newName) {
		try {
			return dao.updateDetailName(detailCode, newName);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean setActive(String detailCode, boolean active) {
		try {
			return dao.setDetailActive(detailCode, active);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
