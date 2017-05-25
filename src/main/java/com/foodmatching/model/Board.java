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
		
		int minutes =(int)((diff/(1000*60)));
		
		int years = (int)(minutes/(60*24*365));
		if(years > 0 ){
			strCreatedTime = years + " 년 ";
		}else{
			int month = (int)(minutes/(60*24*(365/12)));
			if(month > 0){
				strCreatedTime = month + "달 전";
			}else{
				int days = (int)(minutes/(60*24));
				if(days > 0){
					strCreatedTime += days +"일 전";
				}else{
					int hours = (int)(minutes/60);
					
					if(hours > 0){
						strCreatedTime = hours +"시간 전";
					}else{
						if(minutes > 0){
							strCreatedTime = minutes + "분 전";
						}else{
							strCreatedTime = "방금 전";
						}
					}
				}
			}
		}
					
			
		
		
		return strCreatedTime;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

}