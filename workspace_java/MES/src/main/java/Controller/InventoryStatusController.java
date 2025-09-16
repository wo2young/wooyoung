package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.InventoryDAO;
import DAO.ItemMasterDAO;
import DAO.LocationRuleDAO;

@WebServlet("/inventory/status")
public class InventoryStatusController extends HttpServlet {
    private final LocationRuleDAO locationRuleDAO = new LocationRuleDAO();
    private final ItemMasterDAO itemMasterDAO = new ItemMasterDAO();
    private final InventoryDAO inventoryDAO = new InventoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 파라미터 받기
        String locationName = req.getParameter("locationName");
        String itemIdStr = req.getParameter("itemId");
        String lotLike = req.getParameter("lotLike");

        Integer itemId = null;
        if (itemIdStr != null && !itemIdStr.isEmpty()) {
            try {
                itemId = Integer.parseInt(itemIdStr);
            } catch (NumberFormatException e) {
                itemId = null;
            }
        }

        // 1. 창고 목록
        Set<String> locations = locationRuleDAO.getAllowedLocations();
        req.setAttribute("locations", locations);

        // 2. 품목 목록
        List<Map<String, Object>> items = itemMasterDAO.getAllItems();
        req.setAttribute("items", items);

        // 3. 재고 상세 목록
        try {
            List<Map<String, Object>> inventoryRows =
                    inventoryDAO.getInventoryStatus(locationName, itemId, lotLike);
            req.setAttribute("inventoryRows", inventoryRows);

            // 4. 상단 summary (집계)  -- ✅ 변경됨: 총 재고 라인수는 qty > 0만 카운트
            Map<String, Object> summary = new HashMap<>();

            // 총 재고 라인수: 출고로 0/음수 된 라인은 제외
            long totalItemCount = inventoryRows.stream()
                    .map(row -> (java.math.BigDecimal) row.get("qty"))
                    .filter(q -> q != null && q.compareTo(java.math.BigDecimal.ZERO) > 0)
                    .count();
            summary.put("totalItemCount", totalItemCount);

            // 총 재고 수량: 기존처럼 전체 합계 (원하면 여기도 >0만 합산하도록 바꿀 수 있음)
            summary.put("totalQty", inventoryRows.stream()
                    .map(row -> (java.math.BigDecimal) row.get("qty"))
                    .filter(Objects::nonNull)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

            summary.put("shortageItemCount",
                    inventoryRows.stream().filter(row -> "LOW".equals(row.get("status"))).count());
            summary.put("warningItemCount",
                    inventoryRows.stream().filter(row -> "WARN".equals(row.get("status"))).count());

            req.setAttribute("summary", summary);

        } catch (SQLException e) {
            throw new ServletException("재고 조회 오류", e);
        }

        // JSP 연결
        req.getRequestDispatcher("/WEB-INF/views/Inventory_Status.jsp")
           .forward(req, resp);
    }
}
