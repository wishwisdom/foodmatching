package com.foodmatching.utils;

import com.foodmatching.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.foodmatching.domain.model.user.UserForm;

@Component
public class UserFormValidator implements Validator {

	private UserService userService;
	
	@Autowired
	public UserFormValidator(UserService userService){
		this.userService = userService;
	}
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(UserForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserForm form = (UserForm) target;

		// check password
		checkPassowrd(form, errors);

		// check id
		validateEmail(form,errors);

	}

	private void checkPassowrd(UserForm form, Errors errors) {
		if (!form.getPassword().equals(form.getRepassword())) {
			errors.reject("password.no_match", "Passwords do not match");
		}
	}

	private void validateEmail(UserForm form, Errors errors){
		if (userService.findBy(form.getEmail()) != null) {
			errors.reject("email.exists", "User with this email already exists");
		}
	}

}
