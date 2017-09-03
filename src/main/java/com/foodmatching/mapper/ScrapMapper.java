package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Scrap;

@Mapper
public interface ScrapMapper extends CommonMapper<Scrap>{
	
	List<Scrap> findAll(Scrap scrap,int start, int end);
	int deleteAll();
}
