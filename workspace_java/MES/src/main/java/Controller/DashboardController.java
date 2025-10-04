package Controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import Service.DashboardService;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
  private final DashboardService svc = new DashboardService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      // ① 오늘 카드
      Map<String,Object> today = svc.getTodaySummary();
      req.setAttribute("todayGood",  today.getOrDefault("good", 0L));
      req.setAttribute("todayDef",   today.getOrDefault("defect", 0L));
      req.setAttribute("todayRate",  today.getOrDefault("defectRate", 0.0));

      // ② 최근 7일 생산량
      Map<String,Object> p7 = svc.getProdLast7();
      req.setAttribute("labels7", p7.get("labels"));
      req.setAttribute("prod7",   p7.get("data"));

      // ③ 이번달 목표 vs 실적
      Map<String,Object> ta = svc.getTargetVsActualMonth();
      req.setAttribute("labelsMonth", ta.get("labels"));
      req.setAttribute("target",      ta.get("target"));
      req.setAttribute("actual",      ta.get("actual"));

      // ④ 불량 분포
      Map<String,Object> dp = svc.getDefectPieMonth();
      req.setAttribute("defectLabels", dp.get("labels"));
      req.setAttribute("defectCounts", dp.get("counts"));

      // ⑤ 재고 현황
      Map<String,Object> inv = svc.getInventoryStatus();
      req.setAttribute("inventoryLabels", inv.get("labels"));
      req.setAttribute("inventoryCounts", inv.get("counts"));

    } catch (Exception e) {
      req.setAttribute("todayGood", 0L);
      req.setAttribute("todayDef",  0L);
      req.setAttribute("todayRate", 0.0);
      req.setAttribute("labels7", Collections.emptyList());
      req.setAttribute("prod7",   Collections.emptyList());
      req.setAttribute("labelsMonth", Collections.emptyList());
      req.setAttribute("target",      Collections.emptyList());
      req.setAttribute("actual",      Collections.emptyList());
      req.setAttribute("defectLabels", Collections.emptyList());
      req.setAttribute("defectCounts", Collections.emptyList());
      req.setAttribute("inventoryLabels", Collections.emptyList());
      req.setAttribute("inventoryCounts", Collections.emptyList());
      e.printStackTrace();
      req.setAttribute("dashError", e.getClass().getSimpleName() + ": " + e.getMessage());
    }
    req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
  }
}