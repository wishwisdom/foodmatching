package com.foodmatching.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.foodmatching.handler.LoginHandler;
import com.foodmatching.handler.LogoutHandler;
import com.foodmatching.serviceimpl.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	@Autowired
	UserServiceImpl userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/images/**","/img/**","/css/**","/bootstrap/**","/js/**","/register", "/login/**","/*").permitAll()
		.anyRequest().authenticated();

		http
			.formLogin()
			.loginPage("/login")
				.usernameParameter("email").permitAll()
			.successHandler(successHandler())
			.defaultSuccessUrl("/")
			.and()
			.logout().logoutUrl("/logout").logoutSuccessHandler(successLogoutHadnler())
			.permitAll();
			

		http.csrf().disable();

	}
	
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationSuccessHandler successHandler(){
		return new LoginHandler();
	}
	
	@Bean
	public LogoutSuccessHandler successLogoutHadnler(){
		return new LogoutHandler();
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

	}
}
