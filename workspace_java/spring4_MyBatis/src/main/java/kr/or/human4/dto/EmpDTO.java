package kr.or.human4.dto;

import java.sql.Date;

public class EmpDTO {
	
		private int empno;
		private String ename;
		private String job;
		private Integer mgr;
		private Date hiredate;
		private int sal;
		private Integer comm;
		private int deptno;
		
		@Override
		public String toString() {
			return "EmpDAO [empno=" + empno + ", ename=" + ename + ", job=" + job + ", mgr=" + mgr + ", hiredate="
					+ hiredate + ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno + "]";
		}

		public int getEmpno() {
			return empno;
		}

		public void setEmpno(int empno) {
			this.empno = empno;
		}

		public String getEname() {
			return ename;
		}

		public void setEname(String ename) {
			this.ename = ename;
		}

		public String getJob() {
			return job;
		}

		public void setJob(String job) {
			this.job = job;
		}

		public Integer getMgr() {
			return mgr;
		}

		public void setMgr(Integer mgr) {
			this.mgr = mgr;
		}

		public Date getHiredate() {
			return hiredate;
		}

		public void setHiredate(Date hiredate) {
			this.hiredate = hiredate;
		}

		public int getSal() {
			return sal;
		}

		public void setSal(int sal) {
			this.sal = sal;
		}

		public Integer getComm() {
			return comm;
		}

		public void setComm(Integer comm) {
			this.comm = comm;
		}

		public int getDeptno() {
			return deptno;
		}

		public void setDeptno(int deptno) {
			this.deptno = deptno;
		}
	

}
