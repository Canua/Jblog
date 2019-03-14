package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.dto.JSONResult;
import com.douzone.jblog.service.CategoryAjaxService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("category/ajax")
public class CategoryAjaxController {
	
	@Autowired
	CategoryAjaxService categoryAjaxService;
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping("/list")
	public JSONResult list(@RequestParam(value = "id",required = true) String id) {

		UserVo userVo = userService.existId(id);
		List<CategoryVo> list = categoryAjaxService.getList(userVo.getNo());
		
		return JSONResult.success(list);
	}
	
	
	@ResponseBody
	@RequestMapping("/insert")
	public JSONResult insert(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "description") String description, 
			@RequestParam(value = "no", required = true) long no) {
		CategoryVo vo = new CategoryVo();

		vo.setUser_no(no);
		vo.setName(name);
		vo.setDescription(description);
		long last_no = categoryAjaxService.insert(vo);
		
		CategoryVo newVo = categoryAjaxService.get(last_no); 
		return JSONResult.success(newVo);
	}
	@ResponseBody
	@RequestMapping("/delete/{id}")
	public JSONResult delete(
			@PathVariable(value = "id") String id,
			@RequestParam(value = "clickNo")  long clickNo,
			@RequestParam(value = "password") String password) {
		int result = 0;
		UserVo userVo = userService.getUser(id, password);
		if(userVo == null) {
			result = -1;
			return JSONResult.success(result);
		}
		
		CategoryVo vo = new CategoryVo();
		vo.setNo(clickNo);
		result = categoryAjaxService.delete(vo);
		return JSONResult.success(result);
	}
}
