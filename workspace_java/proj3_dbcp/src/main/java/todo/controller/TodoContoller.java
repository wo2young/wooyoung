package todo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.DTO.TodoDTO;
import todo.service.TodoService;

@WebServlet("/todo")
public class TodoContoller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("/todo doGet 실행");

        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        TodoService todoService = new TodoService();

        // command 파라미터로 기능 구분 (detail / del / insert)
        String command = request.getParameter("command");

        // ====================== [상세 조회 → 개별 조회] ======================
        if ("detail".equals(command)) {
            String str_tno = request.getParameter("tno"); // 조회할 tno
            if (str_tno != null) {
                int tno = Integer.parseInt(str_tno);
                TodoDTO todoDTO2 = todoService.getTodo(tno);

                if (todoDTO2 != null) {
                    // 단일행 테이블 출력
                    out.println("<h2>개별 조회 결과</h2>");
                    out.println("<table border=1 cellspacing=0 cellpadding=5>");
                    out.println("<tr><th>번호</th><th>제목</th><th>마감일</th><th>완료</th><th>삭제</th><th>상세보기</th></tr>");

                    out.println("<tr>");
                    out.println("<td>" + todoDTO2.getTno() + "</td>");
                    out.println("<td>" + todoDTO2.getTitle() + "</td>");
                    out.println("<td>" + todoDTO2.getDuedate() + "</td>");
                    out.println("<td>" + todoDTO2.getFinished() + "</td>");

                    // 삭제 버튼
                    out.println("<td>");
                    out.println("  <form method='post' action='todo' style='display:inline;'>");
                    out.println("    <input type='hidden' name='tno' value='" + todoDTO2.getTno() + "'>");
                    out.println("    <input type='hidden' name='command' value='del'>");
                    out.println("    <input type='submit' value='삭제'>");
                    out.println("  </form>");
                    out.println("</td>");

                    // 상세보기 버튼 (현재 detail이지만 일관성을 위해 유지)
                    out.println("<td>");
                    out.println("  <form method='get' action='todo' style='display:inline;'>");
                    out.println("    <input type='hidden' name='tno' value='" + todoDTO2.getTno() + "'>");
                    out.println("    <input type='hidden' name='command' value='detail'>");
                    out.println("    <input type='submit' value='상세보기'>");
                    out.println("  </form>");
                    out.println("</td>");

                    out.println("</tr>");
                    out.println("</table>");
                    
                    out.println("<a href='modify?tno="+ todoDTO2.getTno()+"'><button>수정</button></a>");

                    // 다시 전체 목록으로 돌아가는 링크
                    out.println("<br><a href='todo'>목록으로</a>");
                } else {
                    out.println("<p>해당 데이터가 존재하지 않습니다.</p>");
                    out.println("<br><a href='todo'>목록으로</a>");
                }
            }
        }
        // ====================== [목록 조회 - 기본 화면] ======================
        else {
            List<TodoDTO> list = todoService.getList();

            out.println("<h2>할 일 목록</h2>");
            out.println("<table border=1 cellspacing=0 cellpadding=5>");
            out.println("<tr><th>번호</th><th>제목</th><th>마감일</th><th>완료</th><th>삭제</th><th>상세보기</th></tr>");

            for (TodoDTO dto : list) {
                out.println("<tr>");
                out.println("<td>" + dto.getTno() + "</td>");
                out.println("<td>" + dto.getTitle() + "</td>");
                out.println("<td>" + dto.getDuedate() + "</td>");
                out.println("<td>" + dto.getFinished() + "</td>");

                // 삭제 버튼
                out.println("<td>");
                out.println("  <form method='post' action='todo' style='display:inline;'>");
                out.println("    <input type='hidden' name='tno' value='" + dto.getTno() + "'>");
                out.println("    <input type='hidden' name='command' value='del'>");
                out.println("    <input type='submit' value='삭제'>");
                out.println("  </form>");
                out.println("</td>");

                // 상세보기 버튼
                out.println("<td>");
                out.println("  <form method='get' action='todo' style='display:inline;'>");
                out.println("    <input type='hidden' name='tno' value='" + dto.getTno() + "'>");
                out.println("    <input type='hidden' name='command' value='detail'>");
                out.println("    <input type='submit' value='상세보기'>");
                out.println("  </form>");
                out.println("</td>");

                out.println("</tr>");
            }
            out.println("</table>");

            // 추가 버튼 (등록 페이지로 이동)
            out.println("<br><a href='register.html'><button>추가</button></a>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("/todo doPost 실행");

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        TodoDTO todoDTO = new TodoDTO();
        TodoService todoService = new TodoService();

        String command = request.getParameter("command");

        // ====================== [삭제] ======================
        if ("del".equals(command)) {
            String str_tno = request.getParameter("tno");
            System.out.println("str_tno: " + str_tno);

            try {
                int tno = Integer.parseInt(str_tno);
                todoDTO.setTno(tno);

                int result = todoService.removeTodo(todoDTO);
                System.out.println(result + "행 이(가) 삭제되었습니다.");

                if (result == -1) {
                    response.getWriter().println("<script>");
                    response.getWriter().println("alert('삭제 실패')");
                    response.getWriter().println("</script>");
                }
                // 성공/실패 관계없이 다시 목록으로 이동
                response.sendRedirect("todo");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // ====================== [추가] ======================
        else {
            String title = request.getParameter("title");
            String duedate = request.getParameter("duedate");

            todoDTO.setTitle(title);

            // String → java.sql.Date 변환
            Date date_duedate = Date.valueOf(duedate);
            todoDTO.setDuedate(date_duedate);

            System.out.println(todoDTO);

            int result = todoService.addTodo(todoDTO);
            if (result == -1) {
                response.getWriter().println("<script>");
                response.getWriter().println("alert('추가 실패')");
                response.getWriter().println("</script>");
            } else {
                response.sendRedirect("todo");
            }
        }
    }
}
