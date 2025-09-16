package Controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Service.ProductionPlanService;
import yaDTO.ProductionTargetDTO;
import yaDTO.ItemMasterDTO;
import yaDTO.UserDTO;

@WebServlet(urlPatterns = {
    "/productionPlan",
    "/productionPlan/list",
    "/productionPlan/edit",
    "/productionPlan/delete"
})
public class ProductionPlanController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductionPlanService service = new ProductionPlanService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String uri = request.getRequestURI();

        if (uri.endsWith("/productionPlan")) {
            // 👉 등록 화면
            request.setAttribute("loginUserName", loginUser.getName());
            request.setAttribute("loginUserId", loginUser.getUser_id());
            request.setAttribute("loginUserLoginId", loginUser.getLogin_id());

            List<ItemMasterDTO> drinkItems = service.getDrinkItems();
            request.setAttribute("drinkItems", drinkItems);

            request.getRequestDispatcher("/WEB-INF/views/ProductionPlan.jsp")
                   .forward(request, response);

        } else if (uri.endsWith("/productionPlan/list")) {
            // 👉 조회 화면
            String searchStart = request.getParameter("searchStart");
            String searchEnd = request.getParameter("searchEnd");

            Date startDate = (searchStart != null && !"null".equalsIgnoreCase(searchStart) && !searchStart.trim().isEmpty())
                    ? Date.valueOf(searchStart) : null;
            Date endDate = (searchEnd != null && !"null".equalsIgnoreCase(searchEnd) && !searchEnd.trim().isEmpty())
                    ? Date.valueOf(searchEnd) : null;

            int pageSize = 10;
            int page = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null && !"null".equalsIgnoreCase(pageParam) && !pageParam.trim().isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam.trim());
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            List<ProductionTargetDTO> targetList;
            int totalCount;
            if (startDate != null || endDate != null) {
                targetList = service.getTargetListPaged(startDate, endDate, page, pageSize);
                totalCount = service.getTotalCount(startDate, endDate);
            } else {
                targetList = service.getTargetListPaged(page, pageSize);
                totalCount = service.getTotalCount();
            }

            int totalPage = (int) Math.ceil((double) totalCount / pageSize);

            Map<Integer, String> itemMap = service.getItemMap();

            request.setAttribute("targetList", targetList);
            request.setAttribute("itemMap", itemMap);
            request.setAttribute("searchStart", searchStart);
            request.setAttribute("searchEnd", searchEnd);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPage", totalPage);
            request.setAttribute("totalCount", totalCount);

            request.getRequestDispatcher("/WEB-INF/views/ProductionPlanList.jsp")
                   .forward(request, response);

        } else if (uri.endsWith("/productionPlan/edit")) {
            // 👉 수정 화면 열기 (보안상 관리자만 허용)
            if (!"ADMIN".equalsIgnoreCase(loginUser.getUser_role())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 수정할 수 있습니다.");
                return;
            }

            int targetId = Integer.parseInt(request.getParameter("id"));
            ProductionTargetDTO dto = service.getTargetById(targetId);

            request.setAttribute("target", dto);
            request.setAttribute("drinkItems", service.getDrinkItems());
            request.setAttribute("targetList", service.getTargetList());
            request.setAttribute("itemMap", service.getItemMap());

            request.getRequestDispatcher("/WEB-INF/views/ProductionPlanList.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();

        try {
            if (uri.endsWith("/productionPlan")) {
                // 👉 신규 등록
                int itemId = Integer.parseInt(request.getParameter("item_id"));
                Date periodStart = Date.valueOf(request.getParameter("period_start"));
                Date periodEnd = Date.valueOf(request.getParameter("period_end"));
                int targetQuantity = Integer.parseInt(request.getParameter("target_quantity"));

                UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
                if (loginUser == null) {
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }

                if (periodStart.after(periodEnd)) {
                    request.setAttribute("errorMsg", "시작일은 종료일 이후일 수 없습니다.");
                    request.setAttribute("drinkItems", service.getDrinkItems());
                    request.getRequestDispatcher("/WEB-INF/views/ProductionPlan.jsp").forward(request, response);
                    return;
                }

                if (targetQuantity <= 0) {
                    request.setAttribute("errorMsg", "목표 수량은 1 이상이어야 합니다.");
                    request.setAttribute("drinkItems", service.getDrinkItems());
                    request.getRequestDispatcher("/WEB-INF/views/ProductionPlan.jsp").forward(request, response);
                    return;
                }

                int createdBy = loginUser.getUser_id();
                ProductionTargetDTO dto = new ProductionTargetDTO();
                dto.setItem_id(itemId);
                dto.setPeriod_start(periodStart);
                dto.setPeriod_end(periodEnd);
                dto.setTarget_quantity(targetQuantity);
                dto.setCreated_by(createdBy);

                boolean success = service.insertTarget(dto);
                if (success) {
                    session.setAttribute("msg", "등록되었습니다.");
                    response.sendRedirect(request.getContextPath() + "/productionPlan/list");
                } else {
                    request.setAttribute("errorMsg", "중복된 목표 입니다");
                    request.setAttribute("drinkItems", service.getDrinkItems());
                    request.getRequestDispatcher("/WEB-INF/views/ProductionPlan.jsp").forward(request, response);
                }

            } else if (uri.endsWith("/productionPlan/edit")) {
                // 👉 수정 처리
                UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
                if (loginUser == null) {
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }
                if (!"ADMIN".equalsIgnoreCase(loginUser.getUser_role())) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 수정할 수 있습니다.");
                    return;
                }

                int targetId = Integer.parseInt(request.getParameter("target_id"));
                int itemId = Integer.parseInt(request.getParameter("item_id"));
                Date periodStart = Date.valueOf(request.getParameter("period_start"));
                Date periodEnd = Date.valueOf(request.getParameter("period_end"));
                int targetQuantity = Integer.parseInt(request.getParameter("target_quantity"));

                int page = 1;
                String pageParam = request.getParameter("page");
                if (pageParam != null && !"null".equalsIgnoreCase(pageParam) && !pageParam.trim().isEmpty()) {
                    try {
                        page = Integer.parseInt(pageParam.trim());
                    } catch (NumberFormatException e) {
                        page = 1;
                    }
                }
                String searchStart = request.getParameter("searchStart");
                String searchEnd = request.getParameter("searchEnd");

                ProductionTargetDTO dto = new ProductionTargetDTO();
                dto.setTarget_id(targetId);
                dto.setItem_id(itemId);
                dto.setPeriod_start(periodStart);
                dto.setPeriod_end(periodEnd);
                dto.setTarget_quantity(targetQuantity);

                // ✅ 날짜 검증
                if (periodStart.after(periodEnd)) {
                    session.setAttribute("errorMsg", "시작일은 종료일 이후일 수 없습니다.");
                    response.sendRedirect(request.getContextPath() + "/productionPlan/list?page=" + page
                        + (searchStart != null && !"".equals(searchStart) ? "&searchStart=" + searchStart : "")
                        + (searchEnd != null && !"".equals(searchEnd) ? "&searchEnd=" + searchEnd : ""));
                    return;
                }

                // ✅ 목표 수량 검증 (0 불가)
                if (targetQuantity <= 0) {
                    session.setAttribute("errorMsg", "목표 수량은 1 이상이어야 합니다.");
                    response.sendRedirect(request.getContextPath() + "/productionPlan/list?page=" + page
                        + (searchStart != null && !"".equals(searchStart) ? "&searchStart=" + searchStart : "")
                        + (searchEnd != null && !"".equals(searchEnd) ? "&searchEnd=" + searchEnd : ""));
                    return;
                }

                // ✅ 양품 합계 검증
                int goodSum = service.getGoodQtySumByTarget(targetId);
                if (targetQuantity < goodSum) {
                    session.setAttribute("errorMsg",
                        "이미 생산된 양품(" + goodSum + "개)보다 작은 목표로 수정할 수 없습니다.");
                    response.sendRedirect(request.getContextPath() + "/productionPlan/list?page=" + page
                        + (searchStart != null && !"".equals(searchStart) ? "&searchStart=" + searchStart : "")
                        + (searchEnd != null && !"".equals(searchEnd) ? "&searchEnd=" + searchEnd : ""));
                    return;
                }

                boolean updated = service.updateTarget(dto);
                if (updated) {
                    session.setAttribute("msg", "수정이 완료되었습니다.");
                } else {
                    session.setAttribute("errorMsg", "수정 중 오류 발생");
                }

                response.sendRedirect(request.getContextPath() + "/productionPlan/list?page=" + page
                        + (searchStart != null && !"".equals(searchStart) ? "&searchStart=" + searchStart : "")
                        + (searchEnd != null && !"".equals(searchEnd) ? "&searchEnd=" + searchEnd : ""));

            } else if (uri.endsWith("/productionPlan/delete")) {
                // 👉 삭제 처리
                int targetId = Integer.parseInt(request.getParameter("target_id"));
                boolean deleted = service.deleteTarget(targetId);

                if (deleted) {
                    session.setAttribute("msg", "삭제가 완료되었습니다.");
                } else {
                    session.setAttribute("errorMsg", "삭제 불가: 이미 생산지시가 존재합니다.");
                }

                response.sendRedirect(request.getContextPath() + "/productionPlan/list");
            }

        } catch (Exception e) {
            e.printStackTrace();

            String msg = "요청 처리 중 오류 발생";
            if (e.getMessage() != null && e.getMessage().contains("UQ_TARGET_SPAN")) {
                msg = "중복된 목표입니다.";
            }

            session.setAttribute("errorMsg", msg);
            response.sendRedirect(request.getContextPath() + "/productionPlan/list");
        }
    }
}
