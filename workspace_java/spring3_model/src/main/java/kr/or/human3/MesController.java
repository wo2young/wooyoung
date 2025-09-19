package kr.or.human3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/mes")
@Controller
public class MesController {
	
	@RequestMapping("/bom")
	public String bom() {
		return "bom";
	}
	
	@RequestMapping("/master")
	public String master() {
		
		
		
		return "master";
	}
	
}
