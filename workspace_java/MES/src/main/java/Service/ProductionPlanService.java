package Service;

import java.sql.Date;   // java.sql.Date 꼭 import
import java.util.List;
import java.util.Map;

import DAO.ProductionPlanDAO;
import yaDTO.ProductionTargetDTO;
import yaDTO.ItemMasterDTO;

public class ProductionPlanService {
    private final ProductionPlanDAO dao = new ProductionPlanDAO();

    // ✅ 등록
    public boolean insertTarget(ProductionTargetDTO dto) {
        return dao.insertTarget(dto);
    }

    // ✅ 특정 음료 조회
    public List<ItemMasterDTO> getDrinkItems() {
        return dao.getDrinkItems();
    }

    // ✅ 전체 target 조회
    public List<ProductionTargetDTO> getTargetList() {
        return dao.getTargetList();
    }

    // ✅ 날짜검색 조회
    public List<ProductionTargetDTO> getTargetList(Date startDate, Date endDate) {
        return dao.getTargetList(startDate, endDate);
    }

    // ✅ 페이징 전체 조회
    public List<ProductionTargetDTO> getTargetListPaged(int page, int pageSize) {
        return dao.getTargetListPaged(page, pageSize);
    }

    // ✅ 페이징 + 날짜검색 조회
    public List<ProductionTargetDTO> getTargetListPaged(Date startDate, Date endDate, int page, int pageSize) {
        return dao.getTargetListPaged(startDate, endDate, page, pageSize);
    }

    // ✅ 전체 개수
    public int getTotalCount() {
        return dao.getTotalCount();
    }

    // ✅ 날짜검색 + 개수
    public int getTotalCount(Date startDate, Date endDate) {
        return dao.getTotalCount(startDate, endDate);
    }

    // ✅ item_id → item_name 매핑
    public Map<Integer, String> getItemMap() {
        return dao.getItemMap();
    }

    // ✅ target_id로 단건 조회 (수정 화면용)
    public ProductionTargetDTO getTargetById(int targetId) {
        return dao.getTargetById(targetId);
    }

    // ✅ 수정
    public boolean updateTarget(ProductionTargetDTO dto) {
        return dao.updateTarget(dto);
    }

    // ✅ target_id 기준 생산지시 합계 조회
    public int getOrdersSumByTarget(int targetId) {
        return dao.getOrdersSumByTarget(targetId);
    }

    // ✅ target_id 기준 양품 합계 조회 (변경됨)
    public int getGoodQtySumByTarget(int targetId) {
        return dao.getGoodQtySumByTarget(targetId);
    }

    // ✅ 삭제 기능 (production_order 참조 있으면 false 반환)
    public boolean deleteTarget(int targetId) {
        return dao.deleteTarget(targetId);
    }
}
