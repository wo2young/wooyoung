package sec01.exam01;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RequestServlet
 * 
 * 이 서블릿은 GET/POST 방식으로 전달된 각종 파라미터를 확인하고,
 * 콘솔에 출력한 뒤 JSON 형식의 응답을 반환하는 예제입니다.
 */
@WebServlet("/req") // URL 매핑: /컨텍스트경로/req 로 접근 가능
public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * GET 요청 처리
	 * - 브라우저에서 method="get" 폼 제출 시 호출됨
	 * - URL 쿼리스트링에 있는 key=value 파라미터를 읽어 콘솔에 출력
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		
		// 요청 할 떄 한글 꺠짐 방지
		request.setCharacterEncoding("utf-8");
		
		// 응답 할 떄 한글 깨짐 방지
		response.setContentType("text/html;charset=utf-8");

        // 서블릿이 호출될 때 콘솔 로그
	    System.out.println("/req doGet 실행");
	
        // 단일 파라미터 값 읽기: name="num1"
	    String num1 = request.getParameter("num1");
	    System.out.println("num1: " + num1);
	
	    // 없는 파라미터 요청 시 null 반환 (예: name="num2"가 폼에 없으면)
	    String num2 = request.getParameter("num2");
	    System.out.println("num2: " + num2);
	
	    // 비밀번호 입력값
	    String pw = request.getParameter("pw");
	    System.out.println("pw: " + pw);
	
	    // 체크박스: 동일 name의 첫 번째 값만 반환 (권장: getParameterValues 사용)
	    System.out.println("check: " + request.getParameter("check"));
	
	    // 체크박스 다중 값 읽기
	    String[] checks = request.getParameterValues("check");
	    System.out.println("checks: " + checks); // 배열 자체 출력 시 참조값만 나옴
	    if (checks != null) { // null 체크: 체크박스 선택이 없으면 null
		    for (String chk : checks) {
			    System.out.println(chk); // 각 체크박스 value 출력
		    }
	    }
	
	    // hidden input 값
	    System.out.println("hidden1: " + request.getParameter("hidden1"));

	    // 버튼 값: submit이 아니면 null (type="button"은 전송 안 됨)
	    System.out.println("btn1: " + request.getParameter("btn1"));
	    System.out.println("btn2: " + request.getParameter("btn2"));
	
	    // 라디오 버튼: 동일 name 중 선택된 value만 전송
	    System.out.println("radio: " + request.getParameter("radio1"));
	
	    // 날짜
	    System.out.println("date1: " + request.getParameter("date1"));
	
	    // 숫자 입력
	    System.out.println("number1: " + request.getParameter("number1"));

	    // div는 form 전송 대상 아님 (value 속성 무시) → 항상 null
	    System.out.println("div1: " + request.getParameter("div1"));

	    // select 박스 선택 값
	    System.out.println("select1: " + request.getParameter("select1"));

	    // textarea 값 (태그 안의 텍스트가 값, value 속성은 무시)
	    System.out.println("textarea1: " + request.getParameter("textarea1"));
	
	    // JSON 응답 (단순 테스트)
        // Content-Type 미설정 시 기본 text/html; UTF-8 아님 → 실무에선 반드시 지정
        // 예: response.setContentType("application/json; charset=UTF-8");
	    response.getWriter().println("{\"k\":123}");
	}

	/**
	 * POST 요청 처리
	 * - 브라우저에서 method="post" 폼 제출 시 호출됨
	 * - POST 본문에 있는 파라미터를 읽어 콘솔에 출력
	 * - POST에서 한글 데이터를 받을 경우 request.setCharacterEncoding("UTF-8") 필요 (파라미터 읽기 전)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		
        System.out.println("/req doPost 실행");
		
        // POST 요청 폼의 name="num2" 값 읽기
		System.out.println("num2: " + request.getParameter("num2"));
		
        // JSON 응답
        // GET과 구분하기 위해 다른 값 반환
		response.getWriter().println("{\"k\":456}");
	}

}

/*
===== 전체 정리 =====
1) request.getParameter("이름")
   - GET/POST 방식 모두 사용 가능
   - 항상 String 반환 (없으면 null, 빈 값은 "")
   - 동일 name이 여러 개면 첫 번째 값만 반환

2) request.getParameterValues("이름")
   - 동일 name의 모든 값을 String[]로 반환 (없으면 null)

3) GET vs POST
   - GET: URL 뒤에 쿼리스트링으로 값 전달, 길이 제한 있음, 주소창에 노출
   - POST: HTTP body에 값 전달, 주소창 노출 안 됨, 길이 제한 완화

4) Content-Type 지정
   - HTML 응답: "text/html; charset=UTF-8"
   - JSON 응답: "application/json; charset=UTF-8"

5) POST에서 한글 처리
   - request.setCharacterEncoding("UTF-8")을 반드시 파라미터 읽기 전에 호출해야 함

6) Form 전송 대상
   - name 속성이 있어야 서버에서 읽을 수 있음
   - type="button"은 전송 안 됨 (submit만 가능)
   - div, span 등 일반 태그는 전송 대상 아님
   - textarea의 값은 태그 안 텍스트

7) JSON 응답 예시
   response.setContentType("application/json; charset=UTF-8");
   response.getWriter().write("{\"key\":\"value\"}");
====================
*/
