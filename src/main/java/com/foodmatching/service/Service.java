package com.foodmatching.service;

import java.util.List;

public interface Service<T> {
	public T find(String id);
	//public List<T> findAll(List<Integer> idList);
    public int save(T t);
    public int saveAll(List<T> list);
    public int delete(T t);
    public int deleteAll(List<T> list);
}
