package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public void insertPost(PostVo postVo) {
		sqlSession.insert("post.insertPost", postVo);
	}
	
	public List<PostVo> getMainPostList(CategoryVo vo){
		List<PostVo> list = sqlSession.selectList("post.getMainPostList", vo);
		return list;
	}
	public long lastPostNo(PostVo postVo) {
		long no = sqlSession.selectOne("post.lastPostNo", postVo);
		return no;
	}
	public PostVo getMainPost(PostVo postVo) {
		PostVo vo = sqlSession.selectOne("post.getMainPost", postVo);
		return vo;
	}
	
}
