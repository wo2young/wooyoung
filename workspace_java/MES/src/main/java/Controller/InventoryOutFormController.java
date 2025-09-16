package Controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import DAO.ItemMasterDAO;
import DAO.LocationRuleDAO;
import Service.InventoryService;

@WebServlet("/inventory/out/new")
public class InventoryOutFormController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ItemMasterDAO itemDAO = new ItemMasterDAO();
    private final LocationRuleDAO locationRuleDAO = new LocationRuleDAO();
    private final InventoryService inventoryService = new InventoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        req.setAttribute("currentPage", "out");

        // ✅ FG 전용 품목 (키: item_id, item_name)
        List<Map<String,Object>> items = itemDAO.getItemsOnlyFG();

        // ✅ 허용 LOCATION
        Set<String> allowed = locationRuleDAO.getAllowedLocations();

        req.setAttribute("items", items);
        req.setAttribute("allowedLocations", allowed);

        // ✅ JSP 경로 정확히 (소문자 디렉터리/파일명)
        req.getRequestDispatcher("/WEB-INF/views/Inventory_out.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int itemId       = parseInt(req.getParameter("itemId"));
        String location  = s(req.getParameter("locationName"));
        String lotNo     = s(req.getParameter("lotNo"));
        String remark    = s(req.getParameter("remark")); // 현재는 미사용(필요 시 확장)
        BigDecimal qty   = parseDecimal(req.getParameter("qty")); // out.jsp에서 name="qty" 사용 중

        // createdBy: 파라미터 우선
        int createdBy = parseInt(req.getParameter("createdBy"));

        InventoryService.Result r = inventoryService.insertOutSafe(
                itemId, qty, lotNo, location, createdBy
        );

        req.setAttribute(r.ok ? "success" : "error",
                r.ok ? "출고 등록이 완료되었습니다." : r.message);

        // 등록 결과 메시지와 함께 동일 화면 재표시
        doGet(req, resp);
    }

    // helpers
    private static int parseInt(String s){ try { return Integer.parseInt(s); } catch(Exception e){ return 0; } }
    private static String s(String v){ return v == null ? "" : v.trim(); }
    private static BigDecimal parseDecimal(String s){
        try { return (s==null || s.trim().isEmpty()) ? null : new BigDecimal(s.trim()); }
        catch(Exception e){ return null; }
    }
}
