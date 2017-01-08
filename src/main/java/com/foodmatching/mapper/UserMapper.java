package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

@Mapper
public interface UserMapper {
	//@Select("SELECT * FROM users WHERE nickname=#{nickname}")
	public User findByEmail(String email);
	public User findByNickName(String nickName);
	public User findIdAndPassword(User user);
	public List<String> readAuthority(int id);
	public int insertUser(UserForm userForm);
	public int deleteUser(User user);
}
