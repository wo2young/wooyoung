package kr.or.human4.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.human4.dto.EmpDTO;
import kr.or.human4.service.EmpService;

@Controller
public class EmpController {
	
	@Autowired
	EmpService empService;

	@RequestMapping("/listEmp")
	public String listEmp(Model model) {
		
		List<EmpDTO> list = empService.getEmpList();

		model.addAttribute("list", list);
		
		return "emp";
	}
	
	@RequestMapping("/empOne")
	public String empOne(Model model) {
		
		EmpDTO empDTO = empService.getEmp();

		model.addAttribute("empDTO", empDTO);
		
		return "emp";
	}

	@RequestMapping("/empOneMap")
	public String empOneMap(Model model) {
		
		Map map = empService.getEmpMap();
		
		model.addAttribute("map", map);
		
		return "emp";
	}
	
	@RequestMapping("/getEmpno")
	public String getEmpno(Model model, int empno) {
		System.out.println("empno: "+ empno);
		List list = empService.getEmpno(empno);
		
		model.addAttribute("list", list);
		return "emp";
	}

	@RequestMapping("/getEname")
	public String getEname(Model model, String ename) {
		System.out.println("ename: "+ ename);
		List list = empService.getEname(ename);
		
		model.addAttribute("list", list);
		return "emp";
	}
	
	@RequestMapping("/getEmpnoEname")
	public String getEmpnoEname(Model model, EmpDTO dto) {
		List list = empService.getEmpnoEname(dto);
		
		model.addAttribute("list", list);
		return "emp";
	}
	
	@RequestMapping("/joinEmp2")
	public String joinEmp2(Model model, EmpDTO dto) {
		int result = empService.joinEmp2(dto);
		System.out.println("회원 가입 결과: "+ result);
		
//		if(result == 0) {
//		}else {
//		}
		return "redirect:/listEmp";
	}
	
	// 상세 조회 by empno
	@RequestMapping("/empDetail")
	public String empDetail(Model model, int empno) {
		
		EmpDTO empDTO = empService.getOneEmpno(empno);
		model.addAttribute("empDTO", empDTO);
		
		return "detail";
	}
	
	// 회원 가입 페이지로 이동
	@RequestMapping("/join")
	public String join() {
		return "join";
	}
	
	// 수정 페이지로 이동
	@RequestMapping("/modify")
	public String modify(Model model, EmpDTO dto) {
		EmpDTO empDTO = empService.getOneEmpno(dto.getEmpno());
		model.addAttribute("empDTO", empDTO);
		
		return "modify";
	}
	
	// 수정 후 목록으로
	@RequestMapping("/modifyEmp")
	public String modifyEmp(Model model, EmpDTO dto) {
		int result = empService.modifyEmp2(dto);
		System.out.println("회원 수정 결과: "+ result);
		
		return "redirect:/listEmp";
	}
	
	@RequestMapping("/removeEmp")
	public String removeEmp(Model model, EmpDTO dto) {
		int result = empService.removeEmp2(dto);
		System.out.println("회원 삭제 결과: "+ result);
		
		return "redirect:/listEmp";
	}
	
	@RequestMapping("/search")
	public String search(Model model, EmpDTO dto) {
		
		List<EmpDTO> list = empService.selectEmp(dto);

		model.addAttribute("list", list);
		
		return "emp";
	}
}