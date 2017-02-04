package com.foodmatching.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.User;

@Controller
public class LoginController {
	private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	UserMapper userMapper;
	@GetMapping("/login")
	public String getLoginPage(Model model){
		LOGGER.info("Login");
		User user = new User();
		
		model.addAttribute("user",user);
		
	
		
		return "login";
	}
	
	
}
