package com.foodmatching.domain.model;

import java.util.List;

import com.foodmatching.domain.model.board.Board;
import lombok.Getter;
import lombok.Setter;

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
	
	@Getter @Setter
	List<Food> food;

	public Board getBoard() {
		return board;
	}

	public String getTitle(){
		String title = "";
		
		int size = food.size();
		
		for(int i=0; i < size-1; i++ ){
			title += food.get(i).getFoodName()+" & ";
		}
		
		title += food.get(size-1).getFoodName();
			
		return title;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	


}
