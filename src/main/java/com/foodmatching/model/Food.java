package com.foodmatching.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Food {
	private int foodId;
	private int boardId;
	private String foodName;
	private String foodImage;
	private String location;
}
