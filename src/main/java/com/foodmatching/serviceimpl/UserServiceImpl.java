package com.foodmatching.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
		User user = userMapper.getUser(nickName);
		CustomUser customUser = new CustomUser();
		customUser.setUser(user);
		
		return customUser;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities(String nickName) {
		User user = userMapper.getUser(nickName);
		
		List<String> string_authorities = userMapper.readAuthority(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String authority : string_authorities) {
             authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
	}

	@Override
	public User readUser(String nickName) {
		// TODO Auto-generated method stub
		return null;
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
	public PasswordEncoder passwordEncoder() {
		// TODO Auto-generated method stub
		return null;
	}

}
