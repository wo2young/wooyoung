package emp.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import emp.dto.EmpDTO;
import emp.service.EmpService;

@WebServlet("/add")
public class EmpAddController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EmpDTO dto = new EmpDTO();
		
		try {
			String sEmpno = request.getParameter("empno");
//			int empno = Integer.parseInt(sEmpno);
			dto.setEmpno( (int)getParam(sEmpno) );
			
			String sEname = request.getParameter("ename");
			dto.setEname(sEname);
			String sJob = request.getParameter("job");
			dto.setJob(sJob);
			String sMgr = request.getParameter("mgr");
			int mgr = Integer.parseInt(sMgr);
			dto.setMgr(mgr);
			String sHiredate= request.getParameter("hiredate");
			Date hiredate = Date.valueOf(sHiredate);
			dto.setHiredate(hiredate);
			String sSal = request.getParameter("sal");
			int sal = Integer.parseInt(sSal);
			dto.setSal(sal);
			String sComm = request.getParameter("comm");
			int comm = Integer.parseInt(sComm);
			dto.setComm(comm);
			String sDeptno = request.getParameter("deptno");
			int deptno = Integer.parseInt(sDeptno);
			dto.setDeptno(deptno);
			
			EmpService empService = new EmpService();
			int result = empService.addEmp(dto);
			System.out.println("삽입 결과 : "+result);
			
			response.sendRedirect("list");
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 파라메터 String 값을 줌
	// 만약에
	// 원래 String인 값이라면 String을 돌려줌
	// 원래 int인 값이라면 int를 돌려줌
	private Object getParam(String param) {
		// 일단 모든걸 try안에서 int로 변경
		Object o = param;
		
		try {
			o = Integer.parseInt(param);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return o;
	}

}
