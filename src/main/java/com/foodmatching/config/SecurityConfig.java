package com.foodmatching.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.foodmatching.handler.LoginHandler;
import com.foodmatching.handler.LogoutHandler;
import com.foodmatching.serviceimpl.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserServiceImpl userService;

	//@Override
	//public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("//resources/static/**");
	//}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/images/**","/img/**","/css/**","/bootstrap/**","/register", "/*").permitAll()
		.anyRequest().authenticated();

		http
			.formLogin()
			.loginPage("/login")
				.usernameParameter("email").permitAll()
			.successHandler(successHandler())
			.defaultSuccessUrl("/")
			.and()
			.logout().logoutSuccessUrl("/").logoutSuccessHandler(successLogoutHadnler())
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
