package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.UserDao;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void joinUser(UserVo userVo) {
		long no = userDao.joinUser(userVo);
		System.out.println("no : last_primary key " + no);
	}

	public UserVo getUser(String id, String password) {
		System.out.println("아이디랑 비밀번호 : " + id + " : " + password);
		System.out.println(id);
		System.out.println(password);
		UserVo userVo =  userDao.get(id, password);
		System.out.println("담긴 uservo : " + userVo);
		return userVo;
	}

	public UserVo existId(String id) {
		UserVo userVo = userDao.get(id);
		return userVo;
	}

	public UserVo getNo(String id) {
		UserVo userVo = userDao.getNo(id);
		return userVo;
	}
	
	public List<UserVo> getUserList(){
		List<UserVo> list = userDao.getUserList();
		return list;
	}

}
