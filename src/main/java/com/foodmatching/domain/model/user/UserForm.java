package com.foodmatching.domain.model.user;

import com.foodmatching.domain.model.user.User;
import org.springframework.web.multipart.MultipartFile;

public class UserForm extends User {
	
	private String repassword;
	
	
	private MultipartFile pictureFile;
	
	
	public MultipartFile getPictureFile() {
		return pictureFile;
	}
	public void setPictureFile(MultipartFile pictureFile) {
		this.pictureFile = pictureFile;
		//this.setPicture(pictureFile.getName());
	}
	
	
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	
	
	
}
