package com.foodmatching.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.CustomUser;
import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;
import com.foodmatching.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userMapper.findByEmail(email);
		
		if(user == null){
			throw new UsernameNotFoundException(email);
		}
		CustomUser customUser = new CustomUser(user);
		
		logger.info("loadUserByUsername"+customUser.getUserEmail());
		
		return customUser;
	}
	@Override
	public Collection<GrantedAuthority> getAuthorities(String email) {
		User user = userMapper.findByEmail(email);
		
		List<String> string_authorities = userMapper.readAuthority(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String authority : string_authorities) {
             authorities.add(new SimpleGrantedAuthority(authority));
        }
        return authorities;
	}
	
	@Override
	public User findUserByIdAndPassword(User user) {
		// TODO Auto-generated method stub
		
		
		return userMapper.findIdAndPassword(user);
	}

	

	@Override
	public User find(String id) {
		// TODO Auto-generated method stub
		return userMapper.findByEmail(id);
	}

	@Override
	public List<User> findAll(List<Integer> idList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(UserForm form) {
		
		return userMapper.insertUser(form);
	}

	@Override
	public int saveAll(List<User> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(User t) {
		// TODO Auto-generated method stub
		return userMapper.deleteUser(t);
	}

	@Override
	public int deleteAll(List<User> list) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// Update user
	@Override
	public int save(User t) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
