/**
 * @(#)Junit.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年2月25日 huangzhiqian 创建版本
 */
package com.hzq.test.springJunit;

import java.util.Date;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hzq.common.util.SaltGenerator;
import com.hzq.common.util.SpringContextUtils;
import com.hzq.system.constant.Constant;
import com.hzq.system.entity.SysUser;
import com.hzq.system.service.SysUserService;

/**
 * 
 * 
 * @author huangzhiqian
 */
@RunWith(SpringJUnit4ClassRunner.class) //用于配置spring中测试的环境 
@ContextConfiguration(locations={"classpath:applicationContext-spring.xml","classpath:applicationContext-shiro.xml"}) //用于指定配置文件所在的位置 
//@ContextConfiguration(locations={"classpath:ttttt.xml","classpath:springmvc.xml","classpath:applicationContext-shiro.xml"}) //用于指定配置文件所在的位置 
public class Junit {
	@Transactional
	@Test
	public void tt(){
		
		SysUserService ss=(SysUserService)SpringContextUtils.getBean("sysUserServiceImpl");
		SysUser user = new SysUser();
		
		user.setUsername("8080833308022");
		user.setAddtime(new Date());
		user.setAdduserid("1" + "");
		String salt = SaltGenerator.getSalt(Constant.SALT_LENGTH_HALF);
		SimpleHash hash = new SimpleHash(Constant.ENCRYPT_TYPE, "123456", salt, Constant.ENCRYPT_TIMES);
		String psw = hash.toHex();
		user.setPassword(psw);
		user.setSalt(salt);
		
		ss.addUser(user);
		
	}
	
	
}

