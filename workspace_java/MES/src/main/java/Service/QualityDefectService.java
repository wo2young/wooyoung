package Service;

import DAO.QualityDefectDAO;
import yaDTO.QualityDefectDTO;
import yaDTO.CodeDetailDTO;

import java.sql.Date;   // ✅ 꼭 import
import java.util.List;
import java.util.Map;

public class QualityDefectService {
    private final QualityDefectDAO dao = new QualityDefectDAO();

    // ✅ 불량 등록 (DAO에서 FAIL_QTY 부족 시 false 반환)
    public boolean insertDefect(QualityDefectDTO dto) {
        try {
            return dao.insert(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 예외 발생 시 실패 처리
        }
    }

    // ✅ 전체 실적번호 + FAIL_QTY 가져오기
    public List<QualityDefectDTO> getThisWeekResults() {
        return dao.getThisWeekResults();
    }

    // ✅ 불량 코드 목록 가져오기
    public List<CodeDetailDTO> getDefectCodes() {
        return dao.getDefectCodes();
    }

    // ✅ 상세 조회 (코드별 최신 불량)
    public QualityDefectDTO getDefectDetail(String defectCode) {
        return dao.findByCode(defectCode);
    }

    // ✅ 불량 현황 집계 (전체)
    public List<QualityDefectDTO> getDefectStats() {
        return dao.getDefectStats(null, null);
    }

    // ✅ 불량 현황 집계 (날짜검색)
    public List<QualityDefectDTO> getDefectStats(Date startDate, Date endDate) {
        return dao.getDefectStats(startDate, endDate);
    }

    // ✅ 불량 요약 (전체)
    public Map<String, Object> getDefectSummary() {
        return dao.getDefectSummary(null, null);
    }

    // ✅ 불량 요약 (날짜검색)
    public Map<String, Object> getDefectSummary(Date startDate, Date endDate) {
        return dao.getDefectSummary(startDate, endDate);
    }
}
