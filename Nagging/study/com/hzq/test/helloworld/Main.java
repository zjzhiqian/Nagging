package com.hzq.test.helloworld;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.druid.pool.DruidDataSource;
import com.hzq.common.util.SpringContextUtils;
import com.hzq.test.dao.UserMapper;
import com.hzq.test.entity.User;
import com.hzq.test.service.UserService;

public class Main {
	public static void main(String[] args) throws SQLException {
		ClassPathXmlApplicationContext ctx=new ClassPathXmlApplicationContext("spring.xml");
		UserService userMapper=(UserService) ctx.getBean("userServiceImpl");
		System.out.println(userMapper);
//		userMapper.add();
//		User user = new User(-1, "tom", new Date(), 1234);
//		userMapper.save(user);
	
	}
}
