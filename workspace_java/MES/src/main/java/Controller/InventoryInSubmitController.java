package Controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import Service.InventoryService;

@WebServlet("/inventory/in/submit")
public class InventoryInSubmitController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final InventoryService inventoryService = new InventoryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            // 1) 파라미터
            String itemIdStr = req.getParameter("itemId");
            // 과거/현재 폼 호환: quantity 또는 qty
            String qtyStr    = firstNonBlank(req.getParameter("quantity"), req.getParameter("qty"));
            String location  = req.getParameter("locationName");

            // 필수값 체크
            if (isBlank(itemIdStr) || isBlank(qtyStr) || isBlank(location)) {
                redirectErr(resp, req, "필수값_누락");
                return;
            }

            int itemId = parseInt(itemIdStr);
            BigDecimal qty = parseDecimal(qtyStr);
            if (itemId <= 0 || qty == null || qty.signum() <= 0) {
                redirectErr(resp, req, "값_오류");
                return;
            }

            // 2) 사용자 (세션 우선, 없으면 hidden createdBy 허용)
            HttpSession session = req.getSession(false);
            Integer createdBy = null;
            if (session != null) {
                Object u = session.getAttribute("loginUser");
                if (u instanceof yaDTO.UserDTO) {
                    createdBy = ((yaDTO.UserDTO) u).getUser_id();
                }
            }
            if (createdBy == null) {
                createdBy = parseInt(req.getParameter("createdBy")); // 선택: hidden 보조
                if (createdBy == 0) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
            }

            // 3) 정규화
            String locationUpper = location.trim().toUpperCase();

            // 4) 서비스 호출 (LOCATION 검증 포함)
            InventoryService.Result res =
                    inventoryService.insertInSafe(itemId, qty, locationUpper, createdBy);

            // 5) 결과 리다이렉트
            if (res.ok) {
                resp.sendRedirect(req.getContextPath() + "/inventory/in/new?success=1");
            } else {
                redirectErr(resp, req, res.message);
            }

        } catch (NumberFormatException e) {
            redirectErr(resp, req, "숫자_형식_오류");
        } catch (Exception e) {
            e.printStackTrace();
            redirectErr(resp, req, "서버_오류:" + e.getMessage());
        }
    }

    // helpers
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static String firstNonBlank(String a, String b) {
        return !isBlank(a) ? a : b;
    }
    private static int parseInt(String s){ try { return Integer.parseInt(s.trim()); } catch(Exception e){ return 0; } }
    private static BigDecimal parseDecimal(String s){
        try { return (s==null || s.trim().isEmpty()) ? null : new BigDecimal(s.trim()); }
        catch(Exception e){ return null; }
    }
    private static void redirectErr(HttpServletResponse resp, HttpServletRequest req, String msg) throws IOException {
        String enc = URLEncoder.encode(msg == null ? "오류" : msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/inventory/in/new?err=" + enc);
    }
}
