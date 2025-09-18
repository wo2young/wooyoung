package kr.or.human2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.human2.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	 MemberService memberService;
	
	MemberController(){
		System.out.println("MemberController 생성자 실행");
	}
	
	@RequestMapping("/member")
	String listMember() {
		System.out.println("MemberController listMember() 실행");
		
//		this.memberService  =  memberService();
		List list = memberService.getList();
		System.out.println("list :" + list);
		
		return "home";
	}
}
