package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public long joinUser(UserVo vo) {
		sqlSession.insert("user.joinUser", vo);
		long no = vo.getNo();
		sqlSession.insert("user.createBlog", no);
		return no;
	}
	
	public UserVo get(String id, String password) {
		// 원래 Uservo를 던져야 하지만
		// map을 사용해서 던진다
		// 내장타입 사용
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("password", password);
		System.out.println("맵에 담긴거 : " + map);

		return sqlSession.selectOne("user.getByEmailAndPassword", map);
	}
	
	public UserVo get(String id) {
		return sqlSession.selectOne("user.getById", id);
	}
	
	public UserVo getNo(String id) {
		return sqlSession.selectOne("user.getNo", id);
	}
	
	public List<UserVo> getUserList(){
		return sqlSession.selectList("user.getUserList");
	}
}
