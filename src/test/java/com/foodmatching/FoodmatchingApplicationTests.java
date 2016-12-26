package com.foodmatching;

import javax.annotation.Resource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.foodmatching.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = "test")
public class FoodmatchingApplicationTests {
	@Autowired

    @Resource(name = "anotherService")
    private UserService userService;


    @Test
    public void aTest() {
        
    }

}
