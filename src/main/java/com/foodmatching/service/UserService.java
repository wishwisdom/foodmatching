package com.foodmatching.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.foodmatching.model.User;

public interface UserService extends UserDetailsService{
	//Collection<GrantedAuthority> getAuthorities(String nickName);
	public User readUserByEmail(String email);
	public User getUserById(int email);
    public void createUser(User user);
    public void deleteUser(User user);
}
