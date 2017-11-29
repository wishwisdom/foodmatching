package com.foodmatching.domain.model.board;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {
//	logger.info("multipartfile size : {}",req.getParameter("foodpic1name"));
//	logger.info("file : {}",msr.getFiles("foodpic").size());
//	logger.info("foodtaste1 :" +req.getParameter("foodtaste1"));
//	logger.info("foodtaste2 :" +req.getParameter("foodtaste2"));
//	logger.info("foodname1 : {}",req.getParameter("foodname1"));
//	logger.info("foodname2 : {}",req.getParameter("foodname2"));
//	logger.info("summary : {}",req.getParameter("summary"));
//	logger.info("tag : {}",req.getParameter("tag"));
	MultipartFile[] foodpic;
//	List<String> tastes1;
//	List<String> tastes2;
//	List<String> foodImageNames;
//	String[] foodNames;
//	String summary;
//	List<String> tags;
	

}
