package com.foodmatching.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

	//Collection<GrantedAuthority> getAuthorities(String nickName);
	
public interface UserService extends Service<User>{
	Collection<GrantedAuthority> getAuthorities(String nickName);
    
    public User findUserByIdAndPassword(User user);
    public User readUserByEmail(String email);
	public User getUserById(int email);
    public void createUser(User user);
    public void deleteUser(User user);
    public int save(UserForm form);
}
