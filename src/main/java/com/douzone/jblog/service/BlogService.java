package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogDao;
import com.douzone.jblog.vo.BlogVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;
	
	public boolean updateLogo(BlogVo blogVo) {
		int count = blogDao.updateLogo(blogVo);
		System.out.println("-- Logo : " + count);
		return count == 1;
	}
	public BlogVo getMain(long no) {
		BlogVo blogVo = blogDao.getMain(no);
		return blogVo;
	}
	
}
