package com.foodmatching.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.foodmatching.mapper.ScrapMapper;
import com.foodmatching.model.CustomUser;
import com.foodmatching.model.Scrap;
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
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private UserFormValidator userFormValidator;

	@Autowired
	private ScrapMapper scrapMapper;
	
	
	@InitBinder("user")
	public void initBinder(WebDataBinder binder){
		binder.addValidators(userFormValidator);
	}
	

	@GetMapping("/register")
	public String registerGet(Model model) {
		// email,nickname,password,birth,joinDay

		UserForm userForm = new UserForm();

		model.addAttribute("user", userForm);
		model.addAttribute("test", "OK");

		return "insertOK";
	}
	
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute(value = "user")  UserForm userForm, BindingResult bindingResult) {
		// email,nickname,password,birth,joinDay
		logger.info("ENTER register post");
		logger.info("File : "+(userForm.getPictureFile()==null));
		if(bindingResult.hasErrors()){
			logger.info(bindingResult.getFieldError().getField());
			return "insertOK";
		}
		try{
			logger.info("SAVE USER ");
			userService.save(userForm);
			
		}catch(DataIntegrityViolationException e){
			bindingResult.reject("email.exists", "Email already exists");
            return "insertOK";
		}
		
		
		

		//mav.addObject("test", "TestOK");

		// 다른 곳으로 보낼 페이지 만들어야 함
		return "insertOK";
	}

	
	
	/*
	 * check loginform
	 * @param User user loginform infomation
	 * 
	 * 
	 */
/*	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginPost(User user,Model model){
		
		model.addAttribute("user", user);
		model.addAttribute("test", user.getEmail());
		
		
		return "loginform";
	}
	*/
	
	
	@RequestMapping(value="/users/{id}",method=RequestMethod.GET)
	public String myinfo(@PathVariable(name="id") int id, Model model){
		
		
		return "myinfo";
	}
	
	@GetMapping("/user/scraps")
	public String myScrap(Model model,CustomUser user,int start, int end){
		Scrap scrap = new Scrap();
		scrap.setEmail(user.getUserEmail());
		List<Scrap> list = scrapMapper.findAll(scrap, start, end);
		
		model.addAttribute("scrapList", list);
		model.addAttribute("start",start);
		model.addAttribute("end",end);
		
		return "scaplist";
	}
	/**
	 * Save 'scrap' if not exists in a table or delete it.
	 * 
	 * @param id board id for user's scrap or unscrap
	 * @param currentUser login user 
	 * 
	 * @return total like number of the board {id}
	 */
	@PostMapping(value = "/user/scrap/{id}")
	@ResponseBody
	public int updateScrap(@PathVariable("id") Integer id, @ModelAttribute("customUser") CustomUser user){
		
		Scrap scrap = new Scrap(id,user.getUserEmail());
		
		Scrap isScrap = scrapMapper.find(scrap);
		
		logger.info("scrap test :" + (isScrap==null));
		
		if(isScrap == null){
			scrapMapper.save(scrap);
		}else
			scrapMapper.delete(scrap);
		
		return scrapMapper.countAll(scrap);
	}

}
