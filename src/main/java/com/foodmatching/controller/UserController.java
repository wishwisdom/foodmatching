package com.foodmatching.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.User;

@Controller
public class UserController {
	@Autowired
	UserMapper userMapper;
	
	// @PreAuthority() 걸
	@RequestMapping("/user/{id}")
	public String home(@PathVariable("id") int id,Model model) {
		User user = userMapper.getUserById(id);
		
		
		
		model.addAttribute("user", user);
		
		return "myinfo";
	}
	
	@PostMapping("/users/register")
	public ModelAndView registerUser(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult) {
		// email,nickname,password,birth,joinDay

		ModelAndView mav = new ModelAndView("insertOK");
		user.setPassword((new BCryptPasswordEncoder().encode(user.getPassword())));
		userMapper.insertUser(user);

		mav.addObject("test", "TestOK");

		return mav;
	}

	@GetMapping("/users/register")
	public String registerGet(Model model) {
		// email,nickname,password,birth,joinDay

		User user = new User();

		model.addAttribute("user", user);
		model.addAttribute("test", "OK");

		return "insertOK";
	}

}
