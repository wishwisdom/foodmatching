package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Reply;

@Mapper
public interface ReplyMapper {
	int save(Reply f);
	
	Reply find(int id);
	
	List<Reply> findReply(int id);
	List<Reply> findChildren(int parentId);
	
	void delete(int id);
	
	void update(Reply f, int id);
}
