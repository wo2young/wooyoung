package kr.or.human4.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class EmpDTO {
	
		private int empno;
		private String ename;
		private String job;
		private Integer mgr;
		private Date hiredate;
		private int sal;
		private Integer comm;
		private int deptno;
		
		private int pagePerRows;
		private int page;
		private int start;
		private int end;
		
		private int keyword;
		private int type;
	

}
