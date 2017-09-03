package com.foodmatching;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.foodmatching.mapper.LikeMapper;
import com.foodmatching.model.Like;

public class LikeMapperTests {
	@Autowired
	LikeMapper likeMapper;
	
	
	@Test
	public void saveTest() {
		likeMapper.deleteAll();
		Like l = new Like();
		l.setId(1);
		l.setEmail("test1@test.com");
		
		int rowNum = likeMapper.save(l);
		
		assertTrue(1==rowNum);
		
	}

}
