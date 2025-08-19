package practice.dto;

import java.sql.Date;

public class TodoDTO {
		
	private int tno;
	private String title;
	private Date duedate;
	private int finished;
	
	@Override
	public String toString() {
		return "TodoDTO [tno=" + tno + ", title=" + title + ", duedate=" + duedate + ", finished=" + finished + "]";
	}
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
	
}
