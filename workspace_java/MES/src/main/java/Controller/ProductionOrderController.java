package Controller;

import Service.ProductionOrderService;
import Service.ProductionPlanService;
import yaDTO.ProductionOrderDTO;
import yaDTO.ProductionTargetDTO;
import yaDTO.ItemMasterDTO;
import yaDTO.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/order")
public class ProductionOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ProductionOrderService orderSvc = new ProductionOrderService();
    private final ProductionPlanService targetSvc = new ProductionPlanService();

    private String nv(String v, String def) {
        return (v == null || v.isEmpty()) ? def : v;
    }

    private int ip(String v) {
        try {
            return Integer.parseInt(v);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String act = nv(req.getParameter("act"), "order.list");
        String ctx = req.getContextPath();

        // ✅ 로그인/권한 체크
        UserDTO loginUser = (UserDTO) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            resp.sendRedirect(ctx + "/login");
            return;
        }
        String role = loginUser.getUser_role();
        if (!"ADMIN".equalsIgnoreCase(role) && !"MANAGER".equalsIgnoreCase(role)) {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().write("<script>alert('권한이 없습니다.'); location.href='" + ctx + "/dashboard';</script>");
            return;
        }

        try {
        	switch (act) {
            case "order.list": {
                // ====== 필터 ======
                int targetItemId = ip(req.getParameter("targetItemId")); // 생산목표 필터
                int itemId       = ip(req.getParameter("itemId"));       // 생산지시 필터
                String status    = nv(req.getParameter("status"), "ALL");

                // ====== 생산지시(아래 표) 페이징 ======
                int page = ip(req.getParameter("page"));
                if (page < 1) page = 1;
                int pageSize = ip(req.getParameter("pageSize"));
                if (pageSize <= 0) pageSize = 20;

                int totalOrders = orderSvc.countTotalOrders(itemId, status);
                int totalPages  = (int) Math.ceil(totalOrders / (double) pageSize);
                if (totalPages == 0) totalPages = 1;
                if (page > totalPages) page = totalPages;

                List<ProductionOrderDTO> orders =
                        orderSvc.listOrders(itemId, status, page, pageSize);

                // ====== 생산목표(위 표) 페이징 (JSP 변수명과 일치: targetPage / totalTargetPages) ======
                int targetPage = ip(req.getParameter("targetPage"));
                if (targetPage < 1) targetPage = 1;
                int targetPageSize = ip(req.getParameter("targetPageSize"));
                if (targetPageSize <= 0) targetPageSize = 20;

                List<ProductionTargetDTO> allTargets = targetSvc.getTargetList();
                if (allTargets == null) allTargets = new ArrayList<>();

                // 완료 제외
                allTargets.removeIf(t -> "완료".equals(t.getStatus()));
                // 선택 품목 필터
                if (targetItemId > 0) {
                    final int sel = targetItemId;
                    allTargets.removeIf(t -> t.getItem_id() != sel);
                }

                int totalTargets = allTargets.size();
                int totalTargetPages = (totalTargets == 0) ? 0
                        : (int) Math.ceil(totalTargets / (double) targetPageSize);
                if (totalTargetPages > 0 && targetPage > totalTargetPages) {
                    targetPage = totalTargetPages;
                }

                int from = (targetPage - 1) * targetPageSize;
                int to   = Math.min(from + targetPageSize, totalTargets);
                List<ProductionTargetDTO> targets =
                        (totalTargets == 0) ? new ArrayList<>()
                                : allTargets.subList(from, to);

                // 드롭다운 데이터
                List<ItemMasterDTO> itemList = orderSvc.getTargetItems();

                // ====== View 바인딩 ======
                // 생산목표(위)
                req.setAttribute("targets", targets);
                req.setAttribute("targetPage", targetPage);
                req.setAttribute("totalTargetPages", totalTargetPages);
                req.setAttribute("targetItemSel", targetItemId);
                req.setAttribute("targetPageSize", targetPageSize);

                // 생산지시(아래)
                req.setAttribute("orders", orders);
                req.setAttribute("page", page);
                req.setAttribute("totalPages", totalPages);
                req.setAttribute("totalOrders", totalOrders);
                req.setAttribute("pageSize", pageSize);
                req.setAttribute("itemSel", itemId);
                req.setAttribute("statusSel", status);

                // 공통
                req.setAttribute("itemList", itemList);

                req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
                break;
            }


                case "order.create": {
                    ProductionOrderDTO dto = new ProductionOrderDTO();
                    dto.setTarget_id(ip(req.getParameter("target_id")));
                    dto.setQuantity(ip(req.getParameter("quantity")));
                    dto.setDue_date(Date.valueOf(req.getParameter("due_date")));
                    dto.setCreated_by(loginUser.getUser_id());

                    boolean ok = orderSvc.createOrder(dto);
                    resp.sendRedirect(ctx + "/order?act=order.list&msg=" + (ok ? "create_ok" : "create_fail"));
                    break;
                }

                case "order.update": {
                    ProductionOrderDTO dto = new ProductionOrderDTO();
                    dto.setOrder_id(ip(req.getParameter("order_id")));
                    dto.setQuantity(ip(req.getParameter("quantity")));
                    dto.setDue_date(Date.valueOf(req.getParameter("due_date")));

                    boolean ok = orderSvc.updateOrder(dto);
                    resp.sendRedirect(ctx + "/order?act=order.list&msg=" + (ok ? "upd_ok" : "upd_fail"));
                    break;
                }

                case "order.delete": {
                    int orderId = ip(req.getParameter("order_id"));
                    try {
                        boolean ok = orderSvc.deleteOrder(orderId);
                        resp.sendRedirect(ctx + "/order?act=order.list&msg=" + (ok ? "del_ok" : "del_fail"));
                    } catch (SQLException e) {
                        String msg = e.getMessage();
                        if (msg != null && msg.contains("ORA-20080")) {
                            resp.sendRedirect(ctx + "/order?act=order.list&msg=del_blocked");
                        } else {
                            e.printStackTrace();
                            resp.sendRedirect(ctx + "/order?act=order.list&msg=del_fail");
                        }
                    }
                    break;
                }
                default:
                    resp.sendRedirect(ctx + "/order?act=order.list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}