package com.foodmatching.utils;

import com.foodmatching.domain.service.UserService;
import com.foodmatching.presentation.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.foodmatching.domain.model.user.UserForm;

//@Component
public class UserFormValidator implements Validator {

	private UserService userService;
	
	@Autowired
	public UserFormValidator(UserService userService){
		this.userService = userService;
	}
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(UserController.UserCreateRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserController.UserCreateRequest form = (UserController.UserCreateRequest) target;

		// check password
		checkPassowrd(form, errors);

		// check id
		validateEmail(form,errors);

	}

	private void checkPassowrd(UserController.UserCreateRequest form, Errors errors) {
		if (!form.getPassword().equals(form.getRepassword())) {
			errors.rejectValue("password", "Passwords do not match");
		}
	}

	private void validateEmail(UserController.UserCreateRequest form, Errors errors){
		if (userService.findBy(form.getEmail()) != null) {
			errors.rejectValue("email", "User with this email already exists");
		}
	}

}
