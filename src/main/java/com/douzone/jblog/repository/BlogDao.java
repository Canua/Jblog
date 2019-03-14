package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
@Repository
public class BlogDao {

	@Autowired
	private SqlSession sqlSession;

	public int updateLogo(BlogVo blogVo) {
		return sqlSession.update("blog.updateLogo", blogVo);
	}
	public BlogVo getMain(long no) {
		return sqlSession.selectOne("blog.selectMain", no);
	}
}
