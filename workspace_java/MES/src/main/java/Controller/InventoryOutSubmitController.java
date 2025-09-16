package Controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import Service.InventoryService;

@WebServlet("/inventory/out/submit")
public class InventoryOutSubmitController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final InventoryService inventoryService = new InventoryService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            // 1) 파라미터
            String itemIdStr = req.getParameter("itemId");
            String qtyStr    = req.getParameter("qty");            // out.jsp의 name="qty"
            String location  = req.getParameter("locationName");
            String lotNo     = req.getParameter("lotNo");

            // 기본 검증
            if (isBlank(itemIdStr) || isBlank(qtyStr) || isBlank(location) || isBlank(lotNo)) {
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
                createdBy = parseInt(req.getParameter("createdBy")); // 선택: hidden 필드로 보조
                if (createdBy == 0) {
                    resp.sendRedirect(req.getContextPath() + "/login");
                    return;
                }
            }

            // 3) 정규화
            String locationUpper = location.trim().toUpperCase();
            String lotNoUpper    = lotNo.trim().toUpperCase();

            // 4) 서비스 호출 (FG 확인/LOCATION 검증/잔량 체크 모두 포함)
            InventoryService.Result res =
                    inventoryService.insertOutSafe(itemId, qty, lotNoUpper, locationUpper, createdBy);

            // 5) 결과 리다이렉트
            if (res.ok) {
                resp.sendRedirect(req.getContextPath() + "/inventory/out/new?success=1");
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
    private static int parseInt(String s){ try { return Integer.parseInt(s.trim()); } catch(Exception e){ return 0; } }
    private static BigDecimal parseDecimal(String s){
        try { return (s==null || s.trim().isEmpty()) ? null : new BigDecimal(s.trim()); }
        catch(Exception e){ return null; }
    }
    private static void redirectErr(HttpServletResponse resp, HttpServletRequest req, String msg) throws IOException {
        String enc = URLEncoder.encode(msg == null ? "오류" : msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/inventory/out/new?err=" + enc);
    }
}
