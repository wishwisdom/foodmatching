package com.foodmatching.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

@Mapper
public interface UserMapper extends CommonMapper<User>{
	//@Select("SELECT * FROM users WHERE nickname=#{nickname}")
	//public User findUserByEmail(String email);
	User findByNickName(String nickName);
	User findById(int id);
	Set<String> findAutorities(String id);
	int saveAuthority(String id, String role);
	int saveForm(UserForm user);
	
}
