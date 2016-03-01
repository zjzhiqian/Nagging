/**
 * @(#)InterruptTest.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016年2月29日 huangzhiqian 创建版本
 */
package com.hzq.store.study.code.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class InterruptTest {
		
	
	private static class mtask implements Callable<String> {

		@SuppressWarnings("unused")
		@Override
		public String call() {
			int j = 0 ;
			while("1".equals("1")){
				try {
					System.out.println(" in while  isInterrupt:"+Thread.currentThread().isInterrupted());
					TimeUnit.SECONDS.sleep(3);
					
					Long time1= System.currentTimeMillis();
					for (int i = 0 ;i<1000000000;i++){
						for (int k=0;k<1000000000;k++){
							j++;j--;j++;j--;
						}
					}
					System.err.println(System.currentTimeMillis()-time1);
					
				} catch (InterruptedException e) {
					System.out.println("interrupt exit");
					System.exit(0);
				}
				System.out.println("finish one");
			}
			return "finish";
		}
	}
	
	
	public static void main(String[] args) {
		long time = 3001;
		
		ExecutorService service=Executors.newFixedThreadPool(1);
		Future<String> rs=service.submit(new mtask());
		try {
			TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		rs.cancel(true);
		
		service.shutdown();
	}
	
	
	
}

