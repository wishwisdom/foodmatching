package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.foodmatching.model.User;

@Mapper
public interface UserMapper {
	//@Select("SELECT * FROM users WHERE email=#{email} ")
	public User getUserByEmail(@Param("email") String email);
	//@Select("SELECT * FROM users WHERE id=#{id} ")
	public User getUserById(int id);
	public List<String> readAuthority(int id);
	//@Insert("INSERT into users(email,nickname,password,birth,join_day) VALUES(#{email},#{nickName},#{password},#{birth},now()) ")
	public void insertUser(User user);
	public void deleteUser(User user);
}
