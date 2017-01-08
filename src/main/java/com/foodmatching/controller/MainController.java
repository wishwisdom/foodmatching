package com.foodmatching.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.foodmatching.mapper.UserMapper;

/*
 * @descript 
 *  Login, SignUp,Main Page 관리하는 Controller
 * 
 */
@Controller
public class MainController extends WebMvcConfigurerAdapter{
	@Autowired
	UserMapper userMapper;

	

	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	
	
	

	
}
