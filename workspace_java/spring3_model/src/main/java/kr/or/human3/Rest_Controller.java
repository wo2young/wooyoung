package kr.or.human3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//@ResponseBody
@RestController // 위에 둘을 합친거임
public class Rest_Controller {
	// 클래스에 @ResponseBody에 붙이면 모든 메소드에 적용
	@RequestMapping("/a")
	public String a() {
		return "a";
	}

	@RequestMapping("/b")
	public String b() {
		return "b";
	}
	
}
