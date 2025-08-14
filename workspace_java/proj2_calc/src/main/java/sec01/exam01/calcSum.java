package sec01.exam01;

import java.io.IOException;

import javax.servlet.ServletException;
// Tomcat 9.x 이므로 javax.servlet.* 사용이 맞다. (Tomcat 10+ 는 jakarta.*)
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 숫자 두 개(NUM1, NUM2)를 입력받아 합계를 계산해 HTML로 응답하는 서블릿.
 * 매핑 URL은 /calc 이다.
 */
@WebServlet("/calc") // 이 서블릿은 /컨텍스트경로/calc 로 매핑된다. 예: http://localhost:8080/proj2_calc/calc
public class calcSum extends HttpServlet {
	private static final long serialVersionUID = 1L;

    // 기본 생성자. 특별한 작업은 없음.
    public calcSum() {
        super();
    }

	/**
	 * GET 요청 처리:
	 * - 폼(method="get") 제출 또는 /calc?NUM1=..&NUM2=.. 요청을 처리한다.
	 * - 파라미터는 문자열로 들어오므로 정수로 파싱한다.
	 * - 합계를 계산하여 간단한 HTML로 출력한다.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 파라미터 추출: name="NUM1", name="NUM2" 에 해당
		String str1 = request.getParameter("NUM1"); // 값 없으면 null 또는 "" 가능
		String str2 = request.getParameter("NUM2");

        // 주의: 빈 문자열("")이나 null 이면 Integer.parseInt에서 NumberFormatException 발생
        // 실무에서는 try-catch로 검증/기본값 처리 필요. 여기서는 원본 로직 유지.
		try {
			int num1 = Integer.parseInt(str1);
			int num2 = Integer.parseInt(str2);
	
			int sum = num1 + num2;
		
        // 화면에 직접 보이지 않는 서버 콘솔 로그. 브라우저는 이 출력을 볼 수 없다.
		// System.out.println(num1);
		// System.out.println(num2);

        // Content-Type 지정: 브라우저에게 "HTML이며 UTF-8 인코딩"임을 알려준다.
        // 권장 표기는 "charset=UTF-8" 이다. (대소문자 구분은 보통 없지만 표준 표기 사용 권장)
        // 원본 라인은 유지하고, 권장 라인을 주석으로 안내한다.
		response.setContentType("text/html; charSet=utf-8"); // 원본
        // response.setContentType("text/html; charset=UTF-8"); // 권장 표기

        // 간단한 HTML로 결과 출력
			response.getWriter().println("<h1>NUM1 " + num1 + "</h1>");
			response.getWriter().println("<h1>NUM2 " + num2 + "</h1>");
			response.getWriter().println("<h1>SUM " + sum + "</h1>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * POST 요청 처리:
	 * - 현재는 비어 있음. 폼을 POST로 바꿀 경우 doPost에서 처리하도록 구현하면 된다.
	 * - POST로 한글 파라미터를 받을 땐, doPost 시작부에서 request.setCharacterEncoding("UTF-8") 필요.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 예시:
        // request.setCharacterEncoding("UTF-8"); // POST 본문 파싱 전에 호출해야 함. [중요][실무 자주]
        // String str1 = request.getParameter("NUM1");
        // ...
	}

}

/*
전체 정리
1) 매핑: @WebServlet("/calc") 이면 최종 URL은 /{컨텍스트}/calc 이다. 컨텍스트는 Eclipse Web Project Settings의 Context root 또는 Servers 모듈 Path로 결정. [중요][실무 자주]

2) 파라미터 수신: request.getParameter("키")로 항상 문자열로 받는다.
   - 숫자로 사용할 땐 Integer.parseInt 등으로 변환.
   - 빈 문자열이나 null일 가능성이 있으므로 NumberFormatException 방어 로직을 넣는 것이 안전하다. [중요]

3) 인코딩/콘텐츠 타입:
   - HTML 응답: response.setContentType("text/html; charset=UTF-8")
   - JSON 응답: response.setContentType("application/json; charset=UTF-8")
   - POST 본문 인코딩: doPost에서 request.setCharacterEncoding("UTF-8")를 파라미터 읽기 전에 호출. [중요][실무 자주]

4) GET vs POST:
   - GET: 주소창에 파라미터 노출, 길이 제한, 캐시될 수 있음. 조회성 요청에 적합.
   - POST: 본문으로 전송, 주소창 비노출, 길이 제한 완화. 등록/수정 등 상태 변화에 적합.

5) 상대경로 action의 주의점:
   - JSP가 하위 폴더에 있으면 action="calc"는 /{컨텍스트}/{현재폴더}/calc 로 향한다.
   - 안전하게는 action="${pageContext.request.contextPath}/calc" 사용 권장. [실무 자주]

6) 콘솔 출력 vs 브라우저 출력:
   - System.out.println(...) 은 서버 콘솔에만 보인다.
   - 클라이언트 화면에 보이려면 response.getWriter()로 써야 한다.

7) Tomcat 9.x와 패키지:
   - Tomcat 9.x 는 javax.servlet.* 가 맞다.
   - Tomcat 10+ 는 jakarta.servlet.* 로 변경되었으므로 혼용하지 않도록 주의. [중요]
*/
