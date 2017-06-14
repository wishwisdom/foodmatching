package com.foodmatching.mapper;

import java.util.List;

public interface CommonMapper<T> {
	int save(T t);
	int delete(T t);
	T find(T t);
	List<T> findAll(int id);
	int countAll(T t);
}
