package Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Service.InventoryHistoryService;

@WebServlet("/inventory/history")
public class InventoryHistoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final InventoryHistoryService svc = new InventoryHistoryService();
    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 기본값: 오늘 포함 최근 7일
        LocalDate today = LocalDate.now();
        String startParam = req.getParameter("start");
        String endParam   = req.getParameter("end");
        String typeParam  = req.getParameter("type"); // ALL | IN | OUT

        LocalDate start = parseDateOr(startParam, today.minusDays(7));
        LocalDate end   = parseDateOr(endParam, today);
        if (end.isBefore(start)) { // 잘못 넣었을 때 자동 보정
            LocalDate tmp = start; start = end; end = tmp;
        }

        String type = (typeParam == null || typeParam.isBlank()) ? "ALL" : typeParam.toUpperCase();

        // [포함/포함] 범위를 위해 end+1일의 00:00 직전까지로 처리
        Timestamp startTs = Timestamp.valueOf(start.atStartOfDay());
        Timestamp endTs   = Timestamp.valueOf(end.plusDays(1).atStartOfDay());

        try {
            req.setAttribute("rows", svc.findHistory(startTs, endTs, type));
        } catch (Exception e) {
            req.setAttribute("error", "이력 조회 중 오류: " + e.getMessage());
        }

        // 화면에 선택값 유지
        req.setAttribute("start", start.format(F));
        req.setAttribute("end",   end.format(F));
        req.setAttribute("type",  type);
        req.setAttribute("currentPage", "history"); // 탭 활성화 쓰시는 경우

        req.getRequestDispatcher("/WEB-INF/views/Inventory_History.jsp").forward(req, resp);
    }

    private LocalDate parseDateOr(String s, LocalDate def) {
        try { return (s == null || s.isBlank()) ? def : LocalDate.parse(s); }
        catch (Exception e) { return def; }
    }
}
