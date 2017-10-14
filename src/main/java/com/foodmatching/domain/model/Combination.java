package com.foodmatching.domain.model;

public class Combination {
	private int id;
    private int boardId;
    private int foodId;
    private String foodName;
    private String similaryFood;
    
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFoodId() {
		return foodId;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getSimilaryFood() {
		return similaryFood;
	}
	public void setSimilaryFood(String similaryFood) {
		this.similaryFood = similaryFood;
	}
	
}
