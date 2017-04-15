package com.foodmatching.model;

import java.util.List;

/*
 게시물의 내용을 보여주는 모델.
 추후 댓글도 같이 넣을 예
 */
public class BoardDetail extends Board{
	
	List<Food> foodList;
	List<Comment> commentList;
	
	public List<Comment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	public List<Food> getFoodList() {
		return foodList;
	}
	public void setFoodList(List<Food> foodList) {
		this.foodList = foodList;
	}
	

}
