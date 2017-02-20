package com.foodmatching.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.foodmatching.model.CustomUser;

/*
 * URL 접근할 때마다 불려지는 것.
 * 로그인 사용자 확인하기 위해 필요한 부
 */
@ControllerAdvice
public class CurrentControllerAdvice {
	private final Logger logger = LoggerFactory.getLogger(CurrentControllerAdvice.class);
	@ModelAttribute("customUser")
	public CustomUser getCurrentUser(Authentication authentication) {
		if(authentication != null)
			logger.info("Come Advice : "+(CustomUser) authentication.getPrincipal());
        return (authentication == null) ? null : (CustomUser) authentication.getPrincipal();
	}
}
