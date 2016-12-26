package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.User;

@Mapper
public interface UserMapper {
	//@Select("SELECT * FROM users WHERE nickname=#{nickname}")
	public User getUser(String nickName);
	public List<String> readAuthority(int id);
	public void insertUser(User user);
	public void deleteUser(User user);
}
