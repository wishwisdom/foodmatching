package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.foodmatching.model.Board;
import com.foodmatching.model.BoardDetail;

@Mapper
public interface BoardMapper {
	// 게시글을 분해하여 올리기
	public int save(Board board);
	public int delete(Board board);
	
	public Board find(int id);
	public BoardDetail findById(int id);
	public List<Board> findAll(@Param("startNum") int startNum, @Param("offset") int offset);
	
	public int updateLike(int boardId);
	
	public int countTotalRow();

}
