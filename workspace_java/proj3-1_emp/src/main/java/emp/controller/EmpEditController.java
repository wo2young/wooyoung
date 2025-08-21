package emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import emp.dto.EmpDTO;
import emp.service.EmpService;

@WebServlet("/edit")
public class EmpEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET : 수정폼 출력
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        System.out.println("/edit doGet 실행");

        int empno = 0;
        try {
            String sEmpno = request.getParameter("empno");
            if (sEmpno != null && !sEmpno.isEmpty()) {
                empno = Integer.parseInt(sEmpno);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        EmpDTO empDTO = new EmpDTO();
        empDTO.setEmpno(empno);

        try {
            EmpService empService = new EmpService();
            EmpDTO empDTO2 = empService.getOneemp(empDTO);

            PrintWriter out = response.getWriter();
            out.println("<form method='post' action='" + request.getContextPath() + "/edit'>");
            out.println("<table border=1 cellspacing=0 cellpadding=5>");
            out.println("   <tr><td>empno</td><td><input type='text' name='empno' value='" + empDTO2.getEmpno() + "' readonly></td></tr>");
            out.println("   <tr><td>ename</td><td><input type='text' name='ename' value='" + empDTO2.getEname() + "'></td></tr>");
            out.println("   <tr><td>job</td><td><input type='text' name='job' value='" + empDTO2.getJob() + "'></td></tr>");
            out.println("   <tr><td>mgr</td><td><input type='text' name='mgr' value='" + empDTO2.getMgr() + "'></td></tr>");
            out.println("   <tr><td>hiredate</td><td><input type='Date' name='hiredate' value='" + empDTO2.getHiredate() + "'></td></tr>");
            out.println("   <tr><td>sal</td><td><input type='text' name='sal' value='" + empDTO2.getSal() + "'></td></tr>");
            out.println("   <tr><td>comm</td><td><input type='text' name='comm' value='" + empDTO2.getComm() + "'></td></tr>");
            out.println("   <tr><td>deptno</td><td><input type='text' name='deptno' value='" + empDTO2.getDeptno() + "'></td></tr>");
            out.println("</table>");
            out.println("<input type='submit' value='수정완료'>");
            out.println("</form>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // POST : 수정 실행
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            int empno = parseIntOrZero(request.getParameter("empno"));
            String ename = request.getParameter("ename");
            String job = request.getParameter("job");
            int mgr = parseIntOrZero(request.getParameter("mgr"));
            int sal = parseIntOrZero(request.getParameter("sal"));
            int comm = parseIntOrZero(request.getParameter("comm"));
            int deptno = parseIntOrZero(request.getParameter("deptno"));

            // hiredate 안전 처리
            String hiredateStr = request.getParameter("hiredate");
            Date hiredate = null;
            if (hiredateStr != null && hiredateStr.length() >= 10) {
                hiredate = Date.valueOf(hiredateStr.substring(0, 10));
            }

            EmpDTO dto = new EmpDTO();
            dto.setEmpno(empno);
            dto.setEname(ename);
            dto.setJob(job);
            dto.setMgr(mgr);
            dto.setHiredate(hiredate);
            dto.setSal(sal);
            dto.setComm(comm);
            dto.setDeptno(deptno);

            EmpService empService = new EmpService();
            int result = empService.updateEmp(dto);

            System.out.println("업데이트 결과 : " + result);

            // 업데이트 끝나면 다시 list로 리다이렉트
            response.sendRedirect("list");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== 유틸: null/빈값이면 0 리턴 =====
    private int parseIntOrZero(String param) {
        if (param != null && !param.isEmpty()) {
            return Integer.parseInt(param);
        }
        return 0;
    }
}
