package com.douzone.jblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryAjaxDao;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryAjaxService {

	@Autowired
	private CategoryAjaxDao categoryAjaxDao;
	
	public long insert(CategoryVo categoryVo) {
		long no = categoryAjaxDao.insert(categoryVo);
		return no;
	}

	public CategoryVo get(long no) {
		CategoryVo categoryVo = categoryAjaxDao.get(no);
		return categoryVo;
	}
	
	public List<CategoryVo> getList(long no) {
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		list = categoryAjaxDao.getList(no);
		return list;
	}
	
	public int delete(CategoryVo categoryVo) {
		int result = categoryAjaxDao.delete(categoryVo);
		return result;
	}
	
	public List<CategoryVo> getCategoryName(long no) {
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		list = categoryAjaxDao.getCategoryName(no);
		return list;
	}
	
	public long getLastCategoryNo(long no) {
		long categotyNo = categoryAjaxDao.getLastCategoryNo(no);
		return categotyNo;
	}

}
