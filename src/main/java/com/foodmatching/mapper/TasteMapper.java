package com.foodmatching.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.foodmatching.model.Taste;

@Mapper
public interface TasteMapper {
	int save(Taste taste);
	int delete(Taste taste);
	Taste find(Taste taste);
	List<Taste> findAll();
	int countAll(int id);
}
