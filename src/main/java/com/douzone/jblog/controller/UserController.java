package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/join")
	public String join() {
		return "/user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo, BindingResult result, Model model) {
		// 유효성 검사
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}

		userService.joinUser(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

	@RequestMapping("/joinsuccess")
	public String joinSucess() {
		return "/user/joinsuccess";
	}
	
	@RequestMapping(value = "/search", method=RequestMethod.GET)
	public String search( 
			@RequestParam(value="keyword", required=true, defaultValue="") String keyword, 
			@RequestParam(value="which", required=true) String which) {
		System.out.println(keyword);
		System.out.println(which);
		return "/blog/blog-main";
	}
	

}
