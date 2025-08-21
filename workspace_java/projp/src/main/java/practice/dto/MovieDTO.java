package practice.dto;

import java.sql.Date;

public class MovieDTO {
	private int movie_id;
	private String title;
	private String img_url;
	private Date open_date;
	
	@Override
	public String toString() {
		return "MovieDTO [movie_id=" + movie_id + ", title=" + title + ", img_url=" + img_url + ", open_date="
				+ open_date + "]";
	}
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public Date getOpen_date() {
		return open_date;
	}
	public void setOpen_date(Date open_date) {
		this.open_date = open_date;
	}

}
