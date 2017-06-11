package com.foodmatching.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.foodmatching.handler.LoginHandler;
import com.foodmatching.handler.LogoutHandler;
import com.foodmatching.serviceimpl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	@Autowired
	UserServiceImpl userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/images/**","/img/**","/css/**","/bootstrap/**","/js/**","/register", "/auth/**","/*").permitAll()
		.anyRequest().authenticated();

		http
			.formLogin()
			.loginPage("/login")
				.usernameParameter("email").permitAll()
			.successHandler(successHandler())
			.defaultSuccessUrl("/")
			.and()
			.logout().logoutUrl("/logout").logoutSuccessHandler(successLogoutHadnler())
			.permitAll()
			.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class); 

		http.csrf().disable();

	}
	
	/**
	 * Configure and return Filter object For Facebook and Google' OAuth2 Service 
	 * @return Filter The object provides login and signup by SNS ID.
	 */
	private Filter ssoFilter() {
		OAuth2ClientAuthenticationProcessingFilter oauth2Filter = new OAuth2ClientAuthenticationProcessingFilter(
				"/auth/facebook");
		OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
		
		oauth2Filter.setRestTemplate(oauth2RestTemplate);
		
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(),
				facebook().getClientId());
		
		oauth2Filter.setTokenServices(tokenServices);

		return oauth2Filter;
	}
	

	@Bean
	@ConfigurationProperties("facebook.client")
	public AuthorizationCodeResourceDetails facebook() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("facebook.resource")
	public ResourceServerProperties facebookResource() {
		return new ResourceServerProperties();
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
