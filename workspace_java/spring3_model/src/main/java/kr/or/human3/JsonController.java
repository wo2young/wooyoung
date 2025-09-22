package kr.or.human3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		//@ResponseBody
		// 글씨 그대로 나감
		return "ajax";
	}

	@RequestMapping("/ajax3")
	@ResponseBody
	public MemberDTO ajax3(){
		
		MemberDTO dto = new MemberDTO();
		dto.setId("id");
		dto.setPw("pw");
		dto.setName("name");
		dto.setAge(20);
		dto.setTel("tel");
		
		
		return dto;
	}

	@RequestMapping("/ajax4")
	@ResponseBody
	public List ajax4(){
		
		MemberDTO dto = new MemberDTO();
		dto.setId("id");
		dto.setPw("pw");
		dto.setName("name");
		dto.setAge(20);
		dto.setTel("tel");
		
		List list = new ArrayList();
		list.add(dto);
		list.add(dto);
		list.add(dto);
		
		return list;
	}

	@RequestMapping("/ajax5")
	@ResponseBody
	public Map ajax5(){
		
		MemberDTO dto = new MemberDTO();
		dto.setId("id");
		dto.setPw("pw");
		dto.setName("name");
		dto.setAge(20);
		dto.setTel("tel");
		
		List list = new ArrayList();
		list.add(dto);
		list.add(dto);
		list.add(dto);
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("totalCount", 150);
		map.put("pageNo", 3);
		map.put("keyword", "SCOTT");

		return map;
	}
	
}
