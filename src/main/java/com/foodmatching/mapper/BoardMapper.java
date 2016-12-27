package com.foodmatching.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.User;

@Mapper
public interface BoardMapper {
	// 게시글을 분해하여 올리기
	public void insertBoard(User user);
	public void deleteBoard(User user);

}
