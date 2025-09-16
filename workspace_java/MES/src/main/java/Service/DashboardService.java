package Service;

import java.util.*;
import DAO.DashboardDAO;

public class DashboardService {
  private final DashboardDAO dao = new DashboardDAO();

  public Map<String,Object> getTodaySummary() {
    Map<String,Object> m = dao.selectTodaySummary();
    long good = ((Number)m.getOrDefault("good", 0L)).longValue();
    long def  = ((Number)m.getOrDefault("defect", 0L)).longValue();
    double rate = (good + def) == 0 ? 0.0 : (def * 100.0) / (good + def);
    m.put("defectRate", Math.round(rate * 10.0) / 10.0);
    return m;
  }

  public Map<String,Object> getProdLast7()             { return dao.selectProdLast7(); }
  public Map<String,Object> getTargetVsActualMonth()   { return dao.selectTargetVsActualMonth(); }
  public Map<String,Object> getDefectPieMonth()        { return dao.selectDefectPieMonth(); }
  public Map<String,Object> getInventoryStatus()       { return dao.selectInventoryStatus(); }
}
