package com.douzone.jblog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping({ "", "/main" })
	public String main(Model model) {
		List<UserVo> list = new ArrayList<UserVo>();
		list = userService.getUserList();
		
		System.out.println("getUserList : " + list);
		
		model.addAttribute("list", list);
		
		return "main/index";
	}
	
}
