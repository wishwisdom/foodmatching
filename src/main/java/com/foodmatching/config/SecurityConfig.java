package com.foodmatching.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.foodmatching.service.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private UserService userService;
	@Override
	public void configure(WebSecurity web) throws Exception
	{
		// 예를들어 이런식으로 인증할것들을 풀어주는겁니다. (주로 리소스)
		web.ignoring().antMatchers("/css/**", "/script/**", "/","/bootstrap/**","/images/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("security");
		http.authorizeRequests().antMatchers("/**").permitAll();
		http
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/");
		// 로그인 페이지 : 컨트롤러 매핑을 하지 않으면 기본 제공되는 로그인 페이지가 뜬다.
		//.defaultSuccessUrl("/");
//		http
//			.authorizeRequests()
//			.antMatchers("/css/**", "/index").permitAll()
//			.antMatchers("/user/**").hasRole("USER")
//				.and().formLogin().loginPage("/login").failureUrl("/login").successForwardUrl("/");
//		
		
		http.csrf().disable();

	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
	}
}
