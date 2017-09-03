package com.foodmatching.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Like;

@Mapper
public interface LikeMapper extends CommonMapper<Like>{
	int countAll(int id);
	
}
