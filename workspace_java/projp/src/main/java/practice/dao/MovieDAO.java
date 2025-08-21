package practice.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import practice.dto.MovieDTO;

public class MovieDAO {
	// DB 접속
	private Connection getConn() {
		Connection conn = null;
		try {
			// JNDI 방식 : 글씨로 뭔가를 가져오는 방식
			Context ctx = new InitialContext();
			DataSource dataFactory = (DataSource) ctx.lookup("java:/comp/env/jdbc/oracle");
			conn = dataFactory.getConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public List<MovieDTO> seleteAll() {
		
		// DB 접속
		Connection conn = getConn();
		List<MovieDTO> list = new ArrayList<MovieDTO>(); 
		try {
			// SQL 준비
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT * FROM movie");
			// SQL 실행
			ResultSet rs = ps.executeQuery();
			
			// ResultSet 처리
			while(rs.next()) {
				MovieDTO dto = new MovieDTO();
				
				int movie_id = rs.getInt("movie_id");
				dto.setMovie_id(movie_id);
				
				String title = rs.getString("title");
				dto.setTitle(title);
				
				String img_url = rs.getString("img_url");
				dto.setImg_url(img_url);
				
				Date open_date = rs.getDate("open_date");
				dto.setOpen_date(open_date);
				
				list.add(dto);
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public MovieDTO seleteOne(int movie_id) {
		
		MovieDTO dto = new MovieDTO();
		// DB 접속
		Connection conn = getConn();
		try {
			// SQL 준비
			PreparedStatement ps;
			ps = conn.prepareStatement("SELECT * FROM movie WHERE movie_id = ?");
			ps.setInt(1, movie_id);
			
			// SQL 실행
			ResultSet rs = ps.executeQuery();
			
			// ResultSet 처리
			if(rs.next()) {
				dto = new MovieDTO();
				
				movie_id = rs.getInt("movie_id");
				dto.setMovie_id(movie_id);
				
				String title = rs.getString("title");
				dto.setTitle(title);
				
				String img_url = rs.getString("img_url");
				dto.setImg_url(img_url);
				
				Date open_date = rs.getDate("open_date");
				dto.setOpen_date(open_date);
				
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dto;
		
	}
	
}
