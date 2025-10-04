package DAO;

import yaDTO.QualityDefectDTO;
import yaDTO.CodeDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class QualityDefectDAO {
    private DataSource ds;

    public QualityDefectDAO() {
        try {
            Context init = new InitialContext();
            ds = (DataSource) init.lookup("java:comp/env/jdbc/oracle");
        } catch (Exception e) {
            throw new RuntimeException("DB 연결 오류: " + e.getMessage());
        }
    }

    // ✅ 불량 등록 (DB에만 insert, FAIL_QTY는 그대로 둠)
    public boolean insert(QualityDefectDTO dto) {
        String insertSql = "INSERT INTO QUALITY_DEFECT " +
                           "(DEFECT_ID, RESULT_ID, DETAIL_CODE, QUANTITY, REGISTERED_BY, CREATED_AT) " +
                           "VALUES (SEQ_QUALITY_DEFECT.NEXTVAL, ?, ?, ?, ?, SYSTIMESTAMP)";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            ps.setInt(1, dto.getResult_id());
            ps.setString(2, dto.getDefect_code());
            ps.setInt(3, dto.getQuantity());
            ps.setInt(4, dto.getRegistered_by());

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ 전체 실적번호 조회 (FAIL_QTY - 등록된 불량수량 합계)
    public List<QualityDefectDTO> getThisWeekResults() {
        String sql =
            "SELECT r.RESULT_ID, r.ORDER_ID, r.WORKER_ID, " +
            "       r.GOOD_QTY, r.FAIL_QTY, r.WORK_DATE, " +
            "       u.NAME AS WORKER_NAME, " +
            "       im.ITEM_NAME, " +
            "       (r.FAIL_QTY - NVL(SUM(q.QUANTITY),0)) AS REMAIN_FAIL_QTY " +
            "FROM PRODUCTION_RESULT r " +
            "JOIN PRODUCTION_ORDER o ON r.ORDER_ID = o.ORDER_ID " +
            "JOIN PRODUCTION_TARGET t ON o.TARGET_ID = t.TARGET_ID " +
            "JOIN ITEM_MASTER im ON t.ITEM_ID = im.ITEM_ID " +
            "LEFT JOIN USER_T u ON r.WORKER_ID = u.USER_ID " +
            "LEFT JOIN QUALITY_DEFECT q ON r.RESULT_ID = q.RESULT_ID " +
            "GROUP BY r.RESULT_ID, r.ORDER_ID, r.WORKER_ID, " +
            "         r.GOOD_QTY, r.FAIL_QTY, r.WORK_DATE, u.NAME, im.ITEM_NAME " +
            "ORDER BY r.WORK_DATE DESC";

        List<QualityDefectDTO> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                QualityDefectDTO dto = new QualityDefectDTO();
                dto.setResult_id(rs.getInt("RESULT_ID"));
                dto.setOrder_id(rs.getInt("ORDER_ID"));
                dto.setWorker_id(rs.getInt("WORKER_ID"));
                dto.setWorker_name(rs.getString("WORKER_NAME"));
                dto.setItem_name(rs.getString("ITEM_NAME"));

                // ✅ 원본 FAIL_QTY
                dto.setDefect_quantity(rs.getInt("FAIL_QTY"));

                // ✅ 남은 FAIL_QTY (보여줄 용도)
                dto.setRemain_fail_qty(rs.getInt("REMAIN_FAIL_QTY"));

                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ 불량 코드 목록 가져오기 (DEF만)
    public List<CodeDetailDTO> getDefectCodes() {
        String sql = "SELECT DETAIL_CODE, CODE_ID, CODE_DNAME " +
                     "FROM CODE_DETAIL " +
                     "WHERE IS_ACTIVE = 'Y' AND CODE_ID = 'DEF' " +
                     "ORDER BY DETAIL_CODE";

        List<CodeDetailDTO> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CodeDetailDTO dto = new CodeDetailDTO();
                dto.setDetail_code(rs.getString("DETAIL_CODE"));
                dto.setCode_id(rs.getString("CODE_ID"));
                dto.setCode_Dname(rs.getString("CODE_DNAME"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ 불량 상세 조회 (코드 기준)
    public QualityDefectDTO findByCode(String defectCode) {
        String sql =
            "SELECT q.DEFECT_ID, q.RESULT_ID, q.DETAIL_CODE, q.QUANTITY, " +
            "       q.REGISTERED_BY, q.CREATED_AT, " +
            "       c.CODE_DNAME AS DEFECT_NAME, " +
            "       u.NAME AS WORKER_NAME " +
            "FROM QUALITY_DEFECT q " +
            "JOIN CODE_DETAIL c ON q.DETAIL_CODE = c.DETAIL_CODE " +
            "LEFT JOIN PRODUCTION_RESULT r ON q.RESULT_ID = r.RESULT_ID " +
            "LEFT JOIN USER_T u ON r.WORKER_ID = u.USER_ID " +
            "WHERE q.DETAIL_CODE = ? " +
            "ORDER BY q.CREATED_AT DESC FETCH FIRST 1 ROWS ONLY";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, defectCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                QualityDefectDTO dto = new QualityDefectDTO();
                dto.setDefect_id(rs.getInt("DEFECT_ID"));
                dto.setResult_id(rs.getInt("RESULT_ID"));
                dto.setDefect_code(rs.getString("DETAIL_CODE"));
                dto.setQuantity(rs.getInt("QUANTITY"));
                dto.setRegistered_by(rs.getInt("REGISTERED_BY"));

                Timestamp ts = rs.getTimestamp("CREATED_AT");
                if (ts != null) {
                    dto.setCreated_at(ts.toLocalDateTime());
                }

                dto.setDefect_name(rs.getString("DEFECT_NAME"));
                dto.setWorker_name(rs.getString("WORKER_NAME"));
                return dto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ 불량 현황 집계
    public List<QualityDefectDTO> getDefectStats(Date startDate, Date endDate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.DETAIL_CODE, c.CODE_DNAME, ");
        sql.append("       COUNT(q.DEFECT_ID) AS occurrence_count, ");
        sql.append("       NVL(SUM(q.QUANTITY), 0) AS total_quantity ");
        sql.append("FROM CODE_DETAIL c ");
        sql.append("LEFT JOIN QUALITY_DEFECT q ON c.DETAIL_CODE = q.DETAIL_CODE ");
        sql.append("WHERE c.IS_ACTIVE = 'Y' AND c.CODE_ID = 'DEF' ");

        if (startDate != null) sql.append("AND q.CREATED_AT >= ? ");
        if (endDate != null) sql.append("AND q.CREATED_AT < (? + 1) ");

        sql.append("GROUP BY c.DETAIL_CODE, c.CODE_DNAME ");
        sql.append("ORDER BY c.DETAIL_CODE");

        List<QualityDefectDTO> list = new ArrayList<>();
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (startDate != null) ps.setDate(idx++, startDate);
            if (endDate != null) ps.setDate(idx++, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    QualityDefectDTO dto = new QualityDefectDTO();
                    dto.setDefect_code(rs.getString("DETAIL_CODE"));
                    dto.setDefect_name(rs.getString("CODE_DNAME"));
                    dto.setOccurrence_count(rs.getInt("occurrence_count"));
                    dto.setTotal_quantity(rs.getInt("total_quantity"));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ 불량 요약
    public Map<String, Object> getDefectSummary(Date startDate, Date endDate) {
        Map<String, Object> result = new HashMap<>();

        StringBuilder sqlTotalDefects = new StringBuilder("SELECT NVL(SUM(quantity),0) AS total_defects FROM quality_defect WHERE 1=1 ");
        StringBuilder sqlTotalProd = new StringBuilder("SELECT NVL(SUM(good_qty + fail_qty),0) AS total_prod FROM production_result WHERE 1=1 ");

        if (startDate != null) {
            sqlTotalDefects.append("AND created_at >= ? ");
            sqlTotalProd.append("AND work_date >= ? ");
        }
        if (endDate != null) {
            sqlTotalDefects.append("AND created_at < (? + 1) ");
            sqlTotalProd.append("AND work_date < (? + 1) ");
        }

        try (Connection conn = ds.getConnection()) {
            int totalDefects = 0;
            int totalProd = 0;

            try (PreparedStatement ps = conn.prepareStatement(sqlTotalDefects.toString())) {
                int idx = 1;
                if (startDate != null) ps.setDate(idx++, startDate);
                if (endDate != null) ps.setDate(idx++, endDate);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        totalDefects = rs.getInt("total_defects");
                        result.put("totalDefects", totalDefects);
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlTotalProd.toString())) {
                int idx = 1;
                if (startDate != null) ps.setDate(idx++, startDate);
                if (endDate != null) ps.setDate(idx++, endDate);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        totalProd = rs.getInt("total_prod");
                    }
                }
            }

            double rate = 0.0;
            if (totalProd > 0) {
                rate = (double) totalDefects / totalProd * 100.0;
            }
            result.put("defectRate", Math.round(rate * 100.0) / 100.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
