package cookie;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cookie/set")
public class SetCookie extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글 깨짐 방지
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        
        // 만료일이 있는 쿠키 (10초)
        Cookie c1 = new Cookie("addr", "천안시");
        c1.setMaxAge(10); // 초단위        
        response.addCookie(c1);
        
        // 세션 쿠키 (브라우저 닫을 때만 사라짐)
        Cookie c2 = new Cookie("dinner", "회");
        c2.setMaxAge(-1);
        response.addCookie(c2);
        
        // 12초짜리 쿠키
        Cookie c3 = new Cookie("showPopup", "N");
        c3.setMaxAge(1);   // 1초로 수정함!
        response.addCookie(c3);
    }
}
