package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.ItemMasterDAO;
import DAO.LocationRuleDAO;

@WebServlet("/inventory/in/new")
public class InventoryInFormController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final LocationRuleDAO locationRuleDAO = new LocationRuleDAO();
    private final ItemMasterDAO itemMasterDAO = new ItemMasterDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        req.setAttribute("currentPage", "in");

        try {
            // 1) 허용 LOCATION
            Set<String> allowedLocations = locationRuleDAO.getAllowedLocations();
            req.setAttribute("allowedLocations", allowedLocations);

            // 2) 품목 목록: RM만
            List<Map<String, Object>> items = itemMasterDAO.getItemsOnlyRM();
            req.setAttribute("items", items);

            // 구형 JSP 호환
            List<Map<String, Object>> itemList = new ArrayList<>();
            for (Map<String, Object> row : items) {
                java.util.HashMap<String, Object> m = new java.util.HashMap<>();
                m.put("id",   row.get("item_id"));
                m.put("name", row.get("item_name"));
                itemList.add(m);
            }
            req.setAttribute("itemList", itemList);

            // 메시지 표시: 이 부분이 핵심
            if ("1".equals(req.getParameter("success"))) {
                req.setAttribute("message", "입고 등록이 완료되었습니다.");
            }
            if (req.getParameter("err") != null) {
                req.setAttribute("error", req.getParameter("err"));
            }

            // 3) JSP로 forward
            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/views/Inventory_in.jsp");
            rd.forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "입고 화면 로딩 실패: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/inventory/in.jsp").forward(req, resp);
        }
    }
}