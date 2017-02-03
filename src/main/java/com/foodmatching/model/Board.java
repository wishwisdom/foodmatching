package com.foodmatching.model;

import javax.validation.constraints.NotNull;

public class Board {
	private Long id;
	
	@NotNull
	private String writer;
	
	private Long good;
	private String content;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Long getGood() {
		return good;
	}
	public void setGood(Long good) {
		this.good = good;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
