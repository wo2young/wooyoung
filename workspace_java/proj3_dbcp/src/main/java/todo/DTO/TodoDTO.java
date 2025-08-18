package todo.DTO;

import java.sql.Date;
// DTO : Data Transfer Object
public class TodoDTO {
	
	private int tno;  // 할 일 고유 번호 
	private String title; // 할일
	private Date duedate; // 마감일
	private int finished; // 완료 여부
	public int getTno() {
		return tno;
	}
	public void setTno(int tno) {
		this.tno = tno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
	@Override
	public String toString() {
		return "TodoDTO [tno=" + tno + ", title=" + title + ", duedate=" + duedate + ", finished=" + finished + "]";
	}
	
	
	
}
