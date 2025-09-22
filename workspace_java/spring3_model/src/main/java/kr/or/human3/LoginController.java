package kr.or.human3;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	// void일 경우 주소 그대로 String로 return하는 것과 동일하게 동작
	@RequestMapping("/main")
	public String main(Model model, HttpServletRequest req) {
		System.out.println("/main");
		
		model.addAttribute("id", "abc");
		
		HttpSession session = req.getSession();
		session.setAttribute("level", "admin");
		
		return "main";
	}
}
