package com.foodmatching.presentation.controller;

import javax.servlet.http.HttpServletRequest;

import com.foodmatching.domain.model.CustomUser;
import com.foodmatching.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodmatching.domain.model.user.User;

import java.security.Principal;
@Slf4j
@Controller
public class LoginController {

	@Value("${user.target.preurl}")
	private String preURL;

	@GetMapping("/login")
	public String getLoginPage(HttpServletRequest request, Model model){
		log.info("Login");
		User user = new User();
		model.addAttribute("user",user);

		String preURI = request.getHeader("Referer");
		model.addAttribute(preURL,preURI);

		return "login";
	}

}
