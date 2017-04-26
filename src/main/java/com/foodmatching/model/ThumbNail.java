package com.foodmatching.model;

import java.util.List;

/**
 * ThumbNail for a board including board and food pictures
 * 
 * 
 */
public class ThumbNail{
	
	public ThumbNail(Board board){
		this.board = board;
	}
	
	Board board;
	List<Food> food;

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	public List<Food> getFood() {
		return food;
	}

	public void setFood(List<Food> food) {
		this.food = food;
	}


}
