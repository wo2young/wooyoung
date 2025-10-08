package kr.or.human99.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.human99.dto.UserDTO;

@Repository // ✅ 이거 꼭 있어야 함
public class UserDAO {

	@Autowired
	private SqlSession sqlSession; // ✅ Spring이 주입

	private static final String NS = "kr.or.human99.mappers.UserMapper.";

	public UserDTO findByLoginId(String loginId) {
		return sqlSession.selectOne(NS + "findByLoginId", loginId);
	}

	public UserDTO findById(int userId) {
		return sqlSession.selectOne("kr.or.human99.mappers.UserMapper.findById", userId);
	}

	public void updatePassword(int userId, String newHashedPw) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("password", newHashedPw);
		sqlSession.update("kr.or.human99.mappers.UserMapper.updatePassword", param);
	}
}
