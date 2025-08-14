// src/main/java/sec01/exam01/CalcServlet.java
package sec01.exam01;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/calc/CalcServlet")
public class CalcServlet extends HttpServlet {

    // 폼 화면 calc.jsp를 Get방식으로 가져옴
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/calc/calcForm.jsp")
               .forward(request, response);
    }

    // 계산 처리 → 결과 화면 calcResult로 Post방식으로 보냄
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String s1 = request.getParameter("num1");
        String s2 = request.getParameter("num2");

        Integer num1 = null;
        Integer num2 = null;
        Integer sum  = null;
        String error = null;

        try {
            if (s1 == null || s1.isBlank() || s2 == null || s2.isBlank()) {
                throw new IllegalArgumentException("두 숫자를 모두 입력하세요.");
            }
            num1 = Integer.valueOf(s1.trim());
            num2 = Integer.valueOf(s2.trim());
            sum  = num1 + num2;
        } catch (NumberFormatException e) {
            error = "정수만 입력하세요.";
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        if (error != null) {
            // 입력값 유지해서 폼으로 다시 보냄
            request.setAttribute("error", error);
            request.setAttribute("prevNum1", s1);
            request.setAttribute("prevNum2", s2);
            request.getRequestDispatcher("/WEB-INF/view/calc/calcForm.jsp")
                   .forward(request, response);
            return;
        }

        // 정상 계산 → 결과 페이지로
        request.setAttribute("num1", num1);
        request.setAttribute("num2", num2);
        request.setAttribute("sum",  sum);
        request.getRequestDispatcher("/WEB-INF/view/calc/calcResult.jsp")
               .forward(request, response);
    }
}
