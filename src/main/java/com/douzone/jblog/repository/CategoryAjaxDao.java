package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryAjaxDao {

	@Autowired
	private SqlSession sqlSession;
	
	public CategoryVo get(long searchNo) {
		return sqlSession.selectOne("category.getListSearchNo", searchNo);
		
	}
	
	public long insert(CategoryVo vo) {
		sqlSession.insert("category.insert", vo);
		long no = vo.getNo();
		return no;
	}
	
	
	public List<CategoryVo> getList(long no){
		List<CategoryVo> list = sqlSession.selectList("category.getListAndPage", no);
		return list;
	}

	public int delete(CategoryVo categoryVo) {
		int result = sqlSession.delete("category.delete", categoryVo);
		result = (int) ((result == 1) ? categoryVo.getNo() : -1);
		return result;
	}
	
	public List<CategoryVo> getCategoryName(long no) {
		return sqlSession.selectList("category.getCategoryName", no);
	}
	
	public long getLastCategoryNo(long no) {
		return sqlSession.selectOne("category.getLastCategoryNo", no);
	}
	
}
