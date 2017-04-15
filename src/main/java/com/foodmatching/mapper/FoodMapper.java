package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Food;

@Mapper
public interface FoodMapper {
	
	int save(Food f);
	
	// Get a food image
	Food find(int id);
	
	// Get food images from food's id
	List<Food> findAll(List<Integer> foodList);
	
	// Delete a food image
	void delete(int id);
	
	void update(Food f, int id);
	
}
