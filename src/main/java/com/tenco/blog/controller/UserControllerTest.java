package com.tenco.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tenco.blog.model.User;
import com.tenco.blog.repository.UserRepository;

@Controller
public class UserControllerTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/temp/index")
	public String indexPage() {
		return "/index";
	}
	
	// 1. key= value
	@PostMapping("/temp/join")
	public String join(String username,String password,String email) {
		
		User reqUser = new User();
		reqUser.setUsername(username);
		reqUser.setPassword(password);
		reqUser.setEmail(email);
		
		return "/index";
	}
	//2. Object mapper 통해서 바로 객체 파싱하기
	@PostMapping("/temp/join2")
	@ResponseBody // 페이지 리턴이 아니라 데이터를 리턴하라 명령
	public User join2(@RequestBody User reqUser) {
		
		System.out.println(reqUser);
		userRepository.save(reqUser);
		return reqUser;
	}
	
}
