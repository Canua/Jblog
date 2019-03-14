package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.CategoryAjaxService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/admin/write")
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	UserService userService;
	
	@Autowired
	CategoryAjaxService categoryAjaxService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String write(
			@ModelAttribute PostVo postVo,
			@PathVariable("id") String id) {
		postService.insertPost(postVo);
		return "redirect:/" + id;
	}
}
