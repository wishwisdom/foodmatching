package com.foodmatching.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;
import com.foodmatching.service.UserService;
/*
 * For User relations, Login etc.
 * @author wishwisdom
 * @version v0.0.1
 */
import com.foodmatching.utils.UserFormValidator;


@Controller
public class UserController {
	
	private UserService userService;
	private UserFormValidator userFormValidator;
	
	@Autowired
	public UserController(UserService userService, UserFormValidator userFormValidator){
		this.userService = userService;
		this.userFormValidator = userFormValidator;
	}
	
	@InitBinder("form")
	public void initBinder(WebDataBinder binder){
		binder.addValidators(userFormValidator);
	}
	
	@GetMapping("/user/create")
	public String registerGet(Model model) {
		// email,nickname,password,birth,joinDay

		UserForm userForm = new UserForm();

		model.addAttribute("user", userForm);
		model.addAttribute("test", "OK");

		return "insertOK";
	}
	
	@PostMapping("/user/create")
	public String registerUser(@Valid @ModelAttribute(value = "form")  UserForm userForm, BindingResult bindingResult) {
		// email,nickname,password,birth,joinDay
		
		if(bindingResult.hasErrors()){
			return "insertOK";
		}
		try{
			userService.save(userForm);
		}catch(DataIntegrityViolationException e){
			bindingResult.reject("email.exists", "Email already exists");
            return "insertOK";
		}
		
		
		

		//mav.addObject("test", "TestOK");

		// 다른 곳으로 보낼 페이지 만들어야 함
		return "insertOK";
	}

	
	
	@RequestMapping("/login")
	public String loginForm(Model model){
		
		User user = new User();
		
		model.addAttribute("user", user);
		model.addAttribute("test", "start");
		
		
		return "login";
	}
	
	
	@GetMapping("/user/{id}")
	public ModelAndView getUserPage(@PathVariable("id") Long id){
		// ID  내용을 넣을 예
		return new ModelAndView("","userInfo","test");
	}
	
	/*
	 * check loginform
	 * @param User user loginform infomation
	 * 
	 * 
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginPost(User user,Model model){
		
		model.addAttribute("user", user);
		model.addAttribute("test", user.getEmail());
		
		
		return "loginform";
	}
	
	
	
	@RequestMapping(value="/users/{id}",method=RequestMethod.GET)
	public String myinfo(@PathVariable(name="id") int id, Model model){
		
		
		return "myinfo";
	}

}
