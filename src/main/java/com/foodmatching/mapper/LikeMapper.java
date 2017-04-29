package com.foodmatching.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Like;

@Mapper
public interface LikeMapper {
	int save(Like like);
	int delete(Like like);
	Like find(Like like);
	int countAll(int id);
}
