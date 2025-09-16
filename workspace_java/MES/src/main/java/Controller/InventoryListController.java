package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import DAO.InventoryDAO;

@WebServlet("/inventory/lot/list")
public class InventoryListController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final InventoryDAO inventoryDAO = new InventoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        resp.setHeader("Cache-Control", "no-store");

        String itemIdStr = req.getParameter("itemId");
        String location  = req.getParameter("locationName");
        if (isBlank(location)) location = req.getParameter("location");

        if (isBlank(itemIdStr) || isBlank(location)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, "[]");
            return;
        }

        try {
            int itemId = Integer.parseInt(itemIdStr.trim());
            String locationUpper = location.trim().toUpperCase();

            // ✅ Map으로 받기 (DTO 필요 없음)
            List<Map<String,Object>> lots = inventoryDAO.selectAvailableLotsMap(itemId, locationUpper);

            StringBuilder sb = new StringBuilder(lots.size() * 32 + 16);
            sb.append("[");
            for (int i = 0; i < lots.size(); i++) {
                Map<String,Object> m = lots.get(i);
                String lotNo = (String) m.get("lot_no");
                BigDecimal avail = (BigDecimal) m.get("avail");
                if (avail == null) avail = BigDecimal.ZERO;

                sb.append("{\"lotNo\":\"")
                  .append(escapeJson(lotNo))
                  .append("\",\"qty\":")
                  .append(avail.toPlainString())
                  .append(",\"expire\":null}");

                if (i < lots.size() - 1) sb.append(",");
            }
            sb.append("]");

            writeJson(resp, sb.toString());

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writeJson(resp, "[]");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writeJson(resp, "[]");
        }
    }

    private static boolean isBlank(String s){
        return s == null || s.trim().isEmpty();
    }

    private static void writeJson(HttpServletResponse resp, String json) throws IOException {
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (char c : s.toCharArray()) {
            switch (c) {
                case '"' : sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b");  break;
                case '\f': sb.append("\\f");  break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) sb.append(String.format("\\u%04x",(int)c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }
}
