package Controller;

import Service.ProductionResultService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/result/search")
public class ProductionResultSearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductionResultService service = new ProductionResultService();
    private static final String VIEW = "/WEB-INF/views/p_result_search.jsp";

    /* ---------- utils ---------- */
    private static String toNullIfEmpty(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }
    private static Integer parseIntOrNull(String s) {
        try { return (s == null) ? null : Integer.valueOf(s.trim()); }
        catch (Exception e) { return null; }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        // ⭐ 탭/네비 활성화 (헤더의 currentPage와 탭 전용 resultTab 둘 다 넣어 충돌 방지)
        req.setAttribute("resultTab", "search");
        req.setAttribute("currentPage", "search");

        // 어떤 섹션에서 요청했는지 (기본: 상세 실적 내역)
        String tab = toNullIfEmpty(req.getParameter("tab"));
        if (tab == null) tab = "results";

        // 상세 실적 내역 파라미터
        String from    = toNullIfEmpty(req.getParameter("from"));
        String to      = toNullIfEmpty(req.getParameter("to"));
        String orderId = toNullIfEmpty(req.getParameter("orderId"));

        // 지시대비 달성률 파라미터
        String s_from    = toNullIfEmpty(req.getParameter("s_from"));
        String s_to      = toNullIfEmpty(req.getParameter("s_to"));
        String s_orderId = toNullIfEmpty(req.getParameter("s_orderId"));

        // 페이징 (기본값: 1 / 20)
        Integer page = parseIntOrNull(toNullIfEmpty(req.getParameter("page")));
        Integer size = parseIntOrNull(toNullIfEmpty(req.getParameter("size")));
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 20;

        try {
            Map<String,Object> resultsData;
            Map<String,Object> summaryData;

            if ("summary".equals(tab)) {
                // 달성률 탭에서 조회
                summaryData = service.searchSummaryOnly(s_from, s_to, s_orderId);
                resultsData = service.searchResultsOnly(from, to, orderId, page, size);
            } else {
                // 상세 실적 탭에서 조회(또는 초기)
                resultsData = service.searchResultsOnly(from, to, orderId, page, size);
                summaryData = service.searchSummaryOnly(s_from, s_to, s_orderId);
            }

            // 상세 실적 결과 세팅
            if (resultsData != null) {
                req.setAttribute("results",  resultsData.get("results"));
                req.setAttribute("total",    resultsData.get("total"));
                req.setAttribute("page",     resultsData.get("page"));
                req.setAttribute("size",     resultsData.get("size"));
                req.setAttribute("from",     resultsData.get("from"));
                req.setAttribute("to",       resultsData.get("to"));
                req.setAttribute("orderId",  resultsData.get("orderId"));
            } else {
                req.setAttribute("from", from);
                req.setAttribute("to", to);
                req.setAttribute("orderId", orderId);
                req.setAttribute("page", page);
                req.setAttribute("size", size);
            }

            // 달성률 결과 세팅
            if (summaryData != null) {
                req.setAttribute("summary",  summaryData.get("summary"));
                req.setAttribute("s_from",    summaryData.get("from"));
                req.setAttribute("s_to",      summaryData.get("to"));
                req.setAttribute("s_orderId", summaryData.get("orderId"));
            } else {
                req.setAttribute("s_from", s_from);
                req.setAttribute("s_to", s_to);
                req.setAttribute("s_orderId", s_orderId);
            }

            req.setAttribute("tab", tab);

        } catch (Exception e) {
            // 실패 시에도 화면은 렌더링되도록 에러 메시지만 전달
            req.setAttribute("error", "검색 중 오류가 발생했습니다: " + e.getMessage());
            // 기본 값 유지
            req.setAttribute("from", from);
            req.setAttribute("to", to);
            req.setAttribute("orderId", orderId);
            req.setAttribute("s_from", s_from);
            req.setAttribute("s_to", s_to);
            req.setAttribute("s_orderId", s_orderId);
            req.setAttribute("page", page);
            req.setAttribute("size", size);
            req.setAttribute("tab", tab);
        }

        req.getRequestDispatcher(VIEW).forward(req, resp);
    }
}