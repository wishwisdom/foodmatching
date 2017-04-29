package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;

@Mapper
public interface UserMapper {
	//@Select("SELECT * FROM users WHERE nickname=#{nickname}")
	public User findUserByEmail(String email);
	public User findByNickName(String nickName);
	public User findUserById(int id);
	public User findIdAndPassword(User user);
	public List<String> readAuthority(int id);
	public int insertUser(UserForm user);
	public int deleteUser(User user);
}
