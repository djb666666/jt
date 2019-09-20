package com.jt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.User;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/getMsg")
	public User getUser() {
		User user = new User();
		
		return user;
	}
}
