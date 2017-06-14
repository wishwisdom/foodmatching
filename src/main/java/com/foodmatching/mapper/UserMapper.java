package com.foodmatching.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

@Mapper
public interface UserMapper extends CommonMapper<User>{
	//@Select("SELECT * FROM users WHERE nickname=#{nickname}")
	//public User findUserByEmail(String email);
	public User findByNickName(String nickName);
	public User findById(int id);
	public Set<String> findAutorities(String id);
	
	public int saveAuthority(String id, String role);
	public int saveForm(UserForm user);
	
}
