package kr.or.human3;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/*
 [파일 개요]
 - Spring MVC 컨트롤러 학습용 샘플
 - 뷰 이동 방식: (1) 서블릿 forward, (2) ViewResolver + ModelAndView
 - 파라미터 바인딩 방식: (1) HttpServletRequest로 직접 꺼내기, (2) @RequestParam으로 바인딩
 - 실습 포인트: required 기본값, 기본형 vs 래퍼형, defaultValue, Map 바인딩, 뷰 리졸버 동작
 - 클래스명 오타(ParamConrtoller)는 실행엔 영향 없지만, 혼동 방지를 위해 차후 ParamController로 정리 권장
*/
@Controller
public class ParamConrtoller {
	
	@RequestMapping("/join.do")
	public void joinForm(HttpServletRequest req, HttpServletResponse resp) {
		/*
		 [forward로 join.jsp 열기]
		 - 컨트롤러 메서드에서 직접 RequestDispatcher.forward 호출
		 - URL은 /join.do 그대로 유지되고, 서버 내부에서 /WEB-INF/views/join.jsp로 포워딩됨
		 - ViewResolver를 거치지 않음(경로를 직접 지정)
		*/
		 try {
			req.getRequestDispatcher("/WEB-INF/views/join.jsp").forward(req, resp);
		} catch (ServletException e) {
			// 학습용: 예외 직접 출력
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/join2.do")
	public ModelAndView joinForm2() {
		/*
		 [ViewResolver로 join.jsp 열기]
		 - mav.setViewName("join") 만 설정
		 - InternalResourceViewResolver(prefix/suffix)에 의해 /WEB-INF/views/join.jsp를 찾아감
		   예) prefix=/WEB-INF/views/, suffix=.jsp
		*/
		ModelAndView mav = new ModelAndView();
		mav.setViewName("join");
		return mav;
	}
	
	@RequestMapping("/join3.do")
	public ModelAndView joinForm3() {
		/*
		 - join2.do와 동일하되, 콘솔 로그로 흐름 확인
		*/
		System.out.println("/join3.do 실행");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("join");
		return mav;
	}
	
	@RequestMapping("/join1")
	public void join1(HttpServletRequest req) {
		/*
		 [HttpServletRequest로 직접 파라미터 꺼내기]
		 - 서블릿 스타일로 id 파라미터를 읽는 가장 원초적인 방식
		 - 유효성 검사는 직접 처리해야 함
		*/
		String id = req.getParameter("id");
		System.out.println("id : "+ id);
	}
	
	@RequestMapping("/join2")
	public ModelAndView join2(HttpServletRequest req) {
		/*
		 [요청 속성에 값 저장 → 뷰에서 ${id} 사용]
		 - req.setAttribute("id", id)로 request scope에 저장
		 - forward로 뷰가 열리므로 request scope 유지됨
		 - 주의: redirect면 request scope가 사라짐
		*/
		String id = req.getParameter("id");
		System.out.println("id : "+ id);
		req.setAttribute("id", id);

		ModelAndView mav = new ModelAndView("result");
		return mav;
	}
	
	@RequestMapping("/join3")
	public ModelAndView join3(HttpServletRequest req) {
		/*
		 [ModelAndView로 데이터 담기]
		 - mav.addObject("id", id) → request scope에 자동 바인딩
		 - result.jsp에서 ${id}로 접근
		*/
		String id = req.getParameter("id");
		System.out.println("id : "+ id);
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("id", id);
		return mav;
	}
	
	@RequestMapping("/join4")
	public ModelAndView join4(HttpServletRequest req) {
		/*
		 [DTO 수동 바인딩]
		 - HttpServletRequest에서 값을 꺼내어 직접 DTO에 채우는 방식
		 - 숫자 변환(Integer.parseInt 등) 예외 처리 필요
		 - 스프링의 자동 바인딩(@ModelAttribute)로 대체할 수 있으나, 학습을 위해 수동 예시 유지
		*/
		MemberDTO memberDTO = new MemberDTO();
		try {
			String id = req.getParameter("id");
			String pw = req.getParameter("pw");
			String sAge = req.getParameter("age");
			int age = Integer.parseInt(sAge);
			
			memberDTO.setId(id);
			memberDTO.setPw(pw);
			memberDTO.setAge(age);
		}catch(Exception e) {
			e.printStackTrace();
		}
		// req.setAttribute("id", id); // DTO로 전달하므로 미사용
		
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("memberDTO", memberDTO); // result.jsp에서 ${memberDTO.id} 등으로 접근
		return mav;
	}
	
	@RequestMapping("/join5")
	public ModelAndView join5(
			// String id = req.getParameter("id"); 와 같음
			// 기본적으로 필수 값, 그래서 없으면 400 Bad Reqyest 코드 발생
			// 즉 requied=true가 기본 값
			@RequestParam("id")
			String id,
			
			// @RequestParam("pw") String pw  가 의도한 바에 가까움
			// 현재는 value="name"으로 되어 있어 "pw"가 아닌 "name" 파라미터를 pw 변수에 바인딩함
			// 학습 목적상 현 상태 유지. 실무에선 @RequestParam("pw")로 맞춰야 혼동 없음.
			@RequestParam(value="name", required=true) String pw,
			
			// 필수가 아님, 값이 없으면 null
			@RequestParam(value="name", required=false) 
			String name,
			
			// 기본형(int)은 null 불가. 파라미터가 없으면 400 발생.
			// 필요 시 Integer로 받거나 defaultValue 사용.
			@RequestParam("age") 
			int age, 
			
			// paremeter의 key가 변수명과 같다면 @ReqyestParam을 생략할 수 있다
			// key가 없으면 null
			// 이경우 아래줄이 생략된다. 필수가 아님에 주의!
			// 주의: 스프링 버전에 따라 단순 타입에서 어노테이션 생략시 동작이 다를 수 있으므로,
			//       실무에선 @RequestParam(value="tel", required=false)처럼 명시하는 편이 안전.
			// @RequestParam("tel") 
			String tel, 
			
			// 어노테이션 없음: 변수명과 동일한 쿼리 파라미터가 있으면 바인딩, 없으면 null
			// 문자열이라 null 허용
			String t, // null
			
			// 기본형은 null 불가 → 쿼리에 a가 없으면 바인딩 실패(400)
			// 옵션으로 쓰려면 Integer a 또는 @RequestParam(value="a", required=false, defaultValue="0")
			int a, // null이고 형변환 안되서 400 코드
			
			// 모든 요청 파라미터를 키-값으로 받음(키 중복 시 첫 값)
			// 다중 값이 필요하면 MultiValueMap<String, String> 사용
			@RequestParam
			Map map

			// @RequestParam 여기서 DTO는 안됨
			// memberDTO dto
			) {
		MemberDTO memberDTO = new MemberDTO();
		
		System.out.println("id: "+ id);
		System.out.println("pw: "+ pw);   // 현재는 name 파라미터 값이 들어옴(의도 주의)
		System.out.println("name: "+ name);
		System.out.println("age: "+ age);
		System.out.println("tel: "+ tel);
		System.out.println("t: "+ t);
		System.out.println("map: "+ map);
		ModelAndView mav = new ModelAndView("result");
		return mav;
	}
	
	
	@RequestMapping("/join6")
	public ModelAndView join6(HttpServletRequest req) {
		/*
	     [POST 본문 한글 인코딩 고정]
	     - form POST로 들어오는 body를 UTF-8로 해석하도록 지정
	     - 이미 인코딩이 설정돼 있으면 덮어쓰지 않아도 되지만, 학습용으로 명시
	     - GET 쿼리스트링(?name=...)에는 적용되지 않음(서버 커넥터 설정 필요)
	    */
	    try {
	        if (req.getCharacterEncoding() == null || 
	            !"UTF-8".equalsIgnoreCase(req.getCharacterEncoding())) {
	            req.setCharacterEncoding("UTF-8");
	        }
	    } catch (java.io.UnsupportedEncodingException e) {
	        // 학습용: 간단히 런타임 예외로 래핑
	        throw new RuntimeException(e);
	    }
		/*
		 [DTO 수동 바인딩]
		 - HttpServletRequest에서 값을 꺼내어 직접 DTO에 채우는 방식
		 - 숫자 변환(Integer.parseInt 등) 예외 처리 필요
		 - 스프링의 자동 바인딩(@ModelAttribute)로 대체할 수 있으나, 학습을 위해 수동 예시 유지
		*/
		MemberDTO memberDTO = new MemberDTO();
		try {
			String id = req.getParameter("id");
			String pw = req.getParameter("pw");
			String sAge = req.getParameter("age");
			String name = req.getParameter("name");
			int age = Integer.parseInt(sAge);
			
			memberDTO.setId(id);
			memberDTO.setPw(pw);
			memberDTO.setAge(age);
			memberDTO.setName(name);
		}catch(Exception e) {
			e.printStackTrace();
		}
		// req.setAttribute("id", id); // DTO로 전달하므로 미사용
		
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("memberDTO", memberDTO); // result.jsp에서 ${memberDTO.id} 등으로 접근
		return mav;
	}
	
	@RequestMapping("/join7")
	public ModelAndView join7(
	        // parameter에서 꺼낸 값을 필드명과 매칭해 DTO에 자동 바인딩
	        // 기본 이름은 타입명을 소문자화한 "memberDTO"로 모델에도 자동 추가됨
			// Parameter에서 꺼내서 DTO에 알아서 넣어줌
	        @ModelAttribute 
	        MemberDTO dto1,
	        
	        // DTO룰 자동으로 채우고
	        // 모델에 넣어주기까지 함
	        // 아랫줄을 대신해줌
	        // @ModelAttribute("memberDTO")
	        // 두 번째 DTO는 "memberDTO2"라는 모델 속성명으로 바인딩
	        // 폼/쿼리 파라미터 name은 memberDTO2.id, memberDTO2.pw ... 형태여야 함
	        @ModelAttribute("memberDTO2") 
	        MemberDTO dto2,
//	        @ModelAttribute 생략 가능
	        // 생략하면 타입(클래스)의 앞글자를 소문자로 변경한 key로
	        // 모델에 넣어 줌
	        // @ModelAttribute("memberDTO")
	        MemberDTO dto3
	) {
	    System.out.println("dto1: " + dto1);
	    System.out.println("dto2: " + dto2);
	    System.out.println("dto3: " + dto3);

	    ModelAndView mav = new ModelAndView("result");

	    // @ModelAttribute가 모델에 자동 추가하지만, 학습용으로 명시적으로 한 번 더 넣음
	    mav.addObject("memberDTO", dto1);
	    mav.addObject("memberDTO2", dto2);

	    return mav;
	}
	
	@RequestMapping("/join8")
	public void join8(String id, MemberDTO dto) {
		
	}
}

/*
 [전체 정리]

 1) 뷰 이동 방식
    - forward 직접 호출: req.getRequestDispatcher(...).forward(req, resp)
      · ViewResolver 미사용, 경로를 직접 적어야 함
      · URL은 유지
    - ModelAndView + ViewResolver:
      · setViewName("join") → /WEB-INF/views/join.jsp 로 해석(prefix/suffix)

 2) 파라미터 꺼내기
    - 서블릿 스타일: req.getParameter("id")
      · 수동 변환 필요, 유효성 수동 처리
    - @RequestParam 바인딩:
      · @RequestParam("age") int age: 자동 변환(int)
      · 기본값: required=true → 값 없으면 400
      · 완화: required=false, defaultValue="0", 래퍼형(Integer) 사용
      · 어노테이션 생략:
        * 변수명과 동일한 쿼리 파라미터가 있으면 바인딩될 수 있으나, 버전/설정 의존 → 명시 권장
      · Map 바인딩: @RequestParam Map map → 모든 파라미터 일괄 수집

 3) 기본형 vs 래퍼형
    - 기본형(int, long 등): null 불가 → 파라미터 없으면 400
    - 래퍼형(Integer, Long 등): null 허용 → 선택 파라미터에 적합

 4) join5 주의 포인트
    - pw 변수에 name 파라미터가 바인딩되도록 설정됨(value="name").
      · 학습 목적이면 OK. 실무에서는 @RequestParam("pw")로 일치시키는 편이 안전.
    - int a: 쿼리스트링에 a가 없으면 400. 옵션이면 Integer 또는 defaultValue 사용.
    - tel, t: 어노테이션 생략. 값 없으면 null. 명시적 @RequestParam 사용 권장.

 5) JSP에서 값 접근
    - mav.addObject("id", id) → JSP: ${id}
    - DTO 전달 시 → ${memberDTO.id}
    - request.setAttribute(...)로 넣어도 JSP에서 ${...}로 동일 접근

 6) 테스트 팁
    - http://localhost:8080/human3/join5?id=5&pw=4&name=12&age=24&tel=11&t=00&a=0
      · 현재 코드 기준으로 안전히 동작하도록 a를 꼭 포함
      · map은 모든 파라미터를 출력
*/
