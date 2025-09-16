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
                    int targetItemId = ip(req.getParameter("targetItemId")); // Target 필터
                    int itemId = ip(req.getParameter("itemId"));             // Order 필터
                    String status = nv(req.getParameter("status"), "ALL");

                    List<ProductionTargetDTO> targets = targetSvc.getTargetList();
                    targets.removeIf(t -> "완료".equals(t.getStatus()));
                    if (targetItemId > 0) {
                        targets.removeIf(t -> t.getItem_id() != targetItemId);
                    }

                    List<ProductionOrderDTO> orders = orderSvc.listOrders(itemId, status);
                    List<ItemMasterDTO> itemList = orderSvc.getTargetItems();

                    req.setAttribute("targets", targets);
                    req.setAttribute("orders", orders);
                    req.setAttribute("itemList", itemList);
                    req.setAttribute("targetItemSel", targetItemId);
                    req.setAttribute("itemSel", itemId);
                    req.setAttribute("statusSel", status);
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
