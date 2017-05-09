package com.foodmatching.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.User;

@Controller
public class LoginController {
	private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Value("${user.target.preurl}")
	private String preURL;
	
	@Autowired
	UserMapper userMapper;
	@GetMapping("/login")
	public String getLoginPage(HttpServletRequest request,Model model){
		LOGGER.info("Login");
		User user = new User();
		model.addAttribute("user",user);

		String preURI = request.getHeader("Referer");
		model.addAttribute(preURL,preURI);
		
		return "login";
	}
	
	
}
