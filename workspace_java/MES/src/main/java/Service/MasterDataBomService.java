package Service;

import DAO.MasterDataBomDAO;
import yaDTO.BomDTO;
import yaDTO.BomViewDTO;
import java.util.List;

public class MasterDataBomService {
    private final MasterDataBomDAO dao = new MasterDataBomDAO();

    public List<BomViewDTO> listView() {
        try {
            return dao.listView();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public BomDTO findById(int bomId) {
        try {
            return dao.findById(bomId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(BomDTO d) {
        try {
            return dao.insert(d);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 여기 추가
    public boolean update(BomDTO d) {
        try {
            return dao.update(d);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int bomId) {
        try {
            return dao.delete(bomId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
