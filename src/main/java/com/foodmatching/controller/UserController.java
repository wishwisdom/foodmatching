package com.foodmatching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.User;

@RestController("/users")
public class UserController {
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/{nickName}")
	public User home(@PathVariable("nickName") String nickName) {
		User user = userMapper.getUser(nickName);

		return user;
	}

}
