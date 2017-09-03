package com.foodmatching.serviceimpl;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodmatching.mapper.UserMapper;
import com.foodmatching.model.CustomUser;
import com.foodmatching.model.Role;
import com.foodmatching.model.User;
import com.foodmatching.model.UserForm;
import com.foodmatching.service.UserService;
import com.foodmatching.utils.FileUtil;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserMapper userMapper;
	
	@Value("${board.img.user.path}")
	private String SAVE_PROFILE_PATH;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.info("loadUserByUsername : "+email);
		User user = null;
		try{
			user = this.find(email);
			logger.info("USER : "+user.getId());
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
	public User find(String email) {
		User user = new User();
		user.setEmail(email);
		user = userMapper.find(user);
		
		if(user != null){
			Set<String> roles = userMapper.findAutorities(email);
			logger.info("authotiry : " +roles.size());
			user.setRoles(roles);
		}
		
		return user;
	}

	/**
	 * Join our service
	 * @param UserForm sign up form model
	 */
	
	@Transactional
	@Override
	public int save(UserForm form) {
		String passwd = (new BCryptPasswordEncoder().encode(form.getPassword()));
		form.setPassword(passwd);
		logger.info("SAVE PATH :"+ SAVE_PROFILE_PATH);
		// If file uploaded, then insert user
		String imageName = FileUtil.extractDestinationFileName(form.getPictureFile(),form.getPictureFile().getOriginalFilename());
		//FileUtil.saveFile(SAVE_PROFILE_PATH, imageName, form.getPictureFile());
		form.setPicture(form.getPictureFile().getName());
		form.setPictureName(imageName);
		
		userMapper.saveAuthority(form.getEmail(), Role.ROLE_USER.name());
		
		logger.info("Save : " + form.getEmail());
		
		return userMapper.saveForm(form);
	}

	@Override
	public int saveAll(List<User> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		User user = userMapper.findById(id);
		return user;
	}

	// Update
	@Override
	public int save(User t) {
		// TODO Auto-generated method stub
		return userMapper.save(t);
	}

	@Override
	public int delete(User t) {
		// TODO Auto-generated method stub
		return userMapper.delete(t);
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
		userMapper.save(user);
	}
	
	
}
