package com.foodmatching.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

public interface UserService extends Service<User>{
	Collection<GrantedAuthority> getAuthorities(String nickName);
    
    public User findUserByIdAndPassword(User user);
    
    public int save(UserForm form);
}
