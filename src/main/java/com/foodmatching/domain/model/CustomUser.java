package com.foodmatching.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.foodmatching.domain.model.user.Authority;
import com.foodmatching.domain.model.user.User;
import com.foodmatching.domain.model.user.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Slf4j
public class CustomUser implements UserDetails{
	private Logger logger = LoggerFactory.getLogger(CustomUser.class);
	private User user;
	
	public CustomUser(User user){
		this.user = user;
		if(user.getRoles().isEmpty()){
			user.getRoles().add(new Authority(user,UserRole.ROLE_USER));
		}
	}

	public User getUser(){ return this.user;}

	public String getUserEmail(){
		return user.getEmail();
	}
	
	// For socail login user
	public void setUserEmail(String email){
		user.setEmail(email);
	}
    

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.user.setAccountNonExpired( isAccountNonExpired);
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.user.setAccountNonLocked(isAccountNonLocked);
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.user.setCredentialsNonExpired(isCredentialsNonExpired);
	}

	public void setEnabled(boolean isEnabled) {
		this.user.setEnabled(isEnabled);
	}

	public String getNickName(){
		return this.user.getNickname();
	}
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getNickname();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}

	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<GrantedAuthority> auth = new ArrayList<>();
		this.user.getRoles().forEach(a->{
			auth.add(new SimpleGrantedAuthority(a.getAuthority().name()));
		});;
		
		return auth;
	}
}
