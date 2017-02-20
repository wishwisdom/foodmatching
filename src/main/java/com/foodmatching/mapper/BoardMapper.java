package com.foodmatching.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Board;

@Mapper
public interface BoardMapper {
	// 게시글을 분해하여 올리기
	public void insertBoard(Board board);
	public void deleteBoard(Board board);
	
	public Board findBoard(int id);

}
