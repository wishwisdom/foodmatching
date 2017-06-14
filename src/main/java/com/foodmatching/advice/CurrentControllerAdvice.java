package com.foodmatching.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.foodmatching.model.CustomUser;
import com.foodmatching.model.User;

/**
 * Check if a user is signed or not, and make authoritis.
 * 
 * @author shin
 * @since 2017.06.14
 */
@ControllerAdvice
public class CurrentControllerAdvice {
	private final Logger logger = LoggerFactory.getLogger(CurrentControllerAdvice.class);
	@ModelAttribute("customUser")
	public CustomUser getCurrentUser(Authentication auth) {
		CustomUser user = null;
		if(auth != null){
			if( auth.getPrincipal() instanceof CustomUser){
				user = (CustomUser) auth.getPrincipal();
			}else{ // need authority
				user = new CustomUser(new User());
				user.setUserEmail( (String) auth.getPrincipal() );
			}
		}else if(auth == null){
			user = new CustomUser(new User());
			
		}
			
        return user;
	}
}
