package com.foodmatching;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.foodmatching.domain.model.Scrap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FoodmatchingApplicationTests {
//	@Autowired
//	private ScrapMapper scrapMapper;

	

	@Test
	public void saveTest() {
//		scrapMapper.deleteAll();
//
//		Scrap s = new Scrap();
//
//		s.setEmail("test");
//		s.setId(1);
//
//		Integer result = scrapMapper.save(s);
//
//		Scrap s2 = scrapMapper.find(s);
//
//		assertEquals(Integer.valueOf("1"), result);
//
//		assertTrue(s.getId()==s2.getId());
//		assertTrue(s.getEmail()==s2.getEmail());
//
//		scrapMapper.deleteAll();
	}
}
