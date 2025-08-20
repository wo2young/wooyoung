package emp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import emp.dto.EmpDTO;
import emp.service.EmpService;

@WebServlet("/remove")
public class EmpRemoveController extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 0. 버튼을 누르면 
		
		
		
		// 4. 전체 목록으로
		
        // 파라메터 empno를 int로 형변환
		
		// 1. empno를 가져와서 저장
        int empno = 0;
        try {
        	String sEmpno = request.getParameter("empno");
        	if(sEmpno != null) {
        		empno = Integer.parseInt(sEmpno);
        	}
        }catch(Exception e) {
        	e.printStackTrace();
        }
        // 2. DTO 일해라
        EmpDTO empDTO = new EmpDTO();
        empDTO.setEmpno(empno);
        
        // 3.DB ㄱㄱ
        EmpService empService = new EmpService();
        int result = empService.removeEmp(empDTO);
        System.out.println(result +" 만큼 삭제 되었습니다");
	
        // 4. 전체 목록으로
        response.sendRedirect("list");
        
	}

}
