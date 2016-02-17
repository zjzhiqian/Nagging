/**
 * @(#)CD.java
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

import java.util.Arrays;
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
public class CD {
		public static void main(String[] args) throws InterruptedException {
//			ExecutorService service = Executors.newFixedThreadPool(3);
//			Future<String> fu = service.submit(new mTask());
//			TimeUnit.SECONDS.sleep(1);
//			System.out.println(fu.isDone());
//			fu.cancel(true);
//			service.shutdown();
//			TimeUnit.SECONDS.sleep(3);
//			System.out.println(fu.isDone());
			System.out.println(Arrays.toString("C_123".split("C_")));
		}
		
		
		static class mTask implements Callable<String>{

			@Override
			public String call() throws Exception {
				System.out.println("1");
				TimeUnit.SECONDS.sleep(3);
				System.out.println("2");
				return "Result";
			}
			
			
		}
}

