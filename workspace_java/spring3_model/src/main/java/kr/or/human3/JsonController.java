package kr.or.human3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JsonController {
	
	@RequestMapping("/ajax.do")
	public String ajax(){
		return "ajax";
	}
	
	@RequestMapping("/ajax1")
	public String ajax1(
			// json은 해석하지 못한다
//			@RequestParam(value="id", required=false)
//			String id
			
			@RequestBody
			MemberDTO dto
			){
//		System.out.println("id : "+id);
		System.out.println("dto : "+dto);
	
		return "ajax";
	}
	
	@RequestMapping("/ajax2")
	@ResponseBody
	public String ajax2(){
		
		return "ajax";
	}
	
}
