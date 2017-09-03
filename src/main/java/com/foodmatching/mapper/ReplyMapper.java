package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Reply;

@Mapper
public interface ReplyMapper extends CommonMapper<Reply>{
	List<Reply> findReply(int id);
	List<Reply> findChildren(int parentId);
	void update(Reply f, int id);
}
