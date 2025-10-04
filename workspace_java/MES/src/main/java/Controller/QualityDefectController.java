package Controller;

import yaDTO.QualityDefectDTO;
import yaDTO.UserDTO;
import yaDTO.CodeDetailDTO;       // 불량코드 DTO
import Service.QualityDefectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/quality/new")
public class QualityDefectController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final QualityDefectService service = new QualityDefectService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            // ✅ 상세 조회 (불량코드 기준)
            if ("detail".equals(action)) {
                String code = request.getParameter("code");
                if (code != null && !code.isEmpty()) {
                    QualityDefectDTO detail = service.getDefectDetail(code);
                    request.setAttribute("detail", detail);

                    request.getRequestDispatcher("/WEB-INF/views/QualityDefectStatus.jsp")
                           .forward(request, response);
                    return;
                }
            }

            // ✅ 불량 현황
            if ("status".equals(action)) {
                String searchStart = request.getParameter("searchStart");
                String searchEnd = request.getParameter("searchEnd");

                Date startDate = (searchStart != null && !searchStart.isEmpty())
                        ? Date.valueOf(searchStart) : null;
                Date endDate = (searchEnd != null && !searchEnd.isEmpty())
                        ? Date.valueOf(searchEnd) : null;

                List<QualityDefectDTO> stats = service.getDefectStats(startDate, endDate);
                Map<String, Object> summary = service.getDefectSummary(startDate, endDate);

                request.setAttribute("defectStats", stats);
                request.setAttribute("summary", summary);

                // 검색값 유지
                request.setAttribute("searchStart", searchStart);
                request.setAttribute("searchEnd", searchEnd);

                request.getRequestDispatcher("/WEB-INF/views/QualityDefectStatus.jsp")
                       .forward(request, response);
                return;
            }

            // ✅ 기본: 불량 등록 폼
            List<QualityDefectDTO> thisWeekResults = service.getThisWeekResults();
            request.setAttribute("thisWeekResults", thisWeekResults);

            List<CodeDetailDTO> defectCodes = service.getDefectCodes();
            request.setAttribute("defectCodes", defectCodes);

            request.getRequestDispatcher("/WEB-INF/views/QualityDefect.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "데이터 조회 중 오류 발생");
            request.getRequestDispatcher("/WEB-INF/views/QualityDefect.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // ✅ 로그인 사용자 확인
            UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
            if (loginUser == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // ✅ 입력값 받기
            String resultIdStr = request.getParameter("result_id");
            String detailCode = request.getParameter("detail_code");
            String qtyStr = request.getParameter("quantity");

            if (resultIdStr == null || detailCode == null || qtyStr == null ||
                resultIdStr.isEmpty() || detailCode.isEmpty() || qtyStr.isEmpty()) {
                request.setAttribute("errorMsg", "모든 입력값을 채워주세요.");
                doGet(request, response);
                return;
            }

            int resultId = Integer.parseInt(resultIdStr);
            int quantity = Integer.parseInt(qtyStr);

            if (quantity <= 0) {
                request.setAttribute("errorMsg", "불량 수량은 1 이상이어야 합니다.");
                doGet(request, response);
                return;
            }

            // ✅ DTO 생성
            QualityDefectDTO dto = new QualityDefectDTO();
            dto.setResult_id(resultId);
            dto.setDefect_code(detailCode);
            dto.setQuantity(quantity);
            dto.setRegistered_by(loginUser.getUser_id());

            boolean success = service.insertDefect(dto);

            if (success) {
                // ✅ 등록 완료
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().println("<script>alert('등록 완료'); location.href='" 
                        + request.getContextPath() + "/quality/new';</script>");
            } else {
                // ✅ DAO에서 false 리턴된 경우 (수량 부족 → 롤백됨)
                request.setAttribute("errorMsg", "등록 실패: 불량 수량이 현재 남은 수량보다 큽니다.");
                doGet(request, response);
            }
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            request.setAttribute("errorMsg", "숫자 입력값이 잘못되었습니다.");
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "요청 처리 중 오류 발생");
            doGet(request, response);
        }
    }
}
