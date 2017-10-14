package com.foodmatching.domain.model.board;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {
//	logger.info("multipartfile size : {}",req.getParameter("foodpic1name"));
//	logger.info("file : {}",msr.getFiles("foodpic").size());
//	logger.info("foodtaste1 :" +req.getParameter("foodtaste1"));
//	logger.info("foodtaste2 :" +req.getParameter("foodtaste2"));
//	logger.info("foodname1 : {}",req.getParameter("foodname1"));
//	logger.info("foodname2 : {}",req.getParameter("foodname2"));
//	logger.info("summary : {}",req.getParameter("summary"));
//	logger.info("tag : {}",req.getParameter("tag"));
	List<MultipartFile> pictures;
	List<String> tastes1;
	List<String> tastes2;
	List<String> foodImageNames;
	String[] foodNames;
	String summary;
	List<String> tags;
	
	public BoardForm(){
		
	}
	public BoardForm(List<MultipartFile> pictures, List<String> tastes1, List<String> tastes2, List<String> foodImageNames, String[] foodNames, String summary, List<String> tags){
		this.pictures=pictures;
		this.tastes1=tastes1;
		this.tastes2=tastes2;
		this.foodImageNames=foodImageNames;
		this.foodNames=foodNames;
		this.summary=summary;
		this.tags=tags;
	}


	public void setPictures(List<MultipartFile> pictures) {
		this.pictures = pictures;
	}
}
