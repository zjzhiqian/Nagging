package com.hzq.test.study;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hzq.test.dao.UserMapper;
import com.hzq.test.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml") //加载配置
public class SMTest {

	@Autowired
	private UserMapper mapper;
	
	
	@Test
	public void testAdd() {
		User user = new User(-1, "tom", new Date(), 1234);
		mapper.BaseSave(user);
		
		int id = user.getId();
		System.out.println(id);
	}
}
