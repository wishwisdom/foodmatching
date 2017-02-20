package com.foodmatching.service;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

	//Collection<GrantedAuthority> getAuthorities(String nickName);
	
public interface UserService extends Service<User>{
	
    public User findUserByIdAndPassword(User user);
    public User readUserByEmail(String email);
	public User getUserById(int email);
    public void createUser(UserForm user);
    public int save(UserForm form);
}
