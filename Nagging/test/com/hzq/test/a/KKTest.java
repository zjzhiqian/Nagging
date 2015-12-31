/**
 * @(#)KKTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月31日 huangzhiqian 创建版本
 */
package com.hzq.test.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hzq.common.util.JsonUtil;
import com.hzq.system.entity.SysUser;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class KKTest {
	
	
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(5);
		for(int i = 0 ; i< 100000;i++){
			service.execute(new testTask2());
		}
		service.shutdown();
	}
	
	
}


class testTask2 implements  Runnable {

	@Override
	public void run() {
		SysUser user = new SysUser();
		user.setId(3);
		System.out.println(JsonUtil.obj2json(user));
	}
}
