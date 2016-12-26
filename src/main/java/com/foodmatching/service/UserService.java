package com.foodmatching.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.foodmatching.model.User;

public interface UserService extends UserDetailsService{
	Collection<GrantedAuthority> getAuthorities(String nickName);
	public User readUser(String nickName);
    public void createUser(User user);
    public void deleteUser(User user);
    public PasswordEncoder passwordEncoder();
}
