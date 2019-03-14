package com.douzone.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryAjaxService;
import com.douzone.jblog.service.FileuploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
//@RequestMapping("/admin")
@RequestMapping("/{id:(?!assets|uploads).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryAjaxService categoryAjaxService;

	@Autowired
	private FileuploadService fileuploadService;

	@Autowired
	private PostService postService;
	
	@RequestMapping(value= {"","/{pathNo1}","/{pathNo1}/{pathNo2}"}/*, method=RequestMethod.GET*/)
	public String index(
			@PathVariable String id,
			@PathVariable Optional<Long> pathNo1,
			@PathVariable Optional<Long> pathNo2,
			Model model){	
		// id , categoryno, postno
		
		long lastCategoryNo = 1;
		long lastPostNo = 1;
		
		UserVo userVo = userService.existId(id);
		BlogVo blogVo = blogService.getMain(userVo.getNo());
		
		System.out.println("userVo : " + userVo);
		System.out.println("blogVo : " + userVo);
		
		model.addAttribute("userVo", userVo);
		model.addAttribute("blogVo", blogVo);
		
		
		lastCategoryNo = categoryAjaxService.getLastCategoryNo(userVo.getNo());

		if(!(pathNo1.toString() == "Optional.empty")) {
			lastCategoryNo = pathNo1.get();
		}
		PostVo searchPost = new PostVo();
		searchPost.setCategory_no(lastCategoryNo);
		searchPost.setNo(userVo.getNo());
		System.out.println("?: "+ searchPost);
		lastPostNo = postService.lastPostNo(searchPost);
		System.out.println(lastPostNo);
		if(!(pathNo2.toString() == "Optional.empty")) {
			lastPostNo = pathNo2.get();
		}
		PostVo getMainPost = new PostVo();
		getMainPost.setNo(lastPostNo);
		getMainPost.setCategory_no(lastCategoryNo);
		getMainPost = postService.getMainPost(getMainPost);
		
		System.out.println("--- : " + getMainPost);
		model.addAttribute("mainpost", getMainPost);
		
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		list = categoryAjaxService.getCategoryName(userVo.getNo());
		
		model.addAttribute("categorylist", list);
		
		
		CategoryVo vo = new CategoryVo();
		vo.setNo(lastCategoryNo);
		vo.setUser_no(userVo.getNo());
		List<PostVo> postList = new ArrayList<PostVo>();
		postList = postService.getMainPostList(vo);
		
		model.addAttribute("postlist", postList);
		
		
		return "blog/blog-main";
	}


	@RequestMapping(value = "basic")
	public String basic(
		@PathVariable("id") String id, Model model) {
		UserVo userVo = userService.existId(id);
		BlogVo blogVo = blogService.getMain(userVo.getNo());
		
		System.out.println("--- blogVo : " + blogVo );
		
		model.addAttribute("blogVo", blogVo);

		return "blog/blog-admin-basic";
	}

	@RequestMapping(value = "category")
	public String category(@PathVariable("id") String id, Model model) {
		UserVo userVo = userService.existId(id);
		model.addAttribute("userVo", userVo);
		return "blog/blog-admin-category";
	}

	@RequestMapping(value = "write")
	public String write(@PathVariable("id") String id, Model model) {
		UserVo userVo = userService.existId(id);

		List<CategoryVo> list = new ArrayList<CategoryVo>();
		list = categoryAjaxService.getCategoryName(userVo.getNo());

		
		
		model.addAttribute("categorylist", list);

		return "blog/blog-admin-write";
	}

	@RequestMapping(value = "upload")
	public String update(@ModelAttribute BlogVo blogVo, @RequestParam(value = "logo-file") MultipartFile multipartFile,
			@PathVariable("id") String id) {
		UserVo userVo = userService.existId(id);
		String profile = fileuploadService.restore(multipartFile);
		blogVo.setLogo(profile);
		blogVo.setUser_no(userVo.getNo());
		blogService.updateLogo(blogVo);
		return "redirect:/" + id;
	}
}
