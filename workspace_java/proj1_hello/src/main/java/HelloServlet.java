import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/hello.human") 
// @WebServlet 애너테이션을 이용한 서블릿 매핑
// → "/hello.human"으로 요청이 들어오면 이 서블릿이 실행됨
// 예: http://localhost:8080/프로젝트명/hello.human
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        System.out.println("Hello Servlet"); 
        // 서블릿 객체 생성 시 1번 호출됨
        // 톰캣이 서블릿을 메모리에 로딩할 때 생성자 실행
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
//		response.getWriter().append("<h1>Served</h1> at: ").append(request.getContextPath());
    	
    	// 응답 콘텐츠 타입 지정 (HTML, UTF-8 인코딩)
    	// 클라이언트가 받은 데이터의 형식과 인코딩을 정확히 알 수 있도록 설정
    	response.setContentType("text/html; charset=utf-8"); 
    	
    	// PrintWriter : 응답 본문에 텍스트를 쓰기 위한 출력 스트림
    	PrintWriter out = response.getWriter();
    	
    	// HTML 태그로 시작
    	out.println("<h1>");
    	out.print("Hello Servlet 한글"); // 한글 출력
    	out.println("</h1>");
    	
    	// 0~9까지 반복하며 랜덤으로 절반 확률로 숫자 출력
    	for(int i=0; i<10; i++) {
    		if(Math.random() < 0.5) {
    			out.println(i);    // 숫자 출력
    			out.println("<br>"); // 줄바꿈
    		}
    	}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		// POST 요청이 들어와도 doGet() 로직을 그대로 실행
		doGet(request, response);
	}

}

/*
===== 전체 정리 =====
1) @WebServlet("/hello.human")
   - 해당 URL 패턴으로 요청이 오면 이 서블릿이 실행
   - web.xml 설정 없이 매핑 가능
   - ".human" 같은 확장자도 지정 가능 (RESTful 스타일도 가능)

2) 생성자
   - 서블릿 객체가 처음 로드될 때 1회 실행
   - 요청이 올 때마다 실행되는 게 아니라,
     최초 로드 시점 또는 컨테이너 재시작 시점에 실행

3) doGet()
   - GET 요청 처리 메서드
   - response.setContentType("text/html; charset=utf-8")
     → HTML 페이지를 UTF-8로 인코딩하여 전송
   - PrintWriter를 통해 HTML 태그를 직접 작성 가능
   - Math.random() < 0.5 조건으로 일부 숫자만 출력

4) doPost()
   - POST 요청 처리 메서드
   - 여기서는 doGet()을 호출하여 GET과 동일 로직 사용

5) 실행 흐름
   - 브라우저가 /hello.human 요청
   - 톰캣이 해당 서블릿 객체 로드 → doGet() 실행
   - HTML 형태 응답 생성 → 브라우저에 출력
====================
*/
