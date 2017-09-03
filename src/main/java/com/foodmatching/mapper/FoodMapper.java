package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.foodmatching.model.Food;

@Mapper
public interface FoodMapper extends CommonMapper<Food>{
	
	// Get a food image
	List<Food> find(int id);
	
	// Get food images from food's id
	List<Food> findAll(List<Integer> foodList);
	
	String findByFoodName(@Param("boardId") Integer boardId, @Param("foodName") String foodName);
	
	void update(Food f, int id);
	
	
}
