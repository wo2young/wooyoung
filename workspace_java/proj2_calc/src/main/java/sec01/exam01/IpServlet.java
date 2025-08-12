package sec01.exam01;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IpServlet
 * 
 * 클라이언트의 접속 정보(IP, 요청 URL, 쿼리스트링 등)를 확인하는 서블릿 예제.
 */
//@WebServlet("/ip") 
// 매핑을 주석 처리해두었으므로 web.xml에 직접 매핑이 없으면 이 서블릿은 실행되지 않음.
// 실습 시 @WebServlet("/ip")를 활성화하면 http://localhost:8080/컨텍스트경로/ip 로 호출 가능.
public class IpServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		
		System.out.println("/ip doGet 실행");
		
		// ip 주소를 가져오는 것 중에 하나
        // - 클라이언트의 실제 IP
        // - 단, 프록시나 로드밸런서를 거치면 프록시 IP가 나올 수 있음
		String ip = request.getRemoteAddr();
		System.out.println("ip : " + ip);
		
		// 접근 method 확인
        // - HTTP 메서드(GET, POST 등)
        // - request.getMethod()는 항상 대문자 문자열로 반환됨
		System.out.println("getMethod : " + request.getMethod());
		
		// 접근 주소 전체. 단, 쿼리스트링 제외
        // - 예: http://localhost:8080/proj2_calc/ip
        // - URL 전체(프로토콜, 도메인, 포트, 경로) 반환
		System.out.println("getRequestURL : " + request.getRequestURL());
		
		// 쿼리스트링만 가져오기
        // - 예: URL이 /ip?num=10&name=woo 일 경우 "num=10&name=woo"
        // - 쿼리 파라미터가 없으면 null
		System.out.println("getQueryString : "+ request.getQueryString());
		
		// ip, port, 쿼리스트링을 제외한 주소(URI)
        // - Context Path + 서블릿 경로
        // - 예: "/proj2_calc/ip"
		System.out.println("getRequestURI :"+request.getRequestURI());
		
		// 프로젝트를 구분하는 주소
        // - Context root (=웹 애플리케이션 이름)
        // - 예: "/proj2_calc"
		System.out.println("getContextPath : "+request.getContextPath());
	}

}

/*
===== 전체 정리 =====
1) request.getRemoteAddr()
   - 클라이언트 IP 주소
   - 프록시/방화벽/로드밸런서 뒤에 있으면 실제 IP가 아니라 중간 장비 IP가 반환될 수 있음
   - 실제 클라이언트 IP는 "X-Forwarded-For" HTTP 헤더에서 가져오는 경우도 많음(실무)

2) request.getMethod()
   - 요청에 사용된 HTTP 메서드 반환("GET", "POST" 등)

3) request.getRequestURL()
   - 프로토콜, 호스트명, 포트번호, 경로를 포함한 전체 URL
   - StringBuffer로 반환됨(append 가능)

4) request.getQueryString()
   - URL의 "?" 이후 부분(쿼리 파라미터)
   - 없으면 null

5) request.getRequestURI()
   - Context Path + Servlet Path
   - 쿼리스트링 제외
   - 도메인, 포트 없음

6) request.getContextPath()
   - 웹 애플리케이션의 Context Root
   - 배포 시 프로젝트 이름 또는 서버 설정에 따라 결정
====================
*/
