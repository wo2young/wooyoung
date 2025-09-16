package Controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import Service.ProductionResultService;
import yaDTO.ProductionResultDTO;

@WebServlet("/result/new")
public class ProductionResultCreateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductionResultService service = new ProductionResultService();
    private static final String VIEW = "/WEB-INF/views/p-result-create-web.jsp";

    /* 화면(폼) */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // 로그인 체크
        HttpSession s = req.getSession(false);
        Integer workerId = null;
        if (s != null) {
            Object u = s.getAttribute("loginUser");
            if (u instanceof yaDTO.UserDTO) {
                workerId = ((yaDTO.UserDTO) u).getUser_id();
                s.setAttribute("userId", workerId);
            }
        }
        if (workerId == null) {
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }

        // 열린 생산지시 목록 조회 → JSP
        List<Map<String,Object>> orders = service.getOpenOrders();
        req.setAttribute("orders", orders);

        // 탭 활성화(충돌 대비해 둘 다 셋팅)
        req.setAttribute("resultTab", "create");   // 탭 전용 변수
        req.setAttribute("currentPage", "create"); // 레거시/기존 JSP 호환

        // 플래시 메시지
        if ("1".equals(req.getParameter("success"))) req.setAttribute("message", "작업실적이 등록되었습니다.");
        if (req.getParameter("err") != null)          req.setAttribute("error",   req.getParameter("err"));

        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    /* 저장(폼 submit) */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            String orderStr = req.getParameter("order_id");
            String goodStr  = req.getParameter("good_qty");
            String failStr  = req.getParameter("fail_qty");
            String workStr  = req.getParameter("work_date"); // yyyy-MM-dd'T'HH:mm

            if (isBlank(orderStr) || isBlank(goodStr) || isBlank(failStr) || isBlank(workStr)) {
                redirectErr(req, resp, "필수값_누락"); return;
            }

            Integer orderId = parseInt(orderStr);
            BigDecimal good = parseDec(goodStr);
            BigDecimal fail = parseDec(failStr);
            if (orderId == null || orderId <= 0 || good == null || good.signum() < 0 || fail == null || fail.signum() < 0) {
                redirectErr(req, resp, "값_오류"); return;
            }

            LocalDateTime ldt = LocalDateTime.parse(
                    workStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm", Locale.ROOT));
            Timestamp workAt = Timestamp.valueOf(ldt);

            // 작성자
            Integer workerId = null;
            HttpSession s = req.getSession(false);
            if (s != null) {
                Object u = s.getAttribute("loginUser");
                if (u instanceof yaDTO.UserDTO) workerId = ((yaDTO.UserDTO) u).getUser_id();
            }
            if (workerId == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }

            // DTO
            ProductionResultDTO dto = new ProductionResultDTO();
            dto.setOrder_id(orderId);
            dto.setWorker_id(workerId);
            dto.setGood_qty(good.intValue());
            dto.setFail_qty(fail.intValue());
            dto.setWork_date(workAt.toLocalDateTime());

            boolean ok = service.insertResult(dto);
            if (!ok) { redirectErr(req, resp, "저장실패"); return; }

            // 성공 후 폼으로
            resp.sendRedirect(req.getContextPath()+"/result/new?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            redirectErr(req, resp, "서버오류:"+e.getMessage());
        }
    }

    // utils
    private static boolean isBlank(String s){ return s==null || s.trim().isEmpty(); }
    private static Integer parseInt(String s){ try { return Integer.valueOf(s.trim()); } catch(Exception e){ return null; } }
    private static BigDecimal parseDec(String s){ try { return new BigDecimal(s.trim()); } catch(Exception e){ return null; } }
    private static void redirectErr(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
        String enc = URLEncoder.encode(msg==null? "오류" : msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath()+"/result/new?err="+enc);
    }
}
