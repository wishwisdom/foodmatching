package com.foodmatching.controller;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.FileUploadForm;
import com.foodmatching.model.User;

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
