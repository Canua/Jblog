package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostDao;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {
	
	@Autowired
	private PostDao postDao;
	
	public void insertPost(PostVo postVo) {
		if(postVo.getTitle() == "") {
			postVo.setTitle("제목 없음");
		}
		postDao.insertPost(postVo);
	}
	
	public List<PostVo> getMainPostList(CategoryVo vo){
		List<PostVo> list = postDao.getMainPostList(vo);
		return list;
	}
	public long lastPostNo(PostVo postVo) {
		long no = postDao.lastPostNo(postVo);
		return no;
	}
	public PostVo getMainPost(PostVo postVo) {
		PostVo vo = postDao.getMainPost(postVo);
		return vo;
	}
}
