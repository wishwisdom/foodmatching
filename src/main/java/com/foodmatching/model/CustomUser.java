package com.foodmatching.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails{
	private User user;
	
	public CustomUser(User user){
		this.user = user;
	}
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	
	
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getNickName();
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

	
	// imsi
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

}
