package com.foodmatching.serviceimpl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.CustomUser;
import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;
import com.foodmatching.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("loadUserByUsername : "+email);
		User user = null;
		try{
			user = this.find(email);
		}catch(Exception e){
			logger.info("loadUserByUsername\n"+e.getClass());
		}
		
		if(user == null){
			throw new UsernameNotFoundException("Login Failed");
		}
		//logger.info("ROLE : "+user.getRoles().size());
		return new CustomUser(user);
	}
	
	@Override
	public User findUserByIdAndPassword(User user) {
		// TODO Auto-generated method stub
		
		
		return userMapper.findIdAndPassword(user);
	}

	

	

	@Override

	public User find(String id) {
		// TODO Auto-generated method stub
		User user = userMapper.findUserByEmail(id);
		
		if(user != null){
			Set<String> roles = userMapper.findAutorities(id);
			logger.info("authotiry : " +roles.size());
			user.setRoles(roles);
		}
		
		return user;
	}

	

	@Override
	public int save(UserForm form) {
		String passwd = (new BCryptPasswordEncoder().encode(form.getPassword()));
		form.setPassword(passwd);
		
		// If file uploaded, then insert user
		
		
		return userMapper.insertUser(form);
	}

	@Override
	public int saveAll(List<User> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		User user = userMapper.findUserById(id);
		return user;
	}

	@Override
	public int save(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAll(List<User> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public User readUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(UserForm user) {
		// TODO Auto-generated method stub
		userMapper.insertUser(user);
	}
	
	
}
