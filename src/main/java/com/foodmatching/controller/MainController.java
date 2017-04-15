package com.foodmatching.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * @descript 
 *  Login, SignUp,Main Page 관리하는 Controller
 * 
 */
@Controller
public class MainController extends WebMvcConfigurerAdapter{
	

	@RequestMapping("/")
	public String main() {
		return "main";
	}
	
	
	
}
