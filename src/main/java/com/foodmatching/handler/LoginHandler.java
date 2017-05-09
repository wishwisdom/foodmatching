package com.foodmatching.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * After logging, redirect pre-url(before login url).. 
 *
 * @author Shin-Hong Park
 * @version 0.5
 * @since 2017.05.09
 * 
 * @refference http://chomman.github.io/blog/java/spring%20security/programming/spring-security-redirect-previous-after-login/
 */

public class LoginHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Value("${user.target.preurl}")
	private String preURL;
	
	public LoginHandler(){
		setDefaultTargetUrl("/");
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
	    	Authentication authentication) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session != null){
			String redirectURL = (String)request.getAttribute(preURL);
			
			if(redirectURL != null){
				session.removeAttribute(preURL);
				getRedirectStrategy().sendRedirect(request,response,redirectURL);
			}else{
				super.onAuthenticationSuccess(request, response, authentication);
			}
		}else
			super.onAuthenticationSuccess(request, response, authentication);
	}
}
