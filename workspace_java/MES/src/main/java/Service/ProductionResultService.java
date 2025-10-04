package Service;

import DAO.ProductionResultDAO;
import yaDTO.ProductionResultDTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;

public class ProductionResultService {

    private final ProductionResultDAO dao = new ProductionResultDAO();
    private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    private Date toSqlDate(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        try {
            java.util.Date d = DF.parse(s.trim());
            return new Date(d.getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    private Integer toInt(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return null;
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

    /* ===== 열린 생산지시 조회 ===== */
    public List<Map<String,Object>> getOpenOrders() {
        return dao.getOpenOrders(null);
    }

    /* ===== 상세 실적 검색 ===== */
    public Map<String,Object> searchResultsOnly(String fromStr, String toStr, String orderIdStr,
                                                Integer page, Integer size) {
        Date from = toSqlDate(fromStr);
        Date to   = toSqlDate(toStr);
        Integer orderId = toInt(orderIdStr);

        int p = (page == null || page <= 0) ? 1 : page;
        int s = (size == null || size <= 0) ? 20 : size;

        List<Map<String, Object>> results = dao.selectResults(from, to, orderId, p, s);
        int total = dao.countResults(from, to, orderId);

        Map<String, Object> out = new HashMap<>();
        out.put("results", results);
        out.put("total", total);
        out.put("page", p);
        out.put("size", s);
        out.put("from", fromStr);
        out.put("to", toStr);
        out.put("orderId", orderIdStr);
        return out;
    }

    /* ===== 달성률 요약 검색 ===== */
    public Map<String,Object> searchSummaryOnly(String fromStr, String toStr, String orderIdStr) {
        Date from = toSqlDate(fromStr);
        Date to   = toSqlDate(toStr);
        Integer orderId = toInt(orderIdStr);

        List<Map<String, Object>> summary = dao.selectSummary(from, to, orderId);

        Map<String, Object> out = new HashMap<>();
        out.put("summary", summary);
        out.put("from", fromStr);
        out.put("to", toStr);
        out.put("orderId", orderIdStr);
        return out;
    }

    /* ===== 실적 등록 (등록 후 상태 DONE 처리) ===== */
    public boolean insertResult(ProductionResultDTO dto) {
        if (dto == null) return false;
        if (dto.getOrder_id() <= 0) return false;
        if (dto.getWorker_id() <= 0) return false;
        if (dto.getGood_qty() < 0 || dto.getFail_qty() < 0) return false;
        if (dto.getWork_date() == null) return false;

        try {
            // 1. 실적 등록
            int r = dao.insertResult(
                dto.getOrder_id(),
                dto.getWorker_id(),
                BigDecimal.valueOf(dto.getGood_qty()),
                BigDecimal.valueOf(dto.getFail_qty()),
                null, null, null,
                Timestamp.valueOf(dto.getWork_date())
            );

            // 2. 성공 시 상태 DONE으로 변경
            if (r == 1) {
                dao.updateOrderStatus(dto.getOrder_id(), "DONE");
            }

            return r == 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
