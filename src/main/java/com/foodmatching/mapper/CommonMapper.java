package com.foodmatching.mapper;

import java.util.List;

public interface CommonMapper<T> {
	int save(T t);
	int delete(T t);
	int delete(Integer id);
	T find(T t);
	T find(Integer id);
	List<T> findAll(int id);
	int countAll(T t);
	int deleteAll();
}
