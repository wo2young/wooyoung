package Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import DAO.InventoryDAO;

public class InventoryHistoryService {
    private final InventoryDAO inventoryDAO = new InventoryDAO();

    /** 기간 + 타입(ALL/IN/OUT) 필터로 이력 조회 */
    public List<Map<String,Object>> findHistory(Timestamp startTs, Timestamp endTs, String type) {
        if (startTs == null || endTs == null) throw new IllegalArgumentException("조회 기간이 올바르지 않습니다.");
        String t = (type == null ? "ALL" : type.trim().toUpperCase());
        return inventoryDAO.selectHistory(startTs, endTs, t);
    }
}
