package com.foodmatching.model;

public class Scrap {
	int id;
	String email;
	
	public Scrap(){	
	}
	
	public Scrap(int id, String email){
		this.id=id;
		this.email=email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}