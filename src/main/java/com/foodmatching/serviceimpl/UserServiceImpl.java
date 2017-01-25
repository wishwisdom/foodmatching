package com.foodmatching.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.internal.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.CustomUser;
import com.foodmatching.model.User;
import com.foodmatching.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("loadUserByUsername : "+email);
		User user = null;
		try{
			user = userMapper.getUserByEmail(email);
		}catch(Exception e){
			logger.info("loadUserByUsername\n"+e.getClass());
		}
		
		
		if(user == null){
			throw new UsernameNotFoundException("Login Failed");
		}
		
		return new CustomUser(user);
	}

	

	@Override
	public User readUserByEmail(String email) {
		// TODO Auto-generated method stub
		User user = userMapper.getUserByEmail(email);
		return user;
	}

	@Override
	public void createUser(User user) {
		String rawPassword = user.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
		
        user.setPassword(encodedPassword);
        
		userMapper.insertUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userMapper.deleteUser(user);
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		User user = userMapper.getUserById(id);
		return user;
	}



	@Override
	public Collection<GrantedAuthority> getAuthorities(String nickName) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public User readUser(String nickName) {
		// TODO Auto-generated method stub
		return null;
	}



	
}
