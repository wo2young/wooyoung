package QuizDTO;

public class DTO {
	
	private int num;
	private String ename;
	private String empno;
	private int level_type;
	@Override
	public String toString() {
		return "Service [num=" + num + ", ename=" + ename + ", empno=" + empno + ", level_type=" + level_type + "]";
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public int getLevel_type() {
		return level_type;
	}
	public void setLevel_type(int level_type) {
		this.level_type = level_type;
	}
}
