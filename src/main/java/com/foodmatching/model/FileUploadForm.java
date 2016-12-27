package com.foodmatching.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

// Test 용도로 만든 
public class FileUploadForm {

	private String firstFoodName;

	private String secondFoodName;
	
	// 업로드 파일
	private List<MultipartFile> files;

	private String evalTaste;
	private String tags;
	private String property;

	public String getFirstFoodName() {
		return firstFoodName;
	}

	public void setFirstFoodName(String firstFoodName) {
		this.firstFoodName = firstFoodName;
	}

	public String getSecondFoodName() {
		return secondFoodName;
	}

	public void setSecondFoodName(String secondFoodName) {
		this.secondFoodName = secondFoodName;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getEvalTaste() {
		return evalTaste;
	}

	public void setEvalTaste(String evalTaste) {
		this.evalTaste = evalTaste;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
