package cookie;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetCookies
 */
@WebServlet("/cookie/get")
public class GetCookies extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        boolean isShow = true; // 기본: 팝업 표시
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                
                Cookie c = cookies[i];
                String name = c.getName();
                String value = c.getValue();
                
                System.out.println("name: " + name + ", value: " + value);
                
                // showPopup=N 쿠키가 있으면 팝업 안 보이게
                if ("showPopup".equals(name) && "N".equals(value)) {
                    isShow = false;
                    break; // 찾았으면 더 돌 필요 없음
                }
            }
        }

        if (isShow) {
            response.getWriter().print("팝업 표시");
        } else {
            response.getWriter().print("팝업 없음");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
