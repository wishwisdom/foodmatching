package com.foodmatching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.User;

@Controller
public class MainController {
	@Autowired UserMapper userMapper;
	
	@RequestMapping("/{nickName}")
	public User home(@PathVariable("nickName") String nickName){
		User user = userMapper.getUser(nickName);
		
		return user;
	}
	
	@RequestMapping("/")
	public String main(){
		return "main";
	}
}
