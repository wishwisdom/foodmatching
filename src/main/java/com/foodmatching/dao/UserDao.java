package com.foodmatching.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

	public String getCurrentDateTime() {
		return sqlSession.selectOne("com.foodmatching.mapper.UserMapper.getCurrentDateTime");
	}
}
