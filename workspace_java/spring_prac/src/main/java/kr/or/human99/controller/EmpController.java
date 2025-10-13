package kr.or.human99.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.human99.dto.EmpDTO;
import kr.or.human99.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired
	EmpService empService;
	
	 @RequestMapping("/emp")
	public String selectList(Model model){
		
		List<EmpDTO> list = empService.getEmpList();
		
		model.addAttribute("list",list);
		
		return "emp";
	}
}
