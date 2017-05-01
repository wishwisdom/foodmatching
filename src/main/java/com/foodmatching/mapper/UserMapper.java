package com.foodmatching.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

@Mapper
public interface UserMapper {
	//@Select("SELECT * FROM users WHERE nickname=#{nickname}")
	public User findUserByEmail(String email);
	public User findByNickName(String nickName);
	public User findUserById(int id);
	public User findIdAndPassword(User user);
	public Set<String> findAutorities(String id);
	
	public int saveAuthority(int id, String role);
	public int insertUser(UserForm user);
	public int deleteUser(User user);
}
