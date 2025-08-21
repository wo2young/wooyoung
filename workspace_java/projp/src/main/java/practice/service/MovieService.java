package practice.service;

import java.util.ArrayList;
import java.util.List;

import practice.dao.MovieDAO;
import practice.dto.MovieDTO;

public class MovieService {
	MovieDAO dao = new MovieDAO();
	
	public List<MovieDTO> selectall(){
		return dao.seleteAll();
	}
	
	public MovieDTO selectOne(int movie_id){
		return dao.seleteOne(movie_id);
	}

}
