package com.foodmatching.model;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board {
	private final static Logger logger = LoggerFactory.getLogger(Board.class.getName());
	private int id;
	private String summary;
	private String owner;

	private Timestamp createdDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Return String whitch is diff times between current and created time
	 *  If diff time is so great number, the calucation is not corrected.
	 *   
	 * @return difference between current and created time.
	 */
	public String getTime(){
		String strCreatedTime = "";
		long diff = System.currentTimeMillis()-this.createdDate.getTime();
		 
		
		int days =(int)((diff/(1000*60*60*24)));
				
		if(days > 0){
			strCreatedTime = days +"일 이상";
		}else{
			int hours = (int)((diff/(1000*60*60)));
			
			if(hours > 0){
				strCreatedTime = hours +"시간 이상";
			}else{
				int minutes = (int)((diff/(1000*60)));
				if(minutes > 0){
					strCreatedTime = minutes + "분 이상";
				}else{
					strCreatedTime = "방금 전";
				}
			}
		}
					
			
		
		
		return strCreatedTime;
	}

	public Timestamp getCreatedDate() {
		Long time = System.currentTimeMillis() - createdDate.getTime();

		return new Timestamp(time / (1000 * 60 * 60 * 24));
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}