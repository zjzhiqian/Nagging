/**
 * @(#)DDTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月31日 huangzhiqian 创建版本
 */
package com.hzq.store.study.code.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * ExecutorService  isTerminated 方法测试
 * @author huangzhiqian
 */
public class ThreadPoolTerminateTest {
	
	
	
	
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(5);
		service.execute(new testTask());
		service.execute(new testTask());
		service.execute(new testTask());
		service.execute(new testTask());
		service.shutdown();
		
		while(true){
			System.out.println(service.isShutdown());
			try {
				TimeUnit.MILLISECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(service.isTerminated()){
				System.out.println("end");
				break;
			}
		}
	}
}

class testTask implements  Runnable {

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("122");
		
	}
}