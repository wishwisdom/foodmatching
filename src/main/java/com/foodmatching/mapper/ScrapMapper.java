package com.foodmatching.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Scrap;

@Mapper
public interface ScrapMapper {
	int save(Scrap scrap);
	int delete(Scrap scrap);
	Scrap find(Scrap scrap);
	int count(Scrap scrap);
}
