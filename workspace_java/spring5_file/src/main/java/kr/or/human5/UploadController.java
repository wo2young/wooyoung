package kr.or.human5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UploadController {
	
	@RequestMapping("/upload.view")
	public String view() {
		return "uploadForm";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload() {
		
		return "uploadForm";
	}
	
}
